/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Callback;

/**
 * Class for the floor model which represents the status of
 * the floor buttons
 * @author Dominic Zopf
 *
 */
public class Floor {
	private IWrapElevator mRemoteElevator;
	private int mFloorNumber;
	
	// Model properties for GUI binding
	public BooleanProperty floorButtonDown = new SimpleBooleanProperty();
	public BooleanProperty floorButtonUp = new SimpleBooleanProperty();
	
	
	public Floor(IWrapElevator remoteElevator, int floorNumber) {
		mRemoteElevator = remoteElevator;
		mFloorNumber = floorNumber;
	}
	
	public void setRemoteElevator(IWrapElevator remoteElevator) {
		mRemoteElevator = remoteElevator;
	}
	
	public int getFloorNumber() {
		return mFloorNumber;
	}
	
	public void updateFloorButtonDown() throws java.rmi.RemoteException {
		floorButtonDown.setValue(mRemoteElevator.getFloorButtonDown(mFloorNumber));
	}
	
	public void updateFloorButtonUp() throws java.rmi.RemoteException {
		floorButtonUp.setValue(mRemoteElevator.getFloorButtonUp(mFloorNumber));
	}
	
	public BooleanProperty getFloorButtonDownProperty() {
		return floorButtonDown;
	}
	
	public BooleanProperty getFloorButtonUpProperty() {
		return floorButtonUp;
	}
	
	public static Callback<Floor, Observable[]> extractor() {
		return (Floor f) -> new Observable[] { f.getFloorButtonDownProperty(), f.getFloorButtonUpProperty() };
	}
}
