/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Class for the elevator model which gets the operating status from
 * a remote elevator.
 * @author Dominic Zopf
 *
 */
public class Elevator {
	private int mElevatorNumber;
	private IWrapElevator mRemoteElevator;	
	
	// Model properties for GUI binding
	public IntegerProperty CommitedDirection = new SimpleIntegerProperty();
	public IntegerProperty DoorStatus = new SimpleIntegerProperty();
	public IntegerProperty ElevatorCurrFloor = new SimpleIntegerProperty();
	public IntegerProperty ElevatorSpeed = new SimpleIntegerProperty();
	public IntegerProperty ElevatorWeight = new SimpleIntegerProperty();
	public IntegerProperty ElevatorCurrTarget = new SimpleIntegerProperty();
	
	
	public Elevator(IWrapElevator remoteElevator) {
		mRemoteElevator = remoteElevator;
	}

	public int getElevatorNumber() {
		return mElevatorNumber;
	}

	public void setElevatorNumber(int mElevatorNumber) throws java.rmi.RemoteException {
		if (mElevatorNumber < mRemoteElevator.getElevatorNum()) {
			this.mElevatorNumber = mElevatorNumber;
		} else {
			throw new IllegalArgumentException("Error: Invalid elevator number set!");
		}		
	}
	
	public boolean getElevatorPosIsTarget() throws java.rmi.RemoteException {
		return mRemoteElevator.getElevatorPosIsTarget(mElevatorNumber);
	}
	
	public void updateCommittedDirection() throws java.rmi.RemoteException {
		CommitedDirection.setValue(mRemoteElevator.getCommittedDirection(mElevatorNumber));
	}
	
	public boolean getElevatorButton(int floor) throws java.rmi.RemoteException {
		return mRemoteElevator.getElevatorButton(mElevatorNumber, floor);
	}
	
	public void updateElevatorDoorStatus() throws java.rmi.RemoteException {
		DoorStatus.setValue(mRemoteElevator.getElevatorDoorStatus(mElevatorNumber));
	}
	
	public void updateElevatorFloor() throws java.rmi.RemoteException {
		ElevatorCurrFloor.setValue(mRemoteElevator.getElevatorFloor(mElevatorNumber));
	}
	
	public void updateElevatorSpeed() throws java.rmi.RemoteException {
		ElevatorSpeed.setValue(mRemoteElevator.getElevatorSpeed(mElevatorNumber));
	}
	
	public void updateElevatorWeight() throws java.rmi.RemoteException {
		ElevatorWeight.setValue(mRemoteElevator.getElevatorWeight(mElevatorNumber));
	}
	
	public boolean getServicesFloors(int floor) throws java.rmi.RemoteException {
		return mRemoteElevator.getServicesFloors(mElevatorNumber, floor);
	}
	
	public void updateTarget() throws java.rmi.RemoteException {
		ElevatorCurrTarget.setValue(mRemoteElevator.getTarget(mElevatorNumber));
	}
	
	public void setCommittedDirection(int direction) throws java.rmi.RemoteException {
		mRemoteElevator.setCommittedDirection(mElevatorNumber, direction);
	}
	
	public void setTarget(int target) throws java.rmi.RemoteException {
		mRemoteElevator.setTarget(mElevatorNumber, target);
	}
	
	public long getClockTick() throws java.rmi.RemoteException {
		return mRemoteElevator.getClockTick();
	}
}
