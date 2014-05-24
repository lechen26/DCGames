package view;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ViewMaze extends Observable implements View, Runnable {

	 Display display;
	Shell shell;
	Board board;
	int userCommand = 0;
	int rows, cols;
	int pressed = 0;
	int horizental = 0;
	int vertical = 0;
	Label score;
	boolean rightPressed = false;
	boolean leftPressed = false;
	boolean upPressed = false;
	boolean downPressed = false;
	
	public ViewMaze(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}

	public Display getDisplay() {
		return display;
	}
	/**
	* Initialize components
	*/
	private void initComponents() {
		int scr = 0;
		display = new Display();
		shell = new Shell(display);
		shell.setLayout(new GridLayout(2, false));
		shell.setSize(600, 600);
		shell.setText("Levi and Duvid's Maze Game");

		// Defines menu game
		initializeMenu();

		// Defines the Score label
		score = new Label(shell, SWT.BORDER);
		score.setText("Score:      " + scr);

		// Defines the board game
		initializeBoard();

		// Defines the buttons of the game
		initializeButtons();

		// Defines Key Listener
		shell.forceFocus();
		shell.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				// Switch case on the Key Release
				switch (e.keyCode) {
				case SWT.ARROW_UP:
					upPressed = false;
					pressed--;
					break;
				case SWT.ARROW_DOWN:
					downPressed = false;
					pressed--;
					break;
				case SWT.ARROW_RIGHT:
					rightPressed = false;
					pressed--;
					break;
				case SWT.ARROW_LEFT:
					leftPressed = false;
					pressed--;
					break;
				default:
					break;
				}
				userCommand = e.keyCode;		
				setChanged();				
				// support diagonal moves
				if (pressed == 0) {
					notifyObservers("" + horizental + "," + vertical);
					horizental = 0;
					vertical = 0;
				}			
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// switch case for the key pressed
				switch (e.keyCode) {
				case SWT.ARROW_UP:					
					vertical++;
					if (!upPressed) {
						pressed++;
						upPressed = true;
					}
					break;
				case SWT.ARROW_DOWN:
					vertical--;
					if (!downPressed) {
						pressed++;
						downPressed = true;
					}
					break;
				case SWT.ARROW_RIGHT:
					horizental++;
					if (!rightPressed) {
						pressed++;
						rightPressed = true;
					}
					break;
				case SWT.ARROW_LEFT:
					horizental--;
					if (!leftPressed) {
						pressed++;
						leftPressed = true;
					}
					break;				
				default:
					break;
				}
			}
		});

		/**
		* mouse event listener added
		*/
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++) {
				board.states[i][j].addMouseListener(new MouseListener() {
					int mouseNewXpos;
					int mouseNewYpos;

					@Override
					public void mouseDown(MouseEvent event) {
					}

					@Override
					public void mouseUp(MouseEvent event) {
						mouseNewXpos = event.x;
						mouseNewYpos = event.y;
						Rectangle stateBound = ((State) event.widget).getBounds();
						if ((mouseNewXpos < 0) && (mouseNewYpos < 0)) { // Diagonal Left up
							setChanged();
							horizental = -1;
							vertical = 1;
							notifyObservers("" + horizental + "," + vertical);
						} else if ((mouseNewXpos > stateBound.width) && (mouseNewYpos < 0)) { // Diagonal Right up
							setChanged();
							horizental = 1;
							vertical = 1;
							notifyObservers("" + horizental + "," + vertical);
						} else if ((mouseNewXpos > stateBound.width) && (mouseNewYpos > stateBound.height)) { // Diagonal Right Down
							setChanged();
							horizental = 1;
							vertical = -1;
							notifyObservers("" + horizental + "," + vertical);
						} else if ((mouseNewXpos < 0) && (mouseNewYpos > stateBound.height)) { // Diagonal Left Down
							setChanged();
							horizental = -1;
							vertical = -1;
							notifyObservers("" + horizental + "," + vertical);
						} else if (mouseNewYpos < 0) { // up
							setChanged();
							userCommand = SWT.ARROW_UP;
							notifyObservers();
						} else if (mouseNewXpos < 0) { // Left
							setChanged();
							userCommand = SWT.ARROW_LEFT;
							notifyObservers();
						} else if (mouseNewXpos >= stateBound.width) { // Right
							setChanged();
							userCommand = SWT.ARROW_RIGHT;
							notifyObservers();
						} else if (mouseNewYpos >= stateBound.height) { // Down
							setChanged();
							userCommand = SWT.ARROW_DOWN;
							notifyObservers();
						}
					}

					@Override
					public void mouseDoubleClick(MouseEvent arg0) {
					}

				});
			}

		shell.open();
	}
	/**
	* overriding the run method
	*/
	@Override
	public void run() {
		initComponents();
		setChanged();
		notifyObservers();
		shell.forceFocus();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	@Override
	/**
	* Display the Board with the given data
	*/
	public void displayBoard(final int[][] data) {
		display.asyncExec(new Runnable() { 
			 @Override 
			 public void run() { 
				board.setBoard(data);
				board.redraw();
			 } 
			 });
	}

	/**
	 *  Get user command
	 */
	@Override
	public int getUserCommand() {
		return userCommand;
	}

	/**
	* set user command
	*/
	public void setUserCommand(int command) {
		this.userCommand = command;
	}

	/**
	 * Initialize Board canvas
	 */
	private void initializeBoard() {
		board = new Board(shell, SWT.BORDER, rows, cols, SWT.NONE);
		board.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 10));
		board.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		shell.forceFocus();
	}

	/**
	 * Initialize Menu buttons 
	 */
	private void initializeMenu() {
		// Defines Menu
		Menu menuBar = new Menu(shell, SWT.BAR);
		MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);

		// File menu
		cascadeFileMenu.setText("&File");
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeFileMenu.setMenu(fileMenu);
		MenuItem saveItem = new MenuItem(fileMenu, SWT.PUSH);
		saveItem.setText("&Save");
		MenuItem loadItem = new MenuItem(fileMenu, SWT.PUSH);
		loadItem.setText("&Load");
		MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText("&Exit");

		// Edit menu
		MenuItem cascadeEditMenu = new MenuItem(menuBar, SWT.CASCADE);
		cascadeEditMenu.setText("&Edit");
		Menu editMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeEditMenu.setMenu(editMenu);
		MenuItem restartItem = new MenuItem(editMenu, SWT.PUSH);
		restartItem.setText("&Restart");
		MenuItem undoItem = new MenuItem(editMenu, SWT.PUSH);
		undoItem.setText("&Undo");
		shell.setMenuBar(menuBar);
		// Defines the exit button
		exitItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				display.dispose();			
			}
		});
	}

	/**
	 * Initialize Shell buttons
	 */
	private void initializeButtons() {
		// Defines undo button
		Button undoButton = new Button(shell, SWT.PUSH);
		undoButton.setText("Undo Move");
		undoButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		undoButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				userCommand = 1;
				setChanged();
				notifyObservers();
				shell.forceFocus();
			}
		});

		// Defines restart Button
		Button restartButton = new Button(shell, SWT.PUSH);
		restartButton.setText("Restart Game");
		restartButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		restartButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setUserCommand(2);
				setChanged();
				notifyObservers();
				shell.forceFocus();
			}
		});

		Button loadButton = new Button(shell, SWT.PUSH);
		loadButton.setText("Load Game");
		loadButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		loadButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}

			public void widgetSelected(SelectionEvent arg0) {
				userCommand = 3;
				setChanged();
				notifyObservers();
				shell.forceFocus();
			}
		});
		Button saveButton = new Button(shell, SWT.PUSH);
		saveButton.setText("Save Game");
		saveButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		saveButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				userCommand = 4;
				setChanged();
				notifyObservers();
				shell.forceFocus();
			}
		});
		
		Button hintButton = new Button(shell,SWT.PUSH);
		hintButton.setText("Get Hint");
		hintButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		hintButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setUserCommand(6);
				setChanged();
				notifyObservers();
				shell.forceFocus();
			}
		});
		
		Button solveButton = new Button(shell,SWT.PUSH);
		solveButton.setText("Solve Game");
		solveButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		solveButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setUserCommand(7);
				setChanged();
				notifyObservers();
				shell.forceFocus();
			}
		});
		
		Button stopButton = new Button(shell,SWT.PUSH);
		stopButton.setText("Stop Solver");
		stopButton.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false, 1, 1));
		stopButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setUserCommand(9);
				setChanged();
				notifyObservers();
				shell.forceFocus();
			}
		});


	    Label rmi = new Label(shell, SWT.NONE);		
		rmi.setText("RMI Server:");
		rmi.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,1,1));
		final Combo serverCombo = new Combo(shell, SWT.SIMPLE);	    
	    serverCombo.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,1,1));
	    final String items[] = { "localhost","127.0.0.1" , ""};
	    serverCombo.setItems(items);
	    serverCombo.select(0);	    
	    serverCombo.addSelectionListener(new SelectionAdapter() {
	    	 public void widgetSelected(SelectionEvent e) {
	    		 setUserCommand(8);
	    		 setChanged();
	    		 notifyObservers("server=" + serverCombo.getText());
	    		 System.out.println("Server is=" + serverCombo.getText());
	    	 }
	    });	  
	    
	    serverCombo.addKeyListener(new KeyListener() {			
			public void keyPressed(KeyEvent arg0) {
	        	if (arg0.keyCode == SWT.CR)
	        	{		        	
		        	String res=serverCombo.getText();	            
		            String[] items=serverCombo.getItems();		            
		            for (String it : items)
		            {
		            	if (res.equals(it))
		            	{		            		
		            		setUserCommand(8);
			    		 	setChanged();
			    		 	notifyObservers("server=" + serverCombo.getText());
			    		 	System.out.println("Server is=" + serverCombo.getText());
		            	}		            	
		            }
	        	}								
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
	    });

	}

	/**
	* trying to undo when in first move
	*/
	@Override
	public void undoEnd() {
		MessageBox end = new MessageBox(shell,SWT.POP_UP);		
		end.setMessage("This is the First state of the game");
		end.setText("Undo");
		end.open();		
	}
	/**
	* display score method
	*/
	@Override
	public void displayScore(final int scr) {
		display.asyncExec(new Runnable() { 
			 @Override 
			 public void run() { 		
			score.setText("Score " + scr);
			score.redraw();
		}
		});
	}

	

	/**
	 * Display Game Won board 
	 */
	@Override
	public void gameWon() {
		display.asyncExec(new Runnable() { 
			 @Override 
			 public void run() { 		
			
			new ExternalShell(display,"Game Won","resources/maze/win.jpg").run();
			MessageBox end = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
			end.setMessage("Mini is so Happy!!!:)  do you wanna play another one?");
			end.setText("GAME WON");
			int response = end.open();
			if (response == SWT.NO) {
				display.dispose();
			} else {			
				setUserCommand(2);			
				setChanged();
				notifyObservers();
			}
			 }
		});
	}
	
		
	/**
	 * Display Game Won board 
	 */
	@Override
	public void gameOver() {
		display.asyncExec(new Runnable() { 
			 @Override 
			 public void run() { 				
				new ExternalShell(display,"Finito la comedia","resources/maze/end.jpg").run();
			 	MessageBox end = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				end.setMessage("Next Time, try better for mini, ha? you got to the END point but with More moves then possible! do you wanna play another one?");
				end.setText("GAME FINISH");
				int response = end.open();
				if (response == SWT.NO) {
					display.dispose();					
				} else {
					setUserCommand(2);
					setChanged();
					notifyObservers();
				}
			 }
			 });
	}
	
}
