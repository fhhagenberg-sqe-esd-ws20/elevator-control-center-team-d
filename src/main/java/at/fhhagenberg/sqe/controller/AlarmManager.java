/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.controller;

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

	public ObservableList<String> ErrorList = FXCollections.observableArrayList();
	public ObservableList<String> WarningList = FXCollections.observableArrayList();
	
	@Override
	public void addErrorMessage(String errorMessage) {
		ErrorList.add(errorMessage);
	}

	@Override
	public void addWarningMessage(String warningMessage) {
		WarningList.add(warningMessage);
	}

	@Override
	public void clearErrorMessages() {
		ErrorList.clear();
	}

	@Override
	public void clearWarningMessages() {
		WarningList.clear();
	}
}
