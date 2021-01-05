/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
	public IntegerProperty CommitedDirection = new SimpleIntegerProperty();
	public StringProperty DoorStatus = new SimpleStringProperty();
	public IntegerProperty ElevatorCurrFloor = new SimpleIntegerProperty();
	public StringProperty ElevatorSpeed = new SimpleStringProperty();
	public StringProperty ElevatorWeight = new SimpleStringProperty();
	public IntegerProperty ElevatorCurrTarget = new SimpleIntegerProperty();
	
	
	public Elevator(IWrapElevator remoteElevator) {
		mRemoteElevator = remoteElevator;
		mElevatorNumber = 0;
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
		CommitedDirection.setValue(mRemoteElevator.getCommittedDirection(mElevatorNumber));
	}
	
	@Override
	public boolean getElevatorButton(int floor) throws java.rmi.RemoteException {
		return mRemoteElevator.getElevatorButton(mElevatorNumber, floor);
	}
	
	@Override
	public void updateElevatorDoorStatus() throws java.rmi.RemoteException {
		int doorStatus = mRemoteElevator.getElevatorDoorStatus(mElevatorNumber);
		
		if (doorStatus == IElevator.ELEVATOR_DOORS_CLOSED) {
			DoorStatus.setValue("closed");
		} else if (doorStatus == IElevator.ELEVATOR_DOORS_OPEN) {
			DoorStatus.setValue("open");
		} else {
			DoorStatus.setValue("undefined");
		}
	}
	
	@Override
	public void updateElevatorFloor() throws java.rmi.RemoteException {
		ElevatorCurrFloor.setValue(mRemoteElevator.getElevatorFloor(mElevatorNumber));
	}
	
	@Override
	public void updateElevatorSpeed() throws java.rmi.RemoteException {		
		ElevatorSpeed.setValue(String.valueOf(mRemoteElevator.getElevatorSpeed(mElevatorNumber)) + " ft/s");
	}
	
	@Override
	public void updateElevatorWeight() throws java.rmi.RemoteException {
		ElevatorWeight.setValue(String.valueOf(mRemoteElevator.getElevatorWeight(mElevatorNumber)) + " lbs");
	}
	
	@Override
	public boolean getServicesFloors(int floor) throws java.rmi.RemoteException {
		return mRemoteElevator.getServicesFloors(mElevatorNumber, floor);
	}
	
	@Override
	public void updateTarget() throws java.rmi.RemoteException {
		ElevatorCurrTarget.setValue(mRemoteElevator.getTarget(mElevatorNumber));
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
		return CommitedDirection;
	}
	
	@Override
	public StringProperty getPropDoorStatus() {
		return DoorStatus;
	}
	
	@Override
	public IntegerProperty getPropElevatorCurrFloor() {
		return ElevatorCurrFloor;
	}
	
	@Override
	public StringProperty getPropElevatorSpeed() {
		return ElevatorSpeed;
	}
	
	@Override
	public StringProperty getPropElevatorWeight() {
		return ElevatorWeight;
	}
	
	@Override
	public IntegerProperty getPropElevatorCurrTarget() {
		return ElevatorCurrTarget;
	}
	
	@Override
	public int getCommitedDirection() {
		return CommitedDirection.getValue();
	}
	
	@Override
	public String getDoorStatus() {
		return DoorStatus.getValue();
	}
	
	@Override
	public int getElevatorCurrFloor() {
		return ElevatorCurrFloor.getValue();
	}
	
	@Override
	public String getElevatorSpeed() {
		return ElevatorSpeed.getValue();
	}
	
	@Override
	public String getElevatorWeight() {
		return ElevatorWeight.getValue();
	}
	
	@Override
	public int getElevatorCurrTarget() {
		return ElevatorCurrTarget.getValue();
	}
}

