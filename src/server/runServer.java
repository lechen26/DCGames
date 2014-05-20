package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
		Registry registry = LocateRegistry.createRegistry(Constants.RMI_PORT);
		System.out.println("java RMI registry created.");		
		registry.bind("Server2048", imp2048);		
		System.out.println("PeerServer 2048 bound in registry");
		registry.bind("ServerMaze", impMaze);
		System.out.println("PeerServer Maze bound in registry");		
	}

}
