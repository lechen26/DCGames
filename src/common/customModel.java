package common;
public class customModel{

	int[][] board;
	int score;
	int free;
	boolean over;
	boolean won;
	
	public customModel(int [][] b, int f, int s, boolean gameWon, boolean gameOver) {
		this.board=b;
		this.score=s;
		this.over=gameOver;
		this.won=gameWon;
		this.free=f;
	}
	
	public int getScore(){
		return score;		
	}
	
	public boolean isGameOver() {
		return over;
	}

	public boolean isGameWon() {
		return won;
	}
	
	public int getFree() {
		return free;
	}
	
}
