package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.algorithms.Action;


public interface RemoteInt extends Remote{
	public String getHint(customModel model) throws RemoteException, CloneNotSupportedException;
	public ArrayList<Action> solveGame(customModel model) throws RemoteException, CloneNotSupportedException;
	
}