package model;

public interface Model  {

	public int[][] getData();
	public int getCurrentScore();
	public void initializeBoard();
	public void undoBoard();
	public boolean moveUp(boolean quiet);
	public boolean moveDown(boolean quiet);
	public boolean moveLeft(boolean quiet);
	public boolean moveRight(boolean quiet);
}
