package at.fhhagenberg.sqe.model;

import java.rmi.RemoteException;

public class DummyElevator implements IWrapElevator {

	@Override
	public boolean getElevatorPosIsTarget(int elevatorNumber) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getCommittedDirection(int elevatorNumber) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getElevatorButton(int elevatorNumber, int floor) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getElevatorDoorStatus(int elevatorNumber) throws RemoteException {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getElevatorFloor(int elevatorNumber) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getElevatorNum() throws RemoteException {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getElevatorSpeed(int elevatorNumber) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getElevatorWeight(int elevatorNumber) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getFloorButtonDown(int floor) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getFloorButtonUp(int floor) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getFloorNum() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getServicesFloors(int elevatorNumber, int floor) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTarget(int elevatorNumber) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCommittedDirection(int elevatorNumber, int direction) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTarget(int elevatorNumber, int target) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public long getClockTick() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

}
