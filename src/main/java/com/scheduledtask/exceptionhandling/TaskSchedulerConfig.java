package com.scheduledtask.exceptionhandling;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.ErrorHandler;

/**
 * @author antlma
 *
 * Konfigurace plánovače úloh
 * Zdroj k ošetřování výjimek při provádění úloh ve Springu: https://stackoverflow.com/questions/41041536/universal-exception-handler-for-scheduled-tasks-in-spring-boot-with-java-conf
 *
 */
@Configuration
public class TaskSchedulerConfig implements TaskSchedulerCustomizer {

	/**
	 * Přizpůsobení ošetřování výjimek instance třídy TaskScheduler
	 */
	@Override
	public void customize(ThreadPoolTaskScheduler taskScheduler) {
		taskScheduler.setErrorHandler(new TaskSchedulerExceptionHandler());
	}

	private static class TaskSchedulerExceptionHandler implements ErrorHandler {

		private static final Logger logger = LoggerFactory.getLogger(TaskSchedulerExceptionHandler.class);
		private static final String FRAME_START_STRING = "at com.scheduledtask";    // začátek řádku, ze kterého se vezme název úlohy
		private static final String TASK_NAME_REGEXP = "[A-Z_$][a-zA-Z0-9_$]*Task"; // regulární výraz pro název úlohy

		/**
		 * Ošetřuje chybu, ke které došlo v průběhu provádění úlohy
		 */
		@Override
		public void handleError(Throwable t) {
			String[] frames = ExceptionUtils.getStackFrames(t);
			String msgBase = null;
			String msgConcrete = null;
			for (String frame : frames) {
				if (frame.trim().startsWith(FRAME_START_STRING)) {
					String[] parts = frame.split("\\.");
					String taskName = null;
					for (String part : parts) {
						if (part.matches(TASK_NAME_REGEXP)) {
							taskName = part;
							break;
						}
					}
					if (taskName != null) {
						if ("BaseTask".equals(taskName)) {
							msgBase = "Došlo k chybě v " + taskName + ", " + ExceptionUtils.getMessage(t);
						} else {
							msgConcrete = "Došlo k chybě v " + taskName + ", " + ExceptionUtils.getMessage(t);
						}
					} else {
						msgConcrete = "Nepodařilo se zjistit název úlohy. Během provádění úlohy nastala následující chyba: " + ExceptionUtils.getMessage(t);
					}
				}
			}
			if (msgConcrete != null) {
				logger.error(msgConcrete);
			} else if (msgBase != null) {
				logger.error(msgBase);
			}
			if (t instanceof Error) {
				throw new Error(t);
			}
		}
	}
}
