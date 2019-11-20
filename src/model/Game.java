package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Game {

	private ArrayList<Ball> balls;
	private int totalBounces;
	
	
	public Game() {
		balls = new ArrayList<Ball>();
		totalBounces = 0;
	}
	
	public int getNumBalls() {
		
		return balls.size();
		
	}
	
	public void setBalls(ArrayList<Ball> b	 	) {
		balls = b;
	}
	
	public ArrayList<Ball> getBalls() {
		return balls;
	}
	
	public int generateBalls(File f) {
		
		BufferedReader br;
		
		int status = 0;
		
		try {
			br = new BufferedReader(new FileReader(f));
			
			br.readLine();
			int level = Integer.parseInt(br.readLine());
			br.readLine();
			
			String line = null;
			
			while (( line = br.readLine()) != null) {
				
				String[] data = line.split("\t");
				
				double diameter = Double.parseDouble(data[0]);
				double x = Double.parseDouble(data[1]);
				double y = Double.parseDouble(data[2]);
				double wait = Double.parseDouble(data[3]);
				double vx = Double.parseDouble(data[4]);
				double vy = Double.parseDouble(data[5]);
				int bounces = Integer.parseInt(data[6]);
				boolean stopped = Boolean.parseBoolean(data[7]);
				
				balls.add(new Ball(diameter, x, y, wait, vx, vy, bounces, stopped));
			
				System.out.println(balls.get(0).getDiameter());
			}
			
		} catch (FileNotFoundException e1) {
			
			status = 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			status = 2;
		}
		
		return status;
		
	}
	
	public void countBounces() {
		
		totalBounces = 0;
		
		for (Ball ball : balls) {
			totalBounces = ball.getBounces();
		}
	}
	
	public boolean checkWon() {
		
		boolean won = true;
		
		for (int i = 0; i < balls.size() && won; i++) {
			
			if (!balls.get(i).isStopped())
				won = false;
			
		}
		
		return won;
	}
	
	
	
}
