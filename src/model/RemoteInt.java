package model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import model.algorithms.Action;


public interface RemoteInt extends Remote{

	public String getHint(Model model) throws RemoteException, CloneNotSupportedException;
	public ArrayList<Action> solveGame(Model model) throws RemoteException, CloneNotSupportedException;
}