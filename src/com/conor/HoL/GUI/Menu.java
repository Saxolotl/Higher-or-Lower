package com.conor.HoL.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import com.conor.HoL.Statistics.ReadHandler;
import com.conor.HoL.Statistics.User;
import com.conor.HoL.Statistics.WriteHandler;

/**
 * This class is the main navigation for my GUI. It handles navigating to the game, how to play and statistics frames. It also
 * handles adding users through calling methods in the Read and Write Handlers, and sets the theme of the overall program to Nimbus.
 * @author conortyler
 *
 */
public class Menu {
	private JFrame frmMenu;
	private JPanel pnlMenu;
	private JComboBox<String> cboUser;
	private JLabel lblLogo;
	private JButton btnPlay, btnHowTo, btnStats, btnExit, addBtn, removeBtn;
	private User userCurrent;
	private ReadHandler read = new ReadHandler();
	private ArrayList<User> listUser;

	private boolean populate = true;
	GridBagConstraints c = new GridBagConstraints();

	/**
	 * Initialises GUI components and displays the frame when created. 
	 * @param currUser Sets the current user when returning from another frame to bypass reentering the user password.
	 */
	public Menu(User currUser){
		userCurrent = currUser;

		setUITheme();
		createForm();
		addButtons();
		addLogo();
		addUserOptions();
		populateCombo();

		frmMenu.add(pnlMenu);
		frmMenu.setVisible(true);
	}

	/**
	 * Initialises the frame with the title, size and default close operating and initialises the panel with the GridBagLayout
	 */
	public void createForm(){
		frmMenu = new JFrame("Higher or Lower");
		frmMenu.setSize(1024, 768);
		frmMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UITools.centreFrame(frmMenu);

		pnlMenu = new JPanel(new GridBagLayout());
		pnlMenu.setBorder(new EmptyBorder(0,100,0,100));
		c.fill = GridBagConstraints.HORIZONTAL;
	}

	/**
	 * Initialises buttons with button labels, action listeners and sets their position and size on the panel
	 */
	public void addButtons(){
		btnPlay = new JButton("Play");
		btnPlay.addActionListener(new playListener());
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 50;
		c.weighty = 1;
		c.weightx = 1;
		pnlMenu.add(btnPlay, c);

		btnHowTo = new JButton("How to Play");
		btnHowTo.addActionListener(new howToListener());
		c.gridx = 1;
		c.gridy = 0;
		pnlMenu.add(btnHowTo, c);

		btnStats = new JButton("Statistics");
		btnStats.addActionListener(new statListener());
		c.gridx = 2;
		c.gridy = 0;
		pnlMenu.add(btnStats, c);

		btnExit = new JButton("Exit");
		btnExit.addActionListener(new exitListener());
		c.gridx = 3;
		c.gridy = 0;
		pnlMenu.add(btnExit, c);
	}

	/**
	 * Initialises the logo and sets the position on the panel
	 */
	public void addLogo(){
		ImageIcon logo = new ImageIcon("res/highorlow.png");
		lblLogo = new JLabel(logo);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;
		pnlMenu.add(lblLogo, c);
	}

	/**
	 * Initialises the buttons and combobox for the user system
	 */
	public void addUserOptions(){
		DefaultListCellRenderer center = new DefaultListCellRenderer(); 
		center.setHorizontalAlignment(DefaultListCellRenderer.CENTER); 

		cboUser = new JComboBox<String>();
		cboUser.setForeground(new Color(255,255,255));
		cboUser.setRenderer(center);
		cboUser.addItemListener(new ChangeUserListener());
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		pnlMenu.add(cboUser, c);

		addBtn = new JButton("Add User");
		addBtn.addActionListener(new addUserListener());
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1;
		pnlMenu.add(addBtn, c);

		removeBtn = new JButton("Remove User");
		removeBtn.addActionListener(new delUserListener());
		c.gridx = 3;
		c.gridy = 2;
		pnlMenu.add(removeBtn, c);

	}

	/**
	 * Adds each user found in the Users/ directory to the combobox and corresponding ArrayList. Then the play and stats buttons are enabled if a user is selected when
	 * opening the GUI, otherwise the play and stats button will be disabled
	 */
	public void populateCombo(){
		populate = true;

		listUser = new ArrayList<User>();
		listUser = read.readUsers();

		for(User u : listUser){
			cboUser.addItem(u.getName());

		}

		cboUser.setSelectedItem(userCurrent);

		System.out.println("User " + cboUser.getSelectedItem());

		if(cboUser.getSelectedItem() == null){
			btnPlay.setEnabled(false);
		} else{
			btnPlay.setEnabled(true);
			btnStats.setEnabled(true);
			removeBtn.setEnabled(true);
		}

		populate = false;
	}

	/**
	 * Initialises the WriteHandler object and creates a file for the user in the Users/ directory if there is not one found.
	 * Then all the variables in the user object is written to the file
	 * @param user Specified user to write to a file
	 */
	public void writeUser(User user){
		WriteHandler write = new WriteHandler(user);

		write.createFile();
		write.writeChanges();
	}

	/**
	 * Set the theme of the GUI to the Nimbus Look & Feel, the method checks if the nimbus look and feel is avaliable,
	 * if it is not avaliable then it will stay with the default look and feel of java
	 */
	public void setUITheme(){
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					UIManager.put("nimbusBase", new Color(0,0,100));
					UIManager.put("control", new Color(60,60,60));
					UIManager.put("text", new Color(255,255,255));
					UIManager.put("nimbusLightBackground", new Color(100,100,100));
					UITools.setUIFont(new FontUIResource(new Font("Sans Serif", Font.PLAIN, 18)));
					break;
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmMenu, "An error occured while setting the theme. Default theme is in use.", "Theme error", JOptionPane.ERROR_MESSAGE);

		}


	}

	/**
	 * Creates the dialogs for adding a new username and password and validates this to ensure the input is valid to ensure
	 * no errors occur. Then a new user is added to the list and ComboBox  and a file is created for the new user.
	 */
	public void addUser(){
		JPasswordField pass = new JPasswordField();

		String userName = JOptionPane.showInputDialog(frmMenu, "Input a username", "Add User", JOptionPane.PLAIN_MESSAGE);
		boolean valid = true;

		if(userName == null || userName.trim().length() == 0){
			//if user presses cancel dialog then error message will not display
			if(userName != null){
				JOptionPane.showMessageDialog(frmMenu, "Username cannot be empty", "Add user error", JOptionPane.ERROR_MESSAGE);
			}
			valid = false;
		} else{
			for(User user : listUser){
				if(userName.equals(user.getName())){
					JOptionPane.showMessageDialog(frmMenu, "Username cannot already exist", "Add user error", JOptionPane.ERROR_MESSAGE);
					valid = false;
					break;
				} else{
					valid = true;
				}
			}

		}

		if(valid){
			int passDialog = JOptionPane.showConfirmDialog(frmMenu, pass, "Create password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

			if(passDialog == JOptionPane.OK_OPTION && pass.getPassword() != null && pass.getPassword().length != 0){
				valid = true;
			} else if(passDialog == JOptionPane.OK_OPTION && pass.getPassword() == null || pass.getPassword().length == 0){
				JOptionPane.showMessageDialog(frmMenu, "Password cannot empty", "Add user error", JOptionPane.ERROR_MESSAGE);
				valid = false;
			} else{
				valid = false;
			}
		}

		if(userName != null && pass.getPassword() != null && valid){
			User newUser = new User(userName, 0, 0, 0, 0, 100, String.copyValueOf(pass.getPassword()));
			listUser.add(newUser);
			writeUser(newUser);
			cboUser.addItem(userName);
		}
	}

	/**
	 * Used to dispose the menu frame and create a new instance of the Game GUI with the parameter of the current user for
	 * use in modifying statistics on press of the play button
	 * @author conortyler
	 *
	 */
	class playListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new Game(userCurrent);
			frmMenu.dispose();
		}

	}

	/**
	 * Used to create a new JOptionPane explaining how to play Higher or Lower on press of the how to play button
	 * @author conortyler
	 *
	 */
	class howToListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(frmMenu, "The player is dealt a card, they will then guess if the next card is higher or lower \n"
					+ "If they are correct then the player will guess again. \n"
					+ "If incorrect then the game will end", "How to play", JOptionPane.PLAIN_MESSAGE);
		}

	}

	/**
	 * Used to close the menu frame and create a new instance of the Statistics GUI with the parameter of the userList ArrayList to populate
	 * the leaderboard and ComboBox on Statistics
	 * @author conortyler
	 *
	 */
	class statListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new Statistics(listUser);
		}

	}

	/**
	 * 
	 * @author conortyler
	 *
	 */
	class exitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(JOptionPane.showConfirmDialog(frmMenu, "Would you like to exit?", "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				System.exit(0);
			}

		}

	}

	/**
	 * Used to call the addUser() method when add user button is pressed
	 * @author conortyler
	 *
	 */
	class addUserListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			addUser();
		}

	}

	/**
	 * Used to delete the selected user file from the Users/ directory and remove the User from the ComboBox and ArrayList on press of
	 * the delete button.
	 * @author conortyler
	 *
	 */
	class delUserListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(JOptionPane.showConfirmDialog(frmMenu, "Would you like to delete this user?", "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){

				if(read.removeUser(cboUser.getSelectedItem().toString())){
					int index = cboUser.getSelectedIndex();
					System.out.println(index);
					populate = true;
					cboUser.removeItemAt(index);
					listUser.remove(index);

					cboUser.setSelectedIndex(-1);
					populate = false;
				}
			}
		}

	}

	/**
	 * When a new user is selected in the ComboBox, a JOptionPane shows to enter the password for the user, this is then validated.
	 * If correct then the play and stats buttons are enabled, otherwise the selected item in the ComboBox is changed back to -1 (Nothing selected) and 
	 * the buttons are disabled.	
	 * @author conortyler
	 *
	 */
	class ChangeUserListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent itEvent){
			
			System.out.println(itEvent.getStateChange());
			
			if(itEvent.getStateChange() == ItemEvent.SELECTED){
				int index = cboUser.getSelectedIndex();

				if(cboUser.getSelectedIndex() != -1 && !populate){
					JPasswordField password = new JPasswordField();
					int passDialog = JOptionPane.showConfirmDialog(frmMenu, password, "Enter password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

					if(passDialog == JOptionPane.OK_OPTION && String.copyValueOf(password.getPassword()).equals(listUser.get(index).getPassword())){
						System.out.println(String.copyValueOf(password.getPassword()).equals(listUser.get(index).getPassword()));
						userCurrent = listUser.get(cboUser.getSelectedIndex());
						removeBtn.setEnabled(true);
						btnPlay.setEnabled(true);
					} else if(passDialog == JOptionPane.CANCEL_OPTION){
						cboUser.setSelectedIndex(-1);
					} else{
						JOptionPane.showMessageDialog(frmMenu, "Incorrect Password", "Incorrect Password", JOptionPane.ERROR_MESSAGE);
						cboUser.setSelectedIndex(-1);
					}

				} else{
					btnPlay.setEnabled(false);
					removeBtn.setEnabled(false);
				}
			} else if(itEvent.getStateChange() == ItemEvent.DESELECTED){
				btnPlay.setEnabled(false);
				removeBtn.setEnabled(false);
			}
		}
	}
}
