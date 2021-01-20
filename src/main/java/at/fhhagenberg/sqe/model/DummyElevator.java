/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import java.rmi.RemoteException;

import sqelevator.IElevator;

/**
 * Test class to test one static GUI interface.
 * Settings are equal to the GUI mockup.
 * @author Dominic Zopf
 *
 */
public class DummyElevator implements IWrapElevator {

	@Override
	public boolean getElevatorPosIsTarget(int elevatorNumber) throws RemoteException {
		return false;
	}

	@Override
	public int getCommittedDirection(int elevatorNumber) throws RemoteException {
		return IElevator.ELEVATOR_DIRECTION_UP;
	}

	@Override
	public boolean getElevatorButton(int elevatorNumber, int floor) throws RemoteException {
		if (floor == 0) {
			return false;
		} else if (floor == 1) {
			return false;
		} else if (floor == 2) {
			return false;
		} else if (floor == 3) {
			return false;
		} else if (floor == 4) {
			return false;
		} else if (floor == 5) {
			return true;
		} else if (floor == 6) {
			return false;
		} else if (floor == 7) {
			return true;
		}
		
		return false;
	}

	@Override
	public int getElevatorDoorStatus(int elevatorNumber) throws RemoteException {
		return IElevator.ELEVATOR_DOORS_CLOSED;
	}

	@Override
	public int getElevatorFloor(int elevatorNumber) throws RemoteException {
		if(elevatorNumber == 1)
			return 5;
		
		return 3;
	}

	@Override
	public int getElevatorNum() throws RemoteException {
		return 4;
	}

	@Override
	public int getElevatorSpeed(int elevatorNumber) throws RemoteException {
		return 10;
	}

	@Override
	public int getElevatorWeight(int elevatorNumber) throws RemoteException {
		return 650;
	}

	@Override
	public boolean getFloorButtonDown(int floor) throws RemoteException {
		if (floor == 0) {
			return false;
		} else if (floor == 1) {
			return false;
		} else if (floor == 2) {
			return false;
		} else if (floor == 3) {
			return false;
		} else if (floor == 4) {
			return false;
		} else if (floor == 5) {
			return false;
		} else if (floor == 6) {
			return false;
		} else if (floor == 7) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean getFloorButtonUp(int floor) throws RemoteException {
		if (floor == 0) {
			return true;
		} else if (floor == 1) {
			return false;
		} else if (floor == 2) {
			return false;
		} else if (floor == 3) {
			return false;
		} else if (floor == 4) {
			return false;
		} else if (floor == 5) {
			return false;
		} else if (floor == 6) {
			return false;
		} else if (floor == 7) {
			return false;
		}
		
		return false;
	}

	@Override
	public int getFloorNum() throws RemoteException {
		return 8;
	}

	@Override
	public boolean getServicesFloors(int elevatorNumber, int floor) throws RemoteException {
		if (floor == 0) {
			return true;
		} else if (floor == 1) {
			return true;
		} else if (floor == 2) {
			return true;
		} else if (floor == 3) {
			return true;
		} else if (floor == 4) {
			return false;
		} else if (floor == 5) {
			return true;
		} else if (floor == 6) {
			return true;
		} else if (floor == 7) {
			return true;
		}
		
		return false;
	}

	@Override
	public int getTarget(int elevatorNumber) throws RemoteException {	
		return 5;
	}

	@Override
	public void setCommittedDirection(int elevatorNumber, int direction) throws RemoteException {
		// Do nothing. Dynamic behaviour will be simulated with test mock.
	}

	@Override
	public void setTarget(int elevatorNumber, int target) throws RemoteException {
		// Do nothing. Dynamic behaviour will be simulated with test mock.
	}

	@Override
	public long getClockTick() throws RemoteException {
		return 120354;
	}
}
