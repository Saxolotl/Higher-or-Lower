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
	private JPanel statsPanel;
	private JTabbedPane statsPane;
	private JLabel correctLbl, wrongLbl, highScoreLbl, gamesPlayedLbl;
	private JComboBox<String> userCombo;
	
	private ArrayList<User> userList;
	private User currUser = null;
	
	public void createForm(){
		statsFrame = new JFrame("Statistics");
		statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		statsFrame.setSize(1024, 768);
		UITools.centreFrame(statsFrame);
		
		statsPanel = new JPanel(new GridLayout(1,0));
	}
	
	public void addFields(){
		
	}
	
	public void addButtons(){
		userCombo = new JComboBox<String>();
		userCombo.setForeground(new Color(255,255,255));
		userCombo.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent itEvent){
				System.out.println(userCombo.getSelectedIndex());
				if(userCombo.getSelectedIndex() != -1){
					currUser = userList.get(userCombo.getSelectedIndex());
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
		populateCombo();
		
		statsFrame.add(statsPanel);
		statsFrame.setVisible(true);
	}
}
