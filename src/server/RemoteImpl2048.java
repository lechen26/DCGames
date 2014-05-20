package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

import server.alphaBeta.Player;
import model.Model;
import model.Model2048;
import model.RemoteInt;
import model.algorithms.Action;
import server.alphaBeta;

public class RemoteImpl2048 extends UnicastRemoteObject implements RemoteInt { 

	private static final long serialVersionUID = 1L;
	
	protected RemoteImpl2048() throws RemoteException {	
		super(0);
	}

	@Override
	public String getHint(Model model) throws RemoteException, CloneNotSupportedException {		
		  Map<String, Object> result = alphaBeta.alphabeta((Model2048)model, 7, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.USER);	       
	      return (String)result.get("Direction");					
	}

	@Override
	public ArrayList<Action> solveGame(Model model) throws RemoteException,
			CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
		
	}
	
}