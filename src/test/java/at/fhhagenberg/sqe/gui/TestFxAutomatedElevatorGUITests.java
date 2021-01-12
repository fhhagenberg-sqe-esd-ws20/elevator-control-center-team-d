package at.fhhagenberg.sqe.gui;

import java.util.Timer;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import at.fhhagenberg.sqe.controller.AlarmManager;
import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.model.Building;
import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.IWrapElevator;
import javafx.event.EventHandler;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sqelevator.IElevator;

import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextMatchers;


@ExtendWith(ApplicationExtension.class)
@ExtendWith(MockitoExtension.class)
public class TestFxAutomatedElevatorGUITests 
{
	private MainView view;
	
	private AlarmManager appAlarmManager; 
	private Elevator modelElevator; 
	private Building modelBuilding;
	private ElevatorController elevatorCtrl;
	private Timer updateDataTimer;
    private IWrapElevator mockedRmElevator;

    private Stage stage;
	
	@Start
	public void start(Stage stage)throws Exception
	{		
		mockedRmElevator = Mockito.mock(IWrapElevator.class);
		Mockito.when(mockedRmElevator.getElevatorNum()).thenReturn(5);
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(10);
		Mockito.when(mockedRmElevator.getElevatorWeight(0)).thenReturn(650);
		Mockito.when(mockedRmElevator.getElevatorPosIsTarget(0)).thenReturn(true);
		Mockito.when(mockedRmElevator.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
		Mockito.when(mockedRmElevator.getElevatorDoorStatus(0)).thenReturn(IElevator.ELEVATOR_DOORS_OPEN);
		Mockito.when(mockedRmElevator.getElevatorFloor(0)).thenReturn(0);
		Mockito.when(mockedRmElevator.getElevatorSpeed(0)).thenReturn(0);
		
		Mockito.when(mockedRmElevator.getServicesFloors(0, 0)).thenReturn(true);
		Mockito.when(mockedRmElevator.getServicesFloors(0, 1)).thenReturn(true);
		Mockito.when(mockedRmElevator.getServicesFloors(0, 2)).thenReturn(true);
		Mockito.when(mockedRmElevator.getServicesFloors(0, 3)).thenReturn(true);
		Mockito.when(mockedRmElevator.getServicesFloors(0, 4)).thenReturn(true);
		Mockito.when(mockedRmElevator.getServicesFloors(0, 5)).thenReturn(true);
		Mockito.when(mockedRmElevator.getServicesFloors(0, 6)).thenReturn(true);
		Mockito.when(mockedRmElevator.getServicesFloors(0, 7)).thenReturn(true);
		Mockito.when(mockedRmElevator.getServicesFloors(0, 8)).thenReturn(true);
		Mockito.when(mockedRmElevator.getServicesFloors(0, 9)).thenReturn(true);
		
		Mockito.when(mockedRmElevator.getTarget(0)).thenReturn(0);
		Mockito.when(mockedRmElevator.getClockTick()).thenReturn(100L);
		
		
		appAlarmManager = new AlarmManager();
		modelElevator = new Elevator(mockedRmElevator);
		modelBuilding = new Building(mockedRmElevator, appAlarmManager);
		elevatorCtrl = new ElevatorController(mockedRmElevator, modelElevator, modelBuilding, appAlarmManager);
		updateDataTimer = new Timer();	
		
		elevatorCtrl.setCurrViewElevatorNumber(0);
		initElevatorController();
		
		view = new MainView(elevatorCtrl, stage); 		
		
		this.stage = stage;
		
		// On cancel button cancel update timer
		this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {			
		

			@Override
			public void handle(WindowEvent event) {
				updateDataTimer.cancel();
			}
		});	
	}
	
	private void initElevatorController() {
		updateDataTimer.scheduleAtFixedRate(elevatorCtrl, 0, 100);
	}
	
	@Disabled
	@Test
	public void testChangeManualToAutomatic(FxRobot robot)
	{		
		verifyThat("#5modeButton", LabeledMatchers.hasText("Manual"));
		
		robot.clickOn("#5modeButton");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		verifyThat("#5modeButton", LabeledMatchers.hasText("Automatic"));
	}
	
	private void wait(FxRobot robot) {
		robot.sleep(250); 
	}
	
	@Test
	public void testCurrentPayload(FxRobot robot) {
		wait(robot);		
			
		verifyThat("#5currentPayloadLabel", hasText("Current Payload"));
		verifyThat("#5payloadTxtField", TextMatchers.hasText("650 lbs"));	
	}

	@Test
	public void testEndToEnd(FxRobot robot) throws Exception
	{
		robot.clickOn("#5floorButton5");
		
		Mockito.verify(mockedRmElevator,Mockito.times(1)).setTarget(0, 5);
		Mockito.verify(mockedRmElevator, Mockito.times(1)).setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_UP);
		
		Mockito.when(mockedRmElevator.getTarget(0)).thenReturn(5);
				
		//verifyThat("#5setButton5", NodeMatchers.isVisible());
	}
}
