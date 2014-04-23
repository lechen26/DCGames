package model;

public interface Model  {

	public int[][] getData();
	public void initializeBoard();
	public void undoBoard();
	public void moveUp();
	public void moveDown();
	public void moveLeft();
	public void moveRight();
}
