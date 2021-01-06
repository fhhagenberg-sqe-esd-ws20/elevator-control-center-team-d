/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

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
	
	public StringProperty getPropDoorStatus();
	
	public IntegerProperty getPropElevatorCurrFloor();
	
	public StringProperty getPropElevatorSpeed();
	
	public StringProperty getPropElevatorWeight();
	
	public IntegerProperty getPropElevatorCurrTarget();
	
	public int getCommitedDirection();
	
	public String getDoorStatus();
	
	public int getElevatorCurrFloor();
	
	public String getElevatorSpeed();
	
	public String getElevatorWeight();
	
	public int getElevatorCurrTarget();
	
	public int getIDoorStatus();
	
	public int getIElevatorSpeed();
	
	public int getIElevatorWeight();
}
