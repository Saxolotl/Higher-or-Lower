package com.conor.HoL.Statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class is used for handling the reading of files, such as creating a list of users found in the Users/ directory and for deleting files
 * @author Conor Tyler
 *
 */
public class ReadHandler {
	File[] users = null;
	ArrayList<User> userList = new ArrayList<User>();

	private String userName;
	private int correctGuesses;
	private int wrongGuesses;
	private int highScore;
	private int gamesPlayed;
	private int balance;
	private String password;

	/**
	 * Reads in each file from the Users/ directory and creates a new User object from the values read by the scanner. If the directory does
	 * not exist then the directory is created to prevent errors from occurring.
	 * @return Returns ArrayList of Users for use in user selection / statistics.
	 */
	public ArrayList<User> readUsers(){
		Scanner fileScan = null;
		
		users = new File("Users/").listFiles(new fileFilter());

		if(users == null){
			new File("Users/").mkdir();
			users = new File("Users/").listFiles(new fileFilter());
		}


		for(File user : users){
			try {
				fileScan = new Scanner(user);
				userName = user.toString();
				//Check if directory uses a forward slash in the case of running on Mac or Linux
				userName = userName.replace(userName.contains("Users/") ? "Users/" : "Users\\", "");
				userName = userName.replace(".txt", "");
				
			
				correctGuesses = fileScan.nextInt();
				wrongGuesses = fileScan.nextInt();
				highScore = fileScan.nextInt();
				gamesPlayed = fileScan.nextInt();
				balance = fileScan.nextInt();
				password = fileScan.next();
				
				userList.add(new User(userName, correctGuesses, wrongGuesses, highScore, gamesPlayed, balance, password));
				fileScan.close();
			} catch (FileNotFoundException e) {
				fileScan.close();
				JOptionPane.showMessageDialog(null, "Could not find the file " + user.toString() + ". Does it exist?", "File Not Found", JOptionPane.ERROR_MESSAGE);
			} catch(InputMismatchException e){
				fileScan.close();
				JOptionPane.showMessageDialog(null, "File: " + user.toString() + " is corrupt. Deleting...", "File Not Found", JOptionPane.ERROR_MESSAGE);
				removeUser(userName);
			}
		}

		return userList;
	}

	/**
	 * Handles deleting the file for a specified user by searching through the list of files in the Users/ directory
	 * @param userName User to delete
	 * @return If the deletion of the User was successful
	 */
	public boolean removeUser(String userName){
		users = new File("Users/").listFiles();
		for(File user : users){
			if(user.toString().contains(userName) && user.exists()){
				try {
					Files.delete(user.toPath());
					return true;
				} catch (IOException e) {
					JOptionPane.showMessageDialog(new JFrame(), "Failed to delete the file: " + user.toPath().toString(), "IOException", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}
		return false;
	}
	
	class fileFilter implements FilenameFilter{

		@Override
		public boolean accept(File dir, String name) {
			return name.toLowerCase().endsWith("txt");
		}
		
	}
}
