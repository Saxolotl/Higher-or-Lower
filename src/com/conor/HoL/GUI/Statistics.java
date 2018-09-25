package com.conor.HoL.GUI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.conor.HoL.Statistics.HighScoreComparator;
import com.conor.HoL.Statistics.User;

/**
 * 
 * @author conortyler
 *
 */
public class Statistics {
	private JFrame frmStats;
	private JPanel pnlStats, pnlLeader;
	private JTabbedPane paneStats;
	private JLabel lblCorrect, lblWrong, lblHighScore, lblGamesPlayed, lblBalance;
	private JComboBox<String> userCombo;

	private ArrayList<User> listUser;
	private User currUser = null;

	GridBagConstraints c = new GridBagConstraints();

	/**
	 * Creates the frame and panels for the Statistics GUI and sets the CloseOperation and size, 
	 * along with setting the fill for the GridBagLayout and Borders.
	 */
	public void createForm(){
		frmStats = new JFrame("Statistics");
		frmStats.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmStats.setSize(300, 400);
		UITools.centreFrame(frmStats);

		//statsPanel = new JPanel(new GridLayout(5,1,0,0));
		//statsPanel.setBorder(new EmptyBorder(0,20,0,20));
		pnlStats = new JPanel(new GridBagLayout());
		pnlStats.setBorder(new EmptyBorder(0,50,0,50));
		c.fill = GridBagConstraints.HORIZONTAL;

		//leaderPanel = new JPanel(new GridLayout(0,2));
		//leaderPanel.setBorder(new EmptyBorder(0,20,0,20));
		pnlLeader = new JPanel(new GridBagLayout());
		pnlLeader.setBorder(new EmptyBorder(25,0,0,0));

		paneStats = new JTabbedPane();

	}

	/**
	 * Initialises Labels to the Statistics and Leaderboard panels and sets the position for each one on the GridBagLayout.
	 * Also runs a method to sort userList by highest score
	 */
	public void addFields(){
		lblCorrect = new JLabel("Correct", SwingConstants.CENTER);
		c.gridx = 0;
		c.gridy = 1;
		pnlStats.add(lblCorrect, c);

		lblWrong = new JLabel("Wrong", SwingConstants.CENTER);
		c.gridx = 0;
		c.gridy = 2;
		pnlStats.add(lblWrong, c);

		lblHighScore = new JLabel("High Score", SwingConstants.CENTER);
		c.gridx = 0;
		c.gridy = 3;
		pnlStats.add(lblHighScore, c);

		lblGamesPlayed = new JLabel("Games Played", SwingConstants.CENTER);
		c.gridx = 0;
		c.gridy = 4;
		pnlStats.add(lblGamesPlayed, c);

		lblBalance = new JLabel("Balance", SwingConstants.CENTER);
		c.gridx = 0;
		c.gridy = 5;
		pnlStats.add(lblBalance, c);

		JLabel nameTitle = new JLabel("<html><b><u>Name</b></u></html>", SwingConstants.CENTER);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		pnlLeader.add(nameTitle, c);

		JLabel scoreTitle = new JLabel("<html><b><u>High Score</b></u></html>", SwingConstants.CENTER);
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		pnlLeader.add(scoreTitle, c);


		Collections.sort(listUser, new HighScoreComparator());

		for(int i = 0; listUser.size() > 10 ? i < 10 : i < listUser.size(); i++){
			JLabel name = new JLabel(listUser.get(i).getName(), SwingConstants.CENTER);
			c.gridx = 0;
			c.gridy = i+1;
			pnlLeader.add(name, c);

			JLabel score = new JLabel(Integer.toString(listUser.get(i).getHighScore()), SwingConstants.CENTER);
			c.gridx = 1;
			c.gridy = i+1;
			pnlLeader.add(score, c);
		}

		paneStats.addTab("Statistics", pnlStats);
		paneStats.addTab("Leaderboards", pnlLeader);
	}

	/**
	 * Adds the combobox to the statistics panel, it sets a custom renderer for the combobox to align the text to the centre and populates the
	 * combobox with the userList array.
	 */
	public void addCombo(){
		DefaultListCellRenderer centre = new DefaultListCellRenderer(); 
		centre.setHorizontalAlignment(DefaultListCellRenderer.CENTER); 

		userCombo = new JComboBox<String>();
		userCombo.setForeground(new Color(255,255,255));
		userCombo.setRenderer(centre);
		
		for(User u : listUser){
			userCombo.addItem(u.getName());
		}
		
		userCombo.addItemListener(new ChangeUserListener());
		userCombo.setSelectedIndex(-1);
		
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 1;
		c.weightx = 1;

		pnlStats.add(userCombo,c);
	}

	/**
	 * This constructor initialises the GUI form and elements and sets the frame to be visible.
	 * @param userList Used to pass the ArrayList of users from the Menu GUI to Statistics GUI to populate leaderboard
	 * and ComboBox.
	 */
	public Statistics(ArrayList<User> userList){
		this.listUser = userList;

		createForm();
		addCombo();
		addFields();

		frmStats.add(paneStats);
		frmStats.setVisible(true);
	}

	/**
	 * Changes text of the 4 JLabels on the Statistics panel to reflect corresponding statistics for the chosen user
	 * (Correct Guesses, Wrong Guesses, High Score, Games Played and Balance)
	 * @author conortyler
	 *
	 */
	class ChangeUserListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent itEvent){
			if(userCombo.getSelectedIndex() != -1){
				currUser = listUser.get(userCombo.getSelectedIndex());

				lblCorrect.setText("Correct Guesses: " + Integer.toString(currUser.getCorrect()));
				lblWrong.setText("Wrong Guesses: " + Integer.toString(currUser.getWrong()));
				lblHighScore.setText("High Score: " + Integer.toString(currUser.getHighScore()));
				lblGamesPlayed.setText("Games Played: " + Integer.toString(currUser.getGamesPlayed()));
				lblBalance.setText("Balance: " + Integer.toString(currUser.getBalance()));
			}
		}
	}
}
