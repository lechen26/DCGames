package controller;

import model.Model2048;
import view.View2048;

public class Main2048 {

	public static void main(String[] args) {
		Model2048 m = new Model2048(4,4);
		View2048 ui = new View2048(4,4);
		Presenter p = new Presenter(m,ui);
		m.addObserver(p);
		ui.addObserver(p);			
		//Thread th = new Thread(ui);
		//th.start();
		ui.run();
		//m.initializeBoard();


	}

}
