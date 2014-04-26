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
		if (o == mModel){			
			if (arg != null){
				if (arg.equals("gameOver")){
					System.out.println("Presenter: Game is over");
					ui.gameOver();
				}
				else if (arg.equals("gameWon")){
					System.out.println("Presenter: Won the game");
					ui.gameWon();
				}
				else if (arg.equals("undoEnd")){
					ui.undoEnd();
				}
			}
			else{
				int[][] b=mModel.getData();
				int scr = mModel.getCurrentScore();
				ui.displayData(b);
				ui.displayScore(scr);
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
			switch (indexCMD) {
            case SWT.ARROW_UP:  
            	System.out.println("Presenter: move up");
            	mModel.moveUp(false);
            	break;
            case SWT.ARROW_DOWN:  
            	System.out.println("Presenter: move down");
            	mModel.moveDown(false); 
            	break;
            case SWT.ARROW_RIGHT: 
            	System.out.println("Presenter: move right");
            	mModel.moveRight(false); 
            	break;
            case SWT.ARROW_LEFT:  
            	System.out.println("Presenter: move left");
            	mModel.moveLeft(false); 
            	break;
            default: 
            	// no key pushed... will wait
            	break;
			}
		
		}
	}
}
