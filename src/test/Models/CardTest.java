
package test.Models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

/** 
* Card Tester. 
* 
* @author Team36  
* @since <pre>Mar 26, 2019</pre> 
* @version 2.0 
*/ 

public class CardTest {
	  Phases p;
	  Player player1;
	  Player player2;
	  Country from;
	  Country to;
	  Card card;
	
	
	@Before
	public void before() throws Exception {
	  ArrayList<ArrayList> tempMap = new MapLoader().loadMap("1.map");
	  System.out.println("Inside before");
	  p = new Phases(tempMap.get(0), tempMap.get(1));


	  card = new Card(p);
	  int[] cardIni= {2,3,1};
//	  card.iniCardForTest(cardIni);
	} 

	@After
	public void after() throws Exception {
	  System.out.println("Inside after");
	}

	/**
	*
	* Test ExchangeDiffCard
	* @throws Exception throw exception
	*/
	@Test
	public void testExchangeDiffCard() throws Exception{
		
//		  assertTrue(card.exchangeCard(4));
		
	}

	/**
	*
	* Test ExchangeSameCard
	* @throws Exception throw exception
	*/
	@Test
	public void testExchangeSameCard() throws Exception{
		
//		  assertTrue(card.exchangeCard(1));
		
	}
	
	/**
	*
	* Test checkCardType
	* @throws Exception throw exception
	*/
	@Test
	public void testheckCardsType() throws Exception{
		
//		  assertEquals(5,card.checkCardType());
		
	}
	
}

//package test.Models;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import Game.MapLoader;
//import Models.Card;
//import Models.Country;
//import Models.Phases;
//import Models.Player;
//import View_Components.CardExchangeView;
//
//import java.util.ArrayList;
//import org.junit.Test;
//import org.junit.Before;
//import org.junit.After;
//
//public class CardTest {
//	  Phases p;
//	  Player player1;
//	  Player player2;
//	  Country from;
//	  Country to;
//	  Card card;
//
//
//	@Before
//	public void before() throws Exception {
//	  ArrayList<ArrayList> tempMap = new MapLoader().loadMap("1.map");
//	  System.out.println("Inside before");
//	  p = new Phases(tempMap.get(0), tempMap.get(1));
//
//
//	  card = new Card(p);
//	  int[] cardIni= {2,3,1};
//	  card.iniCardForTest(cardIni);
//	}
//
//	@After
//	public void after() throws Exception {
//	  System.out.println("Inside after");
//	}
//
//	@Test
//	public void testExchangeDiffCard() throws Exception{
//
//		  assertTrue(card.exchangeCard(4));
//
//	}
//
//	@Test
//	public void testExchangeSameCard() throws Exception{
//
//		  assertTrue(card.exchangeCard(1));
//
//	}
//
//	@Test
//	public void testheckCardsType() throws Exception{
//
//		  assertEquals(5,card.checkCardType());
//
//	}
//
//}

