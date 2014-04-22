package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class View2048 extends Observable implements View,Runnable {

	Display display;
	Shell shell;
	Board board;
	int userCommand=0;
	
	private void initComponents(){
		int scr=0;
		display = new Display();
		shell = new Shell(display);
		shell.setLayout(new GridLayout(2,false));
		shell.setSize(600, 500);
		shell.setText("Levi and Duvid's 2048 Game");
	    
		//Defines Menu
		Menu menuBar = new Menu(shell, SWT.BAR);
	    MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
	    //File menu
	    cascadeFileMenu.setText("&File");	        
	    Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
	    cascadeFileMenu.setMenu(fileMenu);
	    MenuItem saveItem = new MenuItem(fileMenu,SWT.PUSH);
	    saveItem.setText("&Save");
	    MenuItem loadItem = new MenuItem(fileMenu,SWT.PUSH);
	    loadItem.setText("&Load");
	    MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
	    exitItem.setText("&Exit");
	    //Edit menu
	    MenuItem cascadeEditMenu = new MenuItem(menuBar,SWT.CASCADE);
	    cascadeEditMenu.setText("&Edit");
	    Menu editMenu = new Menu(shell, SWT.DROP_DOWN);
	    cascadeEditMenu.setMenu(editMenu);
	    MenuItem restartItem = new MenuItem(editMenu,SWT.PUSH);
	    restartItem.setText("&Restart");
	    MenuItem undoItem = new MenuItem(editMenu,SWT.PUSH);
	    undoItem.setText("&Undo");
	    shell.setMenuBar(menuBar);
	    exitItem.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		shell.getDisplay().dispose();
	    		System.exit(0);
	    	}
	    });	    	    	    
	    Label score = new Label(shell, SWT.BORDER);
		score.setText("Score:  " + scr);
		board = new Board(shell, SWT.BORDER);		
		board.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true, 1,5));
		board.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
	    Button b1=new Button(shell, SWT.PUSH);
		b1.setText("Undo Move");
		b1.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));	    
		Button b2=new Button(shell, SWT.PUSH);
		b2.setText("Restart Game");
		b2.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));	   
		Button b3=new Button(shell, SWT.PUSH);
		b3.setText("Load Game");
		b3.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));	    
		Button b4=new Button(shell, SWT.PUSH);
		b4.setText("Save Game");	
		b4.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));		
		board.setFocus();
		board.addKeyListener(new KeyListener() {			
			@Override
			public void keyReleased(KeyEvent arg0) {
			}			
			@Override
			public void keyPressed(KeyEvent e) {								
				if ( ( e.keyCode == SWT.ARROW_DOWN ) || (e.keyCode == SWT.ARROW_LEFT) || (e.keyCode == SWT.ARROW_UP) || (e.keyCode == SWT.ARROW_RIGHT) ) {
					userCommand=e.keyCode;
					setChanged();
					notifyObservers();
				}				
			}
		});
		
	    shell.open();
	}   
	
	@Override
	public void run() { 		
		initComponents();
		setChanged();
		notifyObservers();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){ 
				display.sleep();
			}
	}
	display.dispose();
	}
	
	@Override
	public void displayData(int[][] data) {
		board.setBoard(data);
		//board.displayBoard();
		board.redraw();
		/*
		display.syncExec(new Runnable () {
			@Override
			public void run() {
				board.setBoard(mdata);
			}
		});*/
	}
	

	@Override
	public int getUserCommand() {		
		return userCommand;
	}

	
	
	
	
}
