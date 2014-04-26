package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;

public class ModelMaze extends Observable implements Model {

	int[][] mBoard;	
	ArrayList<int[][]> undoBoards = new ArrayList<int[][]>();
	int M;
	int N;

	public ModelMaze() {
		mBoard=new int[4][4];
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

		for (int i=0; i<mBoard[0].length; i++)
			for (int j=0;j<mBoard.length ; j++)
				if (mBoard[i][j] == 1 )
					return new Point(i,j);

		return null; // din't find the mouse
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
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosY - 1;
		if((newPos >=0) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			if (!quiet)
			{
				mBoard[cPosX][newPos] = mBoard[cPosX][cPosY] ;
				mBoard[cPosX][cPosY] = 0 ;
			}
		}
		setChanged();
		notifyObservers();
		return move;
	}

	@Override
	public boolean moveDown(boolean quiet) {
		boolean move=false;
		if (!quiet) {
			undoBoards.add(copyBoard(mBoard));		
		}
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosY + 1;
		if((newPos <mBoard.length) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			if (!quiet)
			{
				mBoard[cPosX][newPos] = mBoard[cPosX][cPosY] ;
				mBoard[cPosX][cPosY] = 0 ;
			}
		}
		setChanged();
		notifyObservers();
		return move;
	}

	@Override
	public boolean moveLeft(boolean quiet) {
		boolean move=false;
		if (!quiet) {
			undoBoards.add(copyBoard(mBoard));		
		}
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosX - 1;
		if((newPos >=0 ) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			if (!quiet)
			{
				mBoard[newPos][cPosY] = mBoard[cPosX][cPosY] ;
				mBoard[cPosX][cPosY] = 0 ;
			}
		}
		setChanged();
		notifyObservers();
		return move;
	}

	@Override
	public boolean moveRight(boolean quiet) {
		boolean move=false;
		if (!quiet) {
			undoBoards.add(copyBoard(mBoard));		
		}
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosX + 1;
		if((newPos < mBoard[0].length) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			if (!quiet)
			{
				mBoard[newPos][cPosY] = mBoard[cPosX][cPosY] ;
				mBoard[cPosX][cPosY] = 0 ;
			}
		}
		setChanged();
		notifyObservers();
		return move;
	}


	// initialize the Maze Board
	@Override
	public void initializeBoard() {
		createEmptyBoard(4,4);			
//		int[][] b =  {
//	    		{ 2 , 0 , -1 , -1 },
//	    		{ 0 , -1 , 0  , 0 },
//	    		{ 0 , 0 , -1 , 0 },
//	    		{ 0 , -1 , 0 , 1  },
//	    };			
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
		System.out.println("sd");		
	}

}