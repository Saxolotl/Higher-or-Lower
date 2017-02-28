package com.conor.HoL.Statistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
			userFile.createNewFile();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), "An error occured while creating the file. Do you have permission?", "File Creation Error", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(new JFrame(), "An error occured while initialising the file", "File Initialise Error", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(new JFrame(), "An error occured while closing the file. Does the file exist?", "File Close Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	private void writeData(int data){
		try {
			dataWrite.write(Integer.toString(data));
			dataWrite.newLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), "An error occured while writing the file. Do you have permission?", "File Write Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
