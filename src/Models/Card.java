package Models;

import javax.swing.*;
import java.io.Serializable;

/**
 * This class is card collection that each player holds
 */
public class Card implements Serializable {



	private String[] cardName;
	private int[] cardNumber;
	private Phases p;
	private static int cardTurn = 1;



	/**
	 * Constructor
	 * @param p the Phase
 	 */
	public Card(Phases p) {
		cardName = new String[]{"Infantry", "Cavalry", "Artillery"};
		cardNumber = new int[]{0,0,0};
		this.p = p;


	}


	public void setCardNumber(int[] cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * Get the number of certain card
	 * @param cardID ID of the card
	 * @return number of card 
	 */
	public int showCardsNumber(int cardID) {
		return cardNumber[cardID];
	}
	
	/**
	 * The player receives a card of random type
	 */
	protected void addCard() {
		int randCID=(int) Math.floor(3*Math.random());
		System.out.println("This card will add to "+randCID);
		cardNumber[randCID]++;
		p.updateWindow(cardName[randCID]);
		p.updateWindow();
	}

	/**
	 * for player adding card
	 * @param enemy player
	 */
	protected void addCard(Player enemy) {
		p.updateWindow(enemy.getCards());
		for(int i=0;i<3;i++) {
			cardNumber[i]+=enemy.getCards().cardNumber[i];
			enemy.getCards().cardNumber[i] = 0;
		}
		System.out.println("You receive cards from enemy");
		p.updateWindow();


	}
	
	
	/**
	 * Get the number of all cards
	 * @return sum of cards
	 */
	
	public int cardSum() {
		int sumNum=0;
		for(int i=0;i<3;i++) {				
			sumNum+=cardNumber[i];
			}		
		return sumNum;
	}
	
	/**
	 * Check if the number of all cards is bigger or equal than 5
	 * @return false for bigger or equal,true for less
	 */
	
	public boolean checkCardSum() {
		if(cardSum()>=5) return false;
		return true;
		
	}
	
	


	public boolean checkThreeDiffCards(){
		if(cardNumber[0]!= 0 && cardNumber[1] !=0 && cardNumber[2] != 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the number of certain card is bigger or equal than 3 
	 * @param cardID the ID of the card
	 * @return true for bigger or equal,false for less
	 */
	public boolean cardBigger3(int cardID) {
		if(cardNumber[cardID]>=3) {
			return true;
		}
		else {
			return false;
		}
	}



	/**
	 * This method  will return the name of this card
	 * 
	 * @param cardID the ID of the card
	 * @return cardName the name of this card
	 */
	public String showCardsName(int cardID) {
		
		return cardName[cardID];
	}


	/**
	 * Exchanges 3 the same card
	 * @param cardId of the 3 the same card
	 */
	protected void exchangeSameCards(int cardId){
		cardNumber[cardId] -= 3;
		cardTurn++;
		System.out.println("card++");
	}

	/**
	 * Exchange 3 different cards
	 */
	protected void exchangeDiffCards(){
		for(int i = 0; i < 3; i ++){
			cardNumber[i] -= 1;

		}
		cardTurn++;
	}

	public static int getCardTurn(){return cardTurn;}

	
	
}
