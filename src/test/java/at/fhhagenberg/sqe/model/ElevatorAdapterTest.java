/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import sqelevator.IElevator;

/**
 * Test class to verify the functionality of the elevator adapter class.
 * @author Dominic Zopf
 *
 */
@ExtendWith(MockitoExtension.class)
class ElevatorAdapterTest {
	@Mock
	private IElevator mockedElevator;
	
	@Test
	void testGetElevatorPosIsTargetWhenElevatorPosIsTarget() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getElevatorFloor(2)).thenReturn(4);
		Mockito.when(mockedElevator.getTarget(2)).thenReturn(4);
		
		assertTrue(testElevatorAdapter.getElevatorPosIsTarget(2));
		
		Mockito.verify(mockedElevator, Mockito.times(1)).getElevatorFloor(2);
		Mockito.verify(mockedElevator, Mockito.times(1)).getTarget(2);
	}
	
	@Test
	void testGetElevatorPosIsTargetWhenElevatorPosIsNotTarget() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getElevatorFloor(3)).thenReturn(2);
		Mockito.when(mockedElevator.getTarget(3)).thenReturn(4);
		
		assertFalse(testElevatorAdapter.getElevatorPosIsTarget(3));
		
		Mockito.verify(mockedElevator, Mockito.times(1)).getElevatorFloor(3);
		Mockito.verify(mockedElevator, Mockito.times(1)).getTarget(3);
	}
	
	@Test
	void testGetCommitedDirectionUpThenUncommited() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getCommittedDirection(1)).thenReturn(IElevator.ELEVATOR_DIRECTION_UP)
					 .thenReturn(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
		
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, testElevatorAdapter.getCommittedDirection(1));
		assertEquals(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED, testElevatorAdapter.getCommittedDirection(1));
		
		Mockito.verify(mockedElevator, Mockito.times(2)).getCommittedDirection(1);
	}
	
	@Test
	void testGetElevatorButtonPressedThenNotPressedOnDifferentFloors() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getElevatorButton(1, 4)).thenReturn(true);
		Mockito.when(mockedElevator.getElevatorButton(1, 2)).thenReturn(false);
		
		assertTrue(testElevatorAdapter.getElevatorButton(1, 4));
		assertFalse(testElevatorAdapter.getElevatorButton(1, 2));
		
		Mockito.verify(mockedElevator, Mockito.times(1)).getElevatorButton(1, 4);
		Mockito.verify(mockedElevator, Mockito.times(1)).getElevatorButton(1, 2);
	}
	
	@Test
	void testGetElevatorDoorStatusOpenThenClosed() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getElevatorDoorStatus(0)).thenReturn(IElevator.ELEVATOR_DOORS_OPEN)
					 .thenReturn(IElevator.ELEVATOR_DOORS_CLOSED);
		
		assertEquals(IElevator.ELEVATOR_DOORS_OPEN, testElevatorAdapter.getElevatorDoorStatus(0));
		assertEquals(IElevator.ELEVATOR_DOORS_CLOSED, testElevatorAdapter.getElevatorDoorStatus(0));
		
		Mockito.verify(mockedElevator, Mockito.times(2)).getElevatorDoorStatus(0);
	}
	
	@Test
	void testGetElevatorFloorZeroThenFloorFive() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getElevatorFloor(0)).thenReturn(0).thenReturn(5);
		
		assertEquals(0, testElevatorAdapter.getElevatorFloor(0));
		assertEquals(5, testElevatorAdapter.getElevatorFloor(0));
		
		Mockito.verify(mockedElevator, Mockito.times(2)).getElevatorFloor(0);
	}
	
	@Test
	void testGetElevatorNumWithOneElevatorAndFourElevators() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getElevatorNum()).thenReturn(1).thenReturn(4);
		
		assertEquals(1, testElevatorAdapter.getElevatorNum());
		assertEquals(4, testElevatorAdapter.getElevatorNum());
		
		Mockito.verify(mockedElevator, Mockito.times(2)).getElevatorNum();
	}
	
	@Test
	void testGetElevatorSpeedWithZeroSpeedAndPositiveValue() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getElevatorSpeed(1)).thenReturn(0).thenReturn(17);
		
		assertEquals(0, testElevatorAdapter.getElevatorSpeed(1));
		assertEquals(17, testElevatorAdapter.getElevatorSpeed(1));
		
		Mockito.verify(mockedElevator, Mockito.times(2)).getElevatorSpeed(1);
	}
	
	@Test
	void testGetElevatorWeightWithZeroWeightAndPositiveValue() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getElevatorWeight(8)).thenReturn(0).thenReturn(1361);
		
		assertEquals(0, testElevatorAdapter.getElevatorWeight(8));
		assertEquals(1361, testElevatorAdapter.getElevatorWeight(8));
		
		Mockito.verify(mockedElevator, Mockito.times(2)).getElevatorWeight(8);
	}
	
	@Test
	void testGetFloorButtonDownActiveThenInactive() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getFloorButtonDown(2)).thenReturn(true).thenReturn(false);
		
		assertTrue(testElevatorAdapter.getFloorButtonDown(2));
		assertFalse(testElevatorAdapter.getFloorButtonDown(2));
		
		Mockito.verify(mockedElevator, Mockito.times(2)).getFloorButtonDown(2);
	}
	
	@Test
	void testGetFloorButtonUpInactiveThenActive() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getFloorButtonUp(3)).thenReturn(false).thenReturn(true);
		
		assertFalse(testElevatorAdapter.getFloorButtonUp(3));
		assertTrue(testElevatorAdapter.getFloorButtonUp(3));
		
		Mockito.verify(mockedElevator, Mockito.times(2)).getFloorButtonUp(3);
	}
	
	@Test
	void testGetFloorNumWithTwoDifferentFloorNumbers() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getFloorNum()).thenReturn(15).thenReturn(4);
		
		assertEquals(15, testElevatorAdapter.getFloorNum());
		assertEquals(4, testElevatorAdapter.getFloorNum());
		
		Mockito.verify(mockedElevator, Mockito.times(2)).getFloorNum();
	}
	
	@Test
	void testGetServicesFloorsWhenFirstServedAndSecondNotServed() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getServicesFloors(0, 1)).thenReturn(true);
		Mockito.when(mockedElevator.getServicesFloors(0, 2)).thenReturn(false);
		
		assertTrue(testElevatorAdapter.getServicesFloors(0, 1));
		assertFalse(testElevatorAdapter.getServicesFloors(0, 2));
		
		Mockito.verify(mockedElevator, Mockito.times(1)).getServicesFloors(0, 1);
		Mockito.verify(mockedElevator, Mockito.times(1)).getServicesFloors(0, 2);
	}
	
	@Test
	void testGetTargetWithTwoDifferentTargets() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getTarget(0)).thenReturn(4).thenReturn(7);
		
		assertEquals(4, testElevatorAdapter.getTarget(0));
		assertEquals(7, testElevatorAdapter.getTarget(0));
		
		Mockito.verify(mockedElevator, Mockito.times(2)).getTarget(0);
	}
	
	@Test
	void testGetClockTickWithTwoDifferentClockTicks() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.when(mockedElevator.getClockTick()).thenReturn(15542L).thenReturn(415L);
		
		assertEquals(15542L, testElevatorAdapter.getClockTick());
		assertEquals(415L, testElevatorAdapter.getClockTick());
		
		Mockito.verify(mockedElevator, Mockito.times(2)).getClockTick();
	}
	
	@Test
	void testSetCommittedDirectionWithTwoDifferentDirections() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		
		testElevatorAdapter.setCommittedDirection(2, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
		testElevatorAdapter.setCommittedDirection(2, IElevator.ELEVATOR_DIRECTION_DOWN);
		
		Mockito.verify(mockedElevator, Mockito.times(1)).setCommittedDirection(2, IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
		Mockito.verify(mockedElevator, Mockito.times(1)).setCommittedDirection(2, IElevator.ELEVATOR_DIRECTION_DOWN);
	}
	
	@Test
	void testSetTargetWithTwoDifferentTargets() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		
		testElevatorAdapter.setTarget(0, 9);
		testElevatorAdapter.setTarget(0, 0);
		
		Mockito.verify(mockedElevator, Mockito.times(1)).setTarget(0, 9);
		Mockito.verify(mockedElevator, Mockito.times(1)).setTarget(0, 0);
	}
	
	@Test
	void testSetTargetAndGetRemoteException() throws Exception {
		ElevatorAdapter testElevatorAdapter = new ElevatorAdapter(mockedElevator);
		Mockito.doThrow(new RemoteException("Communication error")).when(mockedElevator).setTarget(2, 10);
		
		Exception exception = assertThrows(RemoteException.class, () -> { testElevatorAdapter.setTarget(2, 10); });
		assertEquals("Communication error", exception.getMessage());
		
		Mockito.verify(mockedElevator, Mockito.times(1)).setTarget(2, 10);
	}
}
