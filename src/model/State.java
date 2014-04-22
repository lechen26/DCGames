package model;

import java.awt.Point;
import java.io.Serializable;

public class State implements Serializable {	
	//private double g = 0;
	//private double f = 0;
	protected Object stateName;
	int value;
	
	public State() {		
		this.stateName = null;
		this.value=0;
	}
	
	public State(Object stateName,int value) {
		this.stateName=stateName;
		this.value=value;
	}
	
	public State(Object stateName) {
		this.stateName = stateName;
	}

	public int getX() {
		return ((Point) getStateName()).x;	
	}

	public int getY() {
		return ((Point) getStateName()).y;
	}

	public State getState() {
		return this;
	}

	public Object getStateName() {
		return this.stateName;
	}

	public void setStateName(String name) {
		this.stateName = name;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof State) {
			State dst = (State) o;
			if (this.getStateName().equals(dst.getStateName()))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getStateName().hashCode();
	}

	public void setStateName(Object stateName) {
		this.stateName = stateName;
	}

	@Override
	public String toString() {
		return "Class State: " + this.toString();
	}

}
