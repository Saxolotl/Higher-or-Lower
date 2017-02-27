package com.conor.HoL.Statistics;

public class User{
	private String name;
	private int correctGuesses;
	private int wrongGuesses;
	private int highScore;
	private int gamesPlayed;
	
	public User(String name, int correctGuesses, int wrongGuesses, int highScore, int gamesPlayed){
		this.name = name;
		System.out.println(name);
		this.correctGuesses = correctGuesses;
		this.wrongGuesses = wrongGuesses;
		this.highScore = highScore;
		this.gamesPlayed = gamesPlayed;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getCorrect(){
		System.out.println(correctGuesses);
		return correctGuesses;
	}
	
	public int getWrong(){
		return wrongGuesses;
	}
	
	public void incrementGuesses(boolean correct){
		if(!correct){
			wrongGuesses++;
			System.out.println("wrong " + wrongGuesses);
		} else if(correct){
			correctGuesses++;
			System.out.println("correct " + correctGuesses );
		}
	}
	
	public int getHighScore(){
		return highScore;
	}
	
	public void setHighScore(int highScore){
		this.highScore = highScore;
	}
	
	public int getGamesPlayed(){
		return gamesPlayed;
	}
	
	public void incGamesPlayed(){
		gamesPlayed++;
	}
	
}
