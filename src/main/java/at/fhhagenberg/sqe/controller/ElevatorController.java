/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.TimerTask;

import at.fhhagenberg.sqe.model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for the elevator controller which prepares the data for
 * the view. Update the model data and provide functions for the
 * elevator control.
 * @author Dominic Zopf
 *
 */
public class ElevatorController extends TimerTask {

	private boolean updateModelData = false;
	public enum eOperationStatus { MANUAL, AUTOMATIC };
	private eOperationStatus operationStatus;
	
	public Building buildingModel;
	public Elevator elevatorModel;
	
	// Properties for GUI binding
	public ObservableList<Boolean> ElevatorButtonList = FXCollections.observableArrayList();
	public ObservableList<Boolean> ServicesFloorList = FXCollections.observableArrayList();
	
	// TODO: Refactoring -> Interface for building and elevator,
	// use dependency injection
	public ElevatorController(IWrapElevator remoteElevator) {		
		buildingModel = new Building(remoteElevator);
		elevatorModel = new Elevator(remoteElevator);
		
		initStatusLists();
		
		operationStatus = eOperationStatus.MANUAL;
		updateModelData = true;
	}
	
	private void initStatusLists() {
		ElevatorButtonList.addAll(new ArrayList<Boolean>(buildingModel.FloorNumber.getValue()));
		ServicesFloorList.addAll(new ArrayList<Boolean>(buildingModel.FloorNumber.getValue()));
	}
	
	/**
	 * Set current viewed elevator number to get and set the correct
	 * values in the models 
	 * @param elevatorNumber: current viewed elevator number
	 */
	public void setCurrViewElevatorNumber(int elevatorNumber) {
		try {
			elevatorModel.setElevatorNumber(elevatorNumber);
		} catch (RemoteException e) { 
			// TODO: Use AlarmManager
			System.out.print("Error: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			// TODO: Use AlarmManager	
			System.out.print("Error: " + e.getMessage());
		} catch (Exception e) {
			// TODO: Use AlarmManager	
			System.out.print("Error: " + e.getMessage());
		}
	}
	
	@Override
	public void run() {
		if (updateModelData) {
			// Update properties only in javafx thread
			Platform.runLater(() ->	updateModelValues());
		}		
	}	
	
	public void updateModelValues() {
		try {
			long startClockTick = 0;
			long diffClockTick = 0;
			int tryUpdateCounter = 0;
			final int maxUpdateAttempts = 3;
			
			do {
				startClockTick = elevatorModel.getClockTick();
				updateElevatorModel();
				updateElevatorButtonList();
				updateServicesFloorList();
				updateFloorButtons();	
				diffClockTick = elevatorModel.getClockTick() - startClockTick;
				tryUpdateCounter++;
			} while (diffClockTick != 0 && tryUpdateCounter < maxUpdateAttempts);
			
			if (diffClockTick != 0) {
				throw new RuntimeException("Error in update model values: clock tick not equal!");
			}			
		} catch (RemoteException e) {
			// TODO: Use AlarmManager
			System.out.print("Error: " + e.getMessage());
		} catch (RuntimeException e) {
			// TODO: Use AlarmManager
			System.out.print("Error: " + e.getMessage());
		} catch (Exception e) {
			// TODO: Use AlarmManager
			System.out.print("Error: " + e.getMessage());
		}			
	}
	
	private void updateElevatorButtonList() throws RemoteException {
		for (int i = 0; i < ElevatorButtonList.size(); i++) {
			ElevatorButtonList.set(i, elevatorModel.getElevatorButton(i));
		}
	}
	
	private void updateServicesFloorList() throws RemoteException {
		for (int i = 0; i < ServicesFloorList.size(); i++) {
			ServicesFloorList.set(i, elevatorModel.getServicesFloors(i));
		}
	}
	
	private void updateFloorButtons() throws RemoteException {
		for (var floor : buildingModel.FloorList) {
			floor.updateFloorButtonDown();
			floor.updateFloorButtonUp();
		}
	}
	
	private void updateElevatorModel() throws RemoteException {
		elevatorModel.updateCommittedDirection();
		elevatorModel.updateElevatorDoorStatus();
		elevatorModel.updateElevatorFloor();
		elevatorModel.updateElevatorSpeed();
		elevatorModel.updateElevatorWeight();
		elevatorModel.updateTarget();
	}

	public boolean isUpdateModelData() {
		return updateModelData;
	}

	public void setUpdateModelData(boolean updateModelData) {
		this.updateModelData = updateModelData;
	}

	public eOperationStatus getOperationStatus() {
		return operationStatus;
	}
	
	/**
	 * Button function to switch the operation status
	 */
	public void switchOperationStatus() {
		if (operationStatus.equals(eOperationStatus.MANUAL)) {
			// TODO: start automatic control -> run AutomaticHandler
			operationStatus = eOperationStatus.AUTOMATIC;			
		} else {
			// TODO: stop automatic control -> stop AutomaticHandler
			operationStatus = eOperationStatus.MANUAL;
		}
	}
	
	/**
	 * Button function to set the next target in manual operation mode
	 * @param nextTarget: floor number of next target
	 * @return true: set next target was successful, false: not able to set next target
	 */
	public boolean setNextElevatorTarget(int nextTarget) {
		if (operationStatus == eOperationStatus.MANUAL) {
			try {
				// Only set next target when last target was reached				
				if (!elevatorModel.getElevatorPosIsTarget() || elevatorModel.ElevatorSpeed.getValue() > 0) {
					// TODO: Add warning to AlarmManager
					return false;
				}
				
				// Only set next target when door is open
				if (elevatorModel.DoorStatus.getValue() != IElevator.ELEVATOR_DOORS_OPEN) {
					// TODO: Add warning to AlarmManager
					return false;
				}
				
				if (nextTarget == elevatorModel.ElevatorCurrFloor.getValue()) {
					// TODO: Add warning to AlarmManager
					return false;
				}
				
				if (nextTarget > elevatorModel.ElevatorCurrFloor.getValue()) {
					elevatorModel.setCommittedDirection(IElevator.ELEVATOR_DIRECTION_UP);
				} else {
					elevatorModel.setCommittedDirection(IElevator.ELEVATOR_DIRECTION_DOWN);
				}			
				
				elevatorModel.setTarget(nextTarget);
			} catch (RemoteException e) {
				// TODO: Use AlarmManager
				System.out.print("Error: " + e.getMessage());
			} catch (Exception e) {
				// TODO: Use AlarmManager
				System.out.print("Error: " + e.getMessage());
			}			
		}
		else {
			// TODO: Add warning to AlarmManager
			return false;
		}
		
		return true;		
	}
}
