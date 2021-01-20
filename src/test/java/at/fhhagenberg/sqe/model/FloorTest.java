/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class to verify the functionality of the floor class.
 * @author Dominic Zopf
 *
 */
@ExtendWith(MockitoExtension.class)
class FloorTest {
	@Mock
	private IWrapElevator mockedRmElevator;
	
	@Test
	void testSetAndGetFloorNumber() {
		Floor testFloor = new Floor(mockedRmElevator, 1);
		
		assertEquals(1, testFloor.getFloorNumber());
	}
	
	@Test
	void testFloorButtonDownSetThenNotSet() throws java.rmi.RemoteException {
		Floor testFloor = new Floor(mockedRmElevator, 2);
		Mockito.when(mockedRmElevator.getFloorButtonDown(2)).thenReturn(Boolean.TRUE)
		                             .thenReturn(Boolean.FALSE);
		
		testFloor.updateFloorButtonDown();
		assertTrue(testFloor.floorButtonDown.getValue());
		
		testFloor.updateFloorButtonDown();
		assertFalse(testFloor.floorButtonDown.getValue());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getFloorButtonDown(2);
	}
	
	@Test
	void testFloorButtonUpSetThenNotSet() throws java.rmi.RemoteException {
		Floor testFloor = new Floor(mockedRmElevator, 0);
		Mockito.when(mockedRmElevator.getFloorButtonUp(0)).thenReturn(Boolean.TRUE)
		                             .thenReturn(Boolean.FALSE);
		
		testFloor.updateFloorButtonUp();
		assertTrue(testFloor.floorButtonUp.getValue());
		
		testFloor.updateFloorButtonUp();
		assertFalse(testFloor.floorButtonUp.getValue());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getFloorButtonUp(0);
	}
	
	@Test
	void testGetPropertyFloorButtonDown() throws java.rmi.RemoteException {
		Floor testFloor = new Floor(mockedRmElevator, 0);
		Mockito.when(mockedRmElevator.getFloorButtonDown(0)).thenReturn(Boolean.TRUE);
		testFloor.updateFloorButtonDown();
		
		assertTrue(testFloor.getFloorButtonDownProperty().getValue());		
	}
	
	@Test
	void testGetPropertyFloorButtonUp() throws java.rmi.RemoteException {
		Floor testFloor = new Floor(mockedRmElevator, 1);
		Mockito.when(mockedRmElevator.getFloorButtonUp(1)).thenReturn(Boolean.FALSE);
		testFloor.updateFloorButtonUp();
		
		assertFalse(testFloor.getFloorButtonUpProperty().getValue());	
	}
}
