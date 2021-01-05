package at.fhhagenberg.sqe.gui;

import java.io.FileInputStream;

import javax.swing.GroupLayout.Alignment;

import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.model.Elevator;
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

	private Stage stage;
	//private Label label;
	//private Button countButton;
	//private Text sceneTitle;

//	private Image uparrow;
//	private Image downarrow;
	private Label emptySpacingLabel;
	
	private Button modeButton;
	private Button statusButton;
	private Label  elevDirectionLabel;	//	
	private Button arrowup;
	private Button arrowdown;
	private Label currentPayloadLabel;
	private Text speedTxtField;
	private Label doorStatusLabel;
	private Text doorStatusTxtField;
	
	
	private GridPane elevatorGrid;
	
	private int LabelSpacing = 70;
	
	private String style = "-fx-background-color: #ffffff; "
    		+ "-fx-border-color:  #545454;\r\n"
    		+ "  -fx-border-width: 2px;\r\n"
    		+ "  -fx-border-style: solid;";
	
	private int idNumber;
	
	
	 EventHandler<ActionEvent> switchModeHandler = new EventHandler<ActionEvent>() {
			@Override
		    public void handle(ActionEvent event) {
		        elevatorModel.switchOperationStatus();		        
		        event.consume();
		    }
		};

	private Node speedLabel;
	
	private GridPane CreateFloors()
	{
		GridPane floorPane = new GridPane();
		
		int positionInGrid = 1; 
		for(int i = elevatorModel.buildingModel.getFloorNumber()-1; i >= 0; i--)
		{
			Button floorButton = new Button("Floor " + i);			
			Button setButton = new Button ("set");
			Button requestButton = new Button("stop/call");
						
			floorButton.setStyle("-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #90ee90; -fx-stroke-width: 1; -fx-pref-width: "+LabelSpacing*2+ "; -fx-background-radius: 0");
			setButton.setStyle("-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #90ee90; -fx-stroke-width: 1; -fx-pref-width: "+LabelSpacing/2+ "; -fx-background-radius: 0");
			requestButton.setStyle("-fx-border-width: 1; -fx-border-style: solid; -fx-background-color: #90ee90; -fx-stroke-width: 1; -fx-pref-width: "+ LabelSpacing+ "; -fx-background-radius: 0");
			//setButton.setVisible(false);
			//set id
			floorButton.setId(String.valueOf(idNumber)+"floorButton" + Integer.toString(i));
			setButton.setId(String.valueOf(idNumber)+"setButton" + Integer.toString(i));
			requestButton.setId(String.valueOf(idNumber)+"requestButton" + Integer.toString(i));
			
			modeButton.setId(String.valueOf(idNumber)+"modeButton");
			
						
			elevatorGrid.add(requestButton, 1, positionInGrid);
			elevatorGrid.add(floorButton, 2, positionInGrid);
			elevatorGrid.add(setButton, 3, positionInGrid);
			positionInGrid++;						
		}
		
		return floorPane;
	}
			
	public ElevatorView(ElevatorController model, Stage stage) {
//		emptySpacingLabel = new Label();
//		emptySpacingLabel.setPrefWidth(50);
		
		this.elevatorModel = model;
		this.stage = stage;
		idNumber = model.buildingModel.getElevatorNumber();
			
//		label = new Label("Countdown: ");
//		countButton = new Button("Count");
		// stage.setScene(new Scene(new VBox(label, countButton)));

		modeButton = new Button("Manual");
		modeButton.setStyle("-fx-border-width: 0; -fx-background-color: #ff0000; -fx-stroke-width: 1; -fx-stroke-width: 1;  -fx-background-radius: 0");
		//set id
		modeButton.addEventHandler(ActionEvent.ACTION, switchModeHandler);
		modeButton.setPrefWidth(100);
		modeButton.setPrefHeight(20);
		VBox modeVbox = new VBox(modeButton);
		modeVbox.setAlignment(Pos.BASELINE_CENTER);
		
					
		statusButton = new Button("Status");
		VBox statusVbox = new VBox(statusButton);
		statusVbox.setAlignment(Pos.BASELINE_CENTER);
		//modeLabel.setBorder(2);

		GridPane layoutGrid = new GridPane();

		layoutGrid.setPrefSize(600, 400);		
		layoutGrid.setVgap(20);
		layoutGrid.setHgap(20);
		layoutGrid.setPadding(new Insets(20,20,20,20));
		layoutGrid.setAlignment(Pos.CENTER);
		
		//layoutGrid.add(modeAndStatusHbox, 0, 0, 2, 1);
		//-fx-pref-width: 100;
		
		
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

		arrowdown.setPrefSize(50, 50);
		arrowup.setPrefSize(50, 50);

		HBox arrowsHbox = new HBox(arrowup, arrowdown);
		arrowsHbox.setAlignment(Pos.CENTER);
		
		
		
		VBox elevDirectionVbox = new VBox(elevDirectionLabel, arrowsHbox);
		elevDirectionVbox.setAlignment(Pos.CENTER);
		//elevDirectionVbox.setStyle(style);

	//	layoutGrid.add(elevDirectionVbox, 0, 1);

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Current Payload label and textfield		
		currentPayloadLabel = new Label("Current Payload");
	//	currentPayloadLabel.setAlignment(Pos.CENTER);

		Text payloadTxtField = new Text();
		//payloadTxtField.setTextAlignment(TextAlignment.CENTER);
		payloadTxtField.setText("0");

		VBox payloadVbox = new VBox(currentPayloadLabel, payloadTxtField);
		payloadVbox.setAlignment(Pos.CENTER);
		payloadVbox.setStyle(style);
		
	//	layoutGrid.add(payloadVbox, 0, 2);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// 	    speed label and textfield		
		speedLabel = new Label("Speed");

		speedTxtField = new Text();
		speedTxtField.setText("0");

		VBox speedVbox = new VBox(speedLabel, speedTxtField);
		speedVbox.setAlignment(Pos.CENTER);		
		speedVbox.setStyle(style);

		//layoutGrid.add(speedVbox, 0, 3);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//door status label and textfield		
		doorStatusLabel = new Label("Door Status");
		doorStatusTxtField = new Text();
		doorStatusTxtField.setText("closed");

		VBox doorStatusVbox = new VBox(doorStatusLabel, doorStatusTxtField);
		doorStatusVbox.setStyle(style);
		doorStatusVbox.setAlignment(Pos.CENTER);

		layoutGrid.add(doorStatusVbox, 0, 4);
		
		
		VBox elevatorInfoVBox = new VBox(elevDirectionVbox, payloadVbox, speedVbox, doorStatusVbox);
		elevatorInfoVBox.setSpacing(10);
		
		layoutGrid.add(elevatorInfoVBox, 0,1);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// View for elevators and set target and position
		

		elevatorGrid = new GridPane(); 
		elevatorGrid.setHgap(10);
		elevatorGrid.setStyle(style);
		
		elevatorGrid.setPadding(new Insets(2,2,2,2));
		
		Label directionLabel = new Label("Direction");
		Label requestLabel = new Label("Request");
		
		Label elevatorPositionLabel = new Label("Elevator Position");
		Label setTargetLabel = new Label("Set Target");
		
		directionLabel.setPrefWidth(LabelSpacing);
		requestLabel.setPrefWidth(LabelSpacing);
		elevatorPositionLabel.setPrefWidth(LabelSpacing*2);
		setTargetLabel.setPrefWidth(LabelSpacing);
		
		elevatorGrid.add(directionLabel, 0, 0);
		elevatorGrid.add(requestLabel, 1, 0);
		elevatorGrid.add(elevatorPositionLabel, 2, 0);
		elevatorGrid.add(setTargetLabel, 3, 0);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		//elevatorGrid.add(CreateFloors(),2,1,3,1);
		elevatorGrid.add(CreateFloors(),2,1);
		
		layoutGrid.add(elevatorGrid, 1, 1);
		
		modeButton.setId(String.valueOf(idNumber)+"modeButton");
		statusButton.setId(String.valueOf(idNumber)+"statusButton");
		elevDirectionLabel.setId(String.valueOf(idNumber)+"elevDirectionLabel");
		arrowup.setId(String.valueOf(idNumber)+"arrowup");
		arrowdown.setId(String.valueOf(idNumber)+"arrowdown");
		currentPayloadLabel.setId(String.valueOf(idNumber)+"currentPayloadLabel");
		speedTxtField.setId(String.valueOf(idNumber)+"speedTxtField");
		doorStatusLabel.setId(String.valueOf(idNumber)+"doorStatusLabel");
		doorStatusTxtField.setId(String.valueOf(idNumber)+"doorStatusTxtField");
		
		stage.setScene(new Scene(layoutGrid));
	}
}
