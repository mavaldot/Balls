package thread;

import controller.WindowController;
import javafx.application.Platform;
import model.Game;

public class GameThread extends Thread {

	private Game game;
	private WindowController wc;
	
	public GameThread(Game g, WindowController controller) {
		game = g;
		wc = controller;
	}
	
	public void run() {
		
		while (!game.checkWon()) {	
			// Do nothing
		}
		
		game.countBounces();
		
		Platform.runLater(() -> wc.stop());
		
	}
	
}

