package controller;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.Model;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import view.View;

public class Presenter  implements Observer {

Model mModel;
View ui;	
int horizental=0, vertical=0;
String server=null;
ExecutorService rmiExc = Executors.newCachedThreadPool();

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
			String[] values;
			//if (((String) arg).equals("Terminate")) {								
			//	rmiExc.shutdown();
			//	ui.disposeDisplay();				
			//}
			//Check if we got server name
			if (((String) arg).contains("server=")) {
				values=((String) arg).split("server=");
				server=values[1];
			}else {
				values = ((String) arg ).split(",");	
				horizental=Integer.parseInt(values[0]);
				vertical=Integer.parseInt(values[1]);
			}
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
				rmiExc.execute(new Runnable() {					
					@Override
					public void run() {
						try {
							mModel.getHintFromServer();
						} catch (Exception e1) {			
							e1.printStackTrace();
						}
					}
				});
				break;
			case 7:	// solve game
				rmiExc.execute(new Runnable() {					
					@Override
					public void run() {
						try {							
							mModel.getSolutionFromServer();
						} catch (Exception e) {
							e.printStackTrace();
							//System.out.println("i'm trying to solve");
						}
					}
				});
			case 8: // setting the server to be server for the RMI server 
				mModel.setServer(server);
				break;
			case 9: //stop the auto solver 
				mModel.setStopSolverPressed(true);
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