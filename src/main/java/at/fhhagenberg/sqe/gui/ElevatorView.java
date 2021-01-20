package at.fhhagenberg.sqe.gui;

import java.util.ArrayList;

import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.controller.ElevatorController.eOperationStatus;
import at.fhhagenberg.sqe.model.Floor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sqelevator.IElevator;

public class ElevatorView extends GridPane {
	private ElevatorController elevatorModel;
	private ScrollPane layoutScrollPane;

	private Button modeButton;
	private Button statusButton;
	private Label elevDirectionLabel; //
	private Button arrowup;
	private Button arrowdown;
	private Label currentPayloadLabel;
	private Text speedTxtField;
	private Label doorStatusLabel;
	private Text doorStatusTxtField;
	private Text payloadTxtField;

	private ArrayList<Button> floorButtonList = new ArrayList<Button>();
	private ArrayList<Button> setButtonList = new ArrayList<Button>();
	private ArrayList<Button> requestButtonList = new ArrayList<Button>();

	ListView<Floor> testFloorList;
	
	

	private Label errorLabel;
	private ListView<String> errorListView;
	

	private Label warningLabel;
	private ListView<String> warningListView;

	private GridPane elevatorGrid;
	private GridPane layoutGrid;
	private static int LabelSpacing = 70;
	private static final String STYLE = "-fx-background-color: #ffffff; " + "-fx-border-color:  #545454;\r\n"
			+ "  -fx-border-width: 2px;\r\n" + "  -fx-border-style: solid;";

	private static final String FLOORBUTTONSELECTEDSTYLE = "-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #0000FF; -fx-stroke-width: 1; -fx-pref-width: "
			+ LabelSpacing * 2 + "; -fx-background-radius: 0";
	private static final String FLOORBUTTONNOTSELECTEDSTYLE = "-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #90ee90; -fx-stroke-width: 1; -fx-pref-width: "
			+ LabelSpacing * 2 + "; -fx-background-radius: 0";
	private final String floorButtonNotServiced = "-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #808080; -fx-stroke-width: 1; -fx-pref-width: "
			+ LabelSpacing * 2 + "; -fx-background-radius: 0";

	private static final String SETBUTTONSTYLE = "-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #90ee90; -fx-stroke-width: 1; -fx-pref-width: "
			+ LabelSpacing / 2 + "; -fx-background-radius: 0";
	private static final String REQUESTBUTTONSTYLE = "-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #90ee90; -fx-stroke-width: 1; -fx-pref-width: "
			+ LabelSpacing + "; -fx-background-radius: 0";
	private static final String FLOORBUTTONCLICKEDSTYLE = "-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #006400; -fx-stroke-width: 1; -fx-pref-width: "
			+ LabelSpacing * 2 + "; -fx-background-radius: 0";
	private static final String MODEBUTTONSTYLE = "-fx-border-width: 0; -fx-background-color: #ff0000; -fx-stroke-width: 1; -fx-stroke-width: 1;  -fx-background-radius: 0";
	private static final String DIRECTIONACTIVESTYLE = "-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #FF0000; -fx-stroke-width: 1; -fx-background-radius: 0";
	private static final String DIRECTIONINACTIVESTYLE = "-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #FFFFFF; -fx-stroke-width: 1; -fx-background-radius: 0";

	private final String statusButtonStyle = "-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #90ee90; -fx-stroke-width: 1; -fx-pref-width: "
			+ ((LabelSpacing*8)+8) + "; -fx-background-radius: 0";
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

	public GridPane getLayoutGrid() {
		return layoutGrid;
	}

	private GridPane CreateFloors() {
		GridPane floorPane = new GridPane();

		int positionInGrid = 2;

		elevatorModel.elevatorButtonList.addListener(new ListChangeListener<Boolean>() {
			@Override
			public void onChanged(Change<? extends Boolean> c) {
				for (int i = 0; i < elevatorModel.elevatorButtonList.size(); i++) {
					if (elevatorModel.elevatorButtonList.get(i)) {
						requestButtonList.get(i).setText("stop");
					} else {
						requestButtonList.get(i).setText("");
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

		// change listener for current elevator position
		elevatorModel.elevatorModel.getPropElevatorCurrFloor().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				floorButtonList.get((Integer) newValue).setStyle(
						"-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #0000FF; -fx-stroke-width: 1; -fx-pref-width: "
								+ LabelSpacing * 2 + "; -fx-background-radius: 0");
				floorButtonList.get((Integer) oldValue).setStyle(
						"-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #90ee90; -fx-stroke-width: 1; -fx-pref-width: "
								+ LabelSpacing * 2 + "; -fx-background-radius: 0");
			}
		});

		for (int i = elevatorModel.buildingModel.getFloorNumber() - 1; i >= 0; i--) {
			Button floorButton = new Button("Floor " + i);
			Button setButton = new Button("set");
			Button requestButton = new Button("");

			if (elevatorModel.servicesFloorList.get(i)) {
				floorButton.setStyle(FLOORBUTTONNOTSELECTEDSTYLE);
			} else {
				floorButton.setStyle(floorButtonNotServiced);
				floorButton.setDisable(true);
			}

			setButton.setStyle(SETBUTTONSTYLE);
			requestButton.setStyle(REQUESTBUTTONSTYLE);

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

			floorButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (elevatorModel.setNextElevatorTarget(constantIndex)) {
						if (elevatorModel.getOperationStatus() == eOperationStatus.MANUAL) {							
							floorButton.setStyle(FLOORBUTTONCLICKEDSTYLE);
						}
					}
					event.consume();
				}
			});	

			elevatorGrid.add(requestButton, 1, positionInGrid);
			elevatorGrid.add(floorButton, 2, positionInGrid);
			elevatorGrid.add(setButton, 3, positionInGrid);
			positionInGrid++;
		}

		floorButtonList.get(elevatorModel.elevatorModel.getElevatorCurrFloor()).setStyle(FLOORBUTTONSELECTEDSTYLE);
		return floorPane;
	}

	public ElevatorView(ElevatorController model, Stage stage) {
		super();

		this.elevatorModel = model;

		// this.stage = stage;
		idNumber = model.buildingModel.getElevatorNumber();

		modeButton = new Button("Manual");
		modeButton.setStyle(MODEBUTTONSTYLE);

		// set id
		modeButton.addEventHandler(ActionEvent.ACTION, switchModeHandler);
		modeButton.setPrefWidth(100);
		modeButton.setPrefHeight(20);
		VBox modeVbox = new VBox(modeButton);
		modeVbox.setAlignment(Pos.BASELINE_CENTER);

		statusButton = new Button("Status");
		statusButton.setStyle(statusButtonStyle);
		VBox statusVbox = new VBox(statusButton);
		statusVbox.setAlignment(Pos.BASELINE_CENTER);

		layoutGrid = new GridPane();
		
		layoutGrid.setPrefSize(800, 400);
		layoutGrid.setVgap(20);
		layoutGrid.setHgap(20);
		layoutGrid.setPadding(new Insets(20, 20, 20, 20));
		layoutGrid.setAlignment(Pos.CENTER);

		GridPane.setFillWidth(modeButton, true);
		layoutGrid.add(modeButton, 0, 0);
		layoutGrid.add(statusVbox, 1, 0);
				
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// Elevator direction label and Buttons:
		elevDirectionLabel = new Label("Elevator Direction");

		arrowup = new Button("up");
		arrowdown = new Button("down");

		if (elevatorModel.elevatorModel.getCommitedDirection() == IElevator.ELEVATOR_DIRECTION_DOWN) {
			arrowdown.setStyle(DIRECTIONACTIVESTYLE);
			arrowup.setStyle(DIRECTIONINACTIVESTYLE);

		} else if (elevatorModel.elevatorModel.getCommitedDirection() == IElevator.ELEVATOR_DIRECTION_UP) {
			arrowdown.setStyle(DIRECTIONINACTIVESTYLE);
			arrowup.setStyle(DIRECTIONACTIVESTYLE);
		} else {
			arrowdown.setStyle(DIRECTIONINACTIVESTYLE);
			arrowup.setStyle(DIRECTIONINACTIVESTYLE);
		}

		elevatorModel.elevatorModel.getPropCommitedDirection().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if ((Integer) newValue == IElevator.ELEVATOR_DIRECTION_DOWN) {
					arrowdown.setStyle(DIRECTIONACTIVESTYLE);
					arrowup.setStyle(DIRECTIONINACTIVESTYLE);

				} else if ((Integer) newValue == IElevator.ELEVATOR_DIRECTION_UP) {
					arrowdown.setStyle(DIRECTIONINACTIVESTYLE);
					arrowup.setStyle(DIRECTIONACTIVESTYLE);
				} else {
					arrowdown.setStyle(DIRECTIONINACTIVESTYLE);
					arrowup.setStyle(DIRECTIONINACTIVESTYLE);
				}
			}
		});

		arrowdown.setPrefSize(50, 50);
		arrowup.setPrefSize(50, 50);

		HBox arrowsHbox = new HBox(arrowup, arrowdown);
		arrowsHbox.setAlignment(Pos.CENTER);

		VBox elevDirectionVbox = new VBox(elevDirectionLabel, arrowsHbox);
		elevDirectionVbox.setAlignment(Pos.CENTER);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Current Payload label and textfield		
		currentPayloadLabel = new Label("Current Payload");
		// currentPayloadLabel.setAlignment(Pos.CENTER);

		payloadTxtField = new Text();
		// payloadTxtField.setTextAlignment(TextAlignment.CENTER);

		payloadTxtField.textProperty().bind(model.elevatorModel.getPropElevatorWeight());

		VBox payloadVbox = new VBox(currentPayloadLabel, payloadTxtField);
		payloadVbox.setAlignment(Pos.CENTER);
		payloadVbox.setStyle(STYLE);

		// layoutGrid.add(payloadVbox, 0, 2);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 	    speed label and TextArea		
		speedLabel = new Label("Speed");

		speedTxtField = new Text();

		speedTxtField.textProperty().bind(model.elevatorModel.getPropElevatorSpeed());

		VBox speedVbox = new VBox(speedLabel, speedTxtField);
		speedVbox.setAlignment(Pos.CENTER);
		speedVbox.setStyle(STYLE);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//door status label and TextArea		

		doorStatusLabel = new Label("Door Status");
		doorStatusTxtField = new Text();

		doorStatusTxtField.textProperty().bind(model.elevatorModel.getPropDoorStatus());

		VBox doorStatusVbox = new VBox(doorStatusLabel, doorStatusTxtField);
		doorStatusVbox.setStyle(STYLE);
		doorStatusVbox.setAlignment(Pos.CENTER);

		layoutGrid.add(doorStatusVbox, 0, 4);

		VBox elevatorInfoVBox = new VBox(elevDirectionVbox, payloadVbox, speedVbox, doorStatusVbox);
		elevatorInfoVBox.setSpacing(10);

		layoutGrid.add(elevatorInfoVBox, 0, 1);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// View for elevators and set target and position

		elevatorGrid = new GridPane();
		elevatorGrid.setHgap(10);
		elevatorGrid.setStyle(STYLE);

		elevatorGrid.setPadding(new Insets(2, 2, 2, 2));

		Label directionLabel = new Label("Direction");
		Label requestLabel = new Label("Request");

		Label elevatorPositionLabel = new Label("Elevator Position");
		Label setTargetLabel = new Label("Set Target");

		directionLabel.setPrefWidth(LabelSpacing);
		requestLabel.setPrefWidth(LabelSpacing);
		elevatorPositionLabel.setPrefWidth(LabelSpacing * 2.00);
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
		// testFloorList.disa
		testFloorList.setCellFactory(param -> new ListCell<Floor>() {
			@Override
			protected void updateItem(Floor floor, boolean empty) {
				super.updateItem(floor, empty);

				if (empty || floor == null) {
					setText(null);
					setPrefHeight(floorButtonList.get(0).getHeight() - 0.3);
				} else {
					setPrefHeight(floorButtonList.get(0).getHeight() - 0.3);
					if (floor.floorButtonDown.getValue() && floor.floorButtonUp.getValue()) {
						setText("up/down");
					} else if (floor.floorButtonDown.getValue()) {
						setText("down");
					} else if (floor.floorButtonUp.getValue()) {
						setText("up");
					} else {
						setText(null);
					}
				}
			}
		});

		testFloorList.setPrefHeight(floorButtonList.get(0).getHeight() * floorButtonList.size());

		elevatorGrid.add(testFloorList, 0, 2, 1, floorButtonList.size());

		layoutGrid.add(elevatorGrid, 1, 1);

		warningLabel = new Label("Warnings");
		warningListView = new ListView<String>(model.ctrlAlarmManager.getPropWarningList());		

		errorLabel = new Label("Errors: ");
		errorListView = new ListView<String>(model.ctrlAlarmManager.getPropErrorList());
		
		errorListView.setMinHeight(100);
		warningListView.setMinHeight(100);

		VBox warningVBox = new VBox(warningLabel, warningListView);
		VBox errorVBox = new VBox(errorLabel, errorListView);

		layoutGrid.add(warningVBox, 1, 2);
		layoutGrid.add(errorVBox, 1, 3);

		warningListView.setId(String.valueOf(idNumber) + "warningTextField");
		errorListView.setId(String.valueOf(idNumber) + "errorTextField");

		modeButton.setId(String.valueOf(idNumber) + "modeButton");
		statusButton.setId(String.valueOf(idNumber) + "statusButton");
		elevDirectionLabel.setId(String.valueOf(idNumber) + "elevDirectionLabel");
		arrowup.setId(String.valueOf(idNumber) + "arrowup");
		arrowdown.setId(String.valueOf(idNumber) + "arrowdown");
		currentPayloadLabel.setId(String.valueOf(idNumber) + "currentPayloadLabel");
		speedTxtField.setId(String.valueOf(idNumber) + "speedTxtField");
		doorStatusLabel.setId(String.valueOf(idNumber) + "doorStatusLabel");
		doorStatusTxtField.setId(String.valueOf(idNumber) + "doorStatusTxtField");
		payloadTxtField.setId(String.valueOf(idNumber) + "payloadTxtField");

		
		layoutScrollPane = new ScrollPane(layoutGrid);
		layoutScrollPane.setPrefSize(850, 700);
		add(layoutScrollPane, 0, 0);
	}
}
