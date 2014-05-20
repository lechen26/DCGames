package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import model.Model;
import model.ModelMaze;
import model.algorithms.Action;

import common.RemoteInt;

public class RemoteImplMaze extends UnicastRemoteObject implements RemoteInt { 	
	private static final long serialVersionUID = 1L;	
	protected RemoteImplMaze() throws RemoteException {
		super(0);
	}

	@Override
	/*
	 * get Hint from RMI Server using Astar algorithm
	 * @param model the model
	 * @return the name of the best next move
	 */
	public String getHint(Model model) throws RemoteException, CloneNotSupportedException {				
			aStar as = new aStar();		
			ArrayList<Action> actions = as.runAstar((ModelMaze)model, ((ModelMaze)model).getCurrentPosition(), ((ModelMaze)model).getExitPosition());
			if (!actions.isEmpty())
				return (actions.get(0)).getName();												
		return  null;
	}

	@Override
	/*
	 * get full Solution from the RMI Server using aster algorithm
	 * @param model the model
	 * return the ArrayList of actions which are the best route to the destination
	 */
	public ArrayList<Action> solveGame(Model model) throws RemoteException, CloneNotSupportedException {
		aStar as = new aStar();		
		ArrayList<Action> actions = as.runAstar((ModelMaze)model, ((ModelMaze)model).getStartPosition(), ((ModelMaze)model).getExitPosition());		
		if (!actions.isEmpty())
			return actions;		
		return null;
	}	
}