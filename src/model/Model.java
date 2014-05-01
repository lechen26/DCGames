package model;

public interface Model  {

	public int[][] getData();
	public int getScore();
	public void initializeBoard();
	public void undoBoard(boolean b);
	public void saveGame();
	public void loadGame();
	public boolean moveUp(boolean quiet);
	public boolean moveDown(boolean quiet);
	public boolean moveLeft(boolean quiet);
	public boolean moveRight(boolean quiet);
	public void moveDiagonalRightUp(boolean b);
	public void moveDiagonalRightDown(boolean b);
	public void moveDiagonalLeftUp(boolean b);
	public void moveDiagonalLeftDown(boolean b);
}
