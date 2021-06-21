package com.trc.service;

import com.trc.task.CounterTask;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class CounterService extends Service<Void> {

	private int stepSize;

	public CounterService(int stepSize) {
		this.stepSize = stepSize;
	}

	@Override
	protected Task<Void> createTask() {
		CounterTask counter = new CounterTask(stepSize);
		return counter;
	}

	@Override
	protected void running() {
		super.running();
		System.out.println("Task is running!");
	}

	@Override
	protected void succeeded() {
		super.succeeded();
		System.out.println("Task executed successfully!");
	}

}
