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
int horizental=0, vertical=0;
boolean lastDiagonal=false;

public Presenter(Model model,View view){
	this.mModel = model;
	this.ui = view;
}

@Override
public void update(Observable o, Object arg) {	
	if (o == mModel){	
		if (arg != null)
		{
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
	if (o == ui)
	{
			
		if (arg != null)
		{
			String[] values = ((String) arg ).split(",");	
			horizental=Integer.parseInt(values[0]);
			vertical=Integer.parseInt(values[1]);
		}
		if (horizental > 0 && vertical > 0){
			mModel.moveDiagonalRightUp(true);
			horizental=0;
			vertical=0;
			lastDiagonal=true;
			System.out.println("diago is " + lastDiagonal);

			return;
		}
		else if (horizental > 0 && vertical < 0 ){
			mModel.moveDiagonalRightDown(true);
			horizental=0;
			vertical=0;
			lastDiagonal=true;
			System.out.println("diago is " + lastDiagonal);

			return;
		}
		else if (horizental < 0 && vertical > 0 ){
			mModel.moveDiagonalLeftUp(true);
			horizental=0;
			vertical=0;
			lastDiagonal=true;
			System.out.println("diago is " + lastDiagonal);

			return;
		}
		else if (horizental < 0 && vertical < 0){
			mModel.moveDiagonalLeftDown(true);	
			horizental=0;
			vertical=0;
			lastDiagonal=true;
			System.out.println("diago is " + lastDiagonal);
			return;
		}
		int indexCMD = ui.getUserCommand();
		System.out.println("command=" + indexCMD + "last diag was" + lastDiagonal);

		switch (indexCMD) {
			case 0: // new game
				mModel.initializeBoard();
				lastDiagonal=false;
				break;
			case 2: // restart game
				mModel.initializeBoard();
				lastDiagonal=false;
				break;
			case 1 : // undo move
				mModel.undoBoard(lastDiagonal);
				lastDiagonal=false;
				break;
			case 3: // load game
				mModel.loadGame();
				lastDiagonal=false;
				break;
			case 4: // save game 
				mModel.saveGame();
				lastDiagonal=false;
				break;
			case SWT.ARROW_UP:
				mModel.moveUp(false);
				lastDiagonal=false;
				break;
			case SWT.ARROW_DOWN:
				mModel.moveDown(false);
				lastDiagonal=false;
				break;
			case SWT.ARROW_RIGHT:
				mModel.moveRight(false);
				lastDiagonal=false;
				break;
			case SWT.ARROW_LEFT:
				mModel.moveLeft(false);
				lastDiagonal=false;
				break;
			default:
				// no key pushed... will wait
		            break;
			}
		}
	}
}