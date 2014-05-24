package controller;

import model.ModelMaze;
import view.ViewMaze;

/**
* Main for the Maze app
*/

public class MainMaze {

	public static void main(String[] args) {
		ModelMaze m = new ModelMaze(16,16);
		ViewMaze ui = new ViewMaze(16,16);
		Presenter p = new Presenter(m,ui);
		m.addObserver(p);
		ui.addObserver(p);			
		ui.run();
		}
}