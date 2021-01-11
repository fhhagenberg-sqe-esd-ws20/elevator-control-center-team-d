/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.controller;

import javafx.beans.property.BooleanProperty;

/**
 * Interface for AlarmManager to handle errors and
 * warnings.
 * @author Dominic Zopf
 *
 */
public interface IAlarmManager {
	
	/**
	 * Set boolean variable to show that a remote error occurred
	 */
	public void setRemoteConnectionError();
	
	/**
	 * Reset boolean variable to show that the error was handled
	 */
	public void resetRemoteConnectionError();
	
	/**
	 * Get remote connection error property
	 * @return remote connection boolean property
	 */
	public BooleanProperty getPropRemoteConnError();
	
	/**
	 * Adds new error message to error list
	 * @param errorMessage: string with error message
	 */
	public void addErrorMessage(String errorMessage);
	
	/**
	 * Adds new warning message to warning list
	 * @param warningMessage: string with warning message
	 */
	public void addWarningMessage(String warningMessage);
	
	/**
	 * Clears the current added error messages
	 */
	public void clearErrorMessages();
	
	/**
	 * Clears the current added warning messages
	 */
	public void clearWarningMessages();
}
