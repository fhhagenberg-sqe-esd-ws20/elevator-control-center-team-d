/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqe.model.IModelBuilding;
import at.fhhagenberg.sqe.model.IModelElevator;
import at.fhhagenberg.sqe.model.IWrapElevator;

/**
 * Test class to verify the functionality of the ElevatorController class.
 * @author Dominic Zopf
 *
 */
@ExtendWith(MockitoExtension.class)
public class ElevatorControllerTest {
	@Mock
	private IWrapElevator mockedRmElevator;
	
	@Mock
	private IAlarmManager mockedAlarmManager;
	
	@Mock
	private IModelElevator mockedElevator;
	
	@Mock
	private IModelBuilding mockedBuilding;
	
	@Test
	public void testInitializeUsedControllerObjects() throws java.rmi.RemoteException {	
		ElevatorController testElevatorCtrl = new ElevatorController(mockedRmElevator, mockedElevator, mockedBuilding, mockedAlarmManager);
		
		assertNotNull(testElevatorCtrl.buildingModel);
		assertNotNull(testElevatorCtrl.elevatorModel);
		assertNotNull(testElevatorCtrl.ctrlAlarmManager);		
	}
	
	@Test
	public void testInitializeElevatorButtonListValuesAndSize() throws java.rmi.RemoteException {
		Mockito.when(mockedBuilding.getFloorNumber()).thenReturn(2);
		ElevatorController testElevatorCtrl = new ElevatorController(mockedRmElevator, mockedElevator, mockedBuilding, mockedAlarmManager);
		
		assertFalse(testElevatorCtrl.ElevatorButtonList.get(0));
		assertFalse(testElevatorCtrl.ElevatorButtonList.get(1));
		assertEquals(2, testElevatorCtrl.ElevatorButtonList.size());
	}
	
	@Test
	public void testInitializeServiceFloorListValuesAndSize() throws java.rmi.RemoteException {
		Mockito.when(mockedBuilding.getFloorNumber()).thenReturn(2);
		ElevatorController testElevatorCtrl = new ElevatorController(mockedRmElevator, mockedElevator, mockedBuilding, mockedAlarmManager);
		
		assertTrue(testElevatorCtrl.ServicesFloorList.get(0));
		assertTrue(testElevatorCtrl.ServicesFloorList.get(1));
		assertEquals(2, testElevatorCtrl.ServicesFloorList.size());
	}
	
	@Test
	public void testSetCurrentViewElevatorNumberZero() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedRmElevator, mockedElevator, mockedBuilding, mockedAlarmManager);
		
		testElevatorCtrl.setCurrViewElevatorNumber(0);
		
		Mockito.verify(mockedElevator, Mockito.times(1)).setElevatorNumber(0);
	}
	
	@Test
	public void testRemoteErrorSetCurrentElevatorNumber() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedRmElevator, mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.doThrow(new java.rmi.RemoteException("Communication error")).when(mockedElevator).setElevatorNumber(0);
		
		testElevatorCtrl.setCurrViewElevatorNumber(0);
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addErrorMessage("Remote error (set elevator number): Communication error");
	}
	
	@Test
	public void testIllegalArgumentSetCurrentElevatorNumber() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedRmElevator, mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.doThrow(new IllegalArgumentException("Elevator number invalid")).when(mockedElevator).setElevatorNumber(1);
		
		testElevatorCtrl.setCurrViewElevatorNumber(1);
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addErrorMessage("Illegal argument (set elevator number): Elevator number invalid");
	}
}
