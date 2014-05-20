package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

import model.Model;
import model.Model2048;
import model.algorithms.Action;
import server.alphaBeta.Player;

import common.RemoteInt;

public class RemoteImpl2048 extends UnicastRemoteObject implements RemoteInt { 
	private static final long serialVersionUID = 1L;	
	protected RemoteImpl2048() throws RemoteException {	
		super(0);
	}

	
	@Override
	/*
	 * get Hint from Server using alphaBeta prunning algorithm
	 * @model the model from the client
	 * @return the String that indicate the best direction to move
	 */
	public String getHint(Model model) throws RemoteException, CloneNotSupportedException {		
		  Map<String, Object> result = alphaBeta.alphabeta((Model2048)model, 7, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.USER);	       
	      return (String)result.get("Direction");					
	}

	@Override
	/*
	 * get Full Solution from Server
	 * @param model
	 */
	public ArrayList<Action> solveGame(Model model) throws RemoteException,
			CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
		
	}
	
}