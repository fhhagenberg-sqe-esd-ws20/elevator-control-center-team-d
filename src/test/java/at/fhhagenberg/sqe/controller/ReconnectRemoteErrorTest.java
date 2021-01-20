/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.ElevatorAdapter;
import at.fhhagenberg.sqe.model.Floor;
import at.fhhagenberg.sqe.model.IModelBuilding;
import at.fhhagenberg.sqe.model.IModelElevator;
import at.fhhagenberg.sqe.model.IWrapElevator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import sqelevator.IElevator;

/**
 * Test class to verify the functionality of remote error exception handling 
 * and reconnect behavior.
 * @author Dominic Zopf
 *
 */
@ExtendWith(MockitoExtension.class)
class ReconnectRemoteErrorTest {
	@Mock
	private IAlarmManager mockedAlarmManager;
	
	@Mock
	private IModelBuilding mockedBuilding;
	
	@Mock
	private IElevator mockedElevator;
	
	@Mock
	private IWrapElevator mockedWrapElevator;
	
	@BeforeEach
	public void initMocks() throws Exception {
		Mockito.when(mockedBuilding.getObservableFloorList()).thenReturn(FXCollections.observableArrayList(new Floor(mockedWrapElevator, 0)));
		Mockito.when(mockedBuilding.getFloorNumber()).thenReturn(5);
	}
	
	@Test
	void testReconnectOfRMIAfterRemoteException() throws Exception {
		// AlarmManager property for remote error to activate the change listener which activates the reconnect
		BooleanProperty testRemoteConnectionErrorFlag = new SimpleBooleanProperty(false);
		Mockito.when(mockedAlarmManager.getPropRemoteConnError()).thenReturn(testRemoteConnectionErrorFlag);
		
		// Use original implementation for adapter and elevator model
		IWrapElevator testWrapElevator = new ElevatorAdapter(mockedElevator);
		IModelElevator testModelElevator = new Elevator(testWrapElevator);
		ElevatorController testElevatorCtrl = new ElevatorController(testModelElevator, mockedBuilding, mockedAlarmManager);
		Mockito.doThrow(new java.rmi.RemoteException("Communication error")).when(mockedElevator).getElevatorSpeed(0);
		
		testElevatorCtrl.updateModelValues();
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).setRemoteConnectionError();
		
		// Change error flag to activate reconnect
		testRemoteConnectionErrorFlag.setValue(true);
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addWarningMessage("Warning (Reconnect Handler): Try to reconnect to remote elevator!");
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).resetRemoteConnectionError();
	}
	
	@Test
	void testRemoteConnectionListenerWithChangeFromSetErrorToResetedError() throws Exception {
		// AlarmManager property for remote error to activate the change listener which activates the reconnect
		BooleanProperty testRemoteConnectionErrorFlag = new SimpleBooleanProperty(true);
		Mockito.when(mockedAlarmManager.getPropRemoteConnError()).thenReturn(testRemoteConnectionErrorFlag);
		
		// Use original implementation for adapter and elevator model
		IWrapElevator testWrapElevator = new ElevatorAdapter(mockedElevator);
		IModelElevator testModelElevator = new Elevator(testWrapElevator);
		ElevatorController testElevatorCtrl = new ElevatorController(testModelElevator, mockedBuilding, mockedAlarmManager);
		
		// Change error flag to activate change listener
		testRemoteConnectionErrorFlag.setValue(false);
		
		Mockito.verify(mockedAlarmManager, Mockito.never()).addWarningMessage("Warning (Reconnect Handler): Try to reconnect to remote elevator!");
		Mockito.verify(mockedAlarmManager, Mockito.never()).resetRemoteConnectionError();
	}
}
