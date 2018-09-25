package com.conor.HoL.Game;

import java.util.Random;

/**
 * This class is the workings behind the Game GUI. It randomly generates a card which is then used to display an image on the Game GUI.
 * It also checks to see if the user was correct with their guess.
 * @author Conor Tyler
 *
 */
public class Logic {
	private Random card = new Random();
	private int currCard = 0;
	private int nextCard = (card.nextInt(13));
	private int colourGen = 0;
	private String[] cardName = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
	private String[] cardType = {"clubs", "diamonds", "spades", "hearts"};
	
	/**
	 * Randomly generates the nextCard and colourGen integers and sets the currCard value to the previous nextCard value
	 */
	public void generateCards(){
		currCard = nextCard;
		nextCard = (card.nextInt(13));
		colourGen = (card.nextInt(4));
	}
	
	/**
	 * 
	 * @return Returns the current card for use in classes
	 */
	public int getCurrCard(){
		return currCard;
	}
	
	public void setCurrCard(int currCard){
		this.currCard = currCard;
	}
	
	/**
	 * 
	 * @return Returns the Next card for use in classes
	 */
	public int getNextCard(){
		return nextCard;
	}
	
	public void setNextCard(int nextCard){
		this.nextCard = nextCard;
	}
	
	/**
	 * Calculates if the user is correct or incorrect
	 * @param choice The selection of the user (Higher or Lower)
	 * @return The result (true == correct, false == incorrect)
	 */
	public boolean getResult(int choice){
		//0 == lower, 1 == higher
		if(choice == 1 && currCard <= nextCard || choice == 0 && currCard >= nextCard){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Uses an array to get the card name using the generated number for current card and uses an array to get the type of the card by using the generated number
	 * for the card type.
	 * @return File name for the card which has been generated for use by an ImageIcon.
	 */
	public String getCardImage(){
		return cardName[currCard] + "_of_" + cardType[colourGen] + ".png";
	}
}
