package model;

import java.io.Serializable;

public class State implements Serializable {	
	private double g = 0;
	private double f = 0;
	protected Object stateName;
	int x;
	int y;

	public State() {
		this.x = 0;
		this.y = 0;
		this.stateName = null;
	}
	public State(int x,int y,Object stateName) {
		this.x=x;
		this.y=y;
		this.stateName=stateName;
	}
	
	public State(Object stateName) {
		this.stateName = stateName;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public State getState() {
		return this;
	}

	public Object getStateName() {
		return this.stateName;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
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
