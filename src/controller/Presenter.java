package controller;

import java.util.Observable;
import java.util.Observer;

import view.View;
import model.Model;

public class Presenter implements Observer {
	
	Model mModel;
	View ui;
	
	//Constructor
	public Presenter(Model model,View view){
		this.mModel = model;
		this.ui = view;
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		
		if (o == mModel){
			ui.displayData(mModel.getData());
		}
		if (o == ui){
			int indexCMD = ui.getUserCommand();
			// 1 - up , 2 - down , 3 - right , 4 - left
			if (indexCMD == 1) {
				mModel.moveUp();
			}
			if (indexCMD == 2) {
				mModel.moveDown();
			}
			if (indexCMD == 3) {
				mModel.moveRight();
			}
			if (indexCMD == 4) {
				mModel.moveLeft();
			}
		}
		
	}
	
}
