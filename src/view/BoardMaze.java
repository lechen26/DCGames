package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


public class BoardMaze extends Canvas {
	
	int[][] boardData;
	final int N=4;
	StateMaze states[][];
	
	public BoardMaze(Composite parent, int style) {
		super(parent,style);				
		boardData = new int[][] {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};		
		setLayout(new GridLayout(N,true));
		states = new StateMaze[N][N];
		for(int i=0;i< boardData.length; ++i)
		{
				for(int j=0; j < boardData[0].length; ++j)
				{
					states[i][j] = new StateMaze(BoardMaze.this,SWT.BORDER);
					states[i][j].setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
					states[i][j].setValue(boardData[i][j]);
				}	
				
		}			
		
		this.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				for(int i=0;i< boardData.length; ++i)
				{
					for(int j=0; j < boardData[0].length; ++j)
					{							
						states[i][j].dispose();
					}
				}	}
			});
	}
	
	/*
	 * update array Board with the given array data
	 */
	public void setBoard(int[][] board) {		
		for(int i=0;i< boardData.length; ++i)
		{
			for(int j=0; j < boardData[0].length; ++j)
			{
				boardData[i][j] = board[i][j];				
				states[i][j].setValue(boardData[i][j]);
			}
		}		
	}
	
	/*
	 * 
	 */
	public void gameWin() {
		this.addPaintListener(new PaintListener() {
	    	 public void paintControl(PaintEvent e) { 	
	    		 Canvas canvas = (Canvas) e.widget;
	    		 int canvasX=canvas.getSize().x;
	    		 int canvasY=canvas.getSize().y;
	       		 Color c1 = new Color(e.display, 250, 250, 200);
	    		 e.gc.setBackground(c1);	    		 
	    		 e.gc.fillRectangle(0, 0, canvasX/4, canvasY/4);	    		 	    		 	             
	    	 }
	     });	  
	}
	
	/*
	 * 
	 */
	public void gameOver() {
		System.out.println("Board: gameOver");		
	
		for(int i=0;i< boardData.length; ++i)
		{
			for(int j=0; j < boardData[0].length; ++j)
			{
				states[i][j].dispose();
			}	
		}	
		//this.setLayout(new GridLayout(1,true));
		addPaintListener(new PaintListener() {
	    	 public void paintControl(PaintEvent e) {
	    		 System.out.println("painting new Lose board");	    		 
	    		 Canvas canvas = (Canvas) e.widget;
	    		 int canvasX=canvas.getSize().x;
	    		 int canvasY=canvas.getSize().y;	    		 
	    		 Color c1 = new Color(e.display, 50, 50, 200);
	    		 e.gc.setForeground(c1);	    		 
	    		 e.gc.drawRectangle(0, 0, canvasX, canvasY);	    		 	
	    	 }
	     });		
		redraw();
	}
	
}