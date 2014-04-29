package model;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;

public class Model2048 extends Observable implements Model {
	
	int[][] mBoard;	
	ArrayList<int[][]> undoBoards = new ArrayList<int[][]>();
	//HashSet<Integer,int[][]> undoBoards = new HashSet<Integer,int[][]>();
	int score=0;
	int free=-2;
	
	public Model2048(int rows,int cols) {		
		mBoard = new int[rows][cols];		
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
		{
			System.out.println("No Undo moves to perform");
			setChanged();
			notifyObservers("undoEnd");
		}	
		else
		{
			mBoard=copyBoard(undoBoards.get(undoBoards.size()-1));
			undoBoards.remove(undoBoards.size()-1);			
			setChanged();
			notifyObservers();
		}
		
	}
	
	/* Move all possible cells to the Right and merge cells if needed
	 Input: boolean quiet - if true, do not move states, only see if we have possible movements.
	 Output: boolean - if we have possible movements or not.
	 */
	public boolean moveRight(boolean quiet){
		System.out.println("Moved right on model");
		boolean move=false;
		//save board before operation for undo purposes
		if (!quiet)
			undoBoards.add(copyBoard(mBoard));
			//undoBoards.put(score,copyBoard(mBoard));		
		for(int i=0;i<mBoard.length;i++){		
			ArrayList<Integer> merged = new ArrayList<Integer>(); 
			for(int j=mBoard[0].length-1;j>0;j--){
				int pos = j-1;
				if(mBoard[i][pos] != free){
					for(int k=j;k<mBoard[0].length;k++){ 
						if(mBoard[i][k] == free){
							move=true;
							if (!quiet)
							{
								mBoard[i][k] = mBoard[i][pos];
								mBoard[i][pos] = free;
								pos++;
							}
						}else{						
							if(mBoard[i][pos] == mBoard[i][k] && !merged.contains(k)){
								move=true;
								if(!quiet)
								{									
									score+=mBoard[i][pos];
									mBoard[i][k] = mBoard[i][pos] * 2;
									mBoard[i][pos] = free;
									merged.add(k);																		
								}
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
			//undoBoards.put(score,copyBoard(mBoard));
		boolean move=false;	
		for(int i=0;i<mBoard.length;i++){
			ArrayList<Integer> merged = new ArrayList<Integer>();
			for(int j=0;j<mBoard[0].length-1;j++){
				int pos = j+1;
				if(mBoard[i][pos] != free){				
					for(int k=j;k>=0;k--){ 
						if(mBoard[i][k] == free){
							move=true;
							if (!quiet)
							{
								mBoard[i][k] = mBoard[i][pos];
								mBoard[i][pos] = free;
								pos--;
							}
						}
						else{
							if(mBoard[i][pos] == mBoard[i][k] && !merged.contains(k)){
								move=true;
								if (!quiet)
								{									
									score+=mBoard[i][pos];
									mBoard[i][k] = mBoard[i][pos] * 2;
									mBoard[i][pos] = free;
									merged.add(k);																		
								}
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
			//undoBoards.put(score,copyBoard(mBoard));
		boolean move=false;	
		for(int i=0;i<mBoard[0].length;i++){
			ArrayList<Integer> merged = new ArrayList<Integer>(); 
				for(int j=0;j<mBoard.length-1;j++){
					int pos = j+1;
					if(mBoard[pos][i] != free){				
						for(int k=j;k>=0;k--){
							if(mBoard[k][i] == free){
								move=true;
								if(!quiet)
								{
									mBoard[k][i] = mBoard[pos][i];
									mBoard[pos][i] = free;
									pos--;
								}
							}else{
								if(mBoard[pos][i] == mBoard[k][i] && !merged.contains(k)){
									move=true;
									if (!quiet)
									{										
										score+=mBoard[pos][i];
										mBoard[k][i] = mBoard[pos][i] * 2;
										mBoard[pos][i] = free;
										merged.add(k);																			
									}
									
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
			//undoBoards.put(score,copyBoard(mBoard));
		boolean move=false;
		for(int i=0;i<mBoard[0].length;i++){
			ArrayList<Integer> merged = new ArrayList<Integer>();
			for(int j=mBoard.length-1;j>0;j--){
				int pos = j-1;
				if(mBoard[pos][i] != free){				
					for(int k=j;k<mBoard.length;k++){
						if(mBoard[k][i] == free){
							move=true;
							if (!quiet)
							{
								mBoard[k][i] = mBoard[pos][i];
								mBoard[pos][i] = free;	
								pos++;
							}
						}else{
							if(mBoard[pos][i] == mBoard[k][i] && !merged.contains(k)){
								move=true;
								if (!quiet)
								{									
									score+=mBoard[pos][i];
									mBoard[k][i] = mBoard[pos][i] * 2;
									mBoard[pos][i] = free;
									merged.add(k);																	
								}
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
				mBoard[i][j]=free;
				//Create state with 0?
			}
		}
		undoBoards = new ArrayList<int[][]>();
		//undoBoards = new HashMap<Integer,int[][]>();
	}
	
	/*
	 * Gets all free cells (0 value)
	 * Output: returns a list of Points
	 */
	private ArrayList<Point> getFreeStates() {
		ArrayList<Point> freeStates = new ArrayList<Point>();
		for(int i=0;i<mBoard.length;++i){
			for(int j=0;j<mBoard[0].length;++j){				
				if (mBoard[i][j] == free)
					freeStates.add(new Point(i,j));
			}
		}
		return freeStates;
	}
	
	/*
	 * Generate new state on the board
	 */
	public void generateState() {
		//Check if we gave space to generate
		int freeSize = getFreeStates().size();
		if (freeSize != 0)
		{
			ArrayList<Point> freeStates = getFreeStates();			
			int cellIndex = new Random().nextInt(freeStates.size());
			int cellX = freeStates.get(cellIndex).x;
			int cellY = freeStates.get(cellIndex).y;		
			mBoard[cellX][cellY]=generateScore();
			freeStates.remove(cellIndex);		
		}
	}
	
	/*
	 * Initialize Board: all board with 0 besides 2 cells with random score(2/4)
	 */
	public void initializeBoard() {			
		initializeScore();
		createEmptyBoard();
		ArrayList<Point> freeStates = getFreeStates();			
		for(int i=0;i<2;++i)
		{
			int cellIndex = new Random().nextInt(freeStates.size());
			int cellX = freeStates.get(cellIndex).x;
			int cellY = freeStates.get(cellIndex).y;
			int score=generateScore();
			mBoard[cellX][cellY]=score;
			freeStates.remove(cellIndex);
		}
		//mBoard = new int[][]{{2,4,16,32},{4,8,32,16},{2,16,64,128},{16,8,32,512}};
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
	 * Returns user Score (The maximum value on the board)
	 */
	public int getScore() {
		return this.score;
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
		return ((!isMovesAvailable()) && (getFreeStates().size() == 0) );	
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
	
	private void initializeScore() {
		this.score=0;
	}
	
	public void loadGame() { 
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream("resources/array.txt"));
		} catch (FileNotFoundException e) {			// 
			System.out.println("cannot Load the Game. source file does not exist");
		} catch (IOException e) { 
			System.out.println("I/O error while loading the source file. Error=" + e.getMessage());
		}
		try {
			mBoard = (int[][]) in.readObject();
		} catch (IOException e) {
			System.out.println("I/O error while reading Object from file");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setChanged();
		notifyObservers();
		try {
			in.close();
		} catch (IOException e) {
			System.out.println("Unable to close File.Error=" + e.getMessage());			
		}
	}
	
	public void saveGame() {
		ObjectOutputStream out=null;
		File outFile = new File("resources/array.txt");
		if (!outFile.exists())
			try {
				out = new ObjectOutputStream(new FileOutputStream("array.txt"));
			} catch (FileNotFoundException e) {
				System.out.println("cannot Save the Game. cannot create output file");
			} catch (IOException e) {
				e.printStackTrace();
			}
		try {
			out.writeObject(mBoard);
		} catch (IOException e) {
			System.out.println("CAnnot write the Board to the file");
		}
		try {
			out.close();
		} catch (IOException e) {
			System.out.println("Cannot close the file. Error=" + e.getMessage());
		}
	}

	@Override
	public void moveDiagonalRightUp(boolean b) {
	}

	@Override
	public void moveDiagonalRightDown(boolean b) {
	}

	@Override
	public void moveDiagonalLeftUp(boolean b) {

	}

	@Override
	public void moveDiagonalLeftDown(boolean b) {	
	}

}
