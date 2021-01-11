/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;

/**
 * Interface for building model to handle the building
 * model
 * @author Dominic Zopf
 *
 */
public interface IModelBuilding {

	public void setRemoteElevator(IWrapElevator remoteElevator);
	
	public IntegerProperty getPropFloorNumber();
	
	public IntegerProperty getPropElevatorNumber();
	
	public ObservableList<Floor> getObservableFloorList();
	
	public int getFloorNumber();
	
	public int getElevatorNumber();
}
