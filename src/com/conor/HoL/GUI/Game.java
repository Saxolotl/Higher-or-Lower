package com.conor.HoL.GUI;

import com.conor.HoL.Game.Logic;
import com.conor.HoL.Statistics.User;
import com.conor.HoL.Statistics.WriteHandler;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Game {

	private JFrame gameFrame;
	private JPanel gamePanel, btnPanel, imagePanel;
	private JButton high, low;
	private JLabel logoPicture, scoreLbl;
	private ImageIcon logo;
	private User currUser;
	private boolean result;
	
	Logic hol = new Logic();
	WriteHandler write;
	int score = 0;

	public Game(User currUser){
		this.currUser = currUser;
		
		gameFrame = new JFrame("Higher or Lower");
		gameFrame.setSize(1024,768);
		gameFrame.setVisible(true);
		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		UITools.centreFrame(gameFrame);

		gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(1,2));
		gamePanel.setBorder(new EmptyBorder(15,0,0,0));
		

		hol.generateCards();
		addImage();

		scoreLbl = new JLabel("Score: 0");
		scoreLbl.setHorizontalAlignment(JLabel.CENTER);
		scoreLbl.setForeground(new Color(255,255,255));
		scoreLbl.setFont(new Font("Sans Serif", Font.PLAIN, 30));
		
		gamePanel.add(logoPicture);

		btnPanel = new JPanel(new GridLayout(3, 1, 0, 25));
		btnPanel.setBorder(new EmptyBorder(150,25,150,25));

		high = new JButton("Higher");
		high.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				click(scoreLbl, e);
			}
		});

		low = new JButton("Lower");
		low.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				click(scoreLbl, e);
			}
		});

		btnPanel.add(scoreLbl);
		btnPanel.add(high);
		btnPanel.add(low);

		gamePanel.add(btnPanel);
		



		gameFrame.add(gamePanel);
	}
	
	public void addImage(){
		logo = new ImageIcon(new ImageIcon("res/cards/" + hol.getCardImage()).getImage().getScaledInstance(400, 581, Image.SCALE_SMOOTH));
		System.out.println(logo.toString());
		logoPicture = new JLabel(logo);
	}
	
	public void changeImage(){
		logo = new ImageIcon(new ImageIcon("res/cards/" + hol.getCardImage()).getImage().getScaledInstance(400, 581, Image.SCALE_SMOOTH));
		logoPicture.setIcon(logo);
	}

	private void click(JLabel scoreLbl, ActionEvent e){
		result = hol.getResult(e.getActionCommand());
		if(result){
			hol.generateCards();
			changeImage();
			score++;
			scoreLbl.setText("Score : " + score);
		}
		else{
			gameOver();
			scoreLbl.setText("Your score was : " + score);
			score = 0;
		}
		
		currUser.incrementGuesses(result);
	}
	
	private void gameOver(){
		System.out.println(score + " and " + currUser.getHighScore());
		if(score > currUser.getHighScore()){
			currUser.setHighScore(score);
		}
		currUser.incGamesPlayed();
		write = new WriteHandler(currUser);
		write.writeChanges();
	}

}
