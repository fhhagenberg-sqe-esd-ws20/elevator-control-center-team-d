/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import java.rmi.RemoteException;

import sqelevator.IElevator;

/**
 * Adapter for final elevator remote interface
 * @author Dominic Zopf
 *
 */
public class ElevatorAdapter implements IWrapElevator {
	
	private IElevator mRemoteElevator;
	
	public ElevatorAdapter(IElevator remoteElevator) {
		mRemoteElevator = remoteElevator;
	}

	@Override
	public boolean getElevatorPosIsTarget(int elevatorNumber) throws RemoteException {		
		return mRemoteElevator.getElevatorFloor(elevatorNumber) == mRemoteElevator.getTarget(elevatorNumber);
	}

	@Override
	public int getCommittedDirection(int elevatorNumber) throws RemoteException {
		return mRemoteElevator.getCommittedDirection(elevatorNumber);
	}

	@Override
	public boolean getElevatorButton(int elevatorNumber, int floor) throws RemoteException {
		return mRemoteElevator.getElevatorButton(elevatorNumber, floor);
	}

	@Override
	public int getElevatorDoorStatus(int elevatorNumber) throws RemoteException {
		return mRemoteElevator.getElevatorDoorStatus(elevatorNumber);
	}

	@Override
	public int getElevatorFloor(int elevatorNumber) throws RemoteException {
		return mRemoteElevator.getElevatorFloor(elevatorNumber);
	}

	@Override
	public int getElevatorNum() throws RemoteException {
		return mRemoteElevator.getElevatorNum();
	}

	@Override
	public int getElevatorSpeed(int elevatorNumber) throws RemoteException {
		return mRemoteElevator.getElevatorSpeed(elevatorNumber);
	}

	@Override
	public int getElevatorWeight(int elevatorNumber) throws RemoteException {
		return mRemoteElevator.getElevatorWeight(elevatorNumber);
	}

	@Override
	public boolean getFloorButtonDown(int floor) throws RemoteException {
		return mRemoteElevator.getFloorButtonDown(floor);
	}

	@Override
	public boolean getFloorButtonUp(int floor) throws RemoteException {
		return mRemoteElevator.getFloorButtonUp(floor);
	}

	@Override
	public int getFloorNum() throws RemoteException {
		return mRemoteElevator.getFloorNum();
	}

	@Override
	public boolean getServicesFloors(int elevatorNumber, int floor) throws RemoteException {
		return mRemoteElevator.getServicesFloors(elevatorNumber, floor);
	}

	@Override
	public int getTarget(int elevatorNumber) throws RemoteException {
		return mRemoteElevator.getTarget(elevatorNumber);
	}

	@Override
	public void setCommittedDirection(int elevatorNumber, int direction) throws RemoteException {
		mRemoteElevator.setCommittedDirection(elevatorNumber, direction);
	}

	@Override
	public void setTarget(int elevatorNumber, int target) throws RemoteException {
		mRemoteElevator.setTarget(elevatorNumber, target);
	}

	@Override
	public long getClockTick() throws RemoteException {
		return mRemoteElevator.getClockTick();
	}
}
