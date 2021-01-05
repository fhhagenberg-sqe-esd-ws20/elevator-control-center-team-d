package at.fhhagenberg.sqe.gui;

import java.io.FileInputStream;
import java.util.ArrayList;

import javax.swing.GroupLayout.Alignment;

import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.controller.ElevatorController.eOperationStatus;
import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.Floor;
import at.fhhagenberg.sqe.model.IElevator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ElevatorView extends GridPane {
	private ElevatorController elevatorModel;

	//private Stage stage;
	// private Label label;
	// private Button countButton;
	// private Text sceneTitle;

//	private Image uparrow;
//	private Image downarrow;
	private Label emptySpacingLabel;

	private Button modeButton;
	private Button statusButton;
	private Label elevDirectionLabel; //
	private Button arrowup;
	private Button arrowdown;
	private Label currentPayloadLabel;
	private Text speedTxtField;
	private Label doorStatusLabel;
	private Text doorStatusTxtField;

	private ArrayList<Button> floorButtonList = new ArrayList<Button>();
	private ArrayList<Button> setButtonList = new ArrayList<Button>();
	private ArrayList<Button> requestButtonList = new ArrayList<Button>();
	
	
	
	ListView<Floor> testFloorList;

	private GridPane elevatorGrid;

	private GridPane layoutGrid;
	private int LabelSpacing = 70;

	private String style = "-fx-background-color: #ffffff; " + "-fx-border-color:  #545454;\r\n"
			+ "  -fx-border-width: 2px;\r\n" + "  -fx-border-style: solid;";

	private int idNumber;

	EventHandler<ActionEvent> switchModeHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {

			elevatorModel.switchOperationStatus();
			if (elevatorModel.getOperationStatus() == eOperationStatus.MANUAL) {
				modeButton.setText("Manual");
			} else {
				modeButton.setText("Automatic");
			}

			event.consume();
		}
	};

	private Node speedLabel;

	
	public GridPane getLayoutGrid()
	{
		return layoutGrid;
	}
	
	private GridPane CreateFloors() {
		GridPane floorPane = new GridPane();

		int positionInGrid = 1;

		elevatorModel.ElevatorButtonList.addListener(new ListChangeListener<Boolean>() {
			@Override
			public void onChanged(Change<? extends Boolean> c) {
				// TODO Auto-generated method stub

				for (int i = 0; i < elevatorModel.ElevatorButtonList.size(); i++) {
					if (elevatorModel.ElevatorButtonList.get(i)) {
						if (setButtonList.size() != 0) {
							if (elevatorModel.ElevatorButtonList.get(i)) {
								requestButtonList.get(i).setText("stop");
								// setButtonList.get(i).setVisible(true);
							} else {
								requestButtonList.get(i).setText("");
								// setButtonList.get(i).setVisible(false);
							}
						}
					}
				}
			};
		});
		

		elevatorModel.elevatorModel.getPropElevatorCurrTarget().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				setButtonList.get((Integer) newValue).setVisible(true);
				setButtonList.get((Integer) oldValue).setVisible(false);
			}
		});
		
		
		
		
		//change listener for current elevator position
		elevatorModel.elevatorModel.getPropElevatorCurrFloor().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				floorButtonList.get((Integer) newValue).setStyle("-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #0000FF; -fx-stroke-width: 1; -fx-pref-width: "
						+ LabelSpacing * 2 + "; -fx-background-radius: 0");
				floorButtonList.get((Integer) oldValue).setStyle("-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #90ee90; -fx-stroke-width: 1; -fx-pref-width: "
						+ LabelSpacing * 2 + "; -fx-background-radius: 0");
			}
		});

		for (int i = elevatorModel.buildingModel.getFloorNumber() - 1; i >= 0; i--) 
		{
			Button floorButton = new Button("Floor " + i);
			Button setButton = new Button("set");
			Button requestButton = new Button("none");

			if (elevatorModel.ServicesFloorList.get(i)) {
				floorButton.setStyle(
						"-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #90ee90; -fx-stroke-width: 1; -fx-pref-width: "
								+ LabelSpacing * 2 + "; -fx-background-radius: 0");
			} else {
				floorButton.setStyle(
						"-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #808080; -fx-stroke-width: 1; -fx-pref-width: "
								+ LabelSpacing * 2 + "; -fx-background-radius: 0");
				floorButton.setDisable(true);
			}

			setButton.setStyle(
					"-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #90ee90; -fx-stroke-width: 1; -fx-pref-width: "
							+ LabelSpacing / 2 + "; -fx-background-radius: 0");
			requestButton.setStyle(
					"-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #90ee90; -fx-stroke-width: 1; -fx-pref-width: "
							+ LabelSpacing + "; -fx-background-radius: 0");

			if (i == elevatorModel.elevatorModel.getElevatorCurrTarget()) {
				setButton.setVisible(true);
			} else {
				setButton.setVisible(false);
			}

			// set id
			floorButton.setId(String.valueOf(idNumber) + "floorButton" + Integer.toString(i));
			setButton.setId(String.valueOf(idNumber) + "setButton" + Integer.toString(i));
			requestButton.setId(String.valueOf(idNumber) + "requestButton" + Integer.toString(i));

			floorButtonList.add(0, floorButton);
			setButtonList.add(0, setButton);
			requestButtonList.add(0, requestButton);

			final int constantIndex = i;
			
			if (elevatorModel.buildingModel.getObservableFloorList().get(i).getFloorButtonUpProperty().getValue()) {
				requestButton.setText("call");
			} else {
				requestButton.setText("none");
			}
			
			
			elevatorModel.buildingModel.getObservableFloorList().get(i).getFloorButtonUpProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue) {
						requestButtonList.get(constantIndex).setText("call");
					} else {
						requestButtonList.get(constantIndex).setText("");
					}
				}
								
			});
			
			floorButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
				//	if (elevatorModel.setNextElevatorTarget(constantIndex))
					{
						if (elevatorModel.getOperationStatus() == eOperationStatus.MANUAL) 
						{

							for (var elem : floorButtonList) 
							{
								if (!elem.isDisabled())
									elem.setStyle(
											"-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #90ee90; -fx-stroke-width: 1; -fx-pref-width: "
													+ LabelSpacing * 2 + "; -fx-background-radius: 0");
							}
							floorButton.setStyle(
									"-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #006400; -fx-stroke-width: 1; -fx-pref-width: "
											+ LabelSpacing * 2 + "; -fx-background-radius: 0");
						}
					}
					event.consume();
				}
			});
			modeButton.setId(String.valueOf(idNumber) + "modeButton");

			elevatorGrid.add(requestButton, 1, positionInGrid);
			elevatorGrid.add(floorButton, 2, positionInGrid);
			elevatorGrid.add(setButton, 3, positionInGrid);
			positionInGrid++;
		}
		
		floorButtonList.get(elevatorModel.elevatorModel.getElevatorCurrFloor()).setStyle("-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #0000FF; -fx-stroke-width: 1; -fx-pref-width: "
											+ LabelSpacing * 2 + "; -fx-background-radius: 0");
		//elevatorModel.elevatorModel.getElevatorCurrFloor()

		return floorPane;
	}

	public ElevatorView(ElevatorController model, Stage stage) 
	{
		super();
		
//		emptySpacingLabel = new Label();
//		emptySpacingLabel.setPrefWidth(50);

		this.elevatorModel = model;
		//this.stage = stage;
		idNumber = model.buildingModel.getElevatorNumber();

//		label = new Label("Countdown: ");
//		countButton = new Button("Count");
		// stage.setScene(new Scene(new VBox(label, countButton)));

		modeButton = new Button("Manual");
		modeButton.setStyle(
				"-fx-border-width: 0; -fx-background-color: #ff0000; -fx-stroke-width: 1; -fx-stroke-width: 1;  -fx-background-radius: 0");
		// set id
		modeButton.addEventHandler(ActionEvent.ACTION, switchModeHandler);
		modeButton.setPrefWidth(100);
		modeButton.setPrefHeight(20);
		VBox modeVbox = new VBox(modeButton);
		modeVbox.setAlignment(Pos.BASELINE_CENTER);

		statusButton = new Button("Status");
		VBox statusVbox = new VBox(statusButton);
		statusVbox.setAlignment(Pos.BASELINE_CENTER);
		// modeLabel.setBorder(2);

		layoutGrid = new GridPane();

		layoutGrid.setPrefSize(800, 400);
		layoutGrid.setVgap(20);
		layoutGrid.setHgap(20);
		layoutGrid.setPadding(new Insets(20, 20, 20, 20));
		layoutGrid.setAlignment(Pos.CENTER);

		// layoutGrid.add(modeAndStatusHbox, 0, 0, 2, 1);
		// -fx-pref-width: 100;

		GridPane.setFillWidth(modeButton, true);
		layoutGrid.add(modeButton, 0, 0);
		layoutGrid.add(statusButton, 1, 0);

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Elevator direction label and Arrows:
		elevDirectionLabel = new Label("Elevator Direction");

		// Passing FileInputStream object as a parameter

		// https://www.flaticon.com/de/kostenloses-icon/dreieckiger-pfeil-nach-unten_20972
//		uparrow = new Image(("../../../../../resources/images/arrowup.png").toString(), true);//new Image(new FileInputStream("D:\\ESD3\\SQE\\Eclipse_Workspace\\Assignments\\Projekt\\elevator-control-center-team-d\\src\\main\\resources\\images\\arrowup.png"));
//		downarrow = new Image(("../../../../../resources/images/arrowdown.png").toString(), true);//new Image(new FileInputStream("D:\\ESD3\\SQE\\Eclipse_Workspace\\Assignments\\Projekt\\elevator-control-center-team-d\\src\\main\\resources\\images\\arrowdown.png"));

//		uparrow = new Image(getClass().getClassLoader().getResource("arrowup.png").toString(), true);//new Image(new FileInputStream("D:\\ESD3\\SQE\\Eclipse_Workspace\\Assignments\\Projekt\\elevator-control-center-team-d\\src\\main\\resources\\images\\arrowup.png"));
//		downarrow = new Image(getClass().getClassLoader().getResource("arrowdown.png").toString(), true);//new Image(new FileInputStream("D:\\ESD3\\SQE\\Eclipse_Workspace\\Assignments\\Projekt\\elevator-control-center-team-d\\src\\main\\resources\\images\\arrowdown.png"));
//		
//		ImageView imgViewUp = new ImageView(uparrow);
//		ImageView imgViewDown = new ImageView(downarrow);
//		imgViewUp.setFitWidth(50);
//		imgViewDown.setFitWidth(50);
//		imgViewDown.setPreserveRatio(true);
//		imgViewUp.setPreserveRatio(true);
//		
//
		arrowup = new Button("up");
		arrowdown = new Button("down");
		
		if (elevatorModel.elevatorModel.getCommitedDirection() == IElevator.ELEVATOR_DIRECTION_DOWN) {
			arrowdown.setStyle(
					"-fx-border-width: 2; -fx-border-style: solid; -fx-background-color: #FF0000; -fx-stroke-width: 1; -fx-background-radius: 0");
			arrowup.setStyle(
					"-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #FFFFFF; -fx-stroke-width: 1; -fx-background-radius: 0");
		} else if (elevatorModel.elevatorModel.getCommitedDirection() == IElevator.ELEVATOR_DIRECTION_UP) {
			arrowdown.setStyle(
					"-fx-border-width: 2; -fx-border-style: solid; -fx-background-color: #FFFFFF; -fx-stroke-width: 1; -fx-background-radius: 0");
			arrowup.setStyle(
					"-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #FF0000; -fx-stroke-width: 1; -fx-background-radius: 0");
		} else {
			arrowdown.setStyle(
					"-fx-border-width: 2; -fx-border-style: solid; -fx-background-color: #FFFFFF; -fx-stroke-width: 1; -fx-background-radius: 0");
			arrowup.setStyle(
					"-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #FFFFFF; -fx-stroke-width: 1; -fx-background-radius: 0");
		}		
		
		elevatorModel.elevatorModel.getPropCommitedDirection().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if ((Integer)newValue == IElevator.ELEVATOR_DIRECTION_DOWN) {
					arrowdown.setStyle(
							"-fx-border-width: 2; -fx-border-style: solid; -fx-background-color: #FF0000; -fx-stroke-width: 1; -fx-background-radius: 0");
					arrowup.setStyle(
							"-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #FFFFFF; -fx-stroke-width: 1; -fx-background-radius: 0");
				} else if ((Integer)newValue == IElevator.ELEVATOR_DIRECTION_UP) {
					arrowdown.setStyle(
							"-fx-border-width: 2; -fx-border-style: solid; -fx-background-color: #FFFFFF; -fx-stroke-width: 1; -fx-background-radius: 0");
					arrowup.setStyle(
							"-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #FF0000; -fx-stroke-width: 1; -fx-background-radius: 0");
				} else {
					arrowdown.setStyle(
							"-fx-border-width: 2; -fx-border-style: solid; -fx-background-color: #FFFFFF; -fx-stroke-width: 1; -fx-background-radius: 0");
					arrowup.setStyle(
							"-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #FFFFFF; -fx-stroke-width: 1; -fx-background-radius: 0");
				}
			}
							
		});
		
		
		//FF0000


		arrowdown.setPrefSize(50, 50);		
		arrowup.setPrefSize(50, 50);

		HBox arrowsHbox = new HBox(arrowup, arrowdown);
		arrowsHbox.setAlignment(Pos.CENTER);

		VBox elevDirectionVbox = new VBox(elevDirectionLabel, arrowsHbox);
		elevDirectionVbox.setAlignment(Pos.CENTER);
		// elevDirectionVbox.setStyle(style);

		// layoutGrid.add(elevDirectionVbox, 0, 1);

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Current Payload label and textfield		
		currentPayloadLabel = new Label("Current Payload");
		// currentPayloadLabel.setAlignment(Pos.CENTER);

		Text payloadTxtField = new Text();
		// payloadTxtField.setTextAlignment(TextAlignment.CENTER);
		
		payloadTxtField.textProperty().bind(model.elevatorModel.getPropElevatorWeight());

		VBox payloadVbox = new VBox(currentPayloadLabel, payloadTxtField);
		payloadVbox.setAlignment(Pos.CENTER);
		payloadVbox.setStyle(style);

		// layoutGrid.add(payloadVbox, 0, 2);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 	    speed label and textfield		
		speedLabel = new Label("Speed");

		speedTxtField = new Text();
		
		speedTxtField.textProperty().bind(model.elevatorModel.getPropElevatorSpeed());
		

		VBox speedVbox = new VBox(speedLabel, speedTxtField);
		speedVbox.setAlignment(Pos.CENTER);
		speedVbox.setStyle(style);

		// layoutGrid.add(speedVbox, 0, 3);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//door status label and textfield		
		doorStatusLabel = new Label("Door Status");
		doorStatusTxtField = new Text();
		
		doorStatusTxtField.textProperty().bind(model.elevatorModel.getPropDoorStatus());

		VBox doorStatusVbox = new VBox(doorStatusLabel, doorStatusTxtField);
		doorStatusVbox.setStyle(style);
		doorStatusVbox.setAlignment(Pos.CENTER);

		layoutGrid.add(doorStatusVbox, 0, 4);

		VBox elevatorInfoVBox = new VBox(elevDirectionVbox, payloadVbox, speedVbox, doorStatusVbox);
		elevatorInfoVBox.setSpacing(10);

		layoutGrid.add(elevatorInfoVBox, 0, 1);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// View for elevators and set target and position

		elevatorGrid = new GridPane();
		elevatorGrid.setHgap(10);
		elevatorGrid.setStyle(style);

		elevatorGrid.setPadding(new Insets(2, 2, 2, 2));

		Label directionLabel = new Label("Direction");
		Label requestLabel = new Label("Request");

		Label elevatorPositionLabel = new Label("Elevator Position");
		Label setTargetLabel = new Label("Set Target");

		directionLabel.setPrefWidth(LabelSpacing);
		requestLabel.setPrefWidth(LabelSpacing);
		elevatorPositionLabel.setPrefWidth(LabelSpacing * 2);
		setTargetLabel.setPrefWidth(LabelSpacing);

		elevatorGrid.add(directionLabel, 0, 0);
		elevatorGrid.add(requestLabel, 1, 0);
		elevatorGrid.add(elevatorPositionLabel, 2, 0);
		elevatorGrid.add(setTargetLabel, 3, 0);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		

		// elevatorGrid.add(CreateFloors(),2,1,3,1);
		elevatorGrid.add(CreateFloors(), 2, 1);
		
		
		// Test binding on floor buttons
		testFloorList = new ListView<Floor>(model.buildingModel.getObservableFloorList());
		//testFloorList.disa
		testFloorList.setCellFactory(param -> new ListCell<Floor>() {
			@Override
			protected void updateItem(Floor floor, boolean empty) {
				super.updateItem(floor, empty);
			
				
				
				if (empty || floor == null) {
					setText(null);
					setPrefHeight(floorButtonList.get(0).getHeight());
				} else {
					setPrefHeight(floorButtonList.get(0).getHeight());
					if (floor.FloorButtonDown.getValue() && floor.FloorButtonUp.getValue()) {
						setText("up/down");
				    } else if (floor.FloorButtonDown.getValue()) {
						setText("down");
					} else if (floor.FloorButtonUp.getValue()) {
						setText("up");
					} else {
						setText(null);
					}
				}				
			}
		});
		
		testFloorList.setPrefHeight(floorButtonList.get(0).getHeight()*floorButtonList.size());
		//testFloorList.setMinHeight(value);
		elevatorGrid.add(testFloorList,0,1,1,floorButtonList.size());		
		

		layoutGrid.add(elevatorGrid, 1, 1);

		modeButton.setId(String.valueOf(idNumber) + "modeButton");
		statusButton.setId(String.valueOf(idNumber) + "statusButton");
		elevDirectionLabel.setId(String.valueOf(idNumber) + "elevDirectionLabel");
		arrowup.setId(String.valueOf(idNumber) + "arrowup");
		arrowdown.setId(String.valueOf(idNumber) + "arrowdown");
		currentPayloadLabel.setId(String.valueOf(idNumber) + "currentPayloadLabel");
		speedTxtField.setId(String.valueOf(idNumber) + "speedTxtField");
		doorStatusLabel.setId(String.valueOf(idNumber) + "doorStatusLabel");
		doorStatusTxtField.setId(String.valueOf(idNumber) + "doorStatusTxtField");

		add(layoutGrid, 0,0);
		//stage.setScene(new Scene(layoutGrid));
	}
}