package at.fhhagenberg.sqe.controller;

import java.rmi.RemoteException;

import at.fhhagenberg.sqe.model.*;

public class ElevatorController {

	public Building buildingModel;
	public Elevator elevatorModel;
	
	// TODO: Refactoring -> new in constructor !!??
	
	public ElevatorController() {
		IWrapElevator testRemoteElevator = new DummyElevator();
		
		buildingModel = new Building(testRemoteElevator);
		elevatorModel = new Elevator(testRemoteElevator);
		
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
			
			for (var floor : buildingModel.FloorList) {
				floor.updateFloorButtonDown();
				floor.updateFloorButtonUp();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
}
