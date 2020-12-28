package at.fhhagenberg.sqe.gui;

import at.fhhagenberg.sqe.controller.ElevatorController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
	
	private ElevatorController elevatorCtrl = new ElevatorController();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		var label = new Label("Initial GUI");
		label.textProperty().bind(elevatorCtrl.DoorStatus);
		
		var layout = new BorderPane(label);
		
		var scene = new Scene(layout, 640, 480);
		
		// Set scene
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}
