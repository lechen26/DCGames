package model;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;


public class ModelMaze extends Observable implements Model {

	int[][] mBoard;	
	ArrayList<int[][]> undoBoards = new ArrayList<int[][]>();
	
	int rows,cols;
	int score=0;
	Point exitPosition;	

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
	public boolean moveUp(boolean diagonal) {		
		boolean move=false;
		if (!diagonal)			
			undoBoards.add(copyBoard(mBoard));
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosX - 1;
		if((newPos >= 0) && (mBoard[newPos][cPosY] != -1)){
			move=true;
			mBoard[newPos][cPosY] = mBoard[cPosX][cPosY];
			mBoard[cPosX][cPosY] = 0 ;
			score += 10;
		}
		setChanged();
		notifyObservers();
		if (isGameWon(newPos,cPosY)){
			if (diagonal){
				score-=5;
				setChanged();
				notifyObservers();
			}
			setChanged();
			notifyObservers("gameWon");
		}
		return move;
	}	
	
	/*
	* move down method
	*/
	@Override
	public boolean moveDown(boolean diagonal) {
		boolean move=false;
		if (!diagonal)
			undoBoards.add(copyBoard(mBoard));	
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosX + 1;
		if((newPos < mBoard.length) && (mBoard[newPos][cPosY] != -1)){
			move=true;
			mBoard[newPos][cPosY] = mBoard[cPosX][cPosY] ;
			mBoard[cPosX][cPosY] = 0 ;
			score += 10;
		}
		setChanged();
		notifyObservers();
		if (isGameWon(newPos,cPosY)){
			if (diagonal){
				score-=5;
				setChanged();
				notifyObservers();
			}
			setChanged();
			notifyObservers("gameWon");
		}
		return move;
		
	}
	/*
	* move left method
	*/
	@Override
	public boolean moveLeft(boolean diagonal) {		
		boolean move=false;
		if (!diagonal)
			undoBoards.add(copyBoard(mBoard));		
		else
			System.out.println("left diagonal");
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosY - 1;
		if((newPos >= 0 ) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			mBoard[cPosX][newPos] = mBoard[cPosX][cPosY] ;
			mBoard[cPosX][cPosY] = 0 ;
			score += 10;
		}
		setChanged();
		notifyObservers();
		if (isGameWon(cPosX,newPos)){
			if (diagonal){
				score-=5;
				setChanged();
				notifyObservers();
			}
			setChanged();
			notifyObservers("gameWon");
		}
		return move;
	}
	/*
	* move right method
	*/
	@Override
	public boolean moveRight(boolean diagonal) {
		boolean move=false;
		if (!diagonal)
			undoBoards.add(copyBoard(mBoard));	
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosY + 1;
		if((newPos < mBoard[0].length) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			mBoard[cPosX][newPos] = mBoard[cPosX][cPosY] ;
			mBoard[cPosX][cPosY] = 0 ;
			score += 10;
		}
		setChanged();
		notifyObservers();
		if (isGameWon(cPosX,newPos)){
			if (diagonal){
				score-=5;
				setChanged();
				notifyObservers();
			}
			setChanged();
			notifyObservers("gameWon");
		}
		return move;
	}


	// initialize the Maze Board
	@Override
	public void initializeBoard() {
		System.out.println("initialzeBaord");
		undoBoards = new ArrayList<int[][]>();
		int[][] b = { { 23 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,0 , 0 , 0 , 0 , 0 , 0 }, //1
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
		
		setExitPosition(new Point(0,0));
		mBoard = b;
		setScore(0);
		setChanged();
		notifyObservers();
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

	@Override
	public void undoBoard() {
		if (undoBoards.isEmpty())
			System.out.println("No Undo moves to perform");
		else{ 
			mBoard=copyBoard(undoBoards.get(undoBoards.size()-1));
			undoBoards.remove(undoBoards.size()-1);
			setChanged();
			notifyObservers();
		
		}
	}

	public boolean isGameWon(int currX, int currY){
		if ((currX == getExitPosition().x) && (currY == getExitPosition().y)) 
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
}