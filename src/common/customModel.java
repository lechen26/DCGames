package common;

import java.awt.Point;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Custom Model to be sent through RMI incovations
 */
public class customModel implements Serializable{

	private static final long serialVersionUID = 1L;
	int[][] board;
	int score;
	Point start,end;
	int free;
	boolean over;
	boolean won;
	
	/**
	 * Constructure for creating customModel object (2048)
	 * @param b board array
	 * @param s score
	 */
	public customModel(int [][] b, int s) {
		this.board=b;
		this.score=s;
	}
	
	/**
	 *  Constructure for creating customModel object (Maze)
	 * @param b board array
	 * @param s score
	 * @param startPos start point
	 * @param exitPos end point
	 */
	public customModel(int [][]b, int s, Point startPos, Point exitPos) {
		this.board=b;
		this.score=s;
		this.start=startPos;
		this.end=exitPos;
	}
	
	/**
	 * return the current score
	 * @return score
	 */
	public int getScore(){
		return this.score;		
	}

	/**
	 * return the current Board array
	 * @return copy of board array
	 */
	public int[][] getBoard() {
		return copyBoard(this.board);
	}
	
	/**
	 * return start Point
	 * @return point
	 */
	public Point getStartPosition() {
		return this.start;
	}
	
	/**
	 * return exit point
	 * @return point
	 */
	public Point getEndPosition() {
		return this.end;
	}
	
	
	/**
	 * toString function
	 * @return the string defined the module (board + score)
	 */
	public String toString() {		
		String str = Arrays.deepToString(board);			
		return "Array is " +  str + " and score is " + score;
	}
	
	/**
	 * copy two dimensional array 
	 * @param source the array we want to copy
	 * @return the copied array
	 */
	public static int[][] copyBoard(int[][] source) {
	    int[][] copy = new int[source.length][];
	    for (int i = 0; i < source.length; i++) {
	        copy[i] = Arrays.copyOf(source[i],source[i].length);
	    }
	    return copy;
	}

}
