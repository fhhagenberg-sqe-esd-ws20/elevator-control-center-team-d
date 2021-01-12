/**
 * Name: Sajan Cherukad, Dominic Zopf
 */

package at.fhhagenberg.sqe.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import sqelevator.IElevator;

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
		assertEquals(IElevator.ELEVATOR_DOORS_OPEN, testElevator.getIDoorStatus());
		assertEquals("open", testElevator.getDoorStatus());
		
		testElevator.updateElevatorDoorStatus();
		assertEquals(IElevator.ELEVATOR_DOORS_CLOSED, testElevator.getIDoorStatus());
		assertEquals("closed", testElevator.getDoorStatus());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getElevatorDoorStatus(2);
	}
	
	@Test
	public void testDoorStatusOpeningThenClosing() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);				
		testElevator.setElevatorNumber(2);
		Mockito.when(mockedRmElevator.getElevatorDoorStatus(2)).thenReturn(IElevator.ELEVATOR_DOORS_OPENING)
									 .thenReturn(IElevator.ELEVATOR_DOORS_CLOSING);
		
		testElevator.updateElevatorDoorStatus();
		assertEquals(IElevator.ELEVATOR_DOORS_OPENING, testElevator.getIDoorStatus());
		assertEquals("opening", testElevator.getDoorStatus());
		
		testElevator.updateElevatorDoorStatus();
		assertEquals(IElevator.ELEVATOR_DOORS_CLOSING, testElevator.getIDoorStatus());
		assertEquals("closing", testElevator.getDoorStatus());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getElevatorDoorStatus(2);
	}
	
	@Test
	public void testDoorStatusInvalidState() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);				
		testElevator.setElevatorNumber(2);
		Mockito.when(mockedRmElevator.getElevatorDoorStatus(2)).thenReturn(10);
		
		testElevator.updateElevatorDoorStatus();
		
		assertEquals("undefined", testElevator.getDoorStatus());
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
		assertEquals(0, testElevator.getIElevatorSpeed());
		
		testElevator.updateElevatorSpeed();
		assertEquals(12, testElevator.getIElevatorSpeed());
		
		Mockito.verify(mockedRmElevator, Mockito.times(2)).getElevatorSpeed(2);
	}
	
	@Test
	public void testElevatorWeightWithTwoDifferentWeightValues() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);				
		testElevator.setElevatorNumber(2);
		Mockito.when(mockedRmElevator.getElevatorWeight(2)).thenReturn(210).thenReturn(102);
		
		testElevator.updateElevatorWeight();
		assertEquals(210, testElevator.getIElevatorWeight());
		
		testElevator.updateElevatorWeight();
		assertEquals(102, testElevator.getIElevatorWeight());
		
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
		assertEquals(75, testElevator.getIElevatorWeight());
		
		testElevator.setElevatorNumber(2);
		testElevator.updateElevatorWeight();
		assertEquals(412, testElevator.getIElevatorWeight());
		
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getElevatorWeight(0);
		Mockito.verify(mockedRmElevator, Mockito.times(1)).getElevatorWeight(2);
	}
	
	@Test
	public void testGetterCommitedDirection() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);	
		testElevator.setElevatorNumber(0);
		Mockito.when(mockedRmElevator.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_UP);
		
		testElevator.updateCommittedDirection();
		
		assertEquals(SimpleIntegerProperty.class, testElevator.getPropCommitedDirection().getClass());
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, testElevator.getPropCommitedDirection().getValue());
		assertEquals(IElevator.ELEVATOR_DIRECTION_UP, testElevator.getCommitedDirection());
	}
	
	@Test
	public void testGetterDoorStatus() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);	
		testElevator.setElevatorNumber(0);
		Mockito.when(mockedRmElevator.getElevatorDoorStatus(0)).thenReturn(IElevator.ELEVATOR_DOORS_CLOSED);
		
		testElevator.updateElevatorDoorStatus();
		
		assertEquals(SimpleStringProperty.class, testElevator.getPropDoorStatus().getClass());
		assertEquals("closed", testElevator.getPropDoorStatus().getValue());
		assertEquals("closed", testElevator.getDoorStatus());
		assertEquals(IElevator.ELEVATOR_DOORS_CLOSED, testElevator.getIDoorStatus());
	}
	
	@Test
	public void testGetterElevatorCurrFloor() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);	
		testElevator.setElevatorNumber(0);
		Mockito.when(mockedRmElevator.getElevatorFloor(0)).thenReturn(5);
		
		testElevator.updateElevatorFloor();
		
		assertEquals(SimpleIntegerProperty.class, testElevator.getPropElevatorCurrFloor().getClass());
		assertEquals(5, testElevator.getPropElevatorCurrFloor().getValue());
		assertEquals(5, testElevator.getElevatorCurrFloor());
	}
	
	@Test
	public void testGetterElevatorSpeed() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);	
		testElevator.setElevatorNumber(0);
		Mockito.when(mockedRmElevator.getElevatorSpeed(0)).thenReturn(14);
		
		testElevator.updateElevatorSpeed();
		
		assertEquals(SimpleStringProperty.class, testElevator.getPropElevatorSpeed().getClass());
		assertEquals("14 ft/s", testElevator.getPropElevatorSpeed().getValue());
		assertEquals("14 ft/s", testElevator.getElevatorSpeed());
		assertEquals(14, testElevator.getIElevatorSpeed());
	}
	
	@Test
	public void testGetterElevatorWeight() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);	
		testElevator.setElevatorNumber(0);
		Mockito.when(mockedRmElevator.getElevatorWeight(0)).thenReturn(412);
		
		testElevator.updateElevatorWeight();
		
		assertEquals(SimpleStringProperty.class, testElevator.getPropElevatorWeight().getClass());
		assertEquals("412 lbs", testElevator.getPropElevatorWeight().getValue());
		assertEquals("412 lbs", testElevator.getElevatorWeight());
		assertEquals(412, testElevator.getIElevatorWeight());
	}
	
	@Test
	public void testGetterElevatorCurrTarget() throws java.rmi.RemoteException {
		Elevator testElevator = new Elevator(mockedRmElevator);	
		testElevator.setElevatorNumber(0);
		Mockito.when(mockedRmElevator.getTarget(0)).thenReturn(2);
		
		testElevator.updateTarget();
		
		assertEquals(SimpleIntegerProperty.class, testElevator.getPropElevatorCurrTarget().getClass());
		assertEquals(2, testElevator.getPropElevatorCurrTarget().getValue());
		assertEquals(2, testElevator.getElevatorCurrTarget());
	}
}
