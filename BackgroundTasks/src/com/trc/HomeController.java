package com.trc;

import java.net.URL;
import java.util.ResourceBundle;

import com.trc.service.CounterService;

import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

public class HomeController implements Initializable {
	public ProgressBar progressCounter;
	public ProgressBar progressCounter1;
	public Button btnStartProgress;
	public Button btnStopProgress;
	CounterService counterService;
	CounterService counterService1;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		counterService = new CounterService(25);
		counterService1 = new CounterService(50);
	}

	@FXML
	public void startCountingAndUpdateProgressBar(ActionEvent event) {
		System.out.println("Button Clicked");
		progressCounter.progressProperty().bind(counterService.progressProperty());
		progressCounter1.progressProperty().bind(counterService1.progressProperty());
		if (counterService.getState() == State.READY || counterService.getState() == State.CANCELLED) {
			counterService.start();
			counterService.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new ProgressBarEventHandler());
			counterService.addEventHandler(WorkerStateEvent.WORKER_STATE_CANCELLED, new ProgressBarEventHandler());
		}
		if (counterService1.getState() == State.READY || counterService1.getState() == State.CANCELLED) {
			counterService1.start();
			counterService1.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new ProgressBarEventHandler());
			counterService1.addEventHandler(WorkerStateEvent.WORKER_STATE_CANCELLED, new ProgressBarEventHandler());
		}
	}
	
	@FXML
	public void stopCountingAndResetProgressBar(ActionEvent event) {
		if (counterService.getState() == State.RUNNING) {
			counterService.cancel();
		}
		if (counterService1.getState() == State.RUNNING) {
			counterService1.cancel();
		}
	}
	
	

	class ProgressBarEventHandler implements EventHandler<Event> {

		@Override
		public void handle(Event event) {

			if (event.getSource().equals(counterService)) {
				performOperation(event, counterService);
			} else if (event.getSource().equals(counterService1)) {
				performOperation(event, counterService1);
			}

		}

		private void performOperation(Event event, CounterService counterService) {
			if (event.getEventType() == WorkerStateEvent.WORKER_STATE_SUCCEEDED) {
				System.out.println("Progress bar done!");
				counterService.reset();
				counterService.restart();
			}
			if (event.getEventType() == WorkerStateEvent.WORKER_STATE_CANCELLED) {
				System.out.println("Progress bar done!");
				counterService.reset();
				counterService.cancel();
			}
		}

	}

}
