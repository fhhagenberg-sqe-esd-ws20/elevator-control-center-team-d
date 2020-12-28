package at.fhhagenberg.sqe.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class to verify the functionality of the elevator class.
 * @author Dominic Zopf
 *
 */
@ExtendWith(MockitoExtension.class)
public class ElevatorTest {	
	@Mock
	private IWrapElevator mockedRmElevator;
	
	@BeforeEach
	public void initMock() throws java.rmi.RemoteException {
		Mockito.when(mockedRmElevator.getElevatorNum()).thenReturn(4);
	}
	
	@Test
	public void testSetInvalidElevatorNumber() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);		
		
		Exception exception = assertThrows(IllegalArgumentException.class, () -> { testElevator.setElevatorNumber(4); });
		assertEquals("Error: Invalid elevator number set!", exception.getMessage());
		
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getElevatorNum();
	}
	
	@Test
	public void testGetElevatorNumberSetNumberOneTime() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);
		testElevator.setElevatorNumber(3);
		
		assertEquals(3, testElevator.getElevatorNumber());
	}
	
	@Test
	public void testGetElevatorNumberSetNumberTwoTimes() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);
		testElevator.setElevatorNumber(3);
		testElevator.setElevatorNumber(0);
		
		assertEquals(0, testElevator.getElevatorNumber());
	}
	
	@Test
	public void testElevatorPosIsTargetYesThenNo() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);
		testElevator.setElevatorNumber(2);
		Mockito.when(mockedRmElevator.getElevatorPosIsTarget(2)).thenReturn(Boolean.TRUE)
		 							 .thenReturn(Boolean.FALSE);
		
		assertTrue(testElevator.getElevatorPosIsTarget());
		assertFalse(testElevator.getElevatorPosIsTarget());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getElevatorPosIsTarget(2);
	}
	
	@Test
	public void testCommitedDirectionUpThenDown() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);				
		testElevator.setElevatorNumber(0);
		Mockito.when(mockedRmElevator.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_UP)
									 .thenReturn(IElevator.ELEVATOR_DIRECTION_DOWN);
		
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, testElevator.getCommittedDirection());
		assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN, testElevator.getCommittedDirection());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getCommittedDirection(0);
	}
	
	
	
	
}
