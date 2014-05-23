package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Model;
import model.Model2048;
import model.algorithms.Action;
import server.alphaBeta.Player;

import common.RemoteInt;

public class RemoteImpl2048 extends UnicastRemoteObject implements RemoteInt { 

	private static final long serialVersionUID = 1L;
	HashMap<Model,String> proxy = new HashMap<Model,String>(2);
	
	protected RemoteImpl2048() throws RemoteException {	
		super(0);
	}

	@Override
	public String getHint(Model model) throws RemoteException, CloneNotSupportedException {		
		  /*String proxyStr = checkLocal(model);		  
		  if (proxyStr != null) {
			  System.out.println("Retrieve from local hash");
			  return proxyStr;
		  }*/
		  Map<String, Object> result = alphaBeta.alphabeta((Model2048)model, 7, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.USER);		  
		  //proxy.put(model, (String)result.get("Direction"));
	      return (String)result.get("Direction");					
	}

	@Override
	public ArrayList<Action> solveGame(Model model) throws RemoteException,
			CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;		
	}
		
	private String checkLocal(Model model) {
		System.out.println("Model code is " + model.hashCode());		
		if (proxy.containsKey(model)) {
			System.out.println("already checked that module");
			return proxy.get(model);
		}
		else
			System.out.println("never saw this object in my life");
		return null;
	}
	
}