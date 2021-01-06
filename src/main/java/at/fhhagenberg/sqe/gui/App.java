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
		
		elevatorCtrl.setCurrViewElevatorNumber(0);
		initElevatorController();		
		
		MainView view = new MainView(elevatorCtrl, primaryStage); 			
	
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
