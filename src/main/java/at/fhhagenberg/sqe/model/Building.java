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
	public IntegerProperty floorNumber = new SimpleIntegerProperty();
	public IntegerProperty elevatorNumber = new SimpleIntegerProperty();
	public ObservableList<Floor> floorList = FXCollections.observableArrayList(Floor.extractor());
	
	
	public Building(IWrapElevator remoteElevator, IAlarmManager alarmManager) {
		mRemoteElevator = remoteElevator;
		mAlarmManager = alarmManager;
		
		initBuildingInformation();
	}
	
	@Override
	public void setRemoteElevator(IWrapElevator remoteElevator) {
		mRemoteElevator = remoteElevator;
		
		for (var floor : floorList) {
			floor.setRemoteElevator(remoteElevator);
		}
	}
	
	private void initBuildingInformation() {
		try {
			floorNumber.setValue(mRemoteElevator.getFloorNum());
			elevatorNumber.setValue(mRemoteElevator.getElevatorNum());
		} catch (RemoteException e) {
			floorNumber.setValue(0);
			elevatorNumber.setValue(0);
			mAlarmManager.addErrorMessage("Remote error (init building information): " + e.getMessage());
			mAlarmManager.setRemoteConnectionError();
		}	
		
		if (floorNumber.getValue() < 0) {
			floorNumber.setValue(0);
		}
		
		if (elevatorNumber.getValue() < 0) {
			elevatorNumber.setValue(0);
		}
		
		for (int i = 0; i < floorNumber.getValue(); i++) {
			floorList.add(0, new Floor(mRemoteElevator, i));
		}
	}
	
	@Override
	public IntegerProperty getPropFloorNumber() {
		return floorNumber;
	}
	
	@Override
	public IntegerProperty getPropElevatorNumber() {
		return elevatorNumber;
	}
	
	@Override
	public ObservableList<Floor> getObservableFloorList() {
		return floorList;
	}
	
	@Override
	public int getFloorNumber() {
		return floorNumber.getValue();
	}
	
	@Override
	public int getElevatorNumber() {
		return elevatorNumber.getValue();
	}
}
