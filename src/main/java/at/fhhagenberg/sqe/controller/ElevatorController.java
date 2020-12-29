package at.fhhagenberg.sqe.controller;

import java.rmi.RemoteException;

import at.fhhagenberg.sqe.model.*;

public class ElevatorController {

	public Elevator elevatorModel;
	
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
			elevatorModel.updateElevatorDoorStatus();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
}
