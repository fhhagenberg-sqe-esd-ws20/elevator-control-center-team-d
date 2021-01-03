/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.controller;

/**
 * Interface for AlarmManager to handle errors and
 * warnings.
 * @author Dominic Zopf
 *
 */
public interface IAlarmManager {
	
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
