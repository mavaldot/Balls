package thread;

import application.WindowController;
import javafx.application.Platform;
import model.Ball;

public class BallThread extends Thread {

	private Ball ball;
	
	public BallThread(Ball ball) {
		this.ball = ball;
	}
	
	public void run() {
		
		while (ball != null) {
			
			ball.update(0, 0, 800, 600);	
			
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	
}
