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
public class FloorTest {
	@Mock
	private IWrapElevator mockedRmElevator;
	
	@Test
	public void testSetAndGetFloorNumber() {
		Floor testFloor = new Floor(mockedRmElevator, 1);
		
		assertEquals(1, testFloor.getFloorNumber());
	}
	
	@Test
	public void testFloorButtonDownSetThenNotSet() throws java.rmi.RemoteException {
		Floor testFloor = new Floor(mockedRmElevator, 2);
		Mockito.when(mockedRmElevator.getFloorButtonDown(2)).thenReturn(Boolean.TRUE)
		                             .thenReturn(Boolean.FALSE);
		
		testFloor.updateFloorButtonDown();
		assertTrue(testFloor.FloorButtonDown.getValue());
		
		testFloor.updateFloorButtonDown();
		assertFalse(testFloor.FloorButtonDown.getValue());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getFloorButtonDown(2);
	}
	
	@Test
	public void testFloorButtonUpSetThenNotSet() throws java.rmi.RemoteException {
		Floor testFloor = new Floor(mockedRmElevator, 0);
		Mockito.when(mockedRmElevator.getFloorButtonUp(0)).thenReturn(Boolean.TRUE)
		                             .thenReturn(Boolean.FALSE);
		
		testFloor.updateFloorButtonUp();
		assertTrue(testFloor.FloorButtonUp.getValue());
		
		testFloor.updateFloorButtonUp();
		assertFalse(testFloor.FloorButtonUp.getValue());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getFloorButtonUp(0);
	}
	
	@Test
	public void testGetPropertyFloorButtonDown() throws java.rmi.RemoteException {
		Floor testFloor = new Floor(mockedRmElevator, 0);
		Mockito.when(mockedRmElevator.getFloorButtonDown(0)).thenReturn(Boolean.TRUE);
		testFloor.updateFloorButtonDown();
		
		assertTrue(testFloor.getFloorButtonDownProperty().getValue());		
	}
	
	@Test
	public void testGetPropertyFloorButtonUp() throws java.rmi.RemoteException {
		Floor testFloor = new Floor(mockedRmElevator, 1);
		Mockito.when(mockedRmElevator.getFloorButtonUp(1)).thenReturn(Boolean.FALSE);
		testFloor.updateFloorButtonUp();
		
		assertFalse(testFloor.getFloorButtonUpProperty().getValue());	
	}
}
