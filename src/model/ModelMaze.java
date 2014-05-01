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

	public Point getExitPoint(){
		for (int i=0;i<mBoard[0].length;i++)
			for (int j=0;j<mBoard.length;j++)
				if (mBoard[i][j] == 23)
					return new Point(i,j);
		return (Point) null ; // didnt find the exit point
	}

	/*
	* move up method
	*/
	@Override
	public boolean moveUp(boolean quiet) {
		boolean move=false;		
		undoBoards.add(copyBoard(mBoard));		
		int ePosX = getExitPoint().x;
		int ePosY = getExitPoint().y;
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosX - 1;
		if((newPos >= 0) && (mBoard[newPos][cPosY] != -1)){
			move=true;
			if (!quiet)	
			{
				mBoard[newPos][cPosY] = mBoard[cPosX][cPosY];
				mBoard[cPosX][cPosY] = 0 ;
			}
			setChanged();
			notifyObservers();
			if ((newPos == ePosX) && (cPosY == ePosY)){
				setChanged();
				notifyObservers("gameWon");
			}
		}
		return move;
	}	
	/*
	* move down method
	*/
	@Override
	public boolean moveDown(boolean quiet) {
		boolean move=false;
		if (!quiet) {
			undoBoards.add(copyBoard(mBoard));	
		}
		int ePosX = getExitPoint().x;
		int ePosY = getExitPoint().y;
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosX + 1;
		if((newPos < mBoard.length) && (mBoard[newPos][cPosY] != -1)){
			move=true;
			if (!quiet){
				mBoard[newPos][cPosY] = mBoard[cPosX][cPosY] ;
				mBoard[cPosX][cPosY] = 0 ;
			}
			setChanged();
			notifyObservers();
			if ((newPos == ePosX) && (cPosY == ePosY)){
				setChanged();
				notifyObservers("gameWon");
			}
		}
		return move;
	}
	/*
	* move left method
	*/
	@Override
	public boolean moveLeft(boolean quiet) {
		boolean move=false;
		if (!quiet) {
			undoBoards.add(copyBoard(mBoard));	
		}
		int ePosX = getExitPoint().x;
		int ePosY = getExitPoint().y;
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosY - 1;
		if((newPos >= 0 ) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			if (!quiet){
				mBoard[cPosX][newPos] = mBoard[cPosX][cPosY] ;
				mBoard[cPosX][cPosY] = 0 ;
			}
			setChanged();
			notifyObservers();
			if ((newPos == ePosY) && (cPosX == ePosX)){
				setChanged();
				notifyObservers("gameWon");
			}
		}
		return move;
	}
	/*
	* move right method
	*/
	@Override
	public boolean moveRight(boolean quiet) {
		boolean move=false;
		if (!quiet) {
			undoBoards.add(copyBoard(mBoard));	
		}
		int ePosX = getExitPoint().x;
		int ePosY = getExitPoint().y;
		int cPosX = getCurrentPosition().x;
		int cPosY = getCurrentPosition().y;
		int newPos = cPosY + 1;
		if((newPos < mBoard[0].length) &&(mBoard[cPosX][newPos] != -1)){
			move=true;
			if (!quiet){
				mBoard[cPosX][newPos] = mBoard[cPosX][cPosY] ;
				mBoard[cPosX][cPosY] = 0 ;
			}
			setChanged();
			notifyObservers();
			if ((newPos == ePosY) && (cPosX == ePosX)){
				setChanged();
				notifyObservers("gameWon");
			}
		}
		return move;
	}


	// initialize the Maze Board
	@Override
	public void initializeBoard() {
		createEmptyBoard(rows, cols);	
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
		mBoard = b;
		score=0;
		setChanged();
		notifyObservers();
		undoBoards = new ArrayList<int[][]>();
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

	public boolean isGameWon(int x, int y){
		int cPosX = x;
		int cPosY = y;
		if (mBoard[cPosX][cPosY] == 23 )
			return true;
		else
			return false;
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