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


public class Board2048 extends Canvas {
	
	int[][] boardData;
	final int N=4;
	State2048 states[][];
	
	public Board2048(Composite parent, int style) {
		super(parent,style);
				
		boardData = new int[][] {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};		
		setLayout(new GridLayout(N,true));
		states = new State2048[N][N];
		for(int i=0;i< boardData.length; ++i)
		{
				for(int j=0; j < boardData[0].length; ++j)
				{
					states[i][j] = new State2048(Board2048.this,SWT.BORDER);
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
}