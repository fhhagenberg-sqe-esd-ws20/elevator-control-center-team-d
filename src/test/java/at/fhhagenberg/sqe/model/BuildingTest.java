/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sun.javafx.collections.ObservableListWrapper;

import at.fhhagenberg.sqe.controller.IAlarmManager;
import javafx.beans.property.SimpleIntegerProperty;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to verify the functionality of the building class.
 * @author Dominic Zopf
 *
 */
@ExtendWith(MockitoExtension.class)
class BuildingTest {
	@Mock
	private IWrapElevator mockedRmElevator;
	@Mock
	private IAlarmManager mockedAlarmManager;
	
	@Test
	void testGetFloorNumberOfBuilding() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(6);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
				
		assertEquals(6, testBuilding.floorNumber.getValue());
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getFloorNum();
	}
	
	@Test
	void testGetElevatorNumberOfBuilding() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getElevatorNum()).thenReturn(2);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(2, testBuilding.elevatorNumber.getValue());
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getElevatorNum();
		Mockito.verify(mockedAlarmManager, Mockito.never()).addErrorMessage(Mockito.anyString());
	}
	
	@Test
	void testGetBuildingInformationWithRemoteError() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getElevatorNum()).thenThrow(new java.rmi.RemoteException("Error get elevator number"));
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		Mockito.verify(mockedAlarmManager, Mockito.times(1)).addErrorMessage("Remote error (init building information): Error get elevator number");
		assertEquals(0, testBuilding.floorNumber.getValue());
		assertEquals(0, testBuilding.elevatorNumber.getValue());
		assertEquals(0, testBuilding.floorList.size());
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getFloorNum();
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getElevatorNum();
	}
	
	@Test
	void testFloorListSizeWithFourAddedFloors() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(4);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(4, testBuilding.floorList.size());
	}
	
	@Test
	void testFloorListFloorNumbersWithThreeAddedFloors() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(3);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(0, testBuilding.floorList.get(2).getFloorNumber());
		assertEquals(1, testBuilding.floorList.get(1).getFloorNumber());
		assertEquals(2, testBuilding.floorList.get(0).getFloorNumber());
	}
		
	@Test
	void testFloorListEmpty() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(0);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(0, testBuilding.floorList.size());
	}
	
	@Test
	void testFloorListWithNegativeFloorNumber() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(-1);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(0, testBuilding.floorList.size());
	}
	
	@Test
	void testNegativeFloorNumber() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(-1);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(0, testBuilding.floorNumber.getValue());
	}
	
	@Test
	void testNegativeElevatorNumber() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getElevatorNum()).thenReturn(-5);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(0, testBuilding.elevatorNumber.getValue());
	}
	
	@Test
	void testGetterPropertyFloorNumber() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(2);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(SimpleIntegerProperty.class, testBuilding.getPropFloorNumber().getClass());
		assertEquals(2, testBuilding.getPropFloorNumber().getValue());
	}
	
	@Test
	void testGetterPropertyElevatorNumber() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getElevatorNum()).thenReturn(4);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(SimpleIntegerProperty.class, testBuilding.getPropElevatorNumber().getClass());
		assertEquals(4, testBuilding.getPropElevatorNumber().getValue());
	}
	
	@Test
	void testGetterObservableFloorList() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(2);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(ObservableListWrapper.class, testBuilding.getObservableFloorList().getClass());
		assertEquals(2, testBuilding.getObservableFloorList().size());
	}
	
	@Test
	void testGetterFloorNumber() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(6);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(6, testBuilding.getFloorNumber());
	}
	
	@Test
	void testGetterElevatorNumber() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getElevatorNum()).thenReturn(3);
		
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);
		
		assertEquals(3, testBuilding.getElevatorNumber());
	}
	
	@Test
	void testSetRemoteElevatorWithTwoDifferentMocks() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(3);
		Building testBuilding = new Building(mockedRmElevator, mockedAlarmManager);		
		Mockito.when(mockedRmElevator.getFloorButtonDown(1)).thenReturn(false);
		
		testBuilding.floorList.get(1).updateFloorButtonDown();
		assertFalse(testBuilding.floorList.get(1).getFloorButtonDownProperty().getValue());
		
		IWrapElevator mockedNewRmElevator = Mockito.mock(IWrapElevator.class);
		Mockito.when(mockedNewRmElevator.getFloorButtonDown(1)).thenReturn(true);
		
		testBuilding.setRemoteElevator(mockedNewRmElevator);
		
		testBuilding.floorList.get(1).updateFloorButtonDown();
		assertTrue(testBuilding.floorList.get(1).getFloorButtonDownProperty().getValue());
	}
}
