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
	int M;
	int N;

	public ModelMaze() {
		mBoard=new int[16][16];
	}
	
	public int getM() {
		return M;
	}
	public void setM(int m) {
		M = m;
	}
	public int getN() {
		return N;
	}
	public void setN(int n) {
		N = n;
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
	public int getCurrentScore() {
		// TODO Auto-generated method stub
		return 0;
	}

}