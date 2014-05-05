package view;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
 
/**
 * This class demonstrates animation on an external shell
 */
public class ExternalShell {
 
	private Canvas canvas;
	private Display display;
  
	private static final int IMAGE_WIDTH = 250; // Image width
	private static final int TIMER_INTERVAL = 5; 	//Time interval for image animation
	private int DIM_W;	//current image bounderies
	private int DIM_H;
	private int count=0;
	private int directionX = 1; //Direction of Image	
	private int directionY = 1;		
    int shx, shy; //positions of image
	String shellMessage;
	String imagePath;
	
	public ExternalShell(Display display,String msg,String img) {	
		this.display=display;
		this.shellMessage=msg;
		this.imagePath=img;
	}
  
  
	/*
	 * Execute the shell on different thread and Set Timer for animation processing
	 */
	public void run()  {	
		Shell shell = new Shell(display);
		shell.setSize(400,400);
		shell.setText(shellMessage);
		createContents(shell);
		shell.open();
 
		//Set up the timer for the animation
		Runnable runnable = new Runnable() {
			public void run() {
				animate();
				display.timerExec(TIMER_INTERVAL, this);
			}
		};
 
		// Launch the timer
		display.timerExec(TIMER_INTERVAL, runnable);
    	
		while (!shell.isDisposed()) {
			if (count >= 12 ){        	
				shell.dispose();
				break;
			}
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
 
		// Kill the timer
		display.timerExec(-1, runnable);    
  }

	
	/*
	 * moving Image animation 
	 */
  private void animate() {	  	  
	    shx += directionX;
	    shy += directionY;

	    // Determine out of bounds
	    Rectangle rect = canvas.getClientArea();
	    if (shx < 0) {
	      shx = 0;
	      count++;
	      directionX = 1;
	    } else if (shx > rect.width - IMAGE_WIDTH) {
	      shx = rect.width - IMAGE_WIDTH;
	      directionX = -1;
	      count++;
	    }
	    if (shy < 0) {
	      shy = 0;
	      count++;
	      directionY = 1;
	      count++;
	    } else if (shy > rect.height - IMAGE_WIDTH) {
	      shy = rect.height - IMAGE_WIDTH;
	      directionY = -1;
	    }

	    // Force a redraw
	    canvas.redraw();
	}

  
  /*
   * Create the shell content and PaintControl for drawing the image
   */
  private void createContents(final Shell shell) {
	  shell.setLayout(new FillLayout());
    
	  // Create the canvas for drawing
	  canvas = new Canvas(shell, SWT.NO_BACKGROUND);
	  canvas.addPaintListener(new PaintListener() {    	
	      public void paintControl(PaintEvent event) {    	  
	    	  Image img=new Image(display,imagePath);;
	    	  DIM_W=img.getBounds().width;
	    	  DIM_H=img.getBounds().height;    	   
	    	  event.gc.fillRectangle(canvas.getBounds());          
	          event.gc.drawImage(img,shx,shy);
	        }
	  });
  	}
  }