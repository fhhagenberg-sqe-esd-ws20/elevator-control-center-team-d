package at.fhhagenberg.sqe.gui;

import java.util.Timer;

import at.fhhagenberg.sqe.controller.AlarmManager;
import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.model.Building;
import at.fhhagenberg.sqe.model.DummyElevator;
import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.Floor;
import at.fhhagenberg.sqe.model.IElevator;
import at.fhhagenberg.sqe.model.IWrapElevator;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.BooleanStringConverter;

// TODO: implementation -> now only prototype class
public class App extends Application {
	
	private final long updateDataIntervallMsec = 100;
	
	// DummyElevator only for debugging
	private IWrapElevator testRemoteElevator = new DummyElevator();
	private AlarmManager appAlarmManager = new AlarmManager();
	private Elevator modelElevator = new Elevator(testRemoteElevator);
	private Building modelBuilding = new Building(testRemoteElevator, appAlarmManager);
	private ElevatorController elevatorCtrl = new ElevatorController(testRemoteElevator, modelElevator, modelBuilding, appAlarmManager);
	private Timer updateDataTimer = new Timer();
	
	private void initElevatorController() {
		updateDataTimer.scheduleAtFixedRate(elevatorCtrl, 0, updateDataIntervallMsec);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		elevatorCtrl.setCurrViewElevatorNumber(1);
		initElevatorController();
		
		//ElevatorView view = new ElevatorView(elevatorCtrl, primaryStage);
		
		MainView view = new MainView(elevatorCtrl, primaryStage); 
			
		
	
//		
//		/*
//		var label = new Label("Initial GUI");
//		
//		elevatorCtrl.elevatorModel.DoorStatus.addListener(new ChangeListener<Number>() {
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				// TODO Auto-generated method stub
//				if (newValue.equals(IElevator.ELEVATOR_DOORS_OPEN)) {
//					label.setText("Open");
//				} else if (newValue.equals(IElevator.ELEVATOR_DOORS_CLOSED)) {
//					label.setText("Closed");
//				}
//			}
//		});
//		
//		// Test change listener
//		elevatorCtrl.elevatorModel.DoorStatus.setValue(IElevator.ELEVATOR_DOORS_OPEN);
//		*/
//		
//		
//		/*
//		// Test list of floors
//		ListView<String> testFloorList = new ListView<String>();
//		
//		for (int i = 0; i < elevatorCtrl.buildingModel.FloorList.size(); i++) {
//			testFloorList.getItems().add("Floor " + String.valueOf(i));
//		}
//		*/	
//		
//		// Test binding on floor buttons
//		ListView<Floor> testFloorList = new ListView<Floor>(elevatorCtrl.buildingModel.getObservableFloorList());
//		
//		testFloorList.setCellFactory(param -> new ListCell<Floor>() {
//			@Override
//			protected void updateItem(Floor floor, boolean empty) {
//				super.updateItem(floor, empty);
//								
//				if (empty || floor == null) {
//					setText(null);
//				} else {
//					if (floor.FloorButtonDown.getValue() && floor.FloorButtonUp.getValue()) {
//						setText("up/down");
//				    } else if (floor.FloorButtonDown.getValue()) {
//						setText("down");
//					} else if (floor.FloorButtonUp.getValue()) {
//						setText("up");
//					} else {
//						setText(null);
//					}
//				}				
//			}
//		});
//		
//		/*
//		// Test button switch automatic manual
//		Button selOperationBtn = new Button("Manual");
//		
//		selOperationBtn.setOnAction(e -> {
//			elevatorCtrl.switchOperationStatus();
//			selOperationBtn.setText(elevatorCtrl.getOperationStatus().toString());
//		});
//		*/
//		
//		
//		var layout = new BorderPane(testFloorList);
//		
//		var scene = new Scene(layout, 640, 480);
//		
//		// Set scene
//		primaryStage.setScene(scene);
//		primaryStage.show();
//		
		// On cancel button cancel update timer
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {			
			@Override
			public void handle(WindowEvent event) {
				updateDataTimer.cancel();
			}
		});
	}

	public static void main(String[] args) {
		launch();
	}

}
