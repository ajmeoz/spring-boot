package com.scheduledtask.exceptionhandling;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.ErrorHandler;

@Configuration
public class ApplicationConfiguration implements TaskSchedulerCustomizer {

	@Override
	public void customize(ThreadPoolTaskScheduler taskScheduler) {
		taskScheduler.setErrorHandler(new CustomErrorHandler());
	}

	private static class CustomErrorHandler implements ErrorHandler {

		private static final Logger logger = LoggerFactory.getLogger(CustomErrorHandler.class);

		@Override
		public void handleError(Throwable t) {
			logger.error(ExceptionUtils.getMessage(t) + ", " + ExceptionUtils.getRootCauseMessage(t));
		}
		
	}
	
}
