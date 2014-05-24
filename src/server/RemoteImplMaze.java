package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import model.algorithms.Action;

import common.RemoteInt;
import common.customModel;

public class RemoteImplMaze extends UnicastRemoteObject implements RemoteInt { 	
	private static final long serialVersionUID = 1L;
	
	protected RemoteImplMaze() throws RemoteException {
		super(0);
	}
	/**
	* Implementation of getHint on the Server
	*/
	@Override
	public String getHint(customModel model) throws RemoteException, CloneNotSupportedException {				
			aStar as = new aStar();		
			ArrayList<Action> actions = as.runAstar(model.getBoard(), model.getStartPosition()	,model.getEndPosition());
			if (actions != null && !actions.isEmpty()) {				
				return (actions.get(0)).getName();
			}
		return  null;
	}
	/**
	* Implementation of solveGame on the Server
	*/
	@Override
	public ArrayList<Action> solveGame(customModel model) throws RemoteException, CloneNotSupportedException {
		aStar as = new aStar();		
		ArrayList<Action> actions = as.runAstar(model.getBoard(), model.getStartPosition()	,model.getEndPosition());		
		if (actions != null && !actions.isEmpty())
			return actions;
		return null;
	}	
}