package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tracker;


public class Board extends Canvas { 
	
	int[][] boardData;
	int rows,cols;
	State states[][];
	
	/*
	 * Board constructure, gets Canvas properties, size of the boardData array and style of State (bordered or not)
	 */
	public Board(Composite parent, int style, int rows, int cols, int stateStyle) {
		super(parent,style);	
		boardData = new int[rows][cols];
		setLayout(new GridLayout(cols,true));
		states = new State[rows][cols];
		for(int i=0;i< boardData.length; ++i)
		{
				for(int j=0; j < boardData[0].length; ++j)
				{					
					states[i][j] = new State(Board.this,stateStyle);
					states[i][j].setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
					states[i][j].setValue(boardData[i][j]);
				}	
				
		}			
		
		this.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				for(int i=0;i< boardData.length; ++i){
					for(int j=0; j < boardData[0].length; ++j){							
						states[i][j].dispose();
					}
				}
			}
			
		});
//		// mouse event listener
//		this.addMouseListener(new MouseListener() {
//
//			@Override
//			public void mouseDoubleClick(MouseEvent mousey) {
//				
//			}
//
//			@Override
//			public void mouseDown(MouseEvent mousey) {
//				System.out.println("mouse is clicked");
//				Tracker tracker = new Tracker(getDisplay(), SWT.NONE);
//				int mouseXpos = mousey.x;
//				int mouseYpos = mousey.y;
//				System.out.println("mouse x : " + mouseXpos + "mouse y : " + mouseYpos);
//				
//				tracker.open();
//			}
//
//			@Override
//			public void mouseUp(MouseEvent mousey) {
//				System.out.println("mouse is released");
//			}
//		});
	
//	// tracking the mouse if he entered the listening zone
//	 this.addMouseTrackListener(new MouseTrackListener() {
//		  public void mouseEnter(MouseEvent arg0) {
//	        System.out.println("mouseEnter");
//
//	      }
//		  public void mouseExit(MouseEvent arg0) {
//	        System.out.println("mouseExit");
//
//	      }
//		  public void mouseHover(MouseEvent arg0) {
//	        System.out.println("mouseHover");
//
//	      }
//	    });
	
	} //close of board constructor
	
	
	
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
}