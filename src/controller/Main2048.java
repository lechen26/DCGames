package controller;

import model.Model2048;
import view.View2048;

/**
* Main for 2048 Game
*/

public class Main2048 {

	public static void main(String[] args) {
		Model2048 m = new Model2048(4,4,2048);
		View2048 ui = new View2048(4,4);
		Presenter p = new Presenter(m,ui);
		m.addObserver(p);
		ui.addObserver(p);				
		ui.run();
	}
}
