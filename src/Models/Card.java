package Models;

import javax.swing.*;

public class Card {
	private int playerID;
	private String[] cardName= {"Infantry","Cavalry","Artillery"};
	private int[] cardNumber= {0,0,0}; 
	  
	public Card() {
		//int playerID
	}
	
	public void addCard() {
		int randCID=(int) Math.floor(3*Math.random());
		System.out.println("This card will add to "+randCID);
		JOptionPane.showMessageDialog(null, "This card will add to "+randCID);
		cardNumber[randCID]++;
	}
	
	public void addCard(int[] enemycards) {
		for(int i=0;i<3;i++) {
			cardNumber[i]+=enemycards[i];
		}
		
	}
	
	public int showCardsNumber(int cardID) {
		/*
		for(int i=0;i<3;i++) {
			System.out.println(cardName[i]);
			System.out.println(cardNumber[i]);
		}
		*/
		return cardNumber[cardID];
	}
	
	public int cardSum() {
		int sumNum=0;
		for(int i=0;i<3;i++) {				
			sumNum+=cardNumber[i];
			}		
		return sumNum;
	}
	
	public boolean checkCardSum() {
		if(cardSum()>=5)return false;
		
		return true;
		
	}
	
	//0 for 3 card of different types,1 for 3 cards of the same type,2 for both,-1 for other 
	public int checkCardType() {
		int[] tempType= {-1,-1};
		if(cardNumber[0]!=0&&cardNumber[1]!=0&&cardNumber[2]!=0) {
			tempType[0]=1;
		}
		
		if(cardNumber[0]>=3||cardNumber[1]>=3||cardNumber[2]>=3) {
			tempType[1]=1;
		}
		
		
		if(tempType[0]+tempType[1]==2) {
			return 2;
		}else if(tempType[0]==1) {
			return 0;
		}
		else if(tempType[1]==1) {
			return 1;
		}
		
		return -1;
		
	}
	

	//By calling this method card number will decrease 
	public boolean exchangeCard(int exchangeType) {
		//0 for 3 card of different types,1 for 3 cards of the same type 
		if(checkCardType()==-1 ) {
			return false;
		}
				
		if(exchangeType==0) {
			if(checkCardType()==0) {
				for(int i=0;i<3;i++) {				
					cardNumber[i]--;
					}
				return true;
			}else return false;
	
		}
		else if(exchangeType==1){
			if(checkCardType()==1) {
			for(int i=0;i<3;i++) {				
				if(cardNumber[i]>=3) {				
					cardNumber[i]-=3;
				}
				return true;
			}
			}else return false;	
		}
		
		return true;
		
		
	}

	public String showCardsName(int cardID) {
		
		return cardName[cardID];
	}

	public int[] cardAll() {
		
		return cardNumber;
	}
	
	
}
