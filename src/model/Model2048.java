package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;

public class Model2048 extends Observable implements Model {
	
	int[][] mBoard;		
	ArrayList<int[][]> undoBoards = new ArrayList<int[][]>();
	
	public Model2048() {		
		mBoard = new int[4][4];		
	}
	
	@Override
	public int[][] getData() {
		return mBoard;
	}

	/*
	 * Undo the last operation and revert back to the board before it
	 * if no operation was made, do nothing
	 */
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
	
	/* Move all possible cells to the Right and merge cells if needed
	 Input: boolean quiet - if true, do not move states, only see if we have possible movements.
	 Output: boolean - if we have possible movements or not.
	 */
	public boolean moveRight(boolean quiet){
		//save board before operation for undo purposes
		if (!quiet)
			undoBoards.add(copyBoard(mBoard));			
		boolean move=true;
		for(int i=0;i<mBoard.length;i++){		
			ArrayList<Integer> merged = new ArrayList<Integer>(); 
			for(int j=mBoard[0].length-1;j>0;j--){
				int pos = j-1;
				if(mBoard[i][pos] != 0){
					for(int k=j;k<mBoard[0].length;k++){ 
						if(mBoard[i][k] == 0){
							move=true;
							if (quiet == true)							
								break;							
							mBoard[i][k] = mBoard[i][pos];
							mBoard[i][pos] = 0;
							pos++;											
						}else{						
							if(mBoard[i][pos] == mBoard[i][k] && !merged.contains(k)){
								move=true;
								if (quiet == true)															
									break;								
								mBoard[i][k] = mBoard[i][pos] * 2;
								mBoard[i][pos] = 0;
								merged.add(k);							
							}
							break;
						}
					}
				}
			}
		}		
		if (!quiet)
		{			
			setChanged();
			if (isGameOver())
				notifyObservers("gameOver");
			else if (isGameWon())
				notifyObservers("gameWon");
			else
			{
				generateState();
				notifyObservers();
			}
		}
		return move;
	}

	

	/* Move all possible cells to the Left and merge cells if needed
	 Input: boolean quiet - if true, do not move states, only see if we have possible movements. (such as preview)
	 Output: boolean - if we have possible movements or not.
	 */
	public boolean moveLeft(boolean quiet){		
		//save board before operation for undo purposes
		if (!quiet)
			undoBoards.add(copyBoard(mBoard));
		boolean move=false;	
		for(int i=0;i<mBoard.length;i++){
			ArrayList<Integer> merged = new ArrayList<Integer>();
			for(int j=0;j<mBoard[0].length-1;j++){
				int pos = j+1;
				if(mBoard[i][pos] != 0){				
					for(int k=j;k>=0;k--){ 
						if(mBoard[i][k] == 0){
							move=true;
							if (quiet == true)
								break;
							mBoard[i][k] = mBoard[i][pos];
							mBoard[i][pos] = 0;
							pos--;												
						}
						else{
							if(mBoard[i][pos] == mBoard[i][k] && !merged.contains(k)){
								move=true;
								if (quiet == true)
									break;
								mBoard[i][k] = mBoard[i][pos] * 2;
								mBoard[i][pos] = 0;
								merged.add(k);														
							}
							break;
						}
					}
				}
			}
		}
		if (!quiet)
		{			
			setChanged();
			if (isGameOver())
				notifyObservers("gameOver");
			else if (isGameWon())
				notifyObservers("gameWon");
			else
			{
				generateState();
				notifyObservers();
			}
		}
		return move;
	}
	
	/* Move all possible cells to the Up and merge cells if needed
	 Input: boolean quiet - if true, do not move states, only see if we have possible movements. (such as preview)
	 Output: boolean - if we have possible movements or not.
	 */
	public boolean moveUp(boolean quiet){		
		//save board before operation for undo purposes	
		if (!quiet)
			undoBoards.add(copyBoard(mBoard));
		boolean move=false;	
		for(int i=0;i<mBoard[0].length;i++){
			ArrayList<Integer> merged = new ArrayList<Integer>(); 
				for(int j=0;j<mBoard.length-1;j++){
					int pos = j+1;
					if(mBoard[pos][i] != 0){				
						for(int k=j;k>=0;k--){
							if(mBoard[k][i] == 0){
								move=true;
								if (quiet == true)
									break;
								mBoard[k][i] = mBoard[pos][i];
								mBoard[pos][i] = 0;
								pos--;							
							}else{
								if(mBoard[pos][i] == mBoard[k][i] && !merged.contains(k)){
									move=true;
									if (quiet == true) 
										break;
									mBoard[k][i] = mBoard[pos][i] * 2;
									mBoard[pos][i] = 0;
									merged.add(k);							
								}
								break;
							}
						}
					}
				}
		}
		if (!quiet)
		{			
			setChanged();
			if (isGameOver())
				notifyObservers("gameOver");
			else if (isGameWon())
				notifyObservers("gameWon");
			else
			{
				generateState();
				notifyObservers();
			}
		}
		return move;
	}


	/* Move all possible cells to the Down and merge cells if needed
	 Input: boolean quiet - if true, do not move states, only see if we have possible movements. (such as preview)
	 Output: boolean - if we have possible movements or not.
	 */
	public boolean moveDown(boolean quiet){		
		//save board before operation for undo purposes
		if (! quiet)
			undoBoards.add(copyBoard(mBoard));
		boolean move=false;
		for(int i=0;i<mBoard[0].length;i++){
			ArrayList<Integer> merged = new ArrayList<Integer>();
			for(int j=mBoard.length-1;j>0;j--){
				int pos = j-1;
				if(mBoard[pos][i] != 0){				
					for(int k=j;k<mBoard.length;k++){
						if(mBoard[k][i] == 0){
							move=true;
							if (quiet == true)
								break;
							mBoard[k][i] = mBoard[pos][i];
							mBoard[pos][i] = 0;	
							pos++;						
						}else{
							if(mBoard[pos][i] == mBoard[k][i] && !merged.contains(k)){
								move=true;
								if (quiet == true)
									break;
								mBoard[k][i] = mBoard[pos][i] * 2;
								mBoard[pos][i] = 0;
								merged.add(k);							
							}
							break;
						}
					}
				}
			}
		}
		if (!quiet)
		{			
			setChanged();
			if (isGameOver())
				notifyObservers("gameOver");
			else if (isGameWon())
				notifyObservers("gameWon");
			else
			{
				generateState();
				notifyObservers();
			}
		}
		return move;
	}


	/*
	 * print Board array
	 */
	private void displayBoard(int[][] mBoard) {		
		for(int i=0;i<mBoard.length;++i){
			for(int j=0;j<mBoard[0].length;++j)				
				System.out.print(mBoard[i][j] + " ");			
			System.out.println("\n");
		}
	}
	
	/*
	 *  Initialize Board array with 0 values
	 *  also initialize unfoBoards list
	 */
	private void createEmptyBoard() {
		for(int i=0;i<mBoard.length;++i){
			for(int j=0;j<mBoard[0].length;++j){				
				mBoard[i][j]=0;
				//Create state with 0?
			}
		}
		undoBoards = new ArrayList<int[][]>();
	}
	
	/*
	 * Gets all free cells (0 value)
	 * Output: returns a list of Points
	 */
	private ArrayList<Point> getFreeStates() {
		ArrayList<Point> free = new ArrayList<Point>();
		for(int i=0;i<mBoard.length;++i){
			for(int j=0;j<mBoard[0].length;++j){				
				if (mBoard[i][j] == 0)
					free.add(new Point(i,j));
			}
		}
		return free;
	}
	
	/*
	 * Generate new state on the board
	 */
	public void generateState() {
		ArrayList<Point> free = getFreeStates();			
		int cellIndex = new Random().nextInt(free.size());
		int cellX = free.get(cellIndex).x;
		int cellY = free.get(cellIndex).y;		
		mBoard[cellX][cellY]=generateScore();
		free.remove(cellIndex);		
	}
	
	/*
	 * Initialize Board: all board with 0 besides 2 cells with random score(2/4)
	 */
	public void initializeBoard() {
		System.out.println("Model: initializeBoard");
		createEmptyBoard();
		ArrayList<Point> free = getFreeStates();			
		for(int i=0;i<2;++i)
		{
			int cellIndex = new Random().nextInt(free.size());
			int cellX = free.get(cellIndex).x;
			int cellY = free.get(cellIndex).y;
			int score=generateScore();
			mBoard[cellX][cellY]=score;
			free.remove(cellIndex);
		}	
		setChanged();
		notifyObservers();
	}

	/*
	 * Copy Board array
	 * Output: return the copied array
	 */
	public static int[][] copyBoard(int[][] source) {
	    int[][] copy = new int[source.length][];
	    for (int i = 0; i < source.length; i++) {
	        copy[i] = Arrays.copyOf(source[i],source[i].length);
	    }
	    return copy;
	}
	
	
	/*
	 * generate the next score which will be 2 or 4 (90-10)
	 */
	private int generateScore() {
		return Math.random() < 0.9 ? 2 : 4;		
	}

	/*
	 * Verify if we have any possible move on the game 
	 * check each operation(right,left,down,up) in quiet mode and see if we have possible movements there
	 */
	private boolean isMovesAvailable() {
		return (moveRight(true) || moveLeft(true) || moveUp(true) || moveDown(true));					 		
	}
	
	/*
	 * Check if the game is over. 
	 * if we got no possible moves and if we got no free cells
	 */
	public boolean isGameOver() {
		return !isMovesAvailable() && (getFreeStates().size() == 0);		
	}
	
	/*
	 * Check if we won the game. if we have a cell with 2048 value
	 */
	public boolean isGameWon() 
	{
		for(int i=0; i<mBoard.length;++i)
		{
			for(int j=0;j<mBoard.length;++j)
			{
				if (mBoard[i][j] == 2048)
					return true;
			}
		}
		return false;
	}
}
