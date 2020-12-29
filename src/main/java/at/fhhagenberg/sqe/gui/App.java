package at.fhhagenberg.sqe.gui;

import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.model.Floor;
import at.fhhagenberg.sqe.model.IElevator;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.converter.BooleanStringConverter;

// TODO: implementation -> now only prototype class
public class App extends Application {
	
	private ElevatorController elevatorCtrl = new ElevatorController();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		/*
		var label = new Label("Initial GUI");
		
		elevatorCtrl.elevatorModel.DoorStatus.addListener(new ChangeListener<Number>() {
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
		elevatorCtrl.elevatorModel.DoorStatus.setValue(IElevator.ELEVATOR_DOORS_OPEN);
		*/
		
		
		/*
		// Test list of floors
		ListView<String> testFloorList = new ListView<String>();
		
		for (int i = 0; i < elevatorCtrl.buildingModel.FloorList.size(); i++) {
			testFloorList.getItems().add("Floor " + String.valueOf(i));
		}
		*/
		
		
		// Test binding on floor buttons
		ListView<Floor> testFloorList = new ListView<Floor>(elevatorCtrl.buildingModel.FloorList);
		
		testFloorList.setCellFactory(param -> new ListCell<Floor>() {
			@Override
			protected void updateItem(Floor floor, boolean empty) {
				super.updateItem(floor, empty);
								
				if (empty || floor == null) {
					setText(null);
				} else {
					if (floor.FloorButtonDown.getValue() && floor.FloorButtonUp.getValue()) {
						setText("up/down");
				    } else if (floor.FloorButtonDown.getValue()) {
						setText("down");
					} else if (floor.FloorButtonUp.getValue()) {
						setText("up");
					} else {
						setText(null);
					}
				}				
			}
		});
		
		// Test change floor button state
		elevatorCtrl.buildingModel.FloorList.get(0).FloorButtonUp.setValue(Boolean.FALSE);
		elevatorCtrl.buildingModel.FloorList.get(2).FloorButtonDown.setValue(Boolean.TRUE);
		
		
		var layout = new BorderPane(testFloorList);
		
		var scene = new Scene(layout, 640, 480);
		
		// Set scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}
