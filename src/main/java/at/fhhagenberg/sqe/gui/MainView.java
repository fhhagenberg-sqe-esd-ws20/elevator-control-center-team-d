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
							
		for(int i = 0; i < elevController.buildingModel.getElevatorNumber(); i++)
		{			
			ElevatorView testelev = new ElevatorView(elevController, null);
										
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
					
		Scene scene = new Scene(elevatorTabPane);		
		stage.setScene(scene);
		stage.show();
	}		
}
