package controller;

import java.util.Observable;
import java.util.Observer;

import model.Model;

import org.eclipse.swt.SWT;

import view.View;

public class Presenter implements Observer{
	
	Model mModel;
	View ui;
	int lastCmd;
	
	//Constructor
	public Presenter(Model model,View view){
		this.mModel = model;
		this.ui = view;
	}
	
	
	@Override
	public void update(Observable o, Object arg) {		
		if (o == mModel){			
			int[][] b=mModel.getData();			
			ui.displayData(b);
		}
		if (o == ui){			
			int indexCMD = ui.getUserCommand();			
			if ((indexCMD == 0) || (indexCMD == 2)){				
				mModel.initializeBoard();
			}
			if (indexCMD == 1) {
				System.out.println("Undo button pressed");
				mModel.undoBoard();
			}
			if (indexCMD == SWT.ARROW_UP) {
				lastCmd=SWT.ARROW_UP;
				mModel.moveUp();
			}
			if (indexCMD == SWT.ARROW_DOWN) {
				lastCmd=SWT.ARROW_DOWN;
				mModel.moveDown();
			}
			if (indexCMD == SWT.ARROW_RIGHT) {
				lastCmd=SWT.ARROW_RIGHT;
				mModel.moveRight();
			}
			if (indexCMD == SWT.ARROW_LEFT) {
				lastCmd=SWT.ARROW_LEFT;
				mModel.moveLeft();
			}
		}
		
	}

	
}
