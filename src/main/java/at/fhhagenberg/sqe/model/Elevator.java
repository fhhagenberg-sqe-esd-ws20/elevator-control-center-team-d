/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sqelevator.IElevator;

/**
 * Class for the elevator model which gets the operating status from
 * a remote elevator.
 * @author Dominic Zopf
 *
 */
public class Elevator implements IModelElevator {
	private int mElevatorNumber;
	private IWrapElevator mRemoteElevator;	
	
	// Model properties for GUI binding
	public IntegerProperty commitedDirection = new SimpleIntegerProperty();
	public StringProperty doorStatus = new SimpleStringProperty();
	public IntegerProperty elevatorCurrFloor = new SimpleIntegerProperty();
	public StringProperty elevatorSpeed = new SimpleStringProperty();
	public StringProperty elevatorWeight = new SimpleStringProperty();
	public IntegerProperty elevatorCurrTarget = new SimpleIntegerProperty();
	
	// Save integers for string properties
	private int iDoorStatus;
	private int iElevatorSpeed;
	private int iElevatorWeight;
	
	
	public Elevator(IWrapElevator remoteElevator) {
		mRemoteElevator = remoteElevator;
		mElevatorNumber = 0;
	}
	
	@Override
	public void setRemoteElevator(IWrapElevator remoteElevator) {
		mRemoteElevator = remoteElevator;
	}

	@Override
	public int getElevatorNumber() {
		return mElevatorNumber;
	}

	@Override
	public void setElevatorNumber(int mElevatorNumber) throws java.rmi.RemoteException {
		if (mElevatorNumber < mRemoteElevator.getElevatorNum()) {
			this.mElevatorNumber = mElevatorNumber;
		} else {
			throw new IllegalArgumentException("Error: Invalid elevator number set!");
		}		
	}
	
	@Override
	public boolean getElevatorPosIsTarget() throws java.rmi.RemoteException {
		return mRemoteElevator.getElevatorPosIsTarget(mElevatorNumber);
	}
	
	@Override
	public void updateCommittedDirection() throws java.rmi.RemoteException {
		commitedDirection.setValue(mRemoteElevator.getCommittedDirection(mElevatorNumber));
	}
	
	@Override
	public boolean getElevatorButton(int floor) throws java.rmi.RemoteException {
		return mRemoteElevator.getElevatorButton(mElevatorNumber, floor);
	}
	
	@Override
	public void updateElevatorDoorStatus() throws java.rmi.RemoteException {
		iDoorStatus = mRemoteElevator.getElevatorDoorStatus(mElevatorNumber);
		
		if (iDoorStatus == IElevator.ELEVATOR_DOORS_CLOSED) {
			doorStatus.setValue("closed");
		} else if (iDoorStatus == IElevator.ELEVATOR_DOORS_OPEN) {
			doorStatus.setValue("open");
		} else if (iDoorStatus == IElevator.ELEVATOR_DOORS_OPENING) {
			doorStatus.setValue("opening");
		} else if (iDoorStatus == IElevator.ELEVATOR_DOORS_CLOSING) {
			doorStatus.setValue("closing");
		} else {
			doorStatus.setValue("undefined");
		}
	}
	
	@Override
	public void updateElevatorFloor() throws java.rmi.RemoteException {
		elevatorCurrFloor.setValue(mRemoteElevator.getElevatorFloor(mElevatorNumber));
	}
	
	@Override
	public void updateElevatorSpeed() throws java.rmi.RemoteException {	
		iElevatorSpeed = mRemoteElevator.getElevatorSpeed(mElevatorNumber);
		elevatorSpeed.setValue(String.valueOf(iElevatorSpeed) + " ft/s");
	}
	
	@Override
	public void updateElevatorWeight() throws java.rmi.RemoteException {
		iElevatorWeight = mRemoteElevator.getElevatorWeight(mElevatorNumber);
		elevatorWeight.setValue(String.valueOf(iElevatorWeight) + " lbs");
	}
	
	@Override
	public boolean getServicesFloors(int floor) throws java.rmi.RemoteException {
		return mRemoteElevator.getServicesFloors(mElevatorNumber, floor);
	}
	
	@Override
	public void updateTarget() throws java.rmi.RemoteException {
		elevatorCurrTarget.setValue(mRemoteElevator.getTarget(mElevatorNumber));
	}
	
	@Override
	public void setCommittedDirection(int direction) throws java.rmi.RemoteException {
		mRemoteElevator.setCommittedDirection(mElevatorNumber, direction);
	}
	
	@Override
	public void setTarget(int target) throws java.rmi.RemoteException {
		mRemoteElevator.setTarget(mElevatorNumber, target);
	}
	
	@Override
	public long getClockTick() throws java.rmi.RemoteException {
		return mRemoteElevator.getClockTick();
	}
	
	@Override
	public IntegerProperty getPropCommitedDirection() {
		return commitedDirection;
	}
	
	@Override
	public StringProperty getPropDoorStatus() {
		return doorStatus;
	}
	
	@Override
	public IntegerProperty getPropElevatorCurrFloor() {
		return elevatorCurrFloor;
	}
	
	@Override
	public StringProperty getPropElevatorSpeed() {
		return elevatorSpeed;
	}
	
	@Override
	public StringProperty getPropElevatorWeight() {
		return elevatorWeight;
	}
	
	@Override
	public IntegerProperty getPropElevatorCurrTarget() {
		return elevatorCurrTarget;
	}
	
	@Override
	public int getCommitedDirection() {
		return commitedDirection.getValue();
	}
	
	@Override
	public String getDoorStatus() {
		return doorStatus.getValue();
	}
	
	@Override
	public int getElevatorCurrFloor() {
		return elevatorCurrFloor.getValue();
	}
	
	@Override
	public String getElevatorSpeed() {
		return elevatorSpeed.getValue();
	}
	
	@Override
	public String getElevatorWeight() {
		return elevatorWeight.getValue();
	}
	
	@Override
	public int getElevatorCurrTarget() {
		return elevatorCurrTarget.getValue();
	}
	
	@Override
	public int getIDoorStatus() {
		return iDoorStatus;
	}
	
	@Override
	public int getIElevatorSpeed() {
		return iElevatorSpeed;
	}
	
	@Override
	public int getIElevatorWeight() {
		return iElevatorWeight;
	}
}

