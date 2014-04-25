package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


//public class Board extends Canvas {
	
	//int[][] boardData; //the data of the board
	
	public class Board extends Composite{
	
	int[][] boardData;
	int N;
	int M;
	public Board (Composite parent,int style){

		super(parent,style);
		
		N=4;
		M=4;
		boardData = new int[N][M];
		setLayout(new GridLayout(N,true));
	
		Tile tiles[][] = new Tile[N][M];

		for(int i=0; i<N ; i++)
			for (int j=0 ; j<M ; j++){
				tiles[i][j] = new Tile(this,SWT.BORDER);
				tiles[i][j].setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true));
				tiles[i][j].setValue(boardData[i][j]);
		}

	}

//	public Board(final Composite parent,int style) {
//		super(parent,style);							
//		boardData = new int[][] {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};		
//		this.addPaintListener(new PaintListener() {
//	    	 public void paintControl(PaintEvent e) { 	
//	    		 Canvas canvas = (Canvas) e.widget;
//	    		 int canvasX=canvas.getSize().x;
//	    		 int canvasY=canvas.getSize().y;	    	 	    			    		 	    		
//	             for(int i=0; i < boardData.length; i++){	            	
//	                 for(int j=0; j < boardData[0].length; j++){	                	 
//	                	 Rectangle rc = new Rectangle(i*canvasX/4,j*canvasY/4,canvasX/4,canvasY/4);
//	                	 e.gc.drawRectangle(rc);	                	 
//	                	 e.gc.drawString(boardData[i][j] + "", rc.y+canvasX/8 ,rc.x+canvasY/8);	           
//	                 }	                 
//	                 System.out.println("\n");
//	             }
//	    	 }
//	     });	  
//	 }	
	
	
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