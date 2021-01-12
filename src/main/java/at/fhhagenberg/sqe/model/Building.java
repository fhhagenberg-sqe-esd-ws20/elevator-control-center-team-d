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
public class Building implements IModelBuilding {
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
	
	@Override
	public void setRemoteElevator(IWrapElevator remoteElevator) {
		mRemoteElevator = remoteElevator;
		
		for (var floor : FloorList) {
			floor.setRemoteElevator(remoteElevator);
		}
	}
	
	private void initBuildingInformation() {
		try {
			FloorNumber.setValue(mRemoteElevator.getFloorNum());
			ElevatorNumber.setValue(mRemoteElevator.getElevatorNum());
		} catch (RemoteException e) {
			FloorNumber.setValue(0);
			ElevatorNumber.setValue(0);
			mAlarmManager.addErrorMessage("Remote error (init building information): " + e.getMessage());
			mAlarmManager.setRemoteConnectionError();
		}	
		
		if (FloorNumber.getValue() < 0) {
			FloorNumber.setValue(0);
		}
		
		if (ElevatorNumber.getValue() < 0) {
			ElevatorNumber.setValue(0);
		}
		
		for (int i = 0; i < FloorNumber.getValue(); i++) {
			FloorList.add(0, new Floor(mRemoteElevator, i));
		}
	}
	
	@Override
	public IntegerProperty getPropFloorNumber() {
		return FloorNumber;
	}
	
	@Override
	public IntegerProperty getPropElevatorNumber() {
		return ElevatorNumber;
	}
	
	@Override
	public ObservableList<Floor> getObservableFloorList() {
		return FloorList;
	}
	
	@Override
	public int getFloorNumber() {
		return FloorNumber.getValue();
	}
	
	@Override
	public int getElevatorNumber() {
		return ElevatorNumber.getValue();
	}
}
