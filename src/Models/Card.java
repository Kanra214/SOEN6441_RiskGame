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
		int tempType=-1;
 
		
		if(cardSum()==5) {
			return 2;
		}
		
		if(cardNumber[0]==3||cardNumber[1]==3||cardNumber[2]==3) {
			return 1;
		}
		
		if(cardNumber[0]!=0||cardNumber[1]!=0||cardNumber[2]!=0) {
			return 0;
		}
		
		
		return tempType;
		
	}
	
	
	
	//By calling this method card number will decrease 
	public boolean exchangeCard(boolean exchangeType) {
		//0 for 3 card of different types,1 for 3 cards of the same type 
		if(checkCardType()!=0||checkCardType() !=1 ) {
			return false;
		}
				
		if(exchangeType==true) {
			for(int i=0;i<3;i++) {				
			cardNumber[i]--;
			}			
		}
		else {
			for(int i=0;i<3;i++) {				
				if(cardNumber[i]==3) {
				
					cardNumber[i]-=3;
				}
			}	
		}
		
		return true;
		
		
	}
	
	
}
