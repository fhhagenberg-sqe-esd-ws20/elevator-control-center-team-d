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
	public BooleanProperty FloorButtonDown = new SimpleBooleanProperty();
	public BooleanProperty FloorButtonUp = new SimpleBooleanProperty();
	
	
	public Floor(IWrapElevator remoteElevator, int floorNumber) {
		mRemoteElevator = remoteElevator;
		mFloorNumber = floorNumber;
	}
	
	public int getFloorNumber() {
		return mFloorNumber;
	}
	
	public void updateFloorButtonDown() throws java.rmi.RemoteException {
		FloorButtonDown.setValue(mRemoteElevator.getFloorButtonDown(mFloorNumber));
	}
	
	public void updateFloorButtonUp() throws java.rmi.RemoteException {
		FloorButtonUp.setValue(mRemoteElevator.getFloorButtonUp(mFloorNumber));
	}
	
	public BooleanProperty getFloorButtonDownProperty() {
		return FloorButtonDown;
	}
	
	public BooleanProperty getFloorButtonUpProperty() {
		return FloorButtonUp;
	}
	
	public static Callback<Floor, Observable[]> extractor() {
		return (Floor f) -> new Observable[] { f.getFloorButtonDownProperty(), f.getFloorButtonUpProperty() };
	}
}
