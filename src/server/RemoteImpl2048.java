package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

import model.algorithms.Action;
import server.minimax.AIsolver.Player;
import server.minimax.Board;
import server.minimax.Direction;

import common.RemoteInt;
import common.customModel;

public class RemoteImpl2048 extends UnicastRemoteObject implements RemoteInt { 

	private static final long serialVersionUID = 1L;	
	
	protected RemoteImpl2048() throws RemoteException {	
		super(0);
	}
	/**
	* Implementation of getHint on the Server
	*/
	@Override
	public String getHint(customModel model) throws RemoteException, CloneNotSupportedException {	
		  	Board theBoard = new Board(model.getBoard(),model.getScore());			
			Map<String, Object> result =  server.minimax.AIsolver.alphabeta(theBoard, 7, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.USER);					
			if (result != null && (Direction)result.get("Direction") != null)
				return ((Direction)result.get("Direction")).getDescription();
			return null;
	}
	/**
	* Implementation of solveGame on the Server
	*/
	@Override
	public ArrayList<Action> solveGame(customModel model) throws RemoteException,
			CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;		
	}		
}