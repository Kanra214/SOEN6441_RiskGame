package test.Models; 

import static org.junit.Assert.assertEquals;

import Game.MapLoader;
import Models.Country;
import Models.Phases;
import Models.Player;
import View_Components.CardExchangeView;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;


/** 
* Player Tester. 
* 
* @author Team36  
* @since <pre>Mar 26, 2019</pre> 
* @version 1.0 
*/ 
public class PlayerTest {
  Phases p;
  Player player1;
  Player player2;
  Country from;
  Country to;

@Before
public void before() throws Exception {
  ArrayList<ArrayList> tempMap = new MapLoader().loadMap("1.map");
  System.out.println("Inside before");
  p = new Phases(tempMap.get(0), tempMap.get(1));
  p.gameSetUp(2);
  player1 = p.getPlayers().get(0);
  player2 = p.getPlayers().get(1);

} 

@After
public void after() throws Exception {
  System.out.println("Inside after");
}


/** 
* 
* Method: addPlayerOneCard() 
* 
*/ 
@Test
public void testAddPlayerOneCard() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: fortificate(Country from, Country to, int num) 
* 
*/ 
@Test
public void testFortificate() throws Exception { 
//TODO: Test goes here...
    from = player1.getRealms().get(0);
    for (Country tempCountry : from.getNeighbours() ){
      if (tempCountry.getOwner() == from.getOwner()){
        to = tempCountry;
      }
    }
    from.setArmy(3);
    to.setArmy(3);
    player1.fortificate(from,to,1);
    assertEquals(2,from.getArmy());
    assertEquals(4,to.getArmy());
}





/** 
* 
* Method: incrementMapArmies() 
* 
*/ 
@Test
public void testIncrementMapArmies() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = Player.getClass().getMethod("incrementMapArmies"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: setUnassigned_armies(int unassigned_armies) 
* 
*/ 
@Test
public void testSetUnassigned_armies() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = Player.getClass().getMethod("setUnassigned_armies", int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: findPath(Country sourceCountry, Country targetCountry) 
* @throws Exception throw exception 
* 
*/ 
@Test
public void testFindPath() throws Exception {
//TODO: Test goes here...
  from = player1.getRealms().get(0);
  to = from.getNeighbours().get(0);
  try {
    assertEquals(true,player1.findPath(from,to));
    to = player2.getRealms().get(0);
    assertEquals(false,player1.findPath(from,to));
  } catch (Exception e) {

  }
}
  @Test
  public void CountryNotInRealms() {
    from = player2.getRealms().get(0);
    to = player1.getRealms().get(0);
    try{
      assertEquals(true,player1.findPath(from,to));
    }catch(Exception ex){
      assertEquals("Models.CountryNotInRealms",ex.toString());

    }

  }

  @Test
  public void SourceIsTargetException() {
    from = player1.getRealms().get(0);
    try{
      assertEquals(true,player1.findPath(from,from));
    }catch(Exception ex){
      assertEquals("Models.SourceIsTargetException",ex.toString());

    }

  }
  @Test
  public void NoSuchPathException() {
  from = player1.getRealms().get(0);
  for (Country tempcountry : player1.getRealms()){
    for (Country neighbour : from.getNeighbours()){
      if (tempcountry != neighbour){
        to =tempcountry;
      }
    }
  }
    try{
      player1.fortificate(from,to,1);
    }catch(Exception ex){
      assertEquals("Models.NoSuchPathException",ex.toString());

    }

  }



} 
