package view;

public interface View {

	public void displayData(int[][] data);
	public void displayScore(int scr);
	public int getUserCommand();
	public void gameOver();
	public void gameWon();
	public void undoEnd();
	public void saveGame();
}
