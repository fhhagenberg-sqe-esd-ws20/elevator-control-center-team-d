/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import at.fhhagenberg.sqe.controller.IAlarmManager;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to verify the functionality of the building class.
 * @author Dominic Zopf
 *
 */
@ExtendWith(MockitoExtension.class)
public class BuildingTest {
	@Mock
	private IWrapElevator mockedRmElevator;
	@Mock
	private IAlarmManager mockedAlarmManager;
	
	@Test
	public void testGetFloorNumberOfBuilding() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(6);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
				
		assertEquals(6, testBuilding.FloorNumber.getValue());
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getFloorNum();
	}
	
	@Test
	public void testGetElevatorNumberOfBuilding() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getElevatorNum()).thenReturn(2);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(2, testBuilding.ElevatorNumber.getValue());
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getElevatorNum();
		Mockito.verify(mockedAlarmManager, Mockito.never()).addErrorMessage(Mockito.anyString());
	}
	
	@Test
	public void testGetBuildingInformationWithRemoteError() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getElevatorNum()).thenThrow(new java.rmi.RemoteException("Error get elevator number"));
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addErrorMessage("Remote error (init building information): Error get elevator number");
		assertEquals(0, testBuilding.FloorNumber.getValue());
		assertEquals(0, testBuilding.ElevatorNumber.getValue());
		assertEquals(0, testBuilding.FloorList.size());
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getFloorNum();
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getElevatorNum();
	}
	
	@Test
	public void testFloorListSizeWithFourAddedFloors() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(4);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(4, testBuilding.FloorList.size());
	}
	
	@Test
	public void testFloorListFloorNumbersWithThreeAddedFloors() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(3);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(0, testBuilding.FloorList.get(2).getFloorNumber());
		assertEquals(1, testBuilding.FloorList.get(1).getFloorNumber());
		assertEquals(2, testBuilding.FloorList.get(0).getFloorNumber());
	}
		
	@Test
	public void testFloorListEmpty() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(0);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(0, testBuilding.FloorList.size());
	}
	
	@Test
	public void testFloorListWithNegativeFloorNumber() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(-1);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(0, testBuilding.FloorList.size());
	}
	
	@Test
	public void testNegativeFloorNumber() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(-1);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(0, testBuilding.FloorNumber.getValue());
	}
	
	@Test
	public void testNegativeElevatorNumber() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getElevatorNum()).thenReturn(-5);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(0, testBuilding.ElevatorNumber.getValue());
	}
}
