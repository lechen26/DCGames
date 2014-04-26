package model;

import java.util.Observable;

public class ModelMaze extends Observable implements Model {
	
	@Override
	public int[][] getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean moveUp(boolean quiet) {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean moveDown(boolean quiet) {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean moveLeft(boolean quiet) {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean moveRight(boolean quiet) {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public void initializeBoard() {

		// TODO Auto-generated method stub		
	}

	@Override
	public void undoBoard() {
		System.out.println("sd");		
	}
}
