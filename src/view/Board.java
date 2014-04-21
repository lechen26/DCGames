package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.sun.corba.se.impl.orbutil.graph.Graph;


public class Board extends Canvas {
	
	int[][] boardData; //the data of the board
	
	public Board(Composite parent,int style) {
		super(parent,style);
			     
		this.addPaintListener(new PaintListener() {
	    	 public void paintControl(PaintEvent e) { 	        	
	    		 boardData=new int[4][4];
	    		 System.out.println("length is " + boardData.length);
	    		 //Graph maze = new Graph();
	    		 
	             for(int i=0; i < boardData.length-1; i++){
	                 for(int j=0; j < boardData[0].length-1; j++){     
	                	 System.out.println("square is in (" + (i*100) + "," + (j*100) +")to (" + (i*100+100) +","+ (j*100+100) + ")");
	                     e.gc.drawRectangle(i*100, j*100, i*100+100, j*100+100);	                	 	                    
	                 }
	                 System.out.println("\n");
	             }
	    	 }
	     });	  
	 }	
}
