package com.scheduledtask.exceptionhandling;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.slf4j.Logger;

public class PrinterBaseTask extends BaseTask implements Runnable {
	private static final String PATTERN1 = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final String PATTERN2 = "abc";
	private static final Random RANDOM = new Random();
	private final Logger logger;
	private int n = 1;

	public PrinterBaseTask(Logger logger) {
		this.logger = logger;
	}

	public void printCurrentTime() throws ScheduledTaskException {
		runTask(this);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void run() {
		int i = RANDOM.ints(1, 3).findFirst().getAsInt();
		String pattern = i % 2 == 0 ? PATTERN2 : PATTERN1;
		LocalDateTime now = LocalDateTime.now();
		logger.info(n + ": " + now.format(DateTimeFormatter.ofPattern(pattern)));
		n++;
	}
}
