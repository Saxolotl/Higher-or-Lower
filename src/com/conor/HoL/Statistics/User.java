package com.conor.HoL.Statistics;

/**
 * This class is used to create an Object which is used for storing User statistics.
 * @author Conor Tyler
 *
 */
public class User{
	private String name;
	private String password;
	private int correctGuesses;
	private int wrongGuesses;
	private int highScore;
	private int gamesPlayed;
	private int balance;
	
	/**
	 * Used to create a new Object which stores all the statistics for the current user
	 * @param name Name of the User
	 * @param correctGuesses Amount of guesses which the user has gotten correct
	 * @param wrongGuesses Amount of guesses which the user has gotten wrong
	 * @param highScore The User's highest score
	 * @param gamesPlayed Amount of games the User has played
	 * @param balance Amount of credits the User has
	 * @param password The password of the User
	 */
	public User(String name, int correctGuesses, int wrongGuesses, int highScore, int gamesPlayed, int balance, String password){
		this.name = name;
		this.correctGuesses = correctGuesses;
		this.wrongGuesses = wrongGuesses;
		this.highScore = highScore;
		this.gamesPlayed = gamesPlayed;
		this.balance = balance;
		this.password = password;
	}
	
	/**
	 * 
	 * @return Returns name of the user for use in other classes.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * @return Returns the amount of correct guesses of the user for use in other classes.
	 */
	public int getCorrect(){
		return correctGuesses;
	}
	
	/**
	 * 
	 * @return Returns the amount of wrong guesses of the user for use in other classes.
	 */
	public int getWrong(){
		return wrongGuesses;
	}
	
	/**
	 * Increments correct or wrong guesses depending on the parameter
	 * @param correct Result of if the user was correct or incorrect. Used to determine which variable to increment
	 */
	public void incrementGuesses(boolean correct){
		if(!correct){
			wrongGuesses++;
			System.out.println("wrong " + wrongGuesses);
		} else if(correct){
			correctGuesses++;
			System.out.println("correct " + correctGuesses );
		}
	}
	
	/**
	 * 
	 * @return Returns the high score of the User for use in other classes
	 */
	public int getHighScore(){
		return highScore;
	}

	/**
	 * 
	 * @param highScore Used to set the value of highScore in the class to the value passed as a parameter
	 */
	public void setHighScore(int highScore){
		this.highScore = highScore;
	}
	
	/**
	 * 
	 * @return Returns the User's password for use in other classes.
	 */
	public String getPassword(){
		return password;
	}
	
	/**
	 * 
	 * @param password Used to set the value of Password in the class to the value passed as a parameter
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	/**
	 * 
	 * @param balance Used to set the value of Balance in the class to the value passed as a parameter
	 */
	public void setBalance(int balance){
		this.balance = balance;
	}
	
	/**
	 * 
	 * @return Returns the balance for use in other classes.
	 */
	public int getBalance(){
		return balance;
	}

	/**
	 * 
	 * @return Returns the amount of games played for use in other classes
	 */
	public int getGamesPlayed(){
		return gamesPlayed;
	}
	
	/**
	 * Increments the games played for the User by one.
	 */
	public void incGamesPlayed(){
		gamesPlayed++;
	}
	
}
