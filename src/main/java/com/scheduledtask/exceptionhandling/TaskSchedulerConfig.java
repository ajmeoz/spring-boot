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
		private static final String FRAME_START_STRING = "at com.scheduledtask";
		private static final String TASK_NAME_REGEXP = "[A-Z_$][a-zA-Z0-9_$]*Task";

		/**
		 * Ošetřuje chybu, ke které došlo v průběhu provádění úlohy
		 */
		@Override
		public void handleError(Throwable t) {

			String[] frames = ExceptionUtils.getStackFrames(t);
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
						logger.error("An error occurred in " + taskName + ", " + ExceptionUtils.getMessage(t));
					} else {
						logger.error("Couldn't get task name. The following error occurred: " + ExceptionUtils.getMessage(t));
					}
				}
			}
		}
	}
}
