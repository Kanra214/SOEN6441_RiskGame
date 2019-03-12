package Models;

public class Card {
	private int playerID;
	private String[] cardType= {"Infantry","Cavalry","Artillery"};
	private int[] cardNumber= {0,0,0}; 
	  
	public Card(int playerID) {
		
	}
	
	public void addCard() {
		int randCID=(int) Math.floor(3*Math.random());
		System.out.println(randCID);
		cardNumber[randCID]++;
	}
	
	public void showCards() {
		for(int i=0;i<3;i++) {
			System.out.println(cardType[i]);
			System.out.println(cardNumber[i]);
		}
	}
	//By calling this method card number will decrease 
	public void exchangeCard(boolean exchangeType) {
		//0 for 3 card of different types,1 for 3 cards of the same type 
		
	}
	
	
}
