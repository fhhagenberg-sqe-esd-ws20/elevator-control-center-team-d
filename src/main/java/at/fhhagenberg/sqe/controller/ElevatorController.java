/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.TimerTask;

import at.fhhagenberg.sqe.model.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sqelevator.IElevator;

/**
 * Class for the elevator controller which prepares the data for the view.
 * Update the model data and provide functions for the elevator control.
 * 
 * @author Dominic Zopf
 *
 */
public class ElevatorController extends TimerTask {

	static public final String remoteAddress = "rmi://localhost/ElevatorSim";
	
	private boolean useElevatorSim = false;
	private boolean updateModelData = false;

	public enum eOperationStatus {
		MANUAL, AUTOMATIC
	};

	private eOperationStatus operationStatus;

	public IModelBuilding buildingModel;
	public IModelElevator elevatorModel;
	public IAlarmManager ctrlAlarmManager;

	// Properties for GUI binding
	public ObservableList<Boolean> ElevatorButtonList = FXCollections.observableArrayList();
	public ObservableList<Boolean> ServicesFloorList = FXCollections.observableArrayList();

	public ElevatorController(IModelElevator modelElevator, IModelBuilding modelBuilding, IAlarmManager alarmManager) {
		buildingModel = modelBuilding;
		elevatorModel = modelElevator;
		ctrlAlarmManager = alarmManager;
		
		initStatusLists();
		initListenerConnectionLost();
		updateModelValues();

		operationStatus = eOperationStatus.MANUAL;
		updateModelData = true;
	}

	private void initStatusLists() {
		for (int i = 0; i < buildingModel.getFloorNumber(); i++) {
			ElevatorButtonList.add(Boolean.FALSE);
			ServicesFloorList.add(Boolean.TRUE);
		}
	}
	
	public void setUseElevatorSim(boolean useElevatorSim) {
		this.useElevatorSim = useElevatorSim;
	}
	
	public boolean isUseElevatorSim() {
		return useElevatorSim;
	}
	
	private void initListenerConnectionLost() {
		ctrlAlarmManager.getPropRemoteConnError().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					setUpdateModelData(false);
					reconnectToElevator();
					ctrlAlarmManager.addWarningMessage("Warning (Reconnect Handler): Try to reconnect to remote elevator!");
					ctrlAlarmManager.resetRemoteConnectionError();
					setUpdateModelData(true);
				}
			};
		});
	}
	
	private boolean reconnectToElevator() {
		if (useElevatorSim) {
			IElevator controller;			
			try {
				controller = (IElevator)Naming.lookup(ElevatorController.remoteAddress);
			} catch (NotBoundException e) {
				ctrlAlarmManager.addErrorMessage("Connection binding error (reconnect to elevator): " + e.getMessage());
				return false;
			} catch (MalformedURLException e) {
				ctrlAlarmManager.addErrorMessage("Malformed URL used for connection (reconnect to elevator): " + e.getMessage());
				return false;
			} catch (RemoteException e) {
				ctrlAlarmManager.addErrorMessage("Remote error (reconnect to elevator): " + e.getMessage());
				return false;
			}
			
			IWrapElevator remoteElevator = new ElevatorAdapter(controller);
			elevatorModel.setRemoteElevator(remoteElevator);
			buildingModel.setRemoteElevator(remoteElevator);
		}		
		return true;
	}	

	/**
	 * Set current viewed elevator number to get and set the correct values in the
	 * models
	 * 
	 * @param elevatorNumber: current viewed elevator number
	 */
	public void setCurrViewElevatorNumber(int elevatorNumber) {
		try {
			elevatorModel.setElevatorNumber(elevatorNumber);
		} catch (RemoteException e) {
			ctrlAlarmManager.addErrorMessage("Remote error (set elevator number): " + e.getMessage());
			ctrlAlarmManager.setRemoteConnectionError();
		} catch (IllegalArgumentException e) {
			ctrlAlarmManager.addErrorMessage("Illegal argument (set elevator number): " + e.getMessage());
		}
	}

	@Override
	public void run() {
		if (updateModelData) {
			// Update properties only in javafx thread
			Platform.runLater(() -> updateModelValues());
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
				throw new RuntimeException("Error in update model values: clock tick not equal after attempts!");
			}
		} catch (RemoteException e) {
			ctrlAlarmManager.addErrorMessage("Remote error (update model values): " + e.getMessage());
			ctrlAlarmManager.setRemoteConnectionError();
		} catch (RuntimeException e) {
			ctrlAlarmManager.addErrorMessage("Clock tick error (update model values): " + e.getMessage());
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
		for (var floor : buildingModel.getObservableFloorList()) {
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
	 * 
	 * @param nextTarget: floor number of next target
	 * @return true: set next target was successful, false: not able to set next
	 *         target
	 */
	public boolean setNextElevatorTarget(int nextTarget) {
		if (operationStatus == eOperationStatus.MANUAL) {
			try {
				// Only set next target when last target was reached
				if (!elevatorModel.getElevatorPosIsTarget() || elevatorModel.getIElevatorSpeed() > 0) {
					ctrlAlarmManager
							.addWarningMessage("Warning (set next target): elevator not in previous target position");
					return false;
				}

				// Only set next target when door is open
				if (elevatorModel.getIDoorStatus() != IElevator.ELEVATOR_DOORS_OPEN) {
					ctrlAlarmManager.addWarningMessage("Warning (set next target): elevator doors not open");
					return false;
				}

				if (nextTarget == elevatorModel.getElevatorCurrFloor()) {
					ctrlAlarmManager.addWarningMessage(
							"Warning (set next target): set target could not be current elevator position");
					return false;
				}

				if (nextTarget > elevatorModel.getElevatorCurrFloor()) {
					elevatorModel.setCommittedDirection(IElevator.ELEVATOR_DIRECTION_UP);
				} else {
					elevatorModel.setCommittedDirection(IElevator.ELEVATOR_DIRECTION_DOWN);
				}

				elevatorModel.setTarget(nextTarget);
			} catch (RemoteException e) {
				ctrlAlarmManager.addErrorMessage("Remote error (set next target): " + e.getMessage());
				ctrlAlarmManager.setRemoteConnectionError();
				return false;
			}
		} else {
			ctrlAlarmManager.addWarningMessage(
					"Warning (set next target): next elevator target can only be set in manual operation mode");
			return false;
		}

		return true;
	}
}
