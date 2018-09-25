package com.conor.HoL.GUI;

import com.conor.HoL.Game.Logic;
import com.conor.HoL.Statistics.User;
import com.conor.HoL.Statistics.WriteHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

/**
 * This class is the GUI for the Game. It gets information from the Logic class such as the current card image to be displayed.
 * When a user presses higher or lower, a result is retrieved from Logic to determine if the user was correct. If so, then a new
 * card will be generated and if they bet credits, their new balance will be calculated.
 * @author Conor Tyler
 *
 */
public class Game {

	private JFrame frmGame;
	private JPanel pnlGame;
	private JButton btnHigh, btnLow, btnMenu;
	private JSpinner spinBet;
	private JLabel currPicture, scoreLbl, betLbl;
	private ImageIcon currCardImg;

	private User currUser;
	private Logic hol = new Logic();
	private WriteHandler write;
	GridBagConstraints c = new GridBagConstraints();
	SpinnerNumberModel numModel;

	private int score = 0;
	private int balance = 0;
	private boolean result;

	/**
	 * This constructor is used to run the methods to initialise the GUI and set the frame to visible. It also sets the current user to the value passed through
	 * @param currUser Current user selected from Menu
	 */
	public Game(User currUser){
		this.currUser = currUser;

		createForm();
		addFields();
		addButtons();

		frmGame.add(pnlGame);
		frmGame.setVisible(true);
	}

	/**
	 * Creates the frame and panels for the Game GUI and sets the CloseOperation and size, 
	 * along with setting the fill and weight for the GridBagLayout and Borders.
	 */
	public void createForm(){
		frmGame = new JFrame("Higher or Lower - Game");
		frmGame.setSize(800,600);
		frmGame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		UITools.centreFrame(frmGame);

		pnlGame = new JPanel(new GridBagLayout());
		pnlGame.setBorder(new EmptyBorder(15,0,0,75));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 1;
		c.weightx = 1;
	}

	/**
	 * Adds the image for the current card, along with the labels for score and balance, plus sets the positioning for the elements
	 * using the GridBagLayout.
	 */
	public void addFields(){
		hol.generateCards();

		currCardImg = new ImageIcon(new ImageIcon("res/cards/" + hol.getCardImage()).getImage().getScaledInstance(300, 436, Image.SCALE_SMOOTH));
		currPicture = new JLabel(currCardImg);

		c.gridx = 0;
		c.gridy = 2;
		c.gridheight = 4;

		pnlGame.add(currPicture, c);

		scoreLbl = new JLabel("Score: 0");
		scoreLbl.setHorizontalAlignment(JLabel.CENTER);
		scoreLbl.setForeground(new Color(255,255,255));
		scoreLbl.setFont(new Font("Sans Serif", Font.PLAIN, 30));

		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.5;

		pnlGame.add(scoreLbl, c);

		balance = currUser.getBalance();
		betLbl = new JLabel("Balance: " + balance);
		betLbl.setHorizontalAlignment(JLabel.CENTER);
		betLbl.setForeground(new Color(255,255,255));
		betLbl.setFont(new Font("Sans Serif", Font.PLAIN, 30));

		c.gridx = 1;
		c.gridy = 2;

		pnlGame.add(betLbl, c);

		numModel = new SpinnerNumberModel(0, 0, currUser.getBalance(), 1);
		spinBet = new JSpinner(numModel);

		c.gridx = 1;
		c.gridy = 3;

		pnlGame.add(spinBet, c);
	}

	/**
	 * Initialises buttons with button labels, action listeners and sets their position and size on the panel
	 */
	public void addButtons(){
		btnMenu = new JButton("Back to Menu");
		btnMenu.addActionListener(new MenuListener());
		
		c.gridx = 1;
		c.gridy = 0;

		pnlGame.add(btnMenu, c);

		btnHigh = new JButton("Higher");
		btnHigh.addActionListener(new highListener());

		c.gridx = 1;
		c.gridy = 4;
		c.ipady = 50;

		pnlGame.add(btnHigh, c);

		btnLow = new JButton("Lower");
		btnLow.addActionListener(new lowListener());

		c.gridx = 1;
		c.gridy = 5;

		pnlGame.add(btnLow, c);
	}

	/**
	 * Changes the image to the value returned by the getCardImage() method and then applies the icon to the currPicture label.
	 */
	public void changeImage(){
		currCardImg = new ImageIcon(new ImageIcon("res/cards/" + hol.getCardImage()).getImage().getScaledInstance(300, 436, Image.SCALE_SMOOTH));
		currPicture.setIcon(currCardImg);
	}

	/**
	 * Method that gets the result from the Game class and changes labels / image if result is true, or displays game over
	 * menu if result is false. Also handles incrementing the guesses for statistics.
	 * @param choice Button which has been pressed (lower = 0 / higher = 1)
	 */
	private void click(int choice){
		result = hol.getResult(choice);

		bet(result, (Integer) spinBet.getValue());

		if(result){
			try {
				spinBet.commitEdit();
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(frmGame, "Bet must not exceed the balance", "Bet Error", JOptionPane.ERROR_MESSAGE);
				spinBet.setValue(0);
			}
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

	/**
	 * Calculates whether credits should be awarded when betting
	 * @param result Result of the round (Correct / Wrong)
	 * @param bet Amount of credits the user has chosen to bet
	 */
	private void bet(boolean result, int bet){
		if(balance >= bet){
			if(result){
				balance += 2;
				balance += bet / 2;
			} else{
				balance -= bet;
				
			}
		}

		numModel.setMaximum(balance);
		
		if((Integer)numModel.getValue() > balance){
			numModel.setValue(balance);
		}
		betLbl.setText("Balance: " + balance);
	}

	/**
	 * Handles displaying the game over message and prompt to play again. Also increments the games played statistic and writes all
	 * changes to the Current User to the file.
	 */
	private void gameOver(){
		System.out.println(score + " and " + currUser.getHighScore());
		if(score > currUser.getHighScore()){
			currUser.setHighScore(score);
		}
		currUser.incGamesPlayed();
		write = new WriteHandler(currUser);
		write.writeChanges();
	}

	/**
	 * Listens for when the Higher button is clicked and runs the click() method to calculate the result.
	 * @author Conor Tyler
	 *
	 */
	class highListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e){
			click(1);
		}

	}
	
	/**
	 * Closes the game frame and reopens the menu GUI and passes the current user through
	 * @author Conor Tyler
	 *
	 */
	class MenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			gameOver();
			new Menu(currUser);
			frmGame.dispose();
			
		}
		
	}

	/**
	 * Listens for when the Lower button is clicked and runs the click() method to calculate the result.
	 * @author Conor Tyler
	 *
	 */
	class lowListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e){
			click(0);
		}

	}

}
