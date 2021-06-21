package com.trc.task;

import javafx.concurrent.Task;

public class CounterTask extends Task<Void> {
	private int stepSize;

	public CounterTask(int stepSize) {
		this.stepSize = stepSize;
	}
	
	@Override
	protected Void call() throws Exception {
		final int max = 1000;
		updateProgress(0, max);
		try {
			for (int i = 1; i <= max; i++) {
				updateProgress(i, max);
				Thread.sleep(stepSize);
			}
		} catch (Exception e) {
			updateMessage("Error in counting");
		}
		return null;
	}

}
