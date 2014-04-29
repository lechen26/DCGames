package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

public class ModelMaze extends Observable implements Model {

	int[][] mBoard;	
	ArrayList<int[][]> undoBoards = new ArrayList<int[][]>();
	int rows;
	int cols;
	int score=0;
	
	public ModelMaze(int rows,int cols) {
		mBoard=new int[rows][cols];
	}
	
	@Override
	public int[][] getData() {
		return mBoard;
	}

	public Point getCurrentPosition(){

		for (int i=0; i<mBoard.length; i++)
			for (int j=0;j<mBoard[0].length ; j++)
				if (mBoard[i][j] == 1 )
					return new Point(i,j);

		return (Point) null; // din't find the mouse
	}
	public Point getExitPoint(){
		for (int i=0;i<mBoard[0].length;i++)
			for (int j=0;j<mBoard.length;j++)
				if (mBoard[i][j] == 2)
					return new Point(i,j);
		
		return (Point) null ; // didnt find the exit point
	}

	/*
	 * move up method
	 */
	@Override
	public boolean moveUp(boolean quiet) {
		boolean move=false;
		if (!quiet) {
			undoBoards.add(copyBoard(mBoard));		
		}
		int ePosX = getExitPoint().x;
		int ePosY = getExitPoint().y;
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosX - 1;
		if((newPos >= 0) && (mBoard[newPos][cPosY] != -1)){
			score+=10;
			move=true;
			if (!quiet)
			{
				mBoard[newPos][cPosY] = mBoard[cPosX][cPosY];
				mBoard[cPosX][cPosY] = 0 ;
			}
			setChanged();
			notifyObservers();
			if ((newPos == ePosX) && (cPosY == ePosY)){
				setChanged();
				notifyObservers("gameWon");
			}
		}
		score+=10;
		return move;
}


	@Override
	public boolean moveDown(boolean quiet) {
		boolean move=false;
		if (!quiet) {
			undoBoards.add(copyBoard(mBoard));		
		}
		int ePosX = getExitPoint().x;
		int ePosY = getExitPoint().y;
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosX + 1;
		if((newPos < mBoard.length) && (mBoard[newPos][cPosY] != -1)){
			move=true;
			score+=10;
			if (!quiet)
			{
				mBoard[newPos][cPosY] = mBoard[cPosX][cPosY] ;
				mBoard[cPosX][cPosY] = 0 ;
			}
			setChanged();
			notifyObservers();
			if ((newPos == ePosX) && (cPosY == ePosY)){
				setChanged();
				notifyObservers("gameWon");
			}
		}		
		return move;
	}

	@Override
	public boolean moveLeft(boolean quiet) {
		boolean move=false;
		if (!quiet) {
			undoBoards.add(copyBoard(mBoard));		
		}
		int ePosX = getExitPoint().x;
		int ePosY = getExitPoint().y;
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosY - 1;
		if((newPos >= 0 ) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			score+=10;
			if (!quiet)
			{
				mBoard[cPosX][newPos] = mBoard[cPosX][cPosY] ;
				mBoard[cPosX][cPosY] = 0 ;
			}
			setChanged();
			notifyObservers();
			if ((newPos == ePosY) && (cPosX == ePosX)){
				setChanged();
				notifyObservers("gameWon");
			}
		}
		return move;
	}

	@Override
	public boolean moveRight(boolean quiet) {
		boolean move=false;
		if (!quiet) {
			undoBoards.add(copyBoard(mBoard));		
		}
		int ePosX = getExitPoint().x;
		int ePosY = getExitPoint().y;
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosY + 1;
		if((newPos < mBoard[0].length) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			score+=10;
			if (!quiet)
			{
				mBoard[cPosX][newPos] = mBoard[cPosX][cPosY] ;
				mBoard[cPosX][cPosY] = 0 ;
			}
			setChanged();
			notifyObservers();
			if ((newPos == ePosY) && (cPosX == ePosX)){
				setChanged();
				notifyObservers("gameWon");
			}
		}
		return move;
	}


	// initialize the Maze Board
	@Override
	public void initializeBoard() {
		createEmptyBoard(16,16);		
		int[][] b = { { 2 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,0 , 0 , 0 , 0 , 0 , 0 }, //1
				    { 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,0 , 0 , 0 , 0 , 0 , 0 }, //2
				    { 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,0 , 0 , 0 , 0 , 0 , 0 }, //3
				    { 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,0 , 0 , 0 , 0 , 0 , 0 }, //4
				    { -1 , -1 , -1 , -1 , 0 , 0 , 0 , 0 , 0 , 0 ,0 , 0 , 0 , 0 , 0 , 0 }, //5
				    { -1 , 0 , 0 , 0 , -1 , 0 , -1 , 0 , 0 , 0 ,0 , 0 , 0 , 0 , 0 , 0 }, //6 
				    { -1 , 0 , 0 , 0 , -1 , 0 , -1 , 0 , 0 , 0 ,0 , 0 , 0 , 0 , 0 , 0 }, //7
				    { -1 , 0 , 0 , -1 , -1 , 0 , -1 , 0 , 0 , 0 ,0 , 0 , 0 , 0 , 0 , 0 }, //8
				    { -1 , 0 , 0 , -1 , 0 , 0 , -1 , 0 , 0 , 0 ,0 , 0 , 0 , 0 , 0 , 0 }, //9
				    { -1 , 0 , 0 , -1 , 0 , 0 , 0 , -1 , -1 , -1 ,-1 , -1 , -1 , 0 , 0 , 0 }, //10
				    { -1 , 0 , 0 , -1 , 0 , 0 , 0 , 0 , 0 , 0 ,0 , 0 , 0 , -1 , 0 , 0 }, //11
				    { -1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,0 , 0 , 0 , -1 , 0 , 0 }, //12
				    { -1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,0 , 0 , 0 , -1 , 0 , 0 }, //13
				    { -1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,0 , 0 , 0 , -1 , -1 , -1 }, //14
				    { -1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,0 , 0 , 0 , 0 , 0 , 0}, //15
				    { -1 , -1 , -1 , -1 , -1 , -1 , -1 , -1 , -1 , -1 ,-1 , -1 , -1 , -1 , -1 , 1 }, //16
					};
		mBoard = b;
		setChanged();
		notifyObservers();

	}

	private void createEmptyBoard(int m,int n) {
		for (int i=0 ; i<m ; i++)
			for (int j=0 ; j<n ; j++){
				mBoard[i][j] = 0;
			}

	}

	public void displayBoard(int[][] mBoard) {		
		for(int i=0;i<mBoard.length;++i){
			for(int j=0;j<mBoard[0].length;++j)				
				System.out.print(mBoard[i][j] + " ");			
			System.out.println("\n");
		}
	}

	private void copyBoard(int[][] source,int[][] target) {
		for(int i=0;i<source.length;i++) 
			 target[i] = Arrays.copyOf(source[i], source[i].length);				
	}

	public static int[][] copyBoard(int[][] source) {
	    int[][] copy = new int[source.length][];
	    for (int i = 0; i < source.length; i++) {
	        copy[i] = Arrays.copyOf(source[i],source[i].length);
	    }
	    return copy;
	}
	
	@Override
	public void undoBoard() {
		if (undoBoards.isEmpty())
			System.out.println("No Undo moves to perform");
		else
		{
			mBoard=copyBoard(undoBoards.get(undoBoards.size()-1));
			undoBoards.remove(undoBoards.size()-1);
		}
		setChanged();
		notifyObservers();
	}
	
	public boolean isGameWon(int x, int y) 
	{
		int cPosX = x;
		int cPosY = y;
		if (mBoard[cPosX][cPosY] == 2 )
			return true;
		else
			return false;
	}
	

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void saveGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGame() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void moveDiagonalRightUp(boolean b) {		
		moveUp(b);
		moveRight(b);
		score-=5;
		
	}

	@Override
	public void moveDiagonalRightDown(boolean b) {		
		moveRight(b);
		moveDown(b);
		score-=5;
		
	}

	@Override
	public void moveDiagonalLeftUp(boolean b) {		
		moveLeft(b);
		moveUp(b);
		score-=5;
	}

	@Override
	public void moveDiagonalLeftDown(boolean b) {
		moveLeft(b);
		moveDown(b);
		score-=5;		
	}
	

}