/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import java.rmi.RemoteException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class which represents the model of the building
 * where the elevators are installed
 * @author Dominic Zopf
 *
 */
public class Building {
	private IWrapElevator mRemoteElevator;
	
	// Model properties for GUI binding
	public IntegerProperty FloorNumber = new SimpleIntegerProperty();
	public IntegerProperty ElevatorNumber = new SimpleIntegerProperty();
	public StringProperty ErrorMsgBuilding = new SimpleStringProperty();
	public ObservableList<Floor> FloorList = FXCollections.observableArrayList(Floor.extractor());
	
	
	public Building(IWrapElevator remoteElevator) {
		mRemoteElevator = remoteElevator;
		
		initBuildingInformation();
	}
	
	private void initBuildingInformation() {
		try {
			FloorNumber.setValue(mRemoteElevator.getFloorNum());
			ElevatorNumber.setValue(mRemoteElevator.getElevatorNum());
		} catch (RemoteException e) {
			// TODO: use AlarmManager for error messages
			FloorNumber.setValue(0);
			ElevatorNumber.setValue(0);
			ErrorMsgBuilding.setValue("Error in initBuildingInformation: " + e.getMessage());
		}	
		
		if (FloorNumber.getValue() < 0) {
			FloorNumber.setValue(0);
		}
		
		if (ElevatorNumber.getValue() < 0) {
			ElevatorNumber.setValue(0);
		}
		
		for (int i = 0; i < FloorNumber.getValue(); i++) {
			FloorList.add(new Floor(mRemoteElevator, i));
		}
	}	
}
