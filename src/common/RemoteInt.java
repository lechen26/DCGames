package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.algorithms.Action;


/**
* public interface for the API commands of the server to the client
*/

public interface RemoteInt extends Remote{
	public String getHint(customModel model) throws RemoteException, CloneNotSupportedException;
	public ArrayList<Action> solveGame(customModel model) throws RemoteException, CloneNotSupportedException;
	
}