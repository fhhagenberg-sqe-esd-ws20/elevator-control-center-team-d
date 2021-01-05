/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class to verify the functionality of the elevator class.
 * @author Dominic Zopf
 *
 */
@Disabled
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
		
		testElevator.updateCommittedDirection();
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, testElevator.CommitedDirection.getValue());
		
		testElevator.updateCommittedDirection();		
		assertEquals(IElevator.ELEVATOR_DIRECTION_DOWN, testElevator.CommitedDirection.getValue());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getCommittedDirection(0);
	}
	
	@Test
	public void testElevatorButtonZeroFloorAndThirdFloor() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);				
		testElevator.setElevatorNumber(1);
		Mockito.when(mockedRmElevator.getElevatorButton(1, 0)).thenReturn(Boolean.TRUE);
		Mockito.when(mockedRmElevator.getElevatorButton(1, 3)).thenReturn(Boolean.FALSE);
		
		assertTrue(testElevator.getElevatorButton(0));
		assertFalse(testElevator.getElevatorButton(3));
		
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getElevatorButton(1, 0);
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getElevatorButton(1, 3);
	}
	
	@Test
	public void testDoorStatusOpenThenClosed() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);				
		testElevator.setElevatorNumber(2);
		Mockito.when(mockedRmElevator.getElevatorDoorStatus(2)).thenReturn(IElevator.ELEVATOR_DOORS_OPEN)
									 .thenReturn(IElevator.ELEVATOR_DOORS_CLOSED);
		
		testElevator.updateElevatorDoorStatus();
		assertEquals(IElevator.ELEVATOR_DOORS_OPEN, testElevator.DoorStatus.getValue());
		
		testElevator.updateElevatorDoorStatus();
		assertEquals(IElevator.ELEVATOR_DOORS_CLOSED, testElevator.DoorStatus.getValue());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getElevatorDoorStatus(2);
	}
	
	@Test
	public void testElevatorCurrentFloorZeroThenSecond() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);				
		testElevator.setElevatorNumber(3);
		Mockito.when(mockedRmElevator.getElevatorFloor(3)).thenReturn(0).thenReturn(2);
		
		testElevator.updateElevatorFloor();
		assertEquals(0, testElevator.ElevatorCurrFloor.getValue());
		
		testElevator.updateElevatorFloor();
		assertEquals(2, testElevator.ElevatorCurrFloor.getValue());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getElevatorFloor(3);
	}
	
	@Test
	public void testElevatorSpeedWithTwoDifferentSpeedValues() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);				
		testElevator.setElevatorNumber(2);
		Mockito.when(mockedRmElevator.getElevatorSpeed(2)).thenReturn(0).thenReturn(12);
		
		testElevator.updateElevatorSpeed();
		assertEquals(0, testElevator.ElevatorSpeed.getValue());
		
		testElevator.updateElevatorSpeed();
		assertEquals(12, testElevator.ElevatorSpeed.getValue());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getElevatorSpeed(2);
	}
	
	@Test
	public void testElevatorWeightWithTwoDifferentWeightValues() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);				
		testElevator.setElevatorNumber(2);
		Mockito.when(mockedRmElevator.getElevatorWeight(2)).thenReturn(210).thenReturn(102);
		
		testElevator.updateElevatorWeight();
		assertEquals(210, testElevator.ElevatorWeight.getValue());
		
		testElevator.updateElevatorWeight();
		assertEquals(102, testElevator.ElevatorWeight.getValue());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getElevatorWeight(2);
	}
	
	@Test
	public void testServiceFloorNoThenYes() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);				
		testElevator.setElevatorNumber(1);
		Mockito.when(mockedRmElevator.getServicesFloors(1, 2)).thenReturn(Boolean.FALSE);
		Mockito.when(mockedRmElevator.getServicesFloors(1, 3)).thenReturn(Boolean.TRUE);
		
		assertFalse(testElevator.getServicesFloors(2));
		assertTrue(testElevator.getServicesFloors(3));
		
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getServicesFloors(1, 2);
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getServicesFloors(1, 3);
	}
	
	@Test
	public void testElevatorCurrentTargetWithTwoDifferentTargets() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);				
		testElevator.setElevatorNumber(2);
		Mockito.when(mockedRmElevator.getTarget(2)).thenReturn(0).thenReturn(5);
		
		testElevator.updateTarget();
		assertEquals(0, testElevator.ElevatorCurrTarget.getValue());
		
		testElevator.updateTarget();
		assertEquals(5, testElevator.ElevatorCurrTarget.getValue());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getTarget(2);
	}
	
	@Test
	public void testClockTickWithTwoDifferentValues() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);	
		testElevator.setElevatorNumber(2);
		Mockito.when(mockedRmElevator.getClockTick()).thenReturn(1120025L).thenReturn(1120156L);
		
		assertEquals(1120025L, testElevator.getClockTick());
		assertEquals(1120156L, testElevator.getClockTick());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getClockTick();
	}
	
	@Test
	public void testSetCommitedDirectionUpThenUncommited() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);	
		testElevator.setElevatorNumber(2);
		
		testElevator.setCommittedDirection(IElevator.ELEVATOR_DIRECTION_UP);
		testElevator.setCommittedDirection(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
		
		Mockito.verify(mockedRmElevator, Mockito.times(1)).setCommittedDirection(2, IElevator.ELEVATOR_DIRECTION_UP);
		Mockito.verify(mockedRmElevator, Mockito.times(1)).setCommittedDirection(2, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
	}
	
	@Test
	public void testSetTargetWithTwoDifferentTargets() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);	
		testElevator.setElevatorNumber(0);
		
		testElevator.setTarget(2);
		testElevator.setTarget(4);
		
		Mockito.verify(mockedRmElevator, Mockito.times(1)).setTarget(0, 2);
		Mockito.verify(mockedRmElevator, Mockito.times(1)).setTarget(0, 4);
	}
	
	@Test
	public void testGetElevatorWeightFromTwoDifferentElevators() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);	
		testElevator.setElevatorNumber(0);
		Mockito.when(mockedRmElevator.getElevatorWeight(0)).thenReturn(75);
		Mockito.when(mockedRmElevator.getElevatorWeight(2)).thenReturn(412);
		
		testElevator.updateElevatorWeight();
		assertEquals(75, testElevator.ElevatorWeight.getValue());
		
		testElevator.setElevatorNumber(2);
		testElevator.updateElevatorWeight();
		assertEquals(412, testElevator.ElevatorWeight.getValue());
		
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getElevatorWeight(0);
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getElevatorWeight(2);
	}
}
