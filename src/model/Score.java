package model;

import java.io.Serializable;

public class Score implements Comparable<Score>, Serializable {

	private int points;
	private String name;
	
	public Score(int points, String name) {
		super();
		this.points = points;
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public String getName() {
		return name;
	}
	
	public String asString() {
		String str = points + "\t" + name;
		return str;
	}

	@Override
	public int compareTo(Score s) {
		return points - s.points;
	}

	
	
	
}
