package model;

import java.util.Random;

public class Ball {
	
	private double diameter;
	private double x;
	private double y;
	private double vx;
	private double vy;
	private double wait;
	private int bounces;
	private boolean stopped;
	
	private int r;
	private int g;
	private int b;
	
	private final Random rand = new Random();
	
	public Ball(double diameter, double x, double y, double wait, double vx, double vy, int bounces, boolean stopped) {
		super();
		this.diameter = diameter;
		this.x = x;
		this.y = y; 
		this.wait = wait;
		this.vx = vx;
		this.vy = vy;
		this.bounces = bounces;
		this.stopped = stopped;
		
		r = rand.nextInt(255);
		g = rand.nextInt(255);
		b = rand.nextInt(255);
	}

	
	
	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public double getDiameter() {
		return diameter;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getVx() {
		return vx;
	}

	public double getVy() {
		return vy;
	}

	public double getWait() {
		return wait;
	}

	public int getBounces() {
		return bounces;
	}

	public boolean isStopped() {
		return stopped;
	}
	
	public void update(double minX, double minY, double maxX, double maxY) {
		
		double newMaxX = maxX - diameter;
		double newMaxY = maxY - diameter;
		
		if (!stopped) {
			
			if (x < minX || x > newMaxX)  {
				vx *= -1;
				bounces++;
			}
				

			if (y < minY || y > newMaxY) {
				vy *= -1;
				bounces++;
			}
				
			
			x += vx;
			y += vy;
		}
		
	}
	
	public boolean touches(double mx, double my) {
		
		boolean retVal = (mx >= x && mx <= (x + diameter) && my >= y && my <= (y + diameter));
		
		return retVal;
		
	}
	
	public void stop() {
		stopped = true;
	}
	
}
