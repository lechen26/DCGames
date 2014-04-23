package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;

public class Model2048 extends Observable implements Model {
	
	int[][] mBoard;	
	int[][] undoBoard;
	
	public Model2048() {		
		mBoard = new int[4][4];		
	}

	@Override
	public int[][] getData() {
		return mBoard;
	}

	public void undoBoard() {					
		mBoard=copyBoard(undoBoard);		
		setChanged();
		notifyObservers();
	}
	
	public void moveRight(){
	//Save the board state right before we moving Right	
	undoBoard=copyBoard(mBoard);	
	for(int i=0;i<mBoard.length;i++){		
		ArrayList<Integer> merged = new ArrayList<Integer>(); 
		for(int j=mBoard[0].length-1;j>0;j--){
			int pos = j-1;
			if(mBoard[i][pos] != 0){
				Boolean moved = false;
				for(int k=j;k<mBoard[0].length;k++){ 
					if(mBoard[i][k] == 0){
						mBoard[i][k] = mBoard[i][pos];
						mBoard[i][pos] = 0;
						pos++;
						moved = true;
					}else{						
						if(mBoard[i][pos] == mBoard[i][k] && !merged.contains(k)){
						mBoard[i][k] = mBoard[i][pos] * 2;
						mBoard[i][pos] = 0;
						merged.add(k);
						moved = true;
					}
					break;
				}
			}
			}
		}
	}	
	generateState();
	setChanged();
	notifyObservers();
}

public void moveLeft(){
	//Save the board state right before we moving left
	//undoBoard = this.mBoard.clone();
	undoBoard=copyBoard(mBoard);
	for(int i=0;i<mBoard.length;i++){
		ArrayList<Integer> merged = new ArrayList<Integer>();
		for(int j=0;j<mBoard[0].length-1;j++){
			int pos = j+1;
			if(mBoard[i][pos] != 0){
				Boolean moved = false;
				for(int k=j;k>=0;k--){ 
					if(mBoard[i][k] == 0){
						mBoard[i][k] = mBoard[i][pos];
						mBoard[i][pos] = 0;
						pos--;
						moved = true;
					}
					else{
						if(mBoard[i][pos] == mBoard[i][k] && !merged.contains(k)){
							mBoard[i][k] = mBoard[i][pos] * 2;
							mBoard[i][pos] = 0;
							merged.add(k);
							moved = true;	
						}
						break;
					}
				}
			}
		}
	}
	generateState();
	setChanged();
	notifyObservers();
}

public void moveUp(){
	//Save the board state right before we moving up	
	undoBoard=copyBoard(mBoard);			
	for(int i=0;i<mBoard[0].length;i++){
		ArrayList<Integer> merged = new ArrayList<Integer>(); 
			for(int j=0;j<mBoard.length-1;j++){
				int pos = j+1;
				if(mBoard[pos][i] != 0){					
					for(int k=j;k>=0;k--){
						if(mBoard[k][i] == 0){
							mBoard[k][i] = mBoard[pos][i];
							mBoard[pos][i] = 0;
							pos--;							
						}else{
							if(mBoard[pos][i] == mBoard[k][i] && !merged.contains(k)){
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
	generateState();
	setChanged();
	notifyObservers();
}

public void moveDown(){
	//Save the board state right before we moving down
	undoBoard=copyBoard(mBoard);
	for(int i=0;i<mBoard[0].length;i++){
		ArrayList<Integer> merged = new ArrayList<Integer>();
		for(int j=mBoard.length-1;j>0;j--){
			int pos = j-1;
			if(mBoard[pos][i] != 0){				
				for(int k=j;k<mBoard.length;k++){
					if(mBoard[k][i] == 0){
						mBoard[k][i] = mBoard[pos][i];
						mBoard[pos][i] = 0;	
						pos++;
					}else{
						if(mBoard[pos][i] == mBoard[k][i] && !merged.contains(k)){
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
	generateState();	
	setChanged();
	notifyObservers();
}


	public void displayBoard(int[][] mBoard) {		
		for(int i=0;i<mBoard.length;++i){
			for(int j=0;j<mBoard[0].length;++j)				
				System.out.print(mBoard[i][j] + " ");			
			System.out.println("\n");
		}
	}
	
	// Initialize Board with 0
	private void createEmptyBoard() {
		for(int i=0;i<mBoard.length;++i){
			for(int j=0;j<mBoard[0].length;++j){				
				mBoard[i][j]=0;
				//Create state with 0?
			}
		}
	}
	
	// Gets all cells with 0 value
	private ArrayList<State> getFreeStates() {
		ArrayList<State> free = new ArrayList<State>();
		for(int i=0;i<mBoard.length;++i){
			for(int j=0;j<mBoard[0].length;++j){				
				if (mBoard[i][j] == 0)
					free.add(new State(new Point(i,j),0));
			}
		}
		return free;
	}
	
	public void generateState() {
		ArrayList<State> free = getFreeStates();			
		int cellIndex = new Random().nextInt(free.size());
		int cellX = free.get(cellIndex).getX();
		int cellY = free.get(cellIndex).getY();
		int score=generateScore();
		mBoard[cellX][cellY]=score;
		free.remove(cellIndex);		
	}
	
	//Initialize Board: all board with 0 besides 2 cells with random score(2/4)
	public void initializeBoard() {
		createEmptyBoard();
		ArrayList<State> free = getFreeStates();			
		for(int i=0;i<2;++i)
		{
			int cellIndex = new Random().nextInt(free.size());
			int cellX = free.get(cellIndex).getX();
			int cellY = free.get(cellIndex).getY();
			int score=generateScore();
			mBoard[cellX][cellY]=score;
			free.remove(cellIndex);
		}	
		setChanged();
		notifyObservers();
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
	// generate the next score which will be 2 or 4 (90-10)
	private int generateScore() {
		return Math.random() < 0.9 ? 2 : 4;		
	}
		
}
