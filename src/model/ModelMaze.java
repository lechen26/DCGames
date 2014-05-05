package model;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import maze.Maze;
import maze.MazeDomain;
import maze.MazeHeuristicDistance;
import maze.MazeStandardDistance;
import model.algorithms.Action;
import model.algorithms.Searcher;
import model.algorithms.a_star.Astar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;


public class ModelMaze extends Observable implements Model {

	int[][] mBoard;	
	Map<Integer,int[][]> undoBoards = new LinkedHashMap<Integer,int[][]>();
	
	int rows,cols;
	int score=0;
	Point exitPosition;	
	Point startPosition;
	int numOfMoves=0;
	int minMoves=0;
	
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

	private void checkAndNotify(int x,int y,boolean diagonal)
	{
		if (isGotToEndPoint(x,y)){			
			if (diagonal){
				score-=5;
				setChanged();						
				notifyObservers();
			} if (isGameWon()) {
				setChanged();
				notifyObservers("gameWon");
			}else {			
				setChanged();
				notifyObservers("gameFinish");
			}
		}
	}
	
	/*
	* move up method
	*/
	@Override
	public boolean moveUp(boolean diagonal) {		
		boolean move=false;
		if (!diagonal)			
			undoBoards.put(score,copyBoard(mBoard));
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosX - 1;
		if((newPos >= 0) && (mBoard[newPos][cPosY] != -1)){
			move=true;
			mBoard[newPos][cPosY] = mBoard[cPosX][cPosY];
			mBoard[cPosX][cPosY] = 0 ;
			score += 10;
		}
		if ((move) && (!diagonal))
			numOfMoves++;
		setChanged();
		notifyObservers();
		checkAndNotify(newPos,cPosY,diagonal);
		return move;
	}	
	
	/*
	* move down method
	*/
	@Override
	public boolean moveDown(boolean diagonal) {		
		boolean move=false;
		if (!diagonal)
			undoBoards.put(score,copyBoard(mBoard));	
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosX + 1;
		if((newPos < mBoard.length) && (mBoard[newPos][cPosY] != -1)){
			move=true;
			mBoard[newPos][cPosY] = mBoard[cPosX][cPosY] ;
			mBoard[cPosX][cPosY] = 0 ;
			score += 10;
		}
		if ((move) && (!diagonal))
			numOfMoves++;
		setChanged();
		notifyObservers();
		checkAndNotify(newPos,cPosY,diagonal);		
		return move;
		
	}
	/*
	* move left method
	*/
	@Override
	public boolean moveLeft(boolean diagonal) {		
		boolean move=false;
		if (!diagonal)
			undoBoards.put(score,copyBoard(mBoard));			
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosY - 1;
		if((newPos >= 0 ) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			mBoard[cPosX][newPos] = mBoard[cPosX][cPosY] ;
			mBoard[cPosX][cPosY] = 0 ;
			score += 10;
		}
		if ((move) && (!diagonal))
			numOfMoves++;
		setChanged();
		notifyObservers();
		checkAndNotify(cPosX,newPos,diagonal);
		return move;
	}
	
	
	/*
	* move right method
	*/
	@Override
	public boolean moveRight(boolean diagonal) {		
		boolean move=false;
		if (!diagonal)
			undoBoards.put(score,copyBoard(mBoard));	
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosY + 1;
		if((newPos < mBoard[0].length) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			mBoard[cPosX][newPos] = mBoard[cPosX][cPosY] ;
			mBoard[cPosX][cPosY] = 0 ;
			score += 10;
		}
		if ((move) && (!diagonal))
			numOfMoves++;
		setChanged();
		notifyObservers();
		checkAndNotify(cPosX,newPos,diagonal);
			return move;
	}


	// initialize the Maze Board
	@Override
	public void initializeBoard() {
		System.out.println("initialzeBaord");
		undoBoards = new LinkedHashMap<Integer,int[][]>();
		int[][] b = { { -1 , -1 , -1 , -1 , -1 , -1 , -1 , -1 , 23 , -1 ,-1 , -1 , -1 , -1 , -1 , -1 }, //1
		{ -1 , 0 , 0, 0 , 0 , 0 , 0 , 0 , 0 , -1 ,0 , 0 , 0 , 0 , 0 , -1 }, //2
		{ -1 , 0 , -1 , 0 , -1 , -1 , -1 , 0 , -1 , -1 ,-1 , -1 , 0 , -1 , 0 , -1 }, //3
		{ -1 , 0 , -1 , 0 , -1 , 0 , 0 , 0 , -1 , 0 ,0 , 0 , 0 , -1 , 0 , -1 }, //4
		{ -1 , 0 , -1 , 0 , -1 , -1 , -1 , -1 , -1 , 0 ,-1 , -1 , -1 , -1 , 0 , -1 }, //5
		{ -1 , 0 , -1 , 0 , 0 , 0 , -1 , 0 , 0 , 0 ,0 , 0 , 0 , 0 , 0 , -1 }, //6
		{ -1 , -1 , -1 , -1 , -1 , 0 , -1 , 0 , -1 , -1 ,-1 , -1 , -1 , -1 , -1 , -1 }, //7
		{ -1 , 0 , 0 , 0 , 0, 0 , -1 , 0 , 0 , 0 ,0 , 0 , 0 , 0 , 0 , -1 }, //8
		{ -1 , 0 , -1 , -1 , -1 , 0 , -1 , 0 , 0 , 0 ,0 , 0 , 0 , 0 , 0 , -1 }, //9
		{ -1 , 0 , 0 , -1 , 0 , 0 , 0 , -1 , -1 , -1 ,-1 , -1 , -1 , 0 , -1 , -1 }, //10
		{ -1 , -1 , 0 , -1 , 0 , 0 , 0 , -1 , 0 , 0 ,0 , 0 , 0 , -1 , 0 , -1 }, //11
		{ -1 , 0 , 0 , -1 , 0 , 0 , 0 , -1 , -1 , -1 ,-1 , 0 , 0 , -1 , 0 , -1 }, //12
		{ -1 , 0 , -1 , -1 , 0 , 0 , 0 , 0 , 0 , 0 ,0 , 0 , -1 , -1 , 0 , 0 }, //13
		{ -1 , 0 , 0 , -1 , -1 , -1 , 0 , -1 , -1 , -1 ,-1 , 0 , 0 , -1 , -1 , -1 }, //14	
		{ -1 , -1 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,0 , -1 , 0 , 0 , 0 , 0}, //15
		{ -1 , -1 , -1 , -1 , -1 , -1 , -1 , -1 , -1 , -1 ,-1 , -1 , -1 , -1 , -1 , 1 }, //16
		};
		//int[][] b = { {23,0,0,0}, {-1,-1,0,0},{0,0,0,0},{0,0,0,1}};
		setStartPosition(new Point(15,15));
		setExitPosition(new Point(0,8));
		mBoard = copyBoard(b);
		setScore(0);
		setNumOfMoves(0);
		runAstar();
		setChanged();
		notifyObservers();
	}

	public int getNumOfMoves() {
		return numOfMoves;
	}


	public void setNumOfMoves(int numOfMoves) {
		this.numOfMoves = numOfMoves;
	}


	private void setStartPosition(Point point) {
		this.startPosition=point;		
	}


	public void setScore(int score) {
		this.score = score;
	}


	public Point getExitPosition() {
		return exitPosition;
	}


	public void setExitPosition(Point exitPosition) {
		this.exitPosition = exitPosition;
	}

	public void displayBoard(int[][] mBoard) {	
		for(int i=0;i<mBoard.length;++i){
			for(int j=0;j<mBoard[0].length;++j)	
				System.out.print(mBoard[i][j] + " ");	
				System.out.println("\n");
		}
	}

	public static int[][] copyBoard(int[][] source) {
	    int[][] copy = new int[source.length][];
	    for (int i = 0; i < source.length; i++) {
	        copy[i] = Arrays.copyOf(source[i],source[i].length);
	    }
	    return copy;
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
			List<Integer> list = new ArrayList<Integer>(undoBoards.keySet());
			score=list.get(list.size()-1);
			mBoard=copyBoard(undoBoards.remove(score));
			setChanged();
			notifyObservers();
		}
		
	}

	public boolean isGotToEndPoint(int currX,int currY)
	{
		if ((currX == getExitPosition().x) && (currY == getExitPosition().y))
			return true;
		else
			return false;
	}
	
	public boolean isGameWon(){
		System.out.println("Moves are=" + numOfMoves + "and minMoves=" + minMoves);
		if (numOfMoves ==  minMoves)
			return true;
		else
			return false;
	}

	@Override
	public void moveDiagonalRightUp() {		
		if ((moveRight(false) && moveUp(true)) || (moveUp(false) && moveRight(true)) )
		{		
			score-=5;
			setChanged();
			notifyObservers();
		}
	
	}

	@Override
	public void moveDiagonalRightDown() {		
		if ((moveRight(false) && moveDown(true)) || (moveDown(false) && moveRight(true)) )
		{		
			score-=5;
			setChanged();
			notifyObservers();
		}
	}

	@Override
	public void moveDiagonalLeftUp() {		
		
		if  ( (moveLeft(false) && moveUp(true )) || (moveUp(false) && moveLeft(true)) ) 
		{			
			score-=5;
			setChanged();
			notifyObservers();
		}			
	}

	@Override
	public void moveDiagonalLeftDown() {
		if ( (moveDown(false) && moveLeft(true)) ||  (moveLeft(false) && moveDown(true)) )
		{		
			score-=5;
			setChanged();
			notifyObservers();
		}
	}
	@Override
	public int getScore() {
		return this.score;
	}
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
	
	public void loadGame() {
		Shell fileShell = new Shell();
        FileDialog fileDialog = new FileDialog(fileShell,SWT.OPEN);	  
        fileDialog.setText("Select Source File");
        fileDialog.setFilterExtensions(new String[] { "*.xml" });
        fileDialog.setFilterNames(new String[] { "GameFile(*.xml)" });        
        String sourceFile = fileDialog.open();
        if (sourceFile != null)
        {
		  
	        System.out.println("Chosen to open " + sourceFile);        	        
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
	
	
	
	/*
	 * BFS calculation (Manhatten move) for low value road
	 */
	public void runAstar() {	
		//Change Cheeze value to be as implementd on Astar
		int[][] boardForMaze=copyBoard(mBoard);
		for (int i=0;i<boardForMaze.length;++i){
			for(int j=0;j<boardForMaze[0].length;++j) {
				if ( boardForMaze[i][j] == 23 )
					boardForMaze[i][j]=2;
			}
		}
		Maze maze = new Maze(boardForMaze,startPosition,exitPosition);
		Searcher as = new Astar(new MazeDomain(maze),new MazeHeuristicDistance(), new MazeStandardDistance());		
		ArrayList<Action> actions  = as.search(maze.getStart(),maze.getGoal());
		if (actions != null)		
			minMoves=actions.size();		
	}
}