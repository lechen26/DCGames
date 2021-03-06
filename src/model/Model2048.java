package model;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import common.Constants;
import common.RemoteInt;
import common.customModel;


public class Model2048 extends Observable implements Model {	
	
	ArrayList<int[][]> undoBoards = new ArrayList<int[][]>();
	ArrayList<Integer> undoScores = new ArrayList<Integer>();
	int[][] mBoard;		
	int winNumber;
	int originalWin;
	int score=0;		
	String server;
	boolean win=false;
	int depthNumber=Constants.defaultDepth;
	volatile boolean stopSolverPressed=false;
	int hintsNumber=Constants.defaultHint;
	
	public Model2048(int rows,int cols,int winNumber) {		
		mBoard = new int[rows][cols];	
		this.winNumber=winNumber;
		this.originalWin=winNumber;
	}
	
	/**
	 * Get Winning number
	 * @return the winning number for the game
	 */
	public int getWinNumber() {
		return winNumber;
	}

	/*
	 * set Winning Number
	 * @param the winning number for the game
	 */
	public void setWinNumber(int number) {
		this.winNumber = number;		
	}

	
	/**
	 * double the winning number if we want to continue the game after winning
	 * also set the win boolean to false
	 */
	public void doubleWinNumber(){
		this.winNumber=winNumber*2;
		win=false;
	}
	
	/**
	 * Get Board data
	 * @return the board data
	 */
	@Override
	public int[][] getBoard() {
		return mBoard;
	}

	/**
	 * Set Board data
	 * @param board to set
	 */
	public void setBoard(int[][] board) {
		this.mBoard = board;
	}
	
	/**
	 * Undo the last operation and revert back to the board before it (including score)
	 * if no operation was made, do nothing
	 */
	public void undoBoard() {
		if (undoBoards.isEmpty())
		{			
			setChanged();
			notifyObservers("undoEnd");
		}	
		else
		{
			mBoard = copyBoard(undoBoards.remove(undoBoards.size()-1));			
			score=undoScores.remove(undoScores.size()-1);			
			setChanged();
			notifyObservers();
		}
		
	}
	
	
	/**
	 * set GameOver Board
	 */
	private void setGameOver() {
		for(int i=0;i< mBoard.length; ++i) {		
			for(int j=0; j < mBoard[0].length; ++j) {																	
				mBoard[i][j]=-100;		
			}
		}
		setChanged();
		notifyObservers();		
	}
	
	
	/**
	 * Helper method to check GameOver and Win and notify after specific move
	 */
	private void checkAndNotify() {			
		if (isGameOver()){
			stopSolverPressed=true;			
			setGameOver();
			setChanged();
			notifyObservers("gameOver");
		}  else if ((!win) && (isGameWon())){
			stopSolverPressed=true;
			setChanged();			
			notifyObservers("gameWon");			
		}
		else{
			generateState();
			setChanged();
			notifyObservers();
		}
	}
	
	
	/**
	 * Move all possible cells to the Right and merge cells if needed
	 * @param quiet boolean parameter. indicate wheather to execute operation for real or not
	 * @return true or false indicate if this operation is possible or not	 
	 */
	public boolean moveRight(boolean quiet){		
		boolean move=false;		
		//save board before operation for undo purposes
		if (!quiet)		{			
			undoBoards.add(copyBoard(mBoard));
			undoScores.add(score);
		}
		for(int i=0;i<mBoard.length;i++){		
			ArrayList<Integer> merged = new ArrayList<Integer>(); 
			for(int j=mBoard[0].length-1;j>0;j--){
				int pos = j-1;
				if(mBoard[i][pos] != Constants.free){
					for(int k=j;k<mBoard[0].length;k++){ 
						if(mBoard[i][k] == Constants.free){
							move=true;
							if (!quiet)
							{
								mBoard[i][k] = mBoard[i][pos];
								mBoard[i][pos] = Constants.free;
								pos++;
							}
						}else{						
							if(mBoard[i][pos] == mBoard[i][k] && !merged.contains(k)){
								move=true;
								if(!quiet)
								{									
									score+=mBoard[i][pos];
									mBoard[i][k] = mBoard[i][pos] * 2;
									mBoard[i][pos] = Constants.free;
									merged.add(k);																		
								}
							}
							break;
						}
					}
				}
			}
		}		
		if (!quiet) {
			setChanged();
			notifyObservers();									
			checkAndNotify();
		}
		return move;
	}

	

	/**
	 * Move all possible cells to the Left and merge cells if needed
	 * @param quiet boolean parameter. indicate wheather to execute operation for real or not
	 * @return true or false indicate if this operation is possible or not	 
	 */
	public boolean moveLeft(boolean quiet){		
		//save board before operation for undo purposes
		if (!quiet)		{			
			undoBoards.add(copyBoard(mBoard));
			undoScores.add(score);
		}
		boolean move=false;	
		for(int i=0;i<mBoard.length;i++){
			ArrayList<Integer> merged = new ArrayList<Integer>();
			for(int j=0;j<mBoard[0].length-1;j++){
				int pos = j+1;
				if(mBoard[i][pos] != Constants.free){				
					for(int k=j;k>=0;k--){ 
						if(mBoard[i][k] == Constants.free){
							move=true;
							if (!quiet)
							{
								mBoard[i][k] = mBoard[i][pos];
								mBoard[i][pos] = Constants.free;
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
									mBoard[i][pos] = Constants.free;
									merged.add(k);																		
								}
							}
							break;
						}
					}
				}
			}
		}		
		if (!quiet){
			setChanged();
			notifyObservers();		
			checkAndNotify();
		}
		return move;
	}
	
	/**
	 *  Move all possible cells to the Up and merge cells if needed
	 * @param quiet boolean parameter. indicate wheather to execute operation for real or not
	 * @return true or false indicate if this operation is possible or not	 
	 */
	public boolean moveUp(boolean quiet){		
		//save board before operation for undo purposes	
		if (!quiet)		{			
			undoBoards.add(copyBoard(mBoard));
			undoScores.add(score);
		}
		boolean move=false;	
		for(int i=0;i<mBoard[0].length;i++){
			ArrayList<Integer> merged = new ArrayList<Integer>(); 
				for(int j=0;j<mBoard.length-1;j++){
					int pos = j+1;
					if(mBoard[pos][i] != Constants.free){				
						for(int k=j;k>=0;k--){
							if(mBoard[k][i] == Constants.free){
								move=true;
								if(!quiet)
								{
									mBoard[k][i] = mBoard[pos][i];
									mBoard[pos][i] = Constants.free;
									pos--;
								}
							}else{
								if(mBoard[pos][i] == mBoard[k][i] && !merged.contains(k)){
									move=true;
									if (!quiet)
									{										
										score+=mBoard[pos][i];
										mBoard[k][i] = mBoard[pos][i] * 2;
										mBoard[pos][i] = Constants.free;
										merged.add(k);																			
									}
									
								}
								break;
							}
						}
					}
				}
		}
		if (!quiet){
			setChanged();		
			notifyObservers();			
			checkAndNotify();
		}
		return move;
	}


	/**
	 *  Move all possible cells to the Down and merge cells if needed
	 * @param quiet boolean parameter. indicate wheather to execute operation for real or not
	 * @return true or false indicate if this operation is possible or not	 
	*/
	public boolean moveDown(boolean quiet){		
		//save board before operation for undo purposes
		if (!quiet)		{			
			undoBoards.add(copyBoard(mBoard));
			undoScores.add(score);
		}
		boolean move=false;
		for(int i=0;i<mBoard[0].length;i++){
			ArrayList<Integer> merged = new ArrayList<Integer>();
			for(int j=mBoard.length-1;j>0;j--){
				int pos = j-1;
				if(mBoard[pos][i] != Constants.free){				
					for(int k=j;k<mBoard.length;k++){
						if(mBoard[k][i] == Constants.free){
							move=true;
							if (!quiet)
							{
								mBoard[k][i] = mBoard[pos][i];
								mBoard[pos][i] = Constants.free;	
								pos++;
							}
						}else{
							if(mBoard[pos][i] == mBoard[k][i] && !merged.contains(k)){
								move=true;
								if (!quiet)
								{									
									score+=mBoard[pos][i];
									mBoard[k][i] = mBoard[pos][i] * 2;
									mBoard[pos][i] = Constants.free;
									merged.add(k);																	
								}
							}
							break;
						}
					}
				}
			}
		}
		if (!quiet){
			setChanged();		
			notifyObservers();			
			checkAndNotify();
		}
		return move;
	}

	
	/**
	 *  Initialize Board array with 0 values
	 *  also initialize unfoBoards list
	 */
	private void createEmptyBoard() {
		for(int i=0;i<mBoard.length;++i){
			for(int j=0;j<mBoard[0].length;++j){				
				mBoard[i][j]=Constants.free;			
			}
		}		
		undoBoards = new ArrayList<int[][]>();
		undoScores = new ArrayList<Integer>();
	}
	
	
	/**
	 * Gets all free cells (0 value)
	 * @return freeStates list of Points that are free cells
	 */
	public ArrayList<Point> getFreeStates() {
		ArrayList<Point> freeStates = new ArrayList<Point>();
		for(int i=0;i<mBoard.length;++i){
			for(int j=0;j<mBoard[0].length;++j){				
				if (mBoard[i][j] == Constants.free)
					freeStates.add(new Point(i,j));
			}
		}
		return freeStates;
	}
	
	
	/**
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
	
	/**
	 * Initialize Board: all board with 0 besides 2 cells with random score(2/4)
	 */
	public void initializeBoard() {			
		initializeScore();
		createEmptyBoard();
		setWinNumber(originalWin);
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
		win=false;
		setChanged();
		notifyObservers();
	}

	/**
	 * Copy Board array
	 * @return copy the copied array
	 */
	public static int[][] copyBoard(int[][] source) {
	    int[][] copy = new int[source.length][];
	    for (int i = 0; i < source.length; i++) {
	        copy[i] = Arrays.copyOf(source[i],source[i].length);
	    }
	    return copy;
	}
	
	
	/**
	 * Returns user Score (The maximum value on the board)
	 * @return current score
	 */
	public int getScore() {
		return this.score;
	}
	
	
	/**
	 * generate the next score which will be 2 or 4 (90-10)
	 * @return random score
	 */
	private int generateScore() {
		return Math.random() < 0.9 ? 2 : 4;		
	}

	
	/**
	 * Verify if we have any possible move on the game 
	 * check each operation(right,left,down,up) in quiet mode and see if we have possible movements there
	 */
	private boolean isMovesAvailable() {
		return (moveRight(true) || moveLeft(true) || moveUp(true) || moveDown(true));					 		
	}

	/**
	 * Check if the game is over. 
	 * if we got no possible moves and if we got no free cells
	 * @return true of false indicate if game is over or not
	 */
	public boolean isGameOver() {		
		return ((!isMovesAvailable()) && (getFreeStates().size() == 0) );	
	}
	
	
	/**
	 * Check if we won the game. if we have a cell with 2048 value
	 * @return true or false indicate if we won the game or not
	 */
	public boolean isGameWon() 
	{
		for(int i=0; i<mBoard.length;++i)
		{
			for(int j=0;j<mBoard.length;++j)
			{
				if (mBoard[i][j] == this.winNumber)
				{	
					win=true;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Initialize Score
	 * @param score
	 */
	private void initializeScore() {
		this.score=0;
	}
		
		
	/**
	 * Save game to chosen file
	 */
	@Override
	public void saveGame() {
		
		Shell fileShell = new Shell();
        FileDialog fileDialog = new FileDialog(fileShell,SWT.SAVE);        
        fileDialog.setText("Select Target File");
        fileDialog.setFilterExtensions(new String[] { "*.xml" });
        fileDialog.setFilterNames(new String[] { "GameFile(*.xml)" });
        fileDialog.setFileName("currentGame.xml");
        String outputFile = fileDialog.open();
        if (outputFile != null)
        {
	    	ObjectOutputStream out=null;
	    	try {
	    		out = new ObjectOutputStream(new FileOutputStream(outputFile)); 
	    	}catch (IOException e) {System.out.println("I/O Error ou target file " + e.getMessage());;}
	    	
			try {
				out.writeObject(mBoard);
				out.writeObject(score);
			}catch (IOException e) { System.out.println("Cannot write objects" + e.getMessage());;}
			
	    	try {
				out.close();
			} catch (IOException e) {e.printStackTrace();}
        }else        
        	fileShell.dispose();        
	}
	
	/**
	 * Load game from chosen file
	 */
	public void loadGame() {
		Shell fileShell = new Shell();
        FileDialog fileDialog = new FileDialog(fileShell,SWT.OPEN);	  
        fileDialog.setText("Select Source File");
        fileDialog.setFilterExtensions(new String[] { "*.xml" });
        fileDialog.setFilterNames(new String[] { "GameFile(*.xml)" });        
        String sourceFile = fileDialog.open();
        if (sourceFile != null)
        {		  	                	      
	        MessageBox messageDialog = new MessageBox(fileShell,SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
	        messageDialog.setText("Save & Load");
	        messageDialog.setMessage("Are You Sure? This will erase your current state");
	        int returnCode = messageDialog.open();
	        if (returnCode == SWT.OK) {        	
	        	ObjectInputStream in=null;
	        	try {
	        		in = new ObjectInputStream(new FileInputStream(sourceFile));        		
				} catch (IOException e) {
					System.out.println("I/O error while loading the source file. Error " + e.getMessage());
				}
	        	
				try {					
					mBoard = (int[][]) in.readObject();
					score = (Integer) in.readObject();
				} catch (IOException e) {
					System.out.println("I/O error while loading Objects " + e.getMessage());
				} catch (ClassNotFoundException e) {
					System.out.println("cannot save Object " + e.getMessage());
				}
				
				try {
					in.close();
				} catch (IOException e) {			
					System.out.println("Cannot close source file " + e.getMessage());
				}
	        }
	        setChanged();
	        notifyObservers();
        }
        else
        	fileShell.dispose();      	  
	}

	@Override
	public void moveDiagonalRightUp() {
	}

	@Override
	public void moveDiagonalRightDown() {
	}

	@Override
	public void moveDiagonalLeftUp() {
	}

	@Override
	public void moveDiagonalLeftDown() {
	}
	
	/**
	 * toString function
	 * @return the string defined the module (board + score)
	 */
	public String toString() {		
		String str = Arrays.deepToString(mBoard);			
		return "Array is " +  str + " and score is " + score;
	}
	
	/**
	 * Mapping function between string direction to actual function
	 * @param direction string that indicate the direction we want to move
	 * @param virtual indicate wheather to execute for real or virtually
	 * @return true or false which retunred from the move operation
	 */	
	public boolean move(String direction, boolean virtual) {
		if (direction.equals("Down"))
				return moveDown(virtual);
		else if (direction.equals("Up"))			
			return moveUp(virtual);				
		else if (direction.equals("Right"))
			return moveRight(virtual);
		else if (direction.equals("Left"))			
			return moveLeft(virtual);		
		return false;
	}

	/*
	* get Hint From RMI server
	* @return string that indicate the best next direction to move
	* @throws RemoteException, CloneNoeSupportedException
	*/
	public void getHintFromServer() throws RemoteException, CloneNotSupportedException {
		Registry registry=null;			
		registry = LocateRegistry.getRegistry(server, Constants.RMI_PORT);					
		RemoteInt lookup=null;
		try {
			lookup = (RemoteInt) registry.lookup("Server2048");			
			for(int i=0;i<hintsNumber;++i) {										
				String result = lookup.getHint(new customModel(this.getBoard(), this.getScore(),this.getDepthNumber()));			
				if (result != null)
					move(result,false);				
			}
		} catch (Exception e) {
			System.out.println("client could not connect to RMI Server , Error :" + e.getCause());
		}				
	}
		
	/**
	 * solve the game by the RMI Server
	 * @throws RemoteException, CloneNotSupportedException
	 */
	public void getSolutionFromServer() throws RemoteException, CloneNotSupportedException {
		stopSolverPressed=false;
		Registry registry = LocateRegistry.getRegistry(server, Constants.RMI_PORT);
		RemoteInt lookup=null;
		try { 
			lookup = (RemoteInt) registry.lookup("Server2048");
			//Keep getting hint from Solver until end or win the game
			while (!stopSolverPressed) {			
				String result = lookup.getHint(new customModel(this.getBoard(), this.getScore(),this.getDepthNumber()));
				if (result != null)
					move(result,false);								
				else		
				{					
					stopSolverPressed=true;
				}
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			checkAndNotify();
		} catch (Exception e) {
			System.out.println("Client could not connect to RMI Server , Error " + e.getCause());
		}				
	
	}
	/**
	* own implementation of HashCode method
	*/
	public int hashCode() {
		return getBoard().hashCode() + ((Integer) getScore()).hashCode();
	}
	/**
	* own implementation of equals method
	*/	
	public boolean equals(Object o) {			
		if(null != o) {
		      if(o instanceof Model) {
		        return ((Model)o).getBoard().equals(getBoard()) 
		          && ((Integer)((Model)o).getScore()).equals((Integer)((Model)o).getScore());
		      }
		    }
		    return false;
		  }
	/**
	* Setting the server to server
	* @param this.server
	*/
	public void setServer(String server) {
		this.server=server;
	}
	/**
	* Setting the Stop Solver to true or false
	*/
	@Override
	public void setStopSolverPressed(boolean b) {
		this.stopSolverPressed=b;
		
	}	
	
	public void setDepthNumber(int num) {
		this.depthNumber=num;
	}
	
	public int getDepthNumber()
	{
		return this.depthNumber;
	}
	
	public void setHintsNumber(int num) {
		this.hintsNumber=num;
	}
	
	public int getHintsumber()
	{
		return this.hintsNumber;
	}

}