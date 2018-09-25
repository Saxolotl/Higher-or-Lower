package com.conor.HoL.Statistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Handles writing user statistics to a file 
 * @author Conor Tyler
 *
 */
public class WriteHandler {
	private File userFile;
	private User user;
	BufferedWriter dataWrite = null;

	private int correctGuesses;
	private int wrongGuesses;
	private int highScore;
	private int gamesPlayed;
	private int balance;
	private String password;

	/**
	 * This constructor overrides the default constructor in Java is called when creating a new instance of WriteHandler
	 * @param user This parameter is used to set the {@link user} for use in writing files
	 */
	public WriteHandler(User user){
		this.user = user;
	}

	/**
	 * Creates the Users/ directory if it does not exist and then creates a new file in the Users/ directory with the name specified by the user
	 */
	public boolean createFile(){

		try {
			new File("Users").mkdir();
			userFile = new File("Users/" + user.getName() + ".txt");
			userFile.createNewFile();
			
			if(userFile.exists()){
				return true;
			} else{
				return false;
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), "An error occured while creating the file. Do you have permission?", "File Creation Error", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	/**
	 * Sets statistics gathered from the User to variables in the class for use in writing.
	 */
	public void getStats(){
		correctGuesses = user.getCorrect();
		wrongGuesses = user.getWrong();
		highScore = user.getHighScore();
		gamesPlayed = user.getGamesPlayed();
		balance = user.getBalance();
		password = user.getPassword();
	}

	/**
	 * Creates a new File object which sets the file to the name of the specified user. 
	 * The statistics of the user are gathered and then written to the file using the writeData method. 
	 */
	public boolean writeChanges(){
		try {
			File temp = new File("Users/" + user.getName() + ".txt");
			dataWrite = new BufferedWriter(new FileWriter(temp));
			getStats();

			writeData(correctGuesses);
			writeData(wrongGuesses);
			writeData(highScore);
			writeData(gamesPlayed);
			writeData(balance);
			writeData(password);
			
			dataWrite.close();
			
			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Could not write to the file", "IOException", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	/**
	 * Takes in data which is then used to write to the current line in a file and then makes a new line. Converts input to String from Integer
	 * as BufferedWriters have issues writing integers to files.
	 * Used to easily write data and then create a new line rather than calling two lines of code or using \n
	 * @param data Used to set the value for the current line in writing the file
	 * @throws IOException 
	 */
	private void writeData(int data) throws IOException{
			dataWrite.write(Integer.toString(data));
			dataWrite.newLine();
	}
	
	/**
	 * Takes in data which is then used to write to the current line in a file and then makes a new line.
	 * Used to easily write data and then create a new line rather than calling two lines of code or using \n
	 * @param data Used to set the value for the current line in writing the file
	 * @throws IOException 
	 */
	private void writeData(String data) throws IOException{
		dataWrite.write(data);

	}

}
