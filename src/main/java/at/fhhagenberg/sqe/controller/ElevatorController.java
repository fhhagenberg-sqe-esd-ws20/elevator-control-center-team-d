package at.fhhagenberg.sqe.controller;

import java.rmi.RemoteException;

import at.fhhagenberg.sqe.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ElevatorController {

	private Elevator elevatorModel;
	
	public StringProperty DoorStatus = new SimpleStringProperty();
	
	// TODO: Refactoring -> new in constructor !!??
	
	public ElevatorController() {
		elevatorModel = new Elevator(new DummyElevator());
		elevatorModel.setElevatorNumber(1);		
		
		updateModelValues();
	}
	
	// TODO: use Timer for updating values
	private void updateModelValues() {
		try {
			DoorStatus.setValue(String.valueOf(elevatorModel.getElevatorDoorStatus()));			
			Thread.sleep(100);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
}
