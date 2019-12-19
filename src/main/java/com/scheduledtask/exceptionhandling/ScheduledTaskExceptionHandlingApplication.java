package com.scheduledtask.exceptionhandling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScheduledTaskExceptionHandlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduledTaskExceptionHandlingApplication.class, args);
	}

}
