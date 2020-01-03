package com.scheduledtask.exceptionhandling;

import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SecondTask extends PrinterBaseTask {

	@Scheduled(fixedDelay = 1000)
	public void scheduled() throws ScheduledTaskException {
		if (!isEnabled()) {
			return;
		}
		printCurrentTime();
	}

	public SecondTask() {
		super(LoggerFactory.getLogger(SecondTask.class));
	}
}
