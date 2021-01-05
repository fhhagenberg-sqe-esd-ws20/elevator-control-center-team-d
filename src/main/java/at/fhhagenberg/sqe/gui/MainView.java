package at.fhhagenberg.sqe.gui;

import at.fhhagenberg.sqe.controller.ElevatorController;
import at.fhhagenberg.sqe.model.Building;
import at.fhhagenberg.sqe.model.Elevator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {
	private ElevatorController elevController;
	private Stage stage;
	
	
	public MainView(ElevatorController controller, Stage stg)
	{
		this.elevController = controller;
		this.stage = stg;
		
		TabPane elevatorTabPane = new TabPane();
		
		
		
		//testButton.setMinSize(50, 50);
		
		//test.add(testButton, 0, 0);
		
		for(int i = 0; i < elevController.buildingModel.getElevatorNumber(); i++)
		{
			//GridPane test = new GridPane();
			//GridPane elevView = new GridPane(); 
			ElevatorView testelev = new ElevatorView(elevController, null);
			
			
			//elevController.setCurrViewElevatorNumber(2);
			
			//elevView.add(testelev,0,0);
					//new ElevatorView(elevController, stg);
			//elevView.setPrefSize(500, 500);
			//Tab elevatorTab = new Tab(("elevator"+ String.valueOf(i)), elevView);
			
			//Button testButton = new Button("testbutton");
			
			//test.add(testButton, 0, 0);
			
			Tab elevatorTab = new Tab("Tab"+String.valueOf(i));
			
			final int constantIndex = i;
			
			elevatorTab.setContent(testelev);
			elevatorTab.setOnSelectionChanged(e -> {
					if (elevatorTab.isSelected()) {
						elevController.setCurrViewElevatorNumber(constantIndex);
					}
				});
			
			
			elevatorTabPane.getTabs().add(elevatorTab);
		}
		
		
		//VBox vBox = new VBox(elevatorTabPane);
		Scene scene = new Scene(elevatorTabPane);		
		stage.setScene(scene);
		stage.show();
	}
	
	
}
