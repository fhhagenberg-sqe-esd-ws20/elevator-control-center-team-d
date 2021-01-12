/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqe.controller.ElevatorController.eOperationStatus;
import at.fhhagenberg.sqe.model.Floor;
import at.fhhagenberg.sqe.model.IModelBuilding;
import at.fhhagenberg.sqe.model.IModelElevator;
import at.fhhagenberg.sqe.model.IWrapElevator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import sqelevator.IElevator;

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
	
	@BeforeEach
	public void initMocks() throws java.rmi.RemoteException {
		Mockito.when(mockedBuilding.getObservableFloorList()).thenReturn(FXCollections.observableArrayList(new Floor(mockedRmElevator, 0)));
		Mockito.when(mockedBuilding.getFloorNumber()).thenReturn(5);
		Mockito.when(mockedAlarmManager.getPropRemoteConnError()).thenReturn(new SimpleBooleanProperty());
	}
	
	@Test
	public void testInitializeUsedControllerObjects() throws java.rmi.RemoteException {	
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		
		assertNotNull(testElevatorCtrl.buildingModel);
		assertNotNull(testElevatorCtrl.elevatorModel);
		assertNotNull(testElevatorCtrl.ctrlAlarmManager);		
	}
	
	@Test
	public void testInitializeElevatorButtonListValuesAndSize() throws java.rmi.RemoteException {
		Mockito.when(mockedBuilding.getFloorNumber()).thenReturn(2);
		Mockito.when(mockedElevator.getElevatorButton(0)).thenReturn(true);
		Mockito.when(mockedElevator.getElevatorButton(1)).thenReturn(false);
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		
		assertTrue(testElevatorCtrl.ElevatorButtonList.get(0));
		assertFalse(testElevatorCtrl.ElevatorButtonList.get(1));
		assertEquals(2, testElevatorCtrl.ElevatorButtonList.size());
	}
	
	@Test
	public void testInitializeServiceFloorListValuesAndSize() throws java.rmi.RemoteException {
		Mockito.when(mockedBuilding.getFloorNumber()).thenReturn(2);
		Mockito.when(mockedElevator.getServicesFloors(0)).thenReturn(false);
		Mockito.when(mockedElevator.getServicesFloors(1)).thenReturn(true);
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		
		assertFalse(testElevatorCtrl.ServicesFloorList.get(0));
		assertTrue(testElevatorCtrl.ServicesFloorList.get(1));
		assertEquals(2, testElevatorCtrl.ServicesFloorList.size());
	}
	
	@Test
	public void testSetCurrentViewElevatorNumberZero() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		
		testElevatorCtrl.setCurrViewElevatorNumber(0);
		
		Mockito.verify(mockedElevator, Mockito.times(1)).setElevatorNumber(0);
	}
	
	@Test
	public void testRemoteErrorSetCurrentElevatorNumber() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.doThrow(new java.rmi.RemoteException("Communication error")).when(mockedElevator).setElevatorNumber(0);
		
		testElevatorCtrl.setCurrViewElevatorNumber(0);
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addErrorMessage("Remote error (set elevator number): Communication error");
	}
	
	@Test
	public void testIllegalArgumentSetCurrentElevatorNumber() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.doThrow(new IllegalArgumentException("Elevator number invalid")).when(mockedElevator).setElevatorNumber(1);
		
		testElevatorCtrl.setCurrViewElevatorNumber(1);
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addErrorMessage("Illegal argument (set elevator number): Elevator number invalid");
	}
	
	@Test
	public void testTimerRunMethodWithDeactivatedUpdate() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		testElevatorCtrl.setUpdateModelData(false);
		
		testElevatorCtrl.run();
		
		Mockito.verify(mockedElevator, Mockito.atMostOnce()).updateElevatorDoorStatus();
	}
	
	@Test
	public void testUpdateModelValuesCheckUpdateElevatorModel() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);	
		
		testElevatorCtrl.updateModelValues();
		
		Mockito.verify(mockedElevator, Mockito.times(2)).updateCommittedDirection();
		Mockito.verify(mockedElevator, Mockito.times(2)).updateElevatorDoorStatus();
		Mockito.verify(mockedElevator, Mockito.times(2)).updateElevatorFloor();
		Mockito.verify(mockedElevator, Mockito.times(2)).updateElevatorSpeed();
		Mockito.verify(mockedElevator, Mockito.times(2)).updateElevatorWeight();
		Mockito.verify(mockedElevator, Mockito.times(2)).updateTarget();
	}
	
	@Test
	public void testUpdateModelValuesCheckUpdateElevatorButtonList() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.when(mockedElevator.getElevatorButton(0)).thenReturn(true);
		
		testElevatorCtrl.updateModelValues();
		
		Mockito.verify(mockedElevator, Mockito.times(2)).getElevatorButton(0);
		assertEquals(true, testElevatorCtrl.ElevatorButtonList.get(0)); 
	}
	
	@Test
	public void testUpdateModelValuesCheckUpdateServicesFloorList() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.when(mockedElevator.getServicesFloors(0)).thenReturn(false);
		
		testElevatorCtrl.updateModelValues();
		
		Mockito.verify(mockedElevator, Mockito.times(2)).getServicesFloors(0);
		assertEquals(false, testElevatorCtrl.ServicesFloorList.get(0)); 
	}
	
	@Test
	public void testUpdateModelValuesCheckUpdateFloorButtons() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.when(mockedRmElevator.getFloorButtonDown(0)).thenReturn(false);
		Mockito.when(mockedRmElevator.getFloorButtonUp(0)).thenReturn(true);
		
		testElevatorCtrl.updateModelValues();
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getFloorButtonDown(0);
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getFloorButtonUp(0);
		assertFalse(testElevatorCtrl.buildingModel.getObservableFloorList().get(0).FloorButtonDown.getValue());
		assertTrue(testElevatorCtrl.buildingModel.getObservableFloorList().get(0).FloorButtonUp.getValue());
	}
	
	@Test
	public void testUpdateModelValuesErrorInClockTickThreeTimes() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.when(mockedElevator.getClockTick()).thenReturn(120001L).thenReturn(120005L).thenReturn(120001L).thenReturn(120005L).thenReturn(120001L).thenReturn(120005L);
		
		testElevatorCtrl.updateModelValues();
		
		Mockito.verify(mockedElevator, Mockito.times(4)).updateCommittedDirection();
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addErrorMessage("Clock tick error (update model values): Error in update model values: clock tick not equal after attempts!");
	}
	
	@Test
	public void testUpdateModelValuesErrorRemoteException() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.doThrow(new java.rmi.RemoteException("Communication error")).when(mockedElevator).updateElevatorSpeed();
		
		testElevatorCtrl.updateModelValues();
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addErrorMessage("Remote error (update model values): Communication error");
	}
	
	@Test
	public void testInitUpdateModelDataIsActivated() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		
		assertTrue(testElevatorCtrl.isUpdateModelData());
	}
	
	@Test
	public void testSetAndGetUpdateModelDataFromTrueToFalse() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		
		assertTrue(testElevatorCtrl.isUpdateModelData());
		
		testElevatorCtrl.setUpdateModelData(false);
		
		assertFalse(testElevatorCtrl.isUpdateModelData());
	}
	
	@Test
	public void testInitOperationStatusManual() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		
		assertEquals(eOperationStatus.MANUAL, testElevatorCtrl.getOperationStatus());
	}
	
	@Test
	public void testSetAndGetOperationStatusFromManualToAutomaticAndBack() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		
		assertEquals(eOperationStatus.MANUAL, testElevatorCtrl.getOperationStatus());
		
		testElevatorCtrl.switchOperationStatus();
		
		assertEquals(eOperationStatus.AUTOMATIC, testElevatorCtrl.getOperationStatus());
		
		testElevatorCtrl.switchOperationStatus();
		
		assertEquals(eOperationStatus.MANUAL, testElevatorCtrl.getOperationStatus());
	}
	
	@Test
	public void testSetNextElevatorTargetFromAutomaticMode() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		testElevatorCtrl.switchOperationStatus();
		
		assertFalse(testElevatorCtrl.setNextElevatorTarget(1));
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addWarningMessage("Warning (set next target): next elevator target can only be set in manual operation mode");
	}
	
	@Test
	public void testSetNextElevatorTargetWhenElevatorPosIsNotTarget() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.when(mockedElevator.getElevatorPosIsTarget()).thenReturn(false);
		
		assertFalse(testElevatorCtrl.setNextElevatorTarget(1));
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addWarningMessage("Warning (set next target): elevator not in previous target position");
	}
	
	@Test
	public void testSetNextElevatorTargetWhenElevatorSpeedIsNotZero() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.when(mockedElevator.getElevatorPosIsTarget()).thenReturn(true);
		Mockito.when(mockedElevator.getIElevatorSpeed()).thenReturn(15);
		
		assertFalse(testElevatorCtrl.setNextElevatorTarget(3));
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addWarningMessage("Warning (set next target): elevator not in previous target position");
	}
	
	@Test
	public void testSetNextElevatorTargetWhenDoorStatusIsNotOpen() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.when(mockedElevator.getIDoorStatus()).thenReturn(IElevator.ELEVATOR_DOORS_CLOSED);
		Mockito.when(mockedElevator.getElevatorPosIsTarget()).thenReturn(true);
		Mockito.when(mockedElevator.getIElevatorSpeed()).thenReturn(0);
		
		assertFalse(testElevatorCtrl.setNextElevatorTarget(3));
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addWarningMessage("Warning (set next target): elevator doors not open");
	}
	
	@Test
	public void testSetNextElevatorTargetWhenRemoteErrorOccurs() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.doThrow(new java.rmi.RemoteException("Communication error")).when(mockedElevator).getElevatorPosIsTarget();
		
		assertFalse(testElevatorCtrl.setNextElevatorTarget(2));
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addErrorMessage("Remote error (set next target): Communication error");
	}
	
	@Test
	public void testSetNextElevatorTargetWhenNextTargetIsEqualToElevatorCurrentFloor() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.when(mockedElevator.getIDoorStatus()).thenReturn(IElevator.ELEVATOR_DOORS_OPEN);
		Mockito.when(mockedElevator.getElevatorPosIsTarget()).thenReturn(true);
		Mockito.when(mockedElevator.getIElevatorSpeed()).thenReturn(0);
		Mockito.when(mockedElevator.getElevatorCurrFloor()).thenReturn(2);
		
		assertFalse(testElevatorCtrl.setNextElevatorTarget(2));
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addWarningMessage("Warning (set next target): set target could not be current elevator position");
	}
	
	@Test
	public void testSetNextElevatorTargetWhenCommitedDirectionIsUp() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.when(mockedElevator.getIDoorStatus()).thenReturn(IElevator.ELEVATOR_DOORS_OPEN);
		Mockito.when(mockedElevator.getElevatorPosIsTarget()).thenReturn(true);
		Mockito.when(mockedElevator.getIElevatorSpeed()).thenReturn(0);
		Mockito.when(mockedElevator.getElevatorCurrFloor()).thenReturn(0);
		
		assertTrue(testElevatorCtrl.setNextElevatorTarget(1));
		
		Mockito.verify(mockedElevator, Mockito.times(1)).setCommittedDirection(IElevator.ELEVATOR_DIRECTION_UP);
		Mockito.verify(mockedElevator, Mockito.times(1)).setTarget(1);
	}
	
	@Test
	public void testSetNextElevatorTargetWhenCommitedDirectionIsDown() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		Mockito.when(mockedElevator.getIDoorStatus()).thenReturn(IElevator.ELEVATOR_DOORS_OPEN);
		Mockito.when(mockedElevator.getElevatorPosIsTarget()).thenReturn(true);
		Mockito.when(mockedElevator.getIElevatorSpeed()).thenReturn(0);
		Mockito.when(mockedElevator.getElevatorCurrFloor()).thenReturn(3);
		
		assertTrue(testElevatorCtrl.setNextElevatorTarget(0));
		
		Mockito.verify(mockedElevator, Mockito.times(1)).setCommittedDirection(IElevator.ELEVATOR_DIRECTION_DOWN);
		Mockito.verify(mockedElevator, Mockito.times(1)).setTarget(0);
	}
	
	@Test
	public void testSetAndGetUseElevatorSimFirstTrueThenFalse() throws java.rmi.RemoteException {
		ElevatorController testElevatorCtrl = new ElevatorController(mockedElevator, mockedBuilding, mockedAlarmManager);
		
		testElevatorCtrl.setUseElevatorSim(true);
		assertTrue(testElevatorCtrl.isUseElevatorSim());
		
		testElevatorCtrl.setUseElevatorSim(false);
		assertFalse(testElevatorCtrl.isUseElevatorSim());
	}
}
