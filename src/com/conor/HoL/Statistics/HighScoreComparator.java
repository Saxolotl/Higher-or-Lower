package com.conor.HoL.Statistics;

import java.util.Comparator;

/**
 * Used for sorting my ArrayList of users by highest score for use in the leaderboards
 * @author conortyler
 *
 */

public class HighScoreComparator implements Comparator<User> {

	/**
	 * Compares two users' high scores and returns which one was a larger number
	 */
	@Override
	public int compare(User u1, User u2) {
		return Integer.compare(u2.getHighScore(), u1.getHighScore());
	}

}
