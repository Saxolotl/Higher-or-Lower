package com.conor.HoL.Statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ReadHandler {
	File[] users = null;
	ArrayList<User> userList = new ArrayList<User>();

	private String userName;
	private int correctGuesses;
	private int wrongGuesses;
	private int highScore;
	private int gamesPlayed;

	public ArrayList<User> readUsers(){
		Scanner fileScan = null;

		try{
			users = new File("Users/").listFiles();
		} catch(NullPointerException e){
			new File("Users/").mkdir();
		} finally {
			users = new File("Users/").listFiles();
		}


		for(File user : users){
			try {
				fileScan = new Scanner(user);
				userName = user.toString();
				userName = userName.replace("Users\\", "");
				userName = userName.replace(".txt", "");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			correctGuesses = fileScan.nextInt();
			wrongGuesses = fileScan.nextInt();
			highScore = fileScan.nextInt();
			gamesPlayed = fileScan.nextInt();

			userList.add(new User(userName, correctGuesses, wrongGuesses, highScore, gamesPlayed));
			fileScan.close();
		}


		for(User s : userList){
			System.out.println("User: " + s.getName());
			System.out.println("Correct Guesses: " + s.getCorrect());
			System.out.println("Wrong Guesses: " + s.getWrong());
			System.out.println("High Score: " + s.getHighScore());
			System.out.println("Games Played: " + s.getGamesPlayed());
			System.out.println("");
		}

		return userList;
	}

	public boolean removeUser(String userName){
		users = new File("Users/").listFiles();
		System.out.println(Arrays.toString(users));
		for(File user : users){
			if(user.toString().contains(userName) && user.exists()){
				System.out.println(user.toPath());
				try {
					Files.delete(user.toPath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
