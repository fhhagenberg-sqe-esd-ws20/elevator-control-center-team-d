package at.fhhagenberg.sqe.gui;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Timer;

import at.fhhagenberg.sqe.controller.AlarmManager;
import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.model.Building;
import at.fhhagenberg.sqe.model.DummyElevator;
import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.ElevatorAdapter;
import at.fhhagenberg.sqe.model.IWrapElevator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sqelevator.IElevator;

public class App extends Application {
	
	private final long updateDataIntervallMsec = 100;
	private final boolean useElevatorSim = false;
	
	private IElevator controller;	
	private IWrapElevator remoteElevator;
	private AlarmManager appAlarmManager;
	private Elevator modelElevator;
	private Building modelBuilding;
	private ElevatorController elevatorCtrl;
	private Timer updateDataTimer;
	
	private void initElevatorController() {
		updateDataTimer.scheduleAtFixedRate(elevatorCtrl, 0, updateDataIntervallMsec);
	}
	
	// TODO: Test this code also!!
	private void showConnectionAlert(String alertHeaderMsg, String alertContentMsg) {
		Alert exeptionAlert = new Alert(AlertType.ERROR);
		exeptionAlert.setTitle("Elevator Connect Error");
		exeptionAlert.setHeaderText(alertHeaderMsg);
		exeptionAlert.setContentText(alertContentMsg);
		exeptionAlert.showAndWait();
	}
	
	private boolean connectToElevator() {
		if (useElevatorSim) {
			try {
				controller = (IElevator)Naming.lookup("rmi://localhost/ElevatorSim");
			} catch (NotBoundException e) {
				showConnectionAlert("Connection binding error!" ,e.getMessage());
				return false;
			} catch (MalformedURLException e) {
				showConnectionAlert("Malformed URL used for connection!" ,e.getMessage());
				return false;
			} catch (RemoteException e) {
				showConnectionAlert("Remote error!" ,e.getMessage());
				return false;
			}
			
			remoteElevator = new ElevatorAdapter(controller);
		} else {
			// DummyElevator only for debugging
			remoteElevator = new DummyElevator();
		}	
		
		return true;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		if(!connectToElevator()) {
			return;
		}
		
		appAlarmManager = new AlarmManager();
		modelElevator = new Elevator(remoteElevator);
		modelBuilding = new Building(remoteElevator, appAlarmManager);
		elevatorCtrl = new ElevatorController(remoteElevator, modelElevator, modelBuilding, appAlarmManager);
		updateDataTimer = new Timer();		
		
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
