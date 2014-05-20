package controller;

import java.rmi.RemoteException;
import java.security.PrivilegedAction;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import model.Model;
import org.eclipse.swt.SWT;
import view.View;
import view.ViewMaze;

public class Presenter  implements Observer {

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
			else if (arg.equals("undoEnd")){
				ui.undoEnd();
			}
		}else{							
			int[][] b=mModel.getBoard();				
			ui.displayBoard(b);
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
			case 1 : // undo move
				mModel.undoBoard();				
				break;
			case 3: // load game
				mModel.loadGame();				
				break;
			case 4: // save game 
				mModel.saveGame();				
				break;
			case 5:	// double Win number	
				mModel.doubleWinNumber();	
				break;
			case 6: // get Hint
				try {
					mModel.getHintFromServer();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (CloneNotSupportedException e) {				
					e.printStackTrace();
				}
				break;
			case 7:							
				//ui.getDisplay().syncExec(new Runnable() {
				new Thread(new Runnable() {					
					@Override
					public void run() {
						try {
							mModel.getSolutionFromServer();
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}).start();						
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