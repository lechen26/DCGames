package model;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
import common.Constants;
import common.RemoteInt;
import common.customModel;


public class ModelMaze extends Observable implements Model, Serializable {
	private static final long serialVersionUID = 1L;
	Map<Integer,int[][]> undoBoards = new LinkedHashMap<Integer,int[][]>();
	
	int[][] mBoard;		       
	int rows,cols;	
	Point exitPosition;
	Point startPosition;
	RemoteInt lookup;
	int numOfMoves=0;
	int minMoves=0;
	int score=0;
	boolean stopSolverPressed=false;
	String server;
	
	public ModelMaze(int rows,int cols) {		
		if ((rows != 16) | (cols != 16))
		{
			System.out.println("Sorry. Currenly we do not support different size then 16.");
			System.exit(0);
		}
		mBoard=new int[rows][cols];
	}


	/**
	 * Get Board data
	 * @return board 
	 */
	@Override
	public int[][] getBoard() {
		return mBoard;
	}


	/**
	 * Set number of moves
	 * @param numOfMoves number of moves
	 */
	private void setNumOfMoves(int numOfMoves) {
		this.numOfMoves = numOfMoves;
	}

	/**
	 * Set start position
	 * @param point start position to set
	 */
	public void setStartPosition(Point point) {
		this.startPosition=point;		
	}

	
	/**
	 * Set Exit position
	 * @param exitPosition exit position to set
	 */
	public void setExitPosition(Point exitPosition) {
		this.exitPosition = exitPosition;
	}

	/**
	 * Set current score
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	
	/**
	 * get exit Position
	 * @return exit position
	 */
	public Point getExitPosition() {
		return exitPosition;
	}
	
	/**
	 * get start position
	 * @return start position
	 */
	public Point getStartPosition() {
		return startPosition;
	}

	/**
	 * Get current Position on the Maze
	 * @return get current position
	 */
	public Point getCurrentPosition(){

		for (int i=0; i<mBoard.length; i++)
			for (int j=0;j<mBoard[0].length ; j++)
				if (mBoard[i][j] == Constants.mickey )
					return new Point(i,j);
		return null; // didn't find the mouse
	}
	
	/**
	 * Check if we won or lost after we got to the EndPoint
	 */
	private void checkAndNotify(int x,int y,boolean diagonal){
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
				notifyObservers("gameOver");
			}
		}
	}
	
	/**
	* move up method
	* @param diagonal indicate wheather its diagonal move or not
	* @return true or false indicate if operation was success or not
	*/
	@Override
	public boolean moveUp(boolean diagonal) {		
		boolean move=false;
		if (!diagonal)
			undoBoards.put(score,copyBoard(mBoard));
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosX - 1;
		if((newPos >= 0) && (mBoard[newPos][cPosY] != Constants.wall)){
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
	
	/**
	* move down method
	* @param diagonal indicate wheather its diagonal move or not
	* @return true or false indicate if operation was success or not
	*/
	@Override
	public boolean moveDown(boolean diagonal) {		
		boolean move=false;
		if (!diagonal)
			undoBoards.put(score,copyBoard(mBoard));
			
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosX + 1;
		if((newPos < mBoard.length) && (mBoard[newPos][cPosY] != Constants.wall)){
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
	
	/**
	* move left method
	* @param diagonal indicate wheather its diagonal move or not
	* @return true or false indicate if operation was success or not
	*/
	@Override
	public boolean moveLeft(boolean diagonal) {		
		boolean move=false;
		if (!diagonal)
			undoBoards.put(score,copyBoard(mBoard));
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosY - 1;
		if((newPos >= 0 ) &&(mBoard[cPosX][newPos] != Constants.wall)){
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
	
	
	/**
	* move right method
	* @param diagonal indicate wheather its diagonal move or not
	* @return true or false indicate if operation was success or not
	*/
	@Override
	public boolean moveRight(boolean diagonal) {		
		boolean move=false;
		if (!diagonal)
			undoBoards.put(score,copyBoard(mBoard));
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosY + 1;
		if((newPos < mBoard[0].length) &&(mBoard[cPosX][newPos] != Constants.wall)){
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


	/**
	 * Move diagonal Right and Up
	 */
	@Override
	public void moveDiagonalRightUp() {		
		if ((moveRight(false) && moveUp(true)) || (moveUp(false) && moveRight(true)) )
		{					
			if (score!=0)
				score-=5;
			setChanged();
			notifyObservers();
		}
	
	}

	/**
	 * Move diagonal Right and Down
	 */
	@Override
	public void moveDiagonalRightDown() {		
		if ((moveRight(false) && moveDown(true)) || (moveDown(false) && moveRight(true)) )
		{	
			if (score!=0)
				score-=5;
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	* Move diagonal Left and Up
	*/
	@Override
	public void moveDiagonalLeftUp() {		
		
		if  ( (moveLeft(false) && moveUp(true )) || (moveUp(false) && moveLeft(true)) ) 
		{			
			if (score!=0)
				score-=5;
			setChanged();
			notifyObservers();
		}			
	}

	/**
	* Move diagonal Left and Down
	*/
	@Override
	public void moveDiagonalLeftDown() {
		if ( (moveDown(false) && moveLeft(true)) ||  (moveLeft(false) && moveDown(true)) )
		{		
			if (score!=0)
				score-=5;
			setChanged();
			notifyObservers();
		}
	}

	
	/**
	 *  initialize the Maze Board.
	 *  set start and exit positions and calculate the wanted moves according to astar 
	 */
	@Override
	public void initializeBoard() {		
		int[][] b = { { -1 , -1 , -1 , -1 , -1 , -1 , -1 , -1 , -3 , -1 ,-1 , -1 , -1 , -1 , -1 , -1 }, //1
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
		{ -1 , -1 , -1 , -1 , -1 , -1 , -1 , -1 , -1 , -1 ,-1 , -1 , -1 , -1 , -1 , -2 }, //16
		};
		undoBoards = new LinkedHashMap<Integer,int[][]>();
		setStartPosition(new Point(15,15));
		setExitPosition(new Point(0,8));
		mBoard = copyBoard(b);
		setScore(0);
		setNumOfMoves(0);
		runAstar();
		setChanged();
		notifyObservers();		
	}


	/**
	 * Display Board
	 */
	public void displayBoard(int[][] mBoard) {	
		for(int i=0;i<mBoard.length;++i){
			for(int j=0;j<mBoard[0].length;++j)	
				System.out.print(mBoard[i][j] + " ");	
				System.out.println("\n");
		}
	}

	/**
	 * Copy Board and return the copy
	 * @param source array to copy 
	 * @return copied array
	 */
	public static int[][] copyBoard(int[][] source) {
	    int[][] copy = new int[source.length][];
	    for (int i = 0; i < source.length; i++) {
	        copy[i] = Arrays.copyOf(source[i],source[i].length);
	    }
	    return copy;
	}

	/**
	 * Undo the last operation and revert back to the board before it
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
			numOfMoves--;
			List<Integer> list = new ArrayList<Integer>(undoBoards.keySet());
			score=list.get(list.size()-1);
			mBoard=copyBoard(undoBoards.remove(score));
 			setChanged();
 			notifyObservers();
			setChanged();
			notifyObservers();
	}
		
	}
	
	/**
	 * check if we got to the end point
	 * @return true or false 
	 */
	private boolean isGotToEndPoint(int currX,int currY)
	{
		if ((currX == getExitPosition().x) && (currY == getExitPosition().y))
			return true;
		else
			return false;
	}
		
	
	/**
	 * check if we Won the game
	 * @return true or false
	 */
	public boolean isGameWon(){		
		if (numOfMoves ==  minMoves)
			return true;
		else
			return false;
	}

	/**
	 * get Score
	 * @return score
	 */
	@Override
	public int getScore() {
		return this.score;
	}
		
	/**
	 * Save the game to chosen file
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
	 * Load the game from chosen file
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
	
	
	
	/**
	 * BFS calculation (Manhatten move) for low value road
	 */
	public void runAstar() {	
		//Change Cheeze value to be as implementd on Astar
		int[][] boardForMaze=copyBoard(mBoard);
		for (int i=0;i<boardForMaze.length;++i){
			for(int j=0;j<boardForMaze[0].length;++j) {
				if ( boardForMaze[i][j] == Constants.mini )
					boardForMaze[i][j]=2;
			}
		}
		Maze maze = new Maze(boardForMaze,startPosition,exitPosition);
		Searcher as = new Astar(new MazeDomain(maze),new MazeHeuristicDistance(), new MazeStandardDistance());		
		ArrayList<Action> actions  = as.search(maze.getStart(),maze.getGoal());
		if (actions != null)						
			minMoves=actions.size();			
	}


	@Override
	public void doubleWinNumber() {
	}


	/**
	 * get Hint from RMI Server
	 * @throws RemoteException, CloneNotSupportedException
	 */
	@Override
	public void getHintFromServer() throws RemoteException, CloneNotSupportedException {
		System.out.println("eneterd function");
		Registry registry = LocateRegistry.getRegistry(server, Constants.RMI_PORT);					
		try {
			lookup = (RemoteInt) registry.lookup("ServerMaze");
			String res = lookup.getHint(new customModel(this.getBoard(), this.getScore(), this.getCurrentPosition(), this.getExitPosition()));
			System.out.println("Message from server: " + res);
			executeAction("" + res);
		} catch (NotBoundException e) {
			System.out.println("Unable to lookup Server on registry , Error :" + e.getCause());
		}
						
	}

	
	/**
	 * solve the game using RMI Server, InterruptedException
	 * @throws RemoteException, CloneNotSupportedException
	 */
	public void getSolutionFromServer() throws RemoteException, CloneNotSupportedException, InterruptedException {
		stopSolverPressed=false;
		final Registry registry = LocateRegistry.getRegistry("localhost", Constants.RMI_PORT);		
		try {
			lookup = (RemoteInt) registry.lookup("ServerMaze");
		} catch (Exception e) {
			System.out.println("Maze Client couldnt find Server on registry , Error " + e);
		}			
		final ArrayList<Action> actions = lookup.solveGame(new customModel(this.getBoard(), this.getScore(), this.getCurrentPosition(), this.getExitPosition()));			
			if (!actions.isEmpty()){
				for (Action ac: actions) {
					//System.out.println("Action is=" + ac.getName());
					if (stopSolverPressed)
						break;
					executeAction(ac);					
					Thread.sleep(50);
				}				
			}
	}
		
	/**
	 * Trigger move operation from the given action string
	 * @param action String that identify the move	 
	 */
	private void executeAction(String action) {
		String parsedName = action.replaceAll("\\(", "").replaceAll("\\)", "");
		String position[] = parsedName.toString().split(",");
		int row = Integer.parseInt(position[0]);
		int col = Integer.parseInt(position[1]);			
		//System.out.println("X=" + row + "Y=" + col);
		if ( (row < getCurrentPosition().x) && (col == getCurrentPosition().y)){
			//System.out.println("Should move to up");
			moveUp(false);
		}
		else if ( (row > getCurrentPosition().x) && (col == getCurrentPosition().y)) {
			//System.out.println("Should move to down");
			moveDown(false);
		}				
		else if ( (row ==  getCurrentPosition().x) && (col <  getCurrentPosition().y)) {
			//System.out.println("Should move to left");
			moveLeft(false);
		}
		else if ( (row ==  getCurrentPosition().x) && (col > getCurrentPosition().y)) {
			//System.out.println("Should move to right");
			moveRight(false);
		}
		else if ( (row > getCurrentPosition().x) && (col > getCurrentPosition().y)) {
			//System.out.println("Should move Diagonal right up");
			moveDiagonalRightDown();
		}
		else if ( (row > getCurrentPosition().x) && (col < getCurrentPosition().y)) {
			//System.out.println("Should move Diagonal left up");
			moveDiagonalLeftDown();
		}
		else if ( (row < getCurrentPosition().x) && (col > getCurrentPosition().y)) {
			//System.out.println("Should move Diagonal right down");
			moveDiagonalRightUp();
		}
		else if ( (row < getCurrentPosition().x) && (col < getCurrentPosition().y)) {
			//System.out.println("Should move Diagonal right down");
			moveDiagonalLeftUp();
		}					

	}
	/**
	 * parse action and execute the relevant method
	 * @param ac Action thats needs to be executed 
	 */
	private void executeAction(Action ac) {			
		String parsedName = ac.getName().replaceAll("\\(", "").replaceAll("\\)", "");
		executeAction(parsedName);
	}

	/**
	* toString function
	* @return string that defines the model
	*/
	public String toString() {		
		String str = Arrays.deepToString(mBoard);			
		return "Array is " +  str + " and score is " + score;
	}

	/**
	* setting the server 
	* @param server
	*/
	@Override
	public void setServer(String server) {
		this.server=server;
	}
	/**
	* setting the stop solver
	*/
	public void setStopSolverPressed(boolean b){
		this.stopSolverPressed=b;
	}
}