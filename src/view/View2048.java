package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class View2048 extends Observable implements View,Runnable {


	Display display;
	Shell shell;
	private void initComponents(){
		display = new Display();
		shell = new Shell(display); shell.setLayout(new GridLayout(2, false));
		shell.setSize(400, 300);
		shell.setText("Levi & Duvid's 2048 Game");
		Label score = new Label(shell, SWT.BORDER);
		score.setText("This is the Score");
		score.setLayoutData(new GridData(SWT.FILL,SWT.TOP,false,false,2,1));
		Button b1=new Button(shell, SWT.PUSH);
		b1.setText("Undo Move");
		b1.setLayoutData(new GridData(SWT.FILL,SWT.TOP, false, false, 1,1));
		Board board = new Board(shell, SWT.BORDER);
		board.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true, 1,2));
		Button b2=new Button(shell, SWT.PUSH);
		b2.setText("Restart Game");
		b2.setLayoutData(new GridData(SWT.FILL,SWT.TOP, false, false, 1,1));
	       shell.open();
	}   
	@Override
	public void run() { 
		initComponents(); 
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){ 
				display.sleep();
			}
	}
	display.dispose();
	}

	@Override
	public void displayData(int[][] data) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getUserCommand() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	
	
}
