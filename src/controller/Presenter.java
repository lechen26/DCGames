package controller;

import java.util.Observable;
import java.util.Observer;

import view.View;
import model.Model;

public class Presenter implements Observer {
	
	Model mModel;
	View mView;
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
