package controller;

import model.Model;
import model.Model2048;
import view.View;
import view.View2048;

public class Main2048 {

	public static void main(String[] args) {
		
		Model2048 m = new Model2048();
		View2048 ui = new View2048();
		Presenter p = new Presenter(m,ui);
		m.displayBoard();
		m.addObserver(p);
		ui.addObserver(p);
		
		ui.run();
	}

}
