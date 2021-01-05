package at.fhhagenberg.sqe.controller;

import java.util.Observable;
import java.util.Observer;

import at.fhhagenberg.sqe.gui.MainView;
import at.fhhagenberg.sqe.model.MainModel;

public class MainController implements Observer{
	private MainView mainView;
	private MainModel mainModel;
	
	public MainController(MainModel model, MainView view)
	{
		this.mainModel = model;
		this.mainView = view;
		
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
