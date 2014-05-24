package view;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
 
/**
 * This class demonstrates Transparent Shell for drawing Text
 */
public class TransparentShell {
 
	private Display display;
	String[] shellMessage;	
	int count=0;
	int maxSec=500;
	Rectangle bounds;
	Point location;
	private int TIMER_INTERVAL=5;
	
	
	public TransparentShell(Display display,String[] msg, Rectangle bounds,Point location) {	
		this.display=display;
		this.shellMessage = msg;
		this.bounds=bounds;
		this.location=location;
	}
  
	/**
	 * Execute the shell on different thread and Set Timer for Transparent shell
	 */
	public void run()  {		
		final Shell shell = new Shell(display, SWT.NO_TRIM | SWT.NO_BACKGROUND | SWT.ON_TOP);
		createContents(shell);
		shell.open();

		//Set up the timer for showing Transparent shell
		Runnable timer = new Runnable() {
			public void run() {
				count++;
				if (count <= maxSec)
					display.timerExec(TIMER_INTERVAL, this);
				else
					shell.dispose();
			}
		};
		
	   //Start the Timer
	   display.timerExec(0, timer); 
		
		while (!shell.isDisposed()) {								
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
   }

	

  
  /**
   * Create the shell content and PaintControl for drawing the image
   */
  private void createContents(final Shell shell) {
      final int startx=location.x;
	  final int starty=location.y;
	  //create an area to paint the text
	  Canvas canvas = new Canvas(shell, SWT.NO_BACKGROUND | SWT.ON_TOP);	  
      Rectangle size = new Rectangle(startx, starty, startx+bounds.width,starty+bounds.height);
      canvas.setBounds(size);
      Region region = canvas.getRegion();
      //Define the Font
      Font font = display.getSystemFont();
      FontData[] fd = font.getFontData();
      fd[0].setHeight(30);
      fd[0].setStyle(SWT.BOLD);
      Font bigFont = new Font(display, fd[0]);
      canvas.setFont(bigFont);
      shell.setRegion(region);
      //add the Paint listener to draw the Text
      canvas.addPaintListener(new PaintListener() {
          public void paintControl(PaintEvent e) {
        	  int posx=80;
        	  int posy=140;
        	  for(int i=0;i<shellMessage.length;++i)
        	  {
        		  e.gc.drawString(shellMessage[i],posx, posy,true);
        		  posx+=10;
        		  posy+=80;        		  
        	  }
          }
      });
    }
 }
