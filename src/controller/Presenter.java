package controller;

import java.util.Observable;
import java.util.Observer;
import model.Model;
import org.eclipse.swt.SWT;
import view.View;

public class Presenter implements Observer{

Model mModel;
View ui;	
int horizental=0, vertical=0;

public Presenter(Model model,View view){
	this.mModel = model;
	this.ui = view;
}

@Override
public void update(Observable o, Object arg ) {	
	if (o == mModel){	
		if (arg != null)
		{				
			if (arg.equals("gameOver")){				
				ui.gameOver();
			}
			else if (arg.equals("gameWon")){
				ui.gameWon();
			}					
			//else if (arg.equals("gameFinish")){				
		//		ui.gameFinish();
		//	}
			else if (arg.equals("undoEnd")){
				ui.undoEnd();
			}
		}
		else{							
			int[][] b=mModel.getData();				
			ui.displayData(b);
			int scr = mModel.getScore();			
			ui.displayScore(scr);
			}
	}
	if (o == ui)
	{
		horizental=0;
		vertical=0;					
		//Retrieve argument notified for diagonal moves
		if (arg != null){ 
			String[] values = ((String) arg ).split(",");	
			horizental=Integer.parseInt(values[0]);
			vertical=Integer.parseInt(values[1]);
		}
		//Check for diagonal moves
		if (horizental > 0 && vertical > 0){			
			mModel.moveDiagonalRightUp();
			return;
		}
		else if (horizental > 0 && vertical < 0 ){
			mModel.moveDiagonalRightDown();
			return;
		}
		else if (horizental < 0 && vertical > 0 ){
			mModel.moveDiagonalLeftUp();
			return;
		}
		else if (horizental < 0 && vertical < 0){
			mModel.moveDiagonalLeftDown();	
			return;
		}
		
		int indexCMD = ui.getUserCommand();
		switch (indexCMD) {
			case 0: // new game
				mModel.initializeBoard();
				break;
			case 2: // restart game
				mModel.initializeBoard();				
				break;
			case 11 : // undo move
				mModel.undoBoard();				
				break;
			case 3: // load game
				mModel.loadGame();				
				break;
			case 4: // save game 
				mModel.saveGame();				
				break;
			case 5:				
				mModel.setWinNumber();	
				break;
			case SWT.ARROW_UP:
				mModel.moveUp(false);				
				break;
			case SWT.ARROW_DOWN:
				mModel.moveDown(false);				
				break;
			case SWT.ARROW_RIGHT:
				mModel.moveRight(false);				
				break;
			case SWT.ARROW_LEFT:
				mModel.moveLeft(false);				
				break;
			default:
				// no key pushed... will wait
		            break;
			}
		}
	}
}