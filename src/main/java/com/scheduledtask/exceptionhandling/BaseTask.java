package com.scheduledtask.exceptionhandling;

public abstract class BaseTask {

	public void runTask(Runnable task) {
		task.run();
	}

	public abstract boolean isEnabled();

}