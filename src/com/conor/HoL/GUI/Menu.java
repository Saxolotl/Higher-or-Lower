package com.conor.HoL.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import com.conor.HoL.Statistics.ReadHandler;
import com.conor.HoL.Statistics.User;
import com.conor.HoL.Statistics.WriteHandler;

public class Menu {
	private JFrame menuFrame;
	private JPanel menuPanel, btnPanel, userPanel, logoPanel;
	private JComboBox<String> userCombo;
	private JLabel logoPicture;
	private JButton playBtn, howBtn, statBtn, exitBtn;
	private JDialog addUserDialog;
	private User currUser;
	private ReadHandler read = new ReadHandler();
	private ArrayList<User> userList;

	public Menu(){
		setUITheme();
		createForm();
		initBtn();
		addLogo();
		addUserSettings();
		populateCombo();
		addUser();

		menuFrame.add(menuPanel);
		menuFrame.setVisible(true);
	}

	public void createForm(){
		menuFrame = new JFrame("Higher or Lower");
		menuFrame.setSize(1024, 768);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UITools.centreFrame(menuFrame);

		menuPanel = new JPanel(new GridLayout(0, 2, 10, 10));
		logoPanel = new JPanel();
	}

	public void initBtn(){
		playBtn = new JButton("Play");
		playBtn.addActionListener(new playListener());
		howBtn = new JButton("How to Play");
		howBtn.addActionListener(new howToListener());
		statBtn = new JButton("Statistics");
		statBtn.addActionListener(new statListener());
		exitBtn = new JButton("Exit");
		exitBtn.addActionListener(new exitListener());

		btnPanel = new JPanel(new GridLayout(0, 1, 10, 10));
		btnPanel.setBorder(new EmptyBorder(200,100,200,100));

		btnPanel.add(playBtn);
		btnPanel.add(howBtn);
		btnPanel.add(statBtn);
		btnPanel.add(exitBtn);

		menuPanel.add(btnPanel);
		menuPanel.add(logoPanel);
	}

	public void addLogo(){
		logoPanel.setLayout(new GridLayout(0, 1, 30, 10));
		logoPanel.setBorder(new EmptyBorder(180,0,0,0));
		ImageIcon logo = new ImageIcon("res/highorlow.png");
		logoPicture = new JLabel(logo);
		logoPanel.add(logoPicture);
	}

	private void addUserSettings(){

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

		JButton addBtn = new JButton("Add User");
		addBtn.addActionListener(new addUserListener());
		JButton removeBtn = new JButton("Remove User");
		removeBtn.addActionListener(new delUserListener());

		userPanel = new JPanel();
		userPanel.setLayout(new FlowLayout());
		userPanel.setMaximumSize(new Dimension(500, 100));

		userPanel.add(userCombo);
		userPanel.add(addBtn);
		userPanel.add(removeBtn);

		logoPanel.add(userPanel);
	}

	public void populateCombo(){
		userList = new ArrayList<User>();
		userList = read.readUsers();
		for(User u : userList){
			userCombo.addItem(u.getName());
		}
	}

	public void writeUser(User user){
		WriteHandler write = new WriteHandler(user);

		write.createFile();
		write.writeChanges();
	}

	public void setUITheme(){
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}

		UIManager.put("nimbusBase", new Color(0,0,100));
		UIManager.put("control", new Color(60,60,60));
		UIManager.put("text", new Color(255,255,255));
		UIManager.put("nimbusLightBackground", new Color(100,100,100));
		UITools.setUIFont(new FontUIResource(new Font("Sans Serif", Font.PLAIN, 18)));
	}

	public void addUser(){
		addUserDialog = new JDialog();
		addUserDialog.setSize(400, 125);
		UITools.centreFrame(addUserDialog);

		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
		JPanel entryPanel = new JPanel(new FlowLayout());

		JLabel addUserLbl = new JLabel("Please enter the Name", JLabel.CENTER);
		addUserLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		addUserLbl.setFont(new Font("Sans Serif", Font.PLAIN, 20));
		userPanel.add(addUserLbl);

		JTextField userName = new JTextField();
		userName.setForeground(Color.BLACK);
		userName.setPreferredSize(new Dimension(200,25));

		JButton addBtn = new JButton("Confirm");
		addBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				User newUser = new User(userName.getText(), 0, 0, 0, 0);
				userList.add(newUser);
				writeUser(newUser);
				userCombo.addItem(userName.getText());
				addUserDialog.setVisible(false);
			}
		});
		entryPanel.add(userName);
		entryPanel.add(addBtn);

		userPanel.add(entryPanel);
		addUserDialog.add(userPanel);

	}

	class playListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new Game(currUser);

		}

	}

	class howToListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("implement this");

		}

	}

	class statListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new Statistics(userList);

		}

	}

	class exitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);

		}

	}

	class addUserListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			addUserDialog.setVisible(true);

		}

	}

	class delUserListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println(userCombo.getSelectedItem().toString());
			read.removeUser(userCombo.getSelectedItem().toString());
			userList.remove(userCombo.getSelectedIndex());
			userCombo.removeItemAt(userCombo.getSelectedIndex());

		}

	}
}
