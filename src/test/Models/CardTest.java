
package test.Models;

import Game.MapLoader;
import Models.Card;
import Models.Country;
import Models.Phases;
import Models.Player;
import View_Components.CardExchangeView;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.*;

/** 
* Card Tester. 
* 
* @author Team36  
* @since <pre>Mar 26, 2019</pre> 
* @version 2.0 
*/ 

public class CardTest {
	Phases p;
	Card card;


	@Before
	public void before() throws Exception {
		ArrayList<ArrayList> tempMap = new MapLoader().loadMap("1.txt");
		System.out.println("Inside before");
		p = new Phases(tempMap.get(0), tempMap.get(1));


		card = new Card(p);
		int[] cardIni = {2, 3, 1};

		card.setCardNumber(cardIni);
	}

	@After
	public void after(){
		System.out.println("Inside after");
	}
	/** 
	* 
	* Method: showCardsNumber()
	* 
	* 
	*/ 
	@Test
	public void showCardsNumber() {
		assertEquals(card.showCardsNumber(0), 2);
		assertEquals(card.showCardsNumber(1), 3);
		assertEquals(card.showCardsNumber(2), 1);
	}
	/** 
	* 
	* Method: card.cardSum()
	* 
	* 
	*/ 
	@Test
	public void cardSum() {
		assertEquals(card.cardSum(), 6);
	}
	/** 
	* 
	* Method: checkCardSum
	* 
	* 
	*/ 
	@Test
	public void checkCardSum() {
		assertFalse(card.checkCardSum());
	}
	/** 
	* 
	* Method: checkThreeDiffCards
	* 
	* 
	*/ 
	@Test
	public void checkThreeDiffCards() {
		assertTrue((card.checkThreeDiffCards()));
	}
	/** 
	* 
	* Method: cardBigger3()
	* 
	* 
	*/ 
	@Test
	public void cardBigger3() {
		assertTrue((card.cardBigger3(1)));
		assertFalse((card.cardBigger3(0)));
		assertFalse((card.cardBigger3(2)));
	}
	/** 
	* 
	* Method: showCardsName() 
	* 
	* 
	*/ 
	@Test
	public void showCardsName() {
		String[] cardName = new String[]{"Infantry", "Cavalry", "Artillery"};
		for (int i = 0; i<3; i++)
			assertEquals(card.showCardsName(i), cardName[i]);
	}

}

