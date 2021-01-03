/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class to verify the functionality of the AlarmManager class.
 * @author Dominic Zopf
 *
 */
public class AlarmManagerTest {
	@Test
	public void testAddOneErrorMessage() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.addErrorMessage("Test error 01");
		
		assertEquals("Test error 01", testAlarmManager.ErrorList.get(0));
		assertEquals(1, testAlarmManager.ErrorList.size());
	}
	
	@Test
	public void testAddOneWarningMessage() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.addWarningMessage("Test warning 01");
		
		assertEquals("Test warning 01", testAlarmManager.WarningList.get(0));
		assertEquals(1, testAlarmManager.WarningList.size());
	}
	
	@Test
	public void testAddOneWarningAndErrorMessage() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.addErrorMessage("Test error 02");
		testAlarmManager.addWarningMessage("Test warning 02");
		
		assertEquals("Test error 02", testAlarmManager.ErrorList.get(0));		
		assertEquals("Test warning 02", testAlarmManager.WarningList.get(0));
		assertEquals(1, testAlarmManager.ErrorList.size());
		assertEquals(1, testAlarmManager.WarningList.size());
	}
	
	@Test
	public void testEmptyErrorAndWarningLists() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		assertEquals(0, testAlarmManager.ErrorList.size());
		assertEquals(0, testAlarmManager.WarningList.size());
	}
	
	@Test
	public void testClearingErrorListWithMultipleContainedMessages() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.addErrorMessage("Test error 03");
		testAlarmManager.addErrorMessage("Test error 04");
		testAlarmManager.addErrorMessage("Test error 05");
		
		assertEquals(3, testAlarmManager.ErrorList.size());
		
		testAlarmManager.clearErrorMessages();
		
		assertEquals(0, testAlarmManager.ErrorList.size());
	}
	
	@Test
	public void testClearingWarningListWithMultipleContainedMessages() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.addWarningMessage("Test warning 03");
		testAlarmManager.addWarningMessage("Test warning 04");
		testAlarmManager.addWarningMessage("Test warning 05");
		
		assertEquals(3, testAlarmManager.WarningList.size());
		
		testAlarmManager.clearWarningMessages();
		
		assertEquals(0, testAlarmManager.WarningList.size());
	}
	
	@Test
	public void testClearingEmptyWarningAndErrorLists() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.clearErrorMessages();
		testAlarmManager.clearWarningMessages();
		
		assertEquals(0, testAlarmManager.ErrorList.size());
		assertEquals(0, testAlarmManager.WarningList.size());
	}
	
	@Test
	public void testAddErrorsClearListAndAgainAddErrors() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.addErrorMessage("Test error 06");
		testAlarmManager.addErrorMessage("Test error 07");
		testAlarmManager.clearErrorMessages();
		testAlarmManager.addErrorMessage("Test error 08");
		testAlarmManager.addErrorMessage("Test error 09");
		
		assertEquals("Test error 08", testAlarmManager.ErrorList.get(0));
		assertEquals("Test error 09", testAlarmManager.ErrorList.get(1));
		assertEquals(2, testAlarmManager.ErrorList.size());		
	}
	
	@Test
	public void testAddWarningsClearListAndAgainAddWarnings() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.addWarningMessage("Test warning 06");
		testAlarmManager.addWarningMessage("Test warning 07");
		testAlarmManager.clearWarningMessages();
		testAlarmManager.addWarningMessage("Test warning 08");
		testAlarmManager.addWarningMessage("Test warning 09");
		
		assertEquals("Test warning 08", testAlarmManager.WarningList.get(0));
		assertEquals("Test warning 09", testAlarmManager.WarningList.get(1));
		assertEquals(2, testAlarmManager.WarningList.size());		
	}
}
