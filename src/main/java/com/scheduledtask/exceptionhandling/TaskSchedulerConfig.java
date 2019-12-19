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
 * Spring application configuration
 * Inspired by https://stackoverflow.com/questions/41041536/universal-exception-handler-for-scheduled-tasks-in-spring-boot-with-java-conf
 *
 */
@Configuration
public class TaskSchedulerConfig implements TaskSchedulerCustomizer {

	/**
	 * Exception handling customization of a TaskScheduler instance
	 */
	@Override
	public void customize(ThreadPoolTaskScheduler taskScheduler) {
		taskScheduler.setErrorHandler(new TaskSchedulerExceptionHandler());
	}

	private static class TaskSchedulerExceptionHandler implements ErrorHandler {

		private static final Logger logger = LoggerFactory.getLogger(TaskSchedulerExceptionHandler.class);

		/**
		 * Handles the error occurred during execution of a task
		 */
		@Override
		public void handleError(Throwable t) {
			logger.error(ExceptionUtils.getMessage(t) + ", " + ExceptionUtils.getRootCauseMessage(t));
		}

	}

}
