/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sun.javafx.collections.ObservableListWrapper;

import at.fhhagenberg.sqe.model.Building;

/**
 * Test class to verify the functionality of the AlarmManager class.
 * @author Dominic Zopf
 *
 */
class AlarmManagerTest {
	@Test
	void testAddOneErrorMessage() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.addErrorMessage("Test error 01");
		
		assertEquals("Test error 01", testAlarmManager.errorList.get(0));
		assertEquals(1, testAlarmManager.errorList.size());
	}
	
	@Test
	void testAddOneWarningMessage() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.addWarningMessage("Test warning 01");
		
		assertEquals("Test warning 01", testAlarmManager.warningList.get(0));
		assertEquals(1, testAlarmManager.warningList.size());
	}
	
	@Test
	void testAddOneWarningAndErrorMessage() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.addErrorMessage("Test error 02");
		testAlarmManager.addWarningMessage("Test warning 02");
		
		assertEquals("Test error 02", testAlarmManager.errorList.get(0));		
		assertEquals("Test warning 02", testAlarmManager.warningList.get(0));
		assertEquals(1, testAlarmManager.errorList.size());
		assertEquals(1, testAlarmManager.warningList.size());
	}
	
	@Test
	void testEmptyErrorAndWarningLists() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		assertEquals(0, testAlarmManager.errorList.size());
		assertEquals(0, testAlarmManager.warningList.size());
	}
	
	@Test
	void testClearingErrorListWithMultipleContainedMessages() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.addErrorMessage("Test error 03");
		testAlarmManager.addErrorMessage("Test error 04");
		testAlarmManager.addErrorMessage("Test error 05");
		
		assertEquals(3, testAlarmManager.errorList.size());
		
		testAlarmManager.clearErrorMessages();
		
		assertEquals(0, testAlarmManager.errorList.size());
	}
	
	@Test
	void testClearingWarningListWithMultipleContainedMessages() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.addWarningMessage("Test warning 03");
		testAlarmManager.addWarningMessage("Test warning 04");
		testAlarmManager.addWarningMessage("Test warning 05");
		
		assertEquals(3, testAlarmManager.warningList.size());
		
		testAlarmManager.clearWarningMessages();
		
		assertEquals(0, testAlarmManager.warningList.size());
	}
	
	@Test
	void testClearingEmptyWarningAndErrorLists() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.clearErrorMessages();
		testAlarmManager.clearWarningMessages();
		
		assertEquals(0, testAlarmManager.errorList.size());
		assertEquals(0, testAlarmManager.warningList.size());
	}
	
	@Test
	void testAddErrorsClearListAndAgainAddErrors() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.addErrorMessage("Test error 06");
		testAlarmManager.addErrorMessage("Test error 07");
		testAlarmManager.clearErrorMessages();
		testAlarmManager.addErrorMessage("Test error 08");
		testAlarmManager.addErrorMessage("Test error 09");
		
		assertEquals("Test error 08", testAlarmManager.errorList.get(0));
		assertEquals("Test error 09", testAlarmManager.errorList.get(1));
		assertEquals(2, testAlarmManager.errorList.size());		
	}
	
	@Test
	void testAddWarningsClearListAndAgainAddWarnings() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		testAlarmManager.addWarningMessage("Test warning 06");
		testAlarmManager.addWarningMessage("Test warning 07");
		testAlarmManager.clearWarningMessages();
		testAlarmManager.addWarningMessage("Test warning 08");
		testAlarmManager.addWarningMessage("Test warning 09");
		
		assertEquals("Test warning 08", testAlarmManager.warningList.get(0));
		assertEquals("Test warning 09", testAlarmManager.warningList.get(1));
		assertEquals(2, testAlarmManager.warningList.size());		
	}
	
	@Test
	void testSetRemoteConnectionErrorFromInitFalseToTrue() {
		AlarmManager testAlarmManager = new AlarmManager();
		
		assertFalse(testAlarmManager.remoteConnError.getValue());
		testAlarmManager.setRemoteConnectionError();
		assertTrue(testAlarmManager.remoteConnError.getValue());		
	}
	
	@Test
	void testResetRemoteConnectionErrorFromTrueToFalse() {
		AlarmManager testAlarmManager = new AlarmManager();
		testAlarmManager.setRemoteConnectionError();
		
		testAlarmManager.resetRemoteConnectionError();
		
		assertFalse(testAlarmManager.remoteConnError.getValue());
	}
	
	@Test
	void testGetterObservableErrorList() {
		AlarmManager testAlarmManager = new AlarmManager();		
		
		testAlarmManager.addErrorMessage("Error: Test01");
		testAlarmManager.addErrorMessage("Error: Test02");
		
		assertEquals(ObservableListWrapper.class, testAlarmManager.getPropErrorList().getClass());
		assertEquals(2, testAlarmManager.getPropErrorList().size());
		assertEquals("Error: Test02", testAlarmManager.getPropErrorList().get(1));
	}
	
	@Test
	void testGetterObservableWarningList() {
		AlarmManager testAlarmManager = new AlarmManager();		
		
		testAlarmManager.addWarningMessage("Warning: Test01");
		
		assertEquals(ObservableListWrapper.class, testAlarmManager.getPropWarningList().getClass());
		assertEquals(1, testAlarmManager.getPropWarningList().size());
		assertEquals("Warning: Test01", testAlarmManager.getPropWarningList().get(0));
	}
}
