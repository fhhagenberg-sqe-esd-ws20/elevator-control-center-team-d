package at.fhhagenberg.sqe.gui;

import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.model.IElevator;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
	
	private ElevatorController elevatorCtrl = new ElevatorController();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		var label = new Label("Initial GUI");
		
		elevatorCtrl.DoorStatus.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				if (newValue.equals(IElevator.ELEVATOR_DOORS_OPEN)) {
					label.setText("Open");
				} else if (newValue.equals(IElevator.ELEVATOR_DOORS_CLOSED)) {
					label.setText("Closed");
				}
			}
		});
		
		// Test change listener
		elevatorCtrl.DoorStatus.setValue(IElevator.ELEVATOR_DOORS_OPEN);
		
		var layout = new BorderPane(label);
		
		var scene = new Scene(layout, 640, 480);
		
		// Set scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}
