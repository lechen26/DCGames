package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

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
		int newPos = cPosX - 1;
		if((newPos >=0) && (mBoard[cPosX][newPos] != -1)){
			move=true;
			if (!quiet)
			{
				mBoard[newPos][cPosY] = mBoard[cPosX][cPosY] ;
				mBoard[cPosX][cPosY] = 0 ;
			}
		}
		score+=10;
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
		int newPos = cPosX + 1;
		if((newPos < mBoard.length) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			if (!quiet)
			{
				mBoard[newPos][cPosY] = mBoard[cPosX][cPosY] ;
				mBoard[cPosX][cPosY] = 0 ;
			}
		}
		score+=10;
		setChanged();
		notifyObservers();
		return move;
	}

	@Override
	public boolean moveLeft(boolean quiet) {
		System.out.println("Move left");
		boolean move=false;
		if (!quiet) {
			undoBoards.add(copyBoard(mBoard));		
		}
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosY - 1;
		if((newPos >=0 ) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			if (!quiet)
			{
				mBoard[cPosX][newPos] = mBoard[cPosX][cPosY] ;
				mBoard[cPosX][cPosY] = 0 ;
			}
		}
		score+=10;
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
		int newPos = cPosY + 1;
		if((newPos < mBoard[0].length) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			if (!quiet)
			{
				mBoard[cPosX][newPos] = mBoard[cPosX][cPosY] ;
				mBoard[cPosX][cPosY] = 0 ;
			}
		}
		score+=10;
		setChanged();
		notifyObservers();
		return move;
	}


	// initialize the Maze Board
	@Override
	public void initializeBoard() {
		createEmptyBoard(4,4);
		mBoard[0][2]=1;
		mBoard[1][3]=-1;
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

	@Override
	public int getScore() {
		return score;
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