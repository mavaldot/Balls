package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Game {

	private ArrayList<Ball> balls;
	private int totalBounces;
	private int level;
	
	private ArrayList<Score> s0;
	private ArrayList<Score> s1;
	private ArrayList<Score> s2;
	
	public final static int MAXSCORES = 10;
	
	public Game() {
		balls = new ArrayList<Ball>();
		totalBounces = 0;
		level = 0;
		
		s0 = new ArrayList<Score>();
		s1 = new ArrayList<Score>();
		s2 = new ArrayList<Score>();
		
		loadAllScores();
	}
	
	public int getNumBalls() {
		
		return balls.size();
		
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setBalls(ArrayList<Ball> b) {
		balls = b;
	}
	
	public ArrayList<Ball> getBalls() {
		return balls;
	}
	
	public void generateBalls(File f) throws InvalidFileException, InvalidLevelException {
		
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			
			br.readLine();
			
			try {
				int lvl = Integer.parseInt(br.readLine());
				
				if (lvl < 0 || lvl > 2)
					throw new InvalidLevelException("Level must be between 0 and 2");
				else
					level = lvl;
				
			} 
			catch (Exception e) {
				throw new InvalidLevelException("Invalid level");
			}
			
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
			}
			
		} catch (IOException e) {
			throw new InvalidFileException("There was a problem reading the file");
		}
		
	}
	
	public void countBounces() {
		
		for (Ball ball : balls) {
			totalBounces += ball.getBounces();
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
	
	public void saveNewScore(String name) {	
		
		Score newScore = new Score(totalBounces, name);
		
		boolean success;
		
		switch (level) {
		
		case 0:
			success = setNewScore(newScore, s0); break;
		case 1:
			success = setNewScore(newScore, s1); break;
		case 2:
			success = setNewScore(newScore, s2); break;
		default: 
			success = false; break;
		
		
		}
		
		Comparator<Score> myComparator = new Comparator<Score>() {

			@Override
			public int compare(Score s1, Score s2) {
				
				return s1.compareTo(s2);
			}
			
		};
		
		if (success) {
			
			s0.sort(myComparator);
			s1.sort(myComparator);
			s2.sort(myComparator);
			
			saveAllScores();
		}


	}
	
	public void saveAllScores() {
		
		File f0 = new File("0.sav");
		File f1 = new File("1.sav");
		File f2 = new File("2.sav");
		
		try {
			ObjectOutputStream oos0 = new ObjectOutputStream(new FileOutputStream(f0));
			oos0.writeObject(s0);
			oos0.close();
			
			ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(f1));
			oos1.writeObject(s1);
			oos1.close();
			
			ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(f2));
			oos2.writeObject(s2);
			oos2.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void loadAllScores() {
		
		File f0 = new File("0.sav");
		File f1 = new File("1.sav");
		File f2 = new File("2.sav");
		
		try {
			s0 = (ArrayList<Score>) (new ObjectInputStream(new FileInputStream(f0))).readObject();
			s1 = (ArrayList<Score>) (new ObjectInputStream(new FileInputStream(f1))).readObject();
			s2 = (ArrayList<Score>) (new ObjectInputStream(new FileInputStream(f2))).readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean setNewScore(Score newScore, ArrayList<Score> scores) {
		
		int size = scores.size();
		
		if (size == MAXSCORES) {
			
			if (scores.get(MAXSCORES - 1).compareTo(newScore) < 0)
				scores.remove(MAXSCORES - 1);
			else
				return false;
			
		}
		
		scores.add(newScore);
		
		return true;
		
	}
	
	public String getScores(int code) {
		
		StringBuilder sb = new StringBuilder();
		
		switch (code) {
		
		case 0:
			s0.forEach(s -> sb.append(s.asString() + "\n"));
			break;
		case 1: 
			s1.forEach(s -> sb.append(s.asString() + "\n"));
			break;
		case 2:
			s2.forEach(s -> sb.append(s.asString() + "\n"));
			break;
		
		}
		
		return sb.toString();
		
	}
	
	
}
