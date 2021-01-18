package at.fhhagenberg.sqe.gui;

import java.util.Timer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import at.fhhagenberg.sqe.controller.ElevatorController.eOperationStatus;
import at.fhhagenberg.sqe.model.Building;
import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.IWrapElevator;
import javafx.beans.property.IntegerProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
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
		Mockito.when(mockedRmElevator.getElevatorNum()).thenReturn(1);
		Mockito.when(mockedRmElevator.getFloorNum()).thenReturn(10);
		Mockito.when(mockedRmElevator.getElevatorWeight(0)).thenReturn(650);
		Mockito.when(mockedRmElevator.getElevatorPosIsTarget(0)).thenReturn(true);
	
		Mockito.when(mockedRmElevator.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_UP);
		Mockito.when(mockedRmElevator.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
		Mockito.when(mockedRmElevator.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_DOWN);
		
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
		Mockito.when(mockedRmElevator.getServicesFloors(0, 9)).thenReturn(false);
		
		Mockito.when(mockedRmElevator.getFloorButtonDown(1)).thenReturn(true);
		Mockito.when(mockedRmElevator.getFloorButtonUp(2)).thenReturn(true);
		
		Mockito.when(mockedRmElevator.getFloorButtonDown(3)).thenReturn(false);
		Mockito.when(mockedRmElevator.getFloorButtonUp(4)).thenReturn(false);
		
		Mockito.when(mockedRmElevator.getFloorButtonDown(5)).thenReturn(true);
		Mockito.when(mockedRmElevator.getFloorButtonUp(5)).thenReturn(true);
		
		
		Mockito.when(mockedRmElevator.getTarget(0)).thenReturn(0);
		Mockito.when(mockedRmElevator.getClockTick()).thenReturn(100L);
		
		
		appAlarmManager = new AlarmManager();
		modelElevator = new Elevator(mockedRmElevator);
		modelBuilding = new Building(mockedRmElevator, appAlarmManager);
		elevatorCtrl = new ElevatorController(modelElevator, modelBuilding, appAlarmManager);
		updateDataTimer = new Timer();	
		
		elevatorCtrl.setCurrViewElevatorNumber(0);
		initElevatorController();
		
					
		this.stage = stage;
		
		// On cancel button cancel update timer
		this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {			
		

			@Override
			public void handle(WindowEvent event) {
				updateDataTimer.cancel();
			}
		});	
		
		view = new MainView(elevatorCtrl, stage); 	
	}
	
	private void initElevatorController() {
		updateDataTimer.scheduleAtFixedRate(elevatorCtrl, 0, 100);
	}
	
	
	@Test
	public void testChangeModeButton(FxRobot robot) throws Exception
	{							
		Button button = (Button) stage.getScene().lookup("#1modeButton");
		assertEquals(button.getText(), "Manual");
		
		robot.clickOn("#1modeButton");		
		assertEquals(eOperationStatus.AUTOMATIC, elevatorCtrl.getOperationStatus());
		
		button = (Button) stage.getScene().lookup("#1modeButton");
		assertEquals(button.getText(), "Automatic");			
		
		robot.clickOn("#1modeButton");
		assertEquals(eOperationStatus.MANUAL, elevatorCtrl.getOperationStatus());		
		assertEquals(button.getText(), "Manual");
	}

	private void wait(FxRobot robot) {
		robot.sleep(500); 
	}
	
	@Test
	public void testCurrentPayload(FxRobot robot) {
		wait(robot);		
			
		verifyThat("#1currentPayloadLabel", hasText("Current Payload"));
		verifyThat("#1payloadTxtField", TextMatchers.hasText("650 lbs"));	
	}

	@Test
	public void testEndToEndGUIToModel(FxRobot robot) throws Exception
	{		
		robot.clickOn("#1floorButton5");
		Mockito.when(mockedRmElevator.getTarget(0)).thenReturn(5);
		Thread.sleep(500);
					
		Mockito.verify(mockedRmElevator,Mockito.times(1)).setTarget(0, 5);
		Mockito.verify(mockedRmElevator, Mockito.times(1)).setCommittedDirection(0, IElevator.ELEVATOR_DIRECTION_UP);

		Button button = (Button) stage.getScene().lookup("#1setButton5");
		assertTrue(button.isVisible());
	}
	
	@Test
	public void testEndToEndModelToGUIElevatorSpeed(FxRobot robot) throws Exception
	{
		//Mockito.when(mockedRmElevator.getElevatorSpeed(0)).thenReturn(44);
		Mockito.doReturn(44).when(mockedRmElevator).getElevatorSpeed(0);
		
		Thread.sleep(500);
		verifyThat("#1speedTxtField", TextMatchers.hasText("44 ft/s"));		
	}
	
	@Test
	public void testEndToEndModelToGUIElevatorWeight(FxRobot robot) throws Exception
	{
		Mockito.when(mockedRmElevator.getElevatorWeight(0)).thenReturn(100);
		Thread.sleep(500);
		verifyThat("#1payloadTxtField", TextMatchers.hasText("100 lbs"));			
	}

	@Test
	public void testChangeCommittedDirectionModelUpToGUI(FxRobot robot) throws Exception
	{
		Mockito.when(mockedRmElevator.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_UP);
		Thread.sleep(500);
		Button buttonUp = (Button) stage.getScene().lookup("#1arrowup");
		Button buttonDown = (Button) stage.getScene().lookup("#1arrowdown");
		
		assertEquals("-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #FF0000; -fx-stroke-width: 1; -fx-background-radius: 0", buttonUp.getStyle());
		assertEquals("-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #FFFFFF; -fx-stroke-width: 1; -fx-background-radius: 0", buttonDown.getStyle());		
	}
	
	@Test
	public void testChangeCommittedDirectionModelDownToGUI(FxRobot robot) throws Exception
	{
		Mockito.when(mockedRmElevator.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_DOWN);
		Thread.sleep(500);
		Button buttonUp = (Button) stage.getScene().lookup("#1arrowup");
		Button buttonDown = (Button) stage.getScene().lookup("#1arrowdown");
		assertEquals("-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #FFFFFF; -fx-stroke-width: 1; -fx-background-radius: 0", buttonUp.getStyle());
		assertEquals("-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #FF0000; -fx-stroke-width: 1; -fx-background-radius: 0", buttonDown.getStyle());
		
	}
	@Test
	public void testChangeCommittedDirectionModelUncommittedToGUI(FxRobot robot) throws Exception
	{
		Mockito.when(mockedRmElevator.getCommittedDirection(0)).thenReturn(IElevator.ELEVATOR_DIRECTION_UNCOMMITTED);
		Thread.sleep(500);
		Button buttonUp = (Button) stage.getScene().lookup("#1arrowup");
		Button buttonDown = (Button) stage.getScene().lookup("#1arrowdown");
		assertEquals("-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #FFFFFF; -fx-stroke-width: 1; -fx-background-radius: 0", buttonUp.getStyle());
		assertEquals("-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #FFFFFF; -fx-stroke-width: 1; -fx-background-radius: 0", buttonDown.getStyle());
		
	}
	
}