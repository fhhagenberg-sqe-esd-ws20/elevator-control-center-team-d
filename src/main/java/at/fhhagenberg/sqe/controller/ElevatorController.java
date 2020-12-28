package at.fhhagenberg.sqe.controller;

import java.rmi.RemoteException;

import at.fhhagenberg.sqe.model.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ElevatorController {

	private Elevator elevatorModel;
	
	public IntegerProperty DoorStatus = new SimpleIntegerProperty();
	
	// TODO: Refactoring -> new in constructor !!??
	
	public ElevatorController() {
		elevatorModel = new Elevator(new DummyElevator());
		
		try {
			elevatorModel.setElevatorNumber(1);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		updateModelValues();
	}
	
	// TODO: use Timer for updating values
	public void updateModelValues() {
		try {
			DoorStatus.setValue(elevatorModel.getElevatorDoorStatus());			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
}
