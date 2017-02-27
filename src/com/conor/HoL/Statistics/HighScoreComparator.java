package com.conor.HoL.Statistics;

import java.util.Comparator;

public class HighScoreComparator implements Comparator<User> {

	@Override
	public int compare(User u1, User u2) {
		System.out.println("Sorted :" + Integer.compare(u2.getHighScore(), u1.getHighScore()));
		return Integer.compare(u2.getHighScore(), u1.getHighScore());
	}

}
