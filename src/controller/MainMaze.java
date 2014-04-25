package controller;

import view.ViewMaze;
import model.ModelMaze;

public class MainMaze {

	public static void main(String[] args) {
		ModelMaze m = new ModelMaze();
		ViewMaze ui = new ViewMaze();
		Presenter p = new Presenter(m,ui);
		m.addObserver(p);
		ui.addObserver(p);			
		//Thread th = new Thread(ui);
		//th.start();
		ui.run();
		//m.initializeBoard();
	

	}

}
