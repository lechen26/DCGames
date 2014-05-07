package model;

public interface Model  {

	public int[][] getBoard();
	public int getScore();
	public void initializeBoard();
	public void undoBoard();
	public void saveGame();
	public void loadGame();
	public void doubleWinNumber();
	public boolean moveUp(boolean quiet);
	public boolean moveDown(boolean quiet);
	public boolean moveLeft(boolean quiet);
	public boolean moveRight(boolean quiet);
	public void moveDiagonalRightUp();
	public void moveDiagonalRightDown();
	public void moveDiagonalLeftUp();
	public void moveDiagonalLeftDown();
}
