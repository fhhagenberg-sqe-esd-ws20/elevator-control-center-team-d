/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import javafx.beans.property.IntegerProperty;

/**
 * Interface for elevator model to handle the elevator
 * model
 * @author Dominic Zopf
 *
 */
public interface IModelElevator {
	
	public int getElevatorNumber();
	
	public void setElevatorNumber(int mElevatorNumber) throws java.rmi.RemoteException;
	
	public boolean getElevatorPosIsTarget() throws java.rmi.RemoteException;
	
	public void updateCommittedDirection() throws java.rmi.RemoteException;
	
	public boolean getElevatorButton(int floor) throws java.rmi.RemoteException;
	
	public void updateElevatorDoorStatus() throws java.rmi.RemoteException;
	
	public void updateElevatorFloor() throws java.rmi.RemoteException;
	
	public void updateElevatorSpeed() throws java.rmi.RemoteException;
	
	public void updateElevatorWeight() throws java.rmi.RemoteException;
	
	public boolean getServicesFloors(int floor) throws java.rmi.RemoteException;
	
	public void updateTarget() throws java.rmi.RemoteException;
	
	public void setCommittedDirection(int direction) throws java.rmi.RemoteException;
	
	public void setTarget(int target) throws java.rmi.RemoteException;
	
	public long getClockTick() throws java.rmi.RemoteException;
	
	public IntegerProperty getPropCommitedDirection();
	
	public IntegerProperty getPropDoorStatus();
	
	public IntegerProperty getPropElevatorCurrFloor();
	
	public IntegerProperty getPropElevatorSpeed();
	
	public IntegerProperty getPropElevatorWeight();
	
	public IntegerProperty getPropElevatorCurrTarget();
	
	public int getCommitedDirection();
	
	public int getDoorStatus();
	
	public int getElevatorCurrFloor();
	
	public int getElevatorSpeed();
	
	public int getElevatorWeight();
	
	public int getElevatorCurrTarget();
}
