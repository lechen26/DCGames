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
	public String getHint(Model model) throws RemoteException, CloneNotSupportedException {				
			aStar as = new aStar();		
			ArrayList<Action> actions = as.runAstar((ModelMaze)model, ((ModelMaze)model).getCurrentPosition(), ((ModelMaze)model).getExitPosition());
			if (!actions.isEmpty())
				return (actions.get(0)).getName();												
		return  null;
	}

	@Override
	public ArrayList<Action> solveGame(Model model) throws RemoteException, CloneNotSupportedException {
		aStar as = new aStar();		
		ArrayList<Action> actions = as.runAstar((ModelMaze)model, ((ModelMaze)model).getStartPosition(), ((ModelMaze)model).getExitPosition());		
		if (!actions.isEmpty())
			return actions;		
		return null;
	}	
}