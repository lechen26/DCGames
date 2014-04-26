package controller;

import java.util.Observable;
import java.util.Observer;

import model.Model;

import org.eclipse.swt.SWT;

import view.View;

public class Presenter implements Observer{
	
	Model mModel;
	View ui;	
	
	public Presenter(Model model,View view){
		this.mModel = model;
		this.ui = view;
	}
		
	@Override
	public void update(Observable o, Object arg) {		
		if (o == mModel)
		{			
			if (arg != null)
			{
				if (arg.equals("gameOver")){
					System.out.println("Presenter: Game is over");
					ui.gameOver();
				}
				else if (arg.equals("gameWon")) 
				{
					System.out.println("Presenter: Won the game");
					ui.gameWon();
				}
			}
			else
			{
				int[][] b=mModel.getData();			
				ui.displayData(b);
			}
		}
		if (o == ui){			
			int indexCMD = ui.getUserCommand();			
			if ((indexCMD == 0) || (indexCMD == 2)){				
				mModel.initializeBoard();
			}
			if (indexCMD == 1) {		
				mModel.undoBoard();
			}
			if (indexCMD == SWT.ARROW_UP) {				
				mModel.moveUp(false);
			}
			if (indexCMD == SWT.ARROW_DOWN) {				
				mModel.moveDown(false);
			}
			if (indexCMD == SWT.ARROW_RIGHT) {
				System.out.println("Presenter: right");
				mModel.moveRight(false);
			}
			if (indexCMD == SWT.ARROW_LEFT) {				
				mModel.moveLeft(false);
			}
		}
		
	}
}
