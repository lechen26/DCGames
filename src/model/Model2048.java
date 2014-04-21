package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class Model2048 extends Observable implements Model {
	
	int[][] mBoard;
	
	public Model2048() {		
		mBoard = new int[4][4];		
	}

	@Override
	public int[][] getData() {
		return mBoard;
	}

	@Override
	public void moveUp() {
		int pos=0;		
		//Running on Board on each line from up to down
		for(int i=mBoard[0].length;i>0;i--){				
			for(int j=0;j<mBoard.length-1;j++){
				pos=j+1;
				if(mBoard[pos][i] != 0){					
					for(int k=j;j>0;j--){
						if(mBoard[k][i] == 0){
							mBoard[k][i] = mBoard[i][pos];
							mBoard[pos][k] = 0;
							pos--;							
						}else{
							if(mBoard[pos][i] == mBoard[k][i] ){
								mBoard[k][i] = mBoard[pos][i]* mBoard[pos][i];
								mBoard[pos][i] = 0;															
							}						
						}
					}					
					
				}
			}
		}
		
	}

	@Override
	public void moveDown() {
		int pos=0;		
		//Running on Board on each line from down to up
		for(int i=mBoard[0].length;i>0;i--){				
			for(int j=mBoard.length-1;j>0;j--){
				pos=j-1;
				if(mBoard[pos][i] != 0){					
					for(int k=j;j<mBoard.length;j++){
						if(mBoard[k][i] == 0){
							mBoard[k][i] = mBoard[i][pos];
							mBoard[pos][k] = 0;
							pos++;							
						}else{
							if(mBoard[pos][i] == mBoard[k][i] ){
								mBoard[k][i] = mBoard[pos][i]* mBoard[pos][i];
								mBoard[pos][i] = 0;															
							}						
						}
					}					
					
				}
			}
		}

	}

	@Override
	public void moveLeft() {		
		int pos=0;		
		//Running on Board on each line from left to right
		for(int i=0;i<mBoard.length;i++){				
			for(int j=0;j<mBoard[0].length-1;j++){
				pos=j+1;
				if(mBoard[i][pos] != 0){					
					for(int k=j;k>0;k--){
						if(mBoard[i][k] == 0){
							mBoard[i][k] = mBoard[i][pos];
							mBoard[i][pos] = 0;
							pos++;							
						}else{
							if(mBoard[i][pos] == mBoard[i][k] ){
								mBoard[i][k] = mBoard[i][pos]* mBoard[i][pos];
								mBoard[i][pos] = 0;															
							}						
						}
					}					
					
				}
			}
		}		
	}


	@Override
	public void moveRight() {		
		int pos=0;		
		//Running on Board on each line from right to left
		for(int i=0;i<mBoard.length;i++){				
			for(int j=mBoard[0].length-1;j>0;j--){
				pos=j-1;
				if(mBoard[i][pos] != 0){				
					for(int k=j;k<mBoard[0].length;k++){
						if(mBoard[i][k] == 0){
							mBoard[i][k] = mBoard[i][pos];
							mBoard[i][pos] = 0;
							pos++;		
						}else{
							if(mBoard[i][pos] == mBoard[i][k] ){
								mBoard[i][k] = mBoard[i][pos]* mBoard[i][pos];
								mBoard[i][pos] = 0;																
							}						
						}
					}	
				}
			}
		}
	}

	
	public void displayBoard() {
		initializeBoard();
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
					free.add(new State(i,j,0));
			}
		}
		return free;
	}
	
	//Initialize Board: all board with 0 besides 2 cells with random score(2/4)
	private void initializeBoard() {
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
	}
	
	// generate the next score which will be 2 or 4 (90-10)
	private int generateScore() {
		return Math.random() < 0.9 ? 2 : 4;		
	}
		
}
