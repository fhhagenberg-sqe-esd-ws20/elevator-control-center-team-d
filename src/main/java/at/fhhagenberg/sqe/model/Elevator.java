package at.fhhagenberg.sqe.model;

public class Elevator {
	private int mElevatorNumber;
	private IWrapElevator mRemoteElevator;	
	
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
	
	public int getCommittedDirection() throws java.rmi.RemoteException {
		return mRemoteElevator.getCommittedDirection(mElevatorNumber);
	}
	
	public boolean getElevatorButton(int floor) throws java.rmi.RemoteException {
		return mRemoteElevator.getElevatorButton(mElevatorNumber, floor);
	}
	
	public int getElevatorDoorStatus() throws java.rmi.RemoteException {
		return mRemoteElevator.getElevatorDoorStatus(mElevatorNumber);
	}
	
	public int getElevatorFloor() throws java.rmi.RemoteException {
		return mRemoteElevator.getElevatorFloor(mElevatorNumber);
	}
	
	public int getElevatorSpeed() throws java.rmi.RemoteException {
		return mRemoteElevator.getElevatorSpeed(mElevatorNumber);
	}
	
	public int getElevatorWeight() throws java.rmi.RemoteException {
		return mRemoteElevator.getElevatorWeight(mElevatorNumber);
	}
	
	public boolean getServicesFloors(int floor) throws java.rmi.RemoteException {
		return mRemoteElevator.getServicesFloors(mElevatorNumber, floor);
	}
	
	public int getTarget() throws java.rmi.RemoteException {
		return mRemoteElevator.getTarget(mElevatorNumber);
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
