package com.scheduledtask.exceptionhandling;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask1 {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledTask1.class);
	private static final String PATTERN1 = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final String PATTERN2 = "abc";
	private static final Random RANDOM = new Random();

	private static int n = 1;

	@Scheduled(fixedDelay = 1000)
	public void printCurrentTime() throws ScheduledTaskException {
		try {
			int i = RANDOM.ints(1, 3).findFirst().getAsInt();
			String pattern = i % 2 == 0 ? PATTERN2 : PATTERN1;
			LocalDateTime now = LocalDateTime.now();
			logger.info(n + ": " + now.format(DateTimeFormatter.ofPattern(pattern)));
			n++;
		} catch (Exception e) {
			throw new ScheduledTaskException("Caught exception in ScheduledTask1", e);
		}
	}
}
