package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

import model.Ball;
import model.Game;
import thread.BallThread;
import thread.GameThread;

public class WindowController implements Initializable {
	
	@FXML
	AnchorPane ap;
	
	@FXML
	VBox vbox;
	
	@FXML
	Circle circle;
	
	@FXML
	Button button;
	
	private Game game;
	
	private ArrayList<Ball> balls;
	
	private Canvas canvas;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		game = new Game();
		
		balls = new ArrayList<Ball>();
		
		canvas = new Canvas(800, 600);
		
	}
	
	public void chooseFile(ActionEvent e) {
		
		ap.getChildren().clear();
		
		balls.clear();
		
		final FileChooser fileChooser = new FileChooser();
		
		Window stage = vbox.getScene().getWindow();
		
		File f = fileChooser.showOpenDialog(stage);

		game.generateBalls(f);
		
		
		beginGame();
	}
	
	public void beginGame() {
		
		balls = game.getBalls();
		
		new GameThread(game, this).start();
		
		for (Ball b : balls) {
			new BallThread(b).start();
		}
		
		final double MAXW = canvas.getWidth();
		final double MAXH = canvas.getHeight();
		
		vbox.getChildren().add(canvas);
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		canvas.setOnMouseClicked(e -> {
			
			double x = e.getX();
			double y = e.getY();
			
			for (Ball b : balls) {
				if (b.touches(x,y)) {
					b.stop();
				}
			}
			
			if (game.checkWon())
				enterScoreWindow();
			
		});
		
		new AnimationTimer() {

			@Override
			public void handle(long time) {
				
//				gc.setFill(Color.WHITE);
//				gc.fillRect(0, 0, 800, 600);
				
				for (Ball b : balls) {
					
					gc.setFill(Color.rgb(b.getR(), b.getG(), b.getB()));
					gc.fillOval(b.getX(), b.getY(), b.getDiameter(), b.getDiameter());
	
				}
			}
			
		}.start();
		
	}

	public void stop() {
		
		balls.clear();
		System.out.println("STOP WC");
		
		
	}
	
	
	public void enterScoreWindow() {
		
		ap.getChildren().clear();
		ap.getChildren().add(new Button("YO WASSUP BRUH"));
		
		ap.getChildren().add(new Button("HEYHEYHEYMAN"));
		
			
		
	}
	
	
}
