package server;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.Constants;


public class runServer {

	/**
	 * @param args
	 * @throws AccessException 
	 * @throws RemoteException 
	 * @throws AlreadyBoundException 
	 */
	public static void main(String[] args) throws AccessException {
		viewServer serverUi = new viewServer();
		serverUi.run();
	}
}

