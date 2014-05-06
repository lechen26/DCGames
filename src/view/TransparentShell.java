package view;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.FillLayout;
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
	private int TIMER_INTERVAL=5;
	
	
	public TransparentShell(Display display,String[] msg, Rectangle bounds) {	
		this.display=display;
		this.shellMessage = msg;
		this.bounds=bounds;
	}
  
	/*
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

	

  
  /*
   * Create the shell content and PaintControl for drawing the image
   */
  private void createContents(final Shell shell) {
      final int startx=bounds.x+bounds.width;
	  final int starty=bounds.y+bounds.height;
	  //create an area to paint the text
	  Canvas canvas = new Canvas(shell, SWT.NO_BACKGROUND);      
      Rectangle size = new Rectangle(startx/2, starty/2, 2*startx, 2*starty);
      canvas.setBounds(size);
      
      //Define the Font
      Font font = display.getSystemFont();
      FontData[] fd = font.getFontData();
      fd[0].setHeight(30);
      fd[0].setStyle(SWT.BOLD);
      Font bigFont = new Font(display, fd[0]);
      canvas.setFont(bigFont);
      
      //add the Paint listener to draw the Text
      canvas.addPaintListener(new PaintListener() {
          public void paintControl(PaintEvent e) {
        	  int posx=20;
        	  int posy=10;
        	  for(int i=0;i<shellMessage.length;++i)
        	  {
        		  e.gc.drawString(shellMessage[i],posx, posy,true);
        		  posx+=10;
        		  posy+=90;        		  
        	  }
          }
      });
    }
 }
