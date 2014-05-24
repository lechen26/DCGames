package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import common.Constants;


public class runServer {

	/**
	 * @param args
	 * @throws RemoteException 
	 * @throws AlreadyBoundException 
	 */
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		RemoteImpl2048 imp2048 = new RemoteImpl2048();
		RemoteImplMaze impMaze = new RemoteImplMaze();
		// Creation of Registry 
		Registry registry=null;
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

}
