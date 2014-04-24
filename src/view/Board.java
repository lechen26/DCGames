package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


public class Board extends Canvas {
	
	int[][] boardData; //the data of the board
	
	public Board(final Composite parent,int style) {
		super(parent,style);							
		boardData = new int[][] {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};		
		this.addPaintListener(new PaintListener() {
	    	 public void paintControl(PaintEvent e) { 	
	    		 Canvas canvas = (Canvas) e.widget;
	    		 int canvasX=canvas.getSize().x;
	    		 int canvasY=canvas.getSize().y;	    	 	    			    		 	    		
	             for(int i=0; i < boardData.length; i++){	            	
	                 for(int j=0; j < boardData[0].length; j++){	                	 
	                	 Rectangle rc = new Rectangle(i*canvasX/4,j*canvasY/4,canvasX/4,canvasY/4);
	                	 e.gc.drawRectangle(rc);	                	 
	                	 e.gc.drawString(boardData[i][j] + "", rc.y+canvasX/8 ,rc.x+canvasY/8);	           
	                 }	                 
	                 System.out.println("\n");
	             }
	    	 }
	     });	  
	 }	
	
	
	public void displayBoard() {		
		for(int i=0;i<boardData.length;++i){
			for(int j=0;j<boardData[0].length;++j){
				System.out.print(boardData[i][j]+ " "); 
			}
			System.out.println("\n");
		}
	}
	public void setBoard(int[][] board) {
		boardData=board.clone();
	}

}