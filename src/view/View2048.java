package view;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class View2048 extends Observable implements View,Runnable {

	Display display;
	Shell shell;
	Board board;
	Label score;	
	int userCommand=0;
	int rows,cols;
	
	public View2048(int rows,int cols) {
		this.rows=rows;
		this.cols=cols;
	}
	
	private void initComponents(){
		display = new Display();
		shell = new Shell(display);
		shell.setLayout(new GridLayout(2,false));
		shell.setSize(600, 500);
		shell.setText("Levi and Duvid's 2048 Game");
	    
		//Defines menu game
		initializeMenu();
		
	    //Defines the Score label
	    score = new Label(shell, SWT.NONE);		
		score.setText("Score  0                      ");
		
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
		display.dispose();
		System.exit(0);
	}
	
	/*
	 * Display Game over board 
	 */
	public void gameOver() {				
		MessageBox end = new MessageBox(shell,SWT.ICON_QUESTION | SWT.YES | SWT.NO);		
		end.setMessage("You have lost the game. do you want to play another one?");
		end.setText("gameOver");
		int response = end.open();
		if (response == SWT.NO) {
			display.dispose();
			System.exit(0);
		}
		if (response == SWT.YES){
			setUserCommand(2);
			setChanged();
			notifyObservers();	
		}
	}
	
	public void undoEnd()
	{
		MessageBox end = new MessageBox(shell,SWT.POP_UP);		
		end.setMessage("This is the First state of the game");
		end.setText("Undo");
		end.open();		
	}
	
	/*
	 * Initialize Board canvas
	 */
	private void initializeBoard() {		
		board = new Board(shell, SWT.BORDER,rows,cols,SWT.BORDER);		
		board.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,true, 1,5));
		board.setBackground(display.getSystemColor(SWT.COLOR_GRAY));	
		shell.forceFocus();
	}
	
	/*
	 * Initialize Menu bottons 
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
		loadButton.addSelectionListener(new SelectionListener() {

			@Override	
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			public void widgetSelected(SelectionEvent arg0) {
				userCommand=3;
				setChanged();
				notifyObservers();
				shell.forceFocus();					
			}
		});
		Button saveButton=new Button(shell, SWT.PUSH);
		saveButton.setText("Save Game");	
		saveButton.setLayoutData(new GridData(SWT.NONE,SWT.NONE,false,false,1,1));
		saveButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				userCommand=4;
				setChanged();
				notifyObservers();
				shell.forceFocus();			}
		});
	}

	@Override
	public void displayScore(int scr) {		
		score.setText("Score "+ scr);
	}
	
	public void saveGame() {			
	}

	@Override
	public void gameFinish() {
		// TODO Auto-generated method stub
		
	}		
}
