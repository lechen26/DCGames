package controller;

import model.ModelMaze;
import view.View2048;
import view.ViewMaze;

public class MainMaze {

	public static void main(String[] args) {
		ModelMaze m = new ModelMaze(10,10);
		ViewMaze ui = new ViewMaze(10,10);
		Presenter p = new Presenter(m,ui);
		m.addObserver(p);
		ui.addObserver(p);			
		//Thread th = new Thread(ui);
		//th.start();
		ui.run();
		//m.initializeBoard();


	}

}