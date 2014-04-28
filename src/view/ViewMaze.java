package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ViewMaze extends Observable implements View,Runnable {

	Display display;
	Shell shell;
	BoardMaze board;	
	int userCommand=0;
	
	private void initComponents(){
		int scr=0;
		display = new Display();
		shell = new Shell(display);
		shell.setLayout(new GridLayout(2,false));
		shell.setSize(600, 500);
		shell.setText("Levi and Duvid's Maze Game");
	    
		//Defines menu game
		initializeMenu();
		
	    //Defines the Score label
	    Label score = new Label(shell, SWT.BORDER);
		score.setText("Score:  " + scr);
		
		
		//Defines the board game
		initializeBoard();
		
		//Defines the buttons of the game
		initializeButtons();
		
		//Defines Key Listener
		shell.forceFocus();
		shell.addKeyListener(new KeyListener() {			
			@Override
			public void keyReleased(KeyEvent e) {
			}			
			@Override
			public void keyPressed(KeyEvent e) {			
				if ( ( e.keyCode == SWT.ARROW_DOWN ) || (e.keyCode == SWT.ARROW_LEFT) || (e.keyCode == SWT.ARROW_UP) || (e.keyCode == SWT.ARROW_RIGHT) ) {
					System.out.println("presses");
					userCommand=e.keyCode;
					setChanged();
					notifyObservers();
					shell.forceFocus();
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
		shell.forceFocus();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){ 
				display.sleep();
			}
		}
		display.dispose();
	}
	
		
	@Override
	/*
	 * Display the Board with the given data
	 */
	public void displayData(int[][] data) {		
		board.setBoard(data);
		board.redraw();
				
	}
	

	/*
	 *  Get user command
	 */
	@Override
	public int getUserCommand() {		
		return userCommand;
	}
	
	public void setUserCommand(int command) {
		this.userCommand = command;
	}
	/*
	 * Display game Winning board
	 */
	public void gameWon() {
		System.out.println("I came to viewMaze !!! i win !!!");
		MessageBox end = new MessageBox(shell,SWT.OK);		
		end.setMessage("Your Are the MazeMaster,game won.");
		end.setText("GAME WON");
		int response = end.open();
		if (response == SWT.OK) {
			display.dispose();
			System.exit(0);
		}
	}
	
//	/* NO NEED FOR GAME OVER IN MAZE
//	 * Display Game over board 
//	 */
	public void gameOver() {				
	}
	
	/*
	 * Initialize Board canvas
	 */
	private void initializeBoard() {		
		board = new BoardMaze(shell, SWT.BORDER);		
		board.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true, 1,5));
		board.setBackground(display.getSystemColor(SWT.COLOR_WHITE));	
		shell.forceFocus();
	}
	
	/*
	 * Initialize Menu buttons 
	 */
	private void initializeMenu() {	    
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
	    //Defines the exit button
	    exitItem.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		shell.getDisplay().dispose();
	    		System.exit(0);
	    	}
	    });		
	}
	
	/*
	 * Initialize Shell buttons
	 */
	private void initializeButtons()
	{
		//Defines undo button
	    Button undoButton=new Button(shell, SWT.PUSH);
		undoButton.setText("Undo Move");
		undoButton.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		undoButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				userCommand=1;
				setChanged();
				notifyObservers();	
				shell.forceFocus();
			}
		});
		
		//Defines restart Button
		Button restartButton=new Button(shell, SWT.PUSH);
		restartButton.setText("Restart Game");
		restartButton.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		restartButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				userCommand=2;				
				setChanged();
				notifyObservers();			
				shell.forceFocus();
			}
		});
		
		Button loadButton=new Button(shell, SWT.PUSH);
		loadButton.setText("Load Game");
		loadButton.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		Button saveButton=new Button(shell, SWT.PUSH);
		saveButton.setText("Save Game");	
		saveButton.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
	}

	
}
