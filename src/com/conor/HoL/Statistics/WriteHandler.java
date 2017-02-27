package com.conor.HoL.Statistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteHandler {
	private File userFile;
	private User user;
	FileWriter write = null;
	BufferedWriter dataWrite = null;
	
	private int correctGuesses;
	private int wrongGuesses;
	private int highScore;
	private int gamesPlayed;
	
	public WriteHandler(User user){
		this.user = user;
		System.out.println(user);
	}
	
	public void createFile(){
		
		try {
			new File("Users").mkdir();
			userFile = new File("Users/" + user.getName() + ".txt");
			boolean fileStatus = userFile.createNewFile();
			
			if(fileStatus){
				System.out.println("ayy file made");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getStats(){
		correctGuesses = user.getCorrect();
		wrongGuesses = user.getWrong();
		highScore = user.getHighScore();
		gamesPlayed = user.getGamesPlayed();
	}
	
	public void writeChanges(){
		try {
			File temp = new File("Users/" + user.getName() + ".txt");
			System.out.println(temp.toString());
			write = new FileWriter(temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		dataWrite = new BufferedWriter(write);
		getStats();
		
		writeData(correctGuesses);
		writeData(wrongGuesses);
		writeData(highScore);
		writeData(gamesPlayed);
		
		try {
			dataWrite.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void writeData(int data){
		try {
			dataWrite.write(Integer.toString(data));
			dataWrite.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
