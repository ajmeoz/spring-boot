package com.scheduledtask.exceptionhandling;

public class ScheduledTaskException extends Exception {

	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	public ScheduledTaskException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
