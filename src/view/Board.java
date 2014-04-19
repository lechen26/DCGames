package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class Board extends Canvas {
	
	int[][] boardData; //the data of the board
	
	public Board(Composite parent,int style) {
		super(parent,style);
		
	}
	
}
