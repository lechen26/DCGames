package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


public class Board extends Canvas {
	
	int[][] boardData; //the data of the board
	
	public Board(final Composite parent,int style) {
		super(parent,style);
	
		boardData=new int[4][4];
		//final Image image = new Image(parent.getDisplay(),"resources/2048_pics/2_pic.jpg");		
		//final Image image2 = new Image(parent.getDisplay(), "resources/2048_pics/2_pic.jpg");
		//final Image image4 = new Image(parent.getDisplay(), "resources/2048_pics/4_pic.jpg");		
		
		this.addPaintListener(new PaintListener() {
	    	 public void paintControl(PaintEvent e) { 	
	    		 Canvas canvas = (Canvas) e.widget;
	    		 int canvasX=canvas.getSize().x;
	    		 int canvasY=canvas.getSize().y;	    		 	    			    		 	    		
	             for(int i=0; i <= boardData.length; i++){
	                 for(int j=0; j <= boardData[0].length; j++){     	 
	                	 //e.gc.drawImage(image, 0,0,image.getBounds().width, image.getBounds().height,0,0,canvasX/4,canvasY/4);
	                	 e.gc.drawRectangle(i,j,i*canvasX/4,j*canvasY/4);
	                 }
	                 System.out.println("\n");
	             }
	    	 }
	     });	  
	 }	
}