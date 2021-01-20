/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for AlarmManager to handle errors and
 * warnings. Provides Observable lists to show the 
 * errors and warnings in the GUI.
 * @author Dominic Zopf
 *
 */
public class AlarmManager implements IAlarmManager {

	public ObservableList<String> errorList = FXCollections.observableArrayList();
	public ObservableList<String> warningList = FXCollections.observableArrayList();
	public BooleanProperty remoteConnError = new SimpleBooleanProperty();
	
	public AlarmManager() {
		remoteConnError.set(false);
	}
	
	@Override
	public void setRemoteConnectionError() {
		remoteConnError.set(true);
	}
	
	@Override
	public void resetRemoteConnectionError() {
		remoteConnError.set(false);
	}
	
	@Override
	public BooleanProperty getPropRemoteConnError() {
		return remoteConnError;
	}
	
	@Override
	public void addErrorMessage(String errorMessage) {
		errorList.add(errorMessage);
	}

	@Override
	public void addWarningMessage(String warningMessage) {
		warningList.add(warningMessage);
	}

	@Override
	public void clearErrorMessages() {
		errorList.clear();
	}

	@Override
	public void clearWarningMessages() {
		warningList.clear();
	}
	
	@Override
	public ObservableList<String> getPropErrorList() {
		return errorList;
	}
	
	@Override
	public ObservableList<String> getPropWarningList() {
		return warningList;
	}
}
