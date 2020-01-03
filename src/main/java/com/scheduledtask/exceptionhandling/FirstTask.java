package com.scheduledtask.exceptionhandling;

import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FirstTask extends PrinterBaseTask {

	@Scheduled(fixedDelay = 1000)
	public void scheduled() throws ScheduledTaskException {
		if (!isEnabled()) {
			return;
		}
		printCurrentTime();
	}

	public FirstTask() {
		super(LoggerFactory.getLogger(FirstTask.class));
	}
}
