package view;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;


public class Board extends Composite { 
	
	int[][] boardData;
	int rows,cols;
	State states[][];
	
	/*
	 * Board constructure, gets Canvas properties, size of the boardData array and style of State (bordered or not)
	 */
	public Board(Composite parent, int style, int rows, int cols, int stateStyle) {
		super(parent,style);	
		boardData = new int[rows][cols];
		GridLayout grid = new GridLayout(cols,false);	
		grid.verticalSpacing=0;
		grid.horizontalSpacing=0;
		grid.makeColumnsEqualWidth=false;
		setLayout(grid);		
		states = new State[rows][cols];
		for(int i=0;i< boardData.length; ++i)
		{
				for(int j=0; j < boardData[0].length; j++)
				{					
					states[i][j] = new State(Board.this,stateStyle);
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
						states[i][j].dispose();					
				}
			}
		});
	}
	
	
	private static int[][] copyBoard(int[][] source) {
	    int[][] copy = new int[source.length][];
	    for (int i = 0; i < source.length; i++) {
	        copy[i] = Arrays.copyOf(source[i],source[i].length);
	    }
	    return copy;
	}
	/*
	 * update array Board with the given array data
	 */
	public void setBoard(int[][] board) {
		boardData=copyBoard(board);
		for(int i=0;i< boardData.length; ++i)
		{
			for(int j=0; j < boardData[0].length; ++j)
			{															
				states[i][j].setValue(boardData[i][j]);
			}
		}		
	}	
}