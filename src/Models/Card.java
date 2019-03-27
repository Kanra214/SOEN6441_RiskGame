package Models;

import javax.swing.*;

/**
 * This class is card collection that each player holds
 */
public class Card {



//  	private int playerID;
	private String[] cardName;
	private int[] cardNumber;
	private int cardExchangeNum;// Count the number of exchange card
	private Phases p;
	private static int cardTurn = 1;



	/**
	 * Constructor
 	 */
	public Card(Phases p) {
		//int playerID
		cardName = new String[]{"Infantry", "Cavalry", "Artillery"};
		cardNumber = new int[]{2,1,1};
		this.p = p;


	}

	/**
	 * Getter
	 * @return cardExchangeNum
	 */
	public int getCardExchangeNum() {
		return cardExchangeNum;
	}


	/**
	 * Get the number of certain card
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

	protected void addCard(Player enemy) {
		for(int i=0;i<3;i++) {
			cardNumber[i]+=enemy.getCards().cardNumber[i];
		}
		p.updateWindow(enemy.getCards());
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
	 * Check if the number of all cards is bigger or equal than 
	 * @return false for bigger or equal,true for less
	 */
	
	public boolean checkCardSum() {
		if(cardSum()>=5)return false;
		
		return true;
		
	}
	
	
	/**
	 * Check the type of all cards 
	 * @return 4 for 3 card of different types,3 for 3 cards of the same type,5 for both,-1 for other 
	 */
	//4 for 3 card of different types,0,1,2 for 3 cards of the same type,5 for both,-1 for other 
	public int checkCardType() {
		int[] tempType= {-1,-1};
		if(cardNumber[0]!=0&&cardNumber[1]!=0&&cardNumber[2]!=0) {
			tempType[0]=1;
		}
		
		if(cardNumber[0]>=3||cardNumber[1]>=3||cardNumber[2]>=3) {
			tempType[1]=1;
		}
		
		
		if(tempType[0]+tempType[1]==2) {
			return 5;
		}else if(tempType[0]==1) {
			return 4;
		}
		else if(tempType[1]==1) {
			return 3;
		}
		
		return -1;
		
	}

	public boolean checkThreeDiffCards(){
		if(cardNumber[0]!= 0 && cardNumber[1] !=0 && cardNumber[2] != 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the number of certain card is bigger or equal than 3 
	 * @return true for bigger or equal,false for less
	 */
	
	public boolean cardBigger3(int cardID) {
		if(cardNumber[cardID]>=3) {return true;}
		else return false;
	}

	/**
	 * This method  will decrease card number according to the change mode
	 * @return true for successful exchange,false for not change
	 */
	//By calling this method card number will decrease 
	public boolean exchangeCard(int exchangeType) {
		//0 for 3 card of different types,1 for 3 cards of the same type 
		if(checkCardType()==-1 ) {
			return false;
		}
				
		if(exchangeType==4) {
			if(checkCardType()==4||checkCardType()==5) {
				for(int i=0;i<3;i++) {				
					cardNumber[i]--;
					}
					//cardExchangeNum++;
				return true;
			}else return false;
	
		}
		else if(exchangeType==0||exchangeType==1||exchangeType==2){
			
		if(cardNumber[exchangeType]>=3) {
				cardNumber[exchangeType]-=3;
				
			return true;
		}
		}else return false;	
		
		return true;
		
		
	}

	/**
	 * This method  will return the name of this card
	 * @return cardName the name of this card
	 */
	public String showCardsName(int cardID) {
		
		return cardName[cardID];
	}


	
	/**
	 * This method is used for junit test
	 * 
	 */
	public void iniCardForTest(int[] cardIni) {
		if(cardIni.length==3) {
			cardNumber=cardIni;
		}
		
		
	}

	protected void exchangeSameCards(int cardId){
		cardNumber[cardId] -= 3;
		cardTurn++;


	}
	protected void exchangeDiffCards(){
		for(int i = 0; i < 3; i ++){
			cardNumber[i] -= 1;
			cardTurn++;
		}
	}
	public static int getCardTurn(){return cardTurn;}

	
	
}
