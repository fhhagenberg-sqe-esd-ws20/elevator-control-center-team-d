/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import java.rmi.RemoteException;

import at.fhhagenberg.sqe.controller.IAlarmManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
	private IAlarmManager mAlarmManager;
	
	// Model properties for GUI binding
	public IntegerProperty FloorNumber = new SimpleIntegerProperty();
	public IntegerProperty ElevatorNumber = new SimpleIntegerProperty();
	public ObservableList<Floor> FloorList = FXCollections.observableArrayList(Floor.extractor());
	
	
	public Building(IWrapElevator remoteElevator, IAlarmManager alarmManager) {
		mRemoteElevator = remoteElevator;
		mAlarmManager = alarmManager;
		
		initBuildingInformation();
	}
	
	private void initBuildingInformation() {
		try {
			FloorNumber.setValue(mRemoteElevator.getFloorNum());
			ElevatorNumber.setValue(mRemoteElevator.getElevatorNum());
		} catch (RemoteException e) {
			FloorNumber.setValue(0);
			ElevatorNumber.setValue(0);
			mAlarmManager.addErrorMessage("Remote error (init building information): " + e.getMessage());
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
