package com.conor.HoL.GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.conor.HoL.Statistics.User;

public class Statistics {
	private JFrame statsFrame;
	private JPanel statsPanel, leaderPanel;
	private JTabbedPane statsPane;
	private JLabel correctLbl, wrongLbl, highScoreLbl, gamesPlayedLbl;
	private JComboBox<String> userCombo;
	
	private ArrayList<User> userList;
	private User currUser = null;
	private int index = 0;
	
	public void createForm(){
		statsFrame = new JFrame("Statistics");
		statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		statsFrame.setSize(1024, 768);
		UITools.centreFrame(statsFrame);
		
		statsPanel = new JPanel(new GridLayout(5,1));
	}
	
	public void addFields(){
		correctLbl = new JLabel("Correct");
		wrongLbl = new JLabel("Wrong");
		highScoreLbl = new JLabel("High Score");
		gamesPlayedLbl = new JLabel("Games Played");
		
		statsPane = new JTabbedPane();
		
		statsPane.addTab("Statistics", statsPanel);
		statsPane.addTab("Leaderboards", leaderPanel);
		
		statsPanel.add(correctLbl);
		statsPanel.add(wrongLbl);
		statsPanel.add(highScoreLbl);
		statsPanel.add(gamesPlayedLbl);
	}
	
	public void addButtons(){
		userCombo = new JComboBox<String>();
		userCombo.setForeground(new Color(255,255,255));
		userCombo.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent itEvent){
				System.out.println(userCombo.getSelectedIndex());
				if(userCombo.getSelectedIndex() != -1){
					currUser = userList.get(userCombo.getSelectedIndex());
					
					correctLbl.setText("Correct Guesses: " + Integer.toString(currUser.getCorrect()));
					wrongLbl.setText("Wrong Guesses: " + Integer.toString(currUser.getWrong()));
					highScoreLbl.setText("High Score: " + Integer.toString(currUser.getHighScore()));
					gamesPlayedLbl.setText("Games Played: " + Integer.toString(currUser.getGamesPlayed()));
				}
			}
		});
		
		statsPanel.add(userCombo);
	}
	
	public void populateCombo(){
		for(User u : userList){
			userCombo.addItem(u.getName());
		}
	}
	
	public Statistics(ArrayList<User> userList){
		this.userList = userList;
		
		createForm();
		addButtons();
		addFields();
		populateCombo();
		
		statsFrame.add(statsPane);
		statsFrame.setVisible(true);
	}
}
