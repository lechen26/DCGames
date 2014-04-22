package controller;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;

import view.View;
import model.Model;

public class Presenter implements Observer, Runnable{
	
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
			int[][] b=mModel.getData();			
			ui.displayData(b);
		}
		if (o == ui){			
			int indexCMD = ui.getUserCommand();			
			if (indexCMD == 0) {
				System.out.println("Starting the game");
				mModel.initializeBoard();
			}
			if (indexCMD == SWT.ARROW_UP) {				
				mModel.moveUp();
			}
			if (indexCMD == SWT.ARROW_DOWN) {				
				mModel.moveDown();
			}
			if (indexCMD == SWT.ARROW_RIGHT) {				
				mModel.moveRight();
			}
			if (indexCMD == SWT.ARROW_LEFT) {
				mModel.moveLeft();
			}
		}
		
	}


	@Override
	public void run() {
	
		
	}
	
}
