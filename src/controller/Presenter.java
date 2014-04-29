package controller;

import java.util.Observable;
import java.util.Observer;

import model.Model;

import org.eclipse.swt.SWT;

import view.View;
import view.View;

public class Presenter implements Observer{
	
	Model mModel;
	View ui;	
	int horizental, vertical;
	
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
				ui.displayData(b);			
				int scr = mModel.getScore();				
				ui.displayScore(scr);
			}
		}
		if (o == ui){			
			if (arg != null)
			{
				String[] values = ((String) arg ).split(",");				
				horizental=Integer.parseInt(values[0]);
				vertical=Integer.parseInt(values[1]);								
			}
			int indexCMD = ui.getUserCommand();
				//Check Diagonal moves
				if (horizental > 0 && vertical > 0)
					mModel.moveDiagonalRightUp(false);			
				else if (horizental > 0  && vertical < 0 )
					mModel.moveDiagonalRightDown(false);
				else if (horizental < 0 && vertical > 0 )
					mModel.moveDiagonalLeftUp(false);
				else if (horizental < 0 && vertical < 0)
					mModel.moveDiagonalLeftDown(false);
				
				else
				{
					switch (indexCMD) {
					case 0 :
						mModel.initializeBoard();
						break;
					case 2:
						mModel.initializeBoard();
						break;
					case 1 :
						mModel.undoBoard();
						break;
					case 3:
						mModel.loadGame();
						break;
					case 4:
						mModel.saveGame();
						break;
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
}
