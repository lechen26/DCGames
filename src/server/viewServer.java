package server;


import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.omg.PortableInterceptor.SUCCESSFUL;

import common.Constants;


public class viewServer extends Observable implements Runnable {
	Shell shell;
	Display display;
	Registry registry=null;
	@Override
	public void run() {
		Display display = new Display();
	    Shell shell = new Shell(display);
	    shell.setSize(300, 100);
	    shell.setText("SERVER");
	    shell.setLayout(new GridLayout(2,false));

	    final Button startServer = new Button(shell, SWT.PUSH);
	    startServer.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,1,1));
	    startServer.setText("Start Server");
	    final Button stopServer = new Button(shell, SWT.PUSH);
	    stopServer.setLayoutData(new GridData(SWT.FILL,SWT.NONE,false,false,1,1));
	    stopServer.setText("Stop Server");
	    //final Text text = new Text(shell, SWT.SHADOW_IN);
	    final Label label = new Label(shell, SWT.BORDER);
	    label.setText("                                ");
	    startServer.addSelectionListener(new SelectionListener() {

	      public void widgetSelected(SelectionEvent event) {
	        try {
				startServer();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AlreadyBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        label.setText("Server Started");
	      }
	      	
	      public void widgetDefaultSelected(SelectionEvent event) {
	        //
	      }
	    });
	    
	    stopServer.addSelectionListener(new SelectionListener() {

		      public void widgetSelected(SelectionEvent event) {
		        try {
					stopServer();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        label.setText("Server Stopped");
		      }
		      	
		      public void widgetDefaultSelected(SelectionEvent event) {
		        //
		      }
		    });
	    
	    shell.open();
	    
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	    display.dispose();
	    shell.dispose();
	    System.exit(0);
	  }
	/**
	 * Start the server method
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	public void startServer() throws RemoteException, AlreadyBoundException{
		RemoteImpl2048 imp2048 = new RemoteImpl2048();
		RemoteImplMaze impMaze = new RemoteImplMaze();
	
		try {
			registry = LocateRegistry.createRegistry(Constants.RMI_PORT);
			System.out.println("java RMI registry created.");

			//Server2048 binding
			registry.bind("Server2048", imp2048);
			System.out.println("2048 app bounded to server on port " + Constants.RMI_PORT);

			//ServerMaze binding		
			registry.bind("ServerMaze", impMaze);
			System.out.println("Maze app bounded to server on port " + Constants.RMI_PORT);		

		}catch (RemoteException e) {
			System.out.println("Registry already created on Port " + Constants.RMI_PORT);			
		}		
	}
	/**
	 * Stop server method
	 * @throws RemoteException
	 */
	public void stopServer() throws RemoteException {
		int succesful = 0;
        try {
            registry.unbind("Server2048");
            registry.unbind("ServerMaze");
            UnicastRemoteObject.unexportObject(registry, true);
            Thread.sleep(1000);
            
        } catch (NotBoundException e) {
            System.out.println("Error shutting down the server - could not unbind the registry" + e);
            succesful = -1;
        } catch (InterruptedException e) {
            System.out.println("Unable to sleep when shutting down the server" + e);
            succesful = -1;
        }
        catch (AccessException e) {
            System.out.println("Access Exception" + e);
            succesful = -1;
        }
        catch (UnmarshalException e) {
            System.out.println(e.detail.getMessage());
           System.out.println("UnMarshall Exception" + e);
            succesful = -1;
        }
        catch (RemoteException e) {
            System.out.println(e.detail.getMessage());
            System.out.println("Remote Exception" + e);
            succesful = -1;
        }

       System.out.println("server shut down gracefully");     
       //System.exit(succesful);		
	}
}

	

