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

	public void setElevatorNumber(int mElevatorNumber) {
		this.mElevatorNumber = mElevatorNumber;
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
}
