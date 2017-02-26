package com.conor.HoL.Game;

import java.util.Random;

public class Logic {
	private Random card = new Random();
	private int currCard = 0;
	private int nextCard = (card.nextInt(12) + 1);
	private int colourGen = 0;
	private String[] cardName = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
	private String[] cardType = {"clubs", "diamonds", "spades", "hearts"};
	
	public void generateCards(){
		currCard = nextCard;
		nextCard = (card.nextInt(13));
		colourGen = (card.nextInt(4));
	}
	
	public int getCurrCard(){
		return currCard;
	}
	
	public int getNextCard(){
		return nextCard;
	}
	
	public boolean getResult(String btn){
		if(btn.equals("Higher") && currCard <= nextCard || btn.equals("Lower") && currCard >= nextCard){
			System.out.println(currCard + " " + nextCard);
			
			return true;
		} else {
			System.out.println(currCard + " " + nextCard);
			return false;
		}
	}
	
	public String getCardImage(){
		return cardName[currCard] + "_of_" + cardType[colourGen] + ".png";
	}
}
