package model;

import java.util.Observable;

public class Model2048 extends Observable implements Model {
	
	int[][] mBoard;
	
	public Model2048() {
		
		mBoard = new int[4][4];
		
	}

	@Override
	public int[][] getData() {
		return mBoard;
	}

	@Override
	public void moveUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub

	}

}
