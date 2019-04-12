package test.Models;

import Game.MapLoader;
import Models.*;
import View_Components.CardExchangeView;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * reinforcement Tester.
 *
 * @author Khang
 * @since <pre>Mar 3, 2019</pre>
 * @version 1.0
 */

public class PhasesTest {
    Phases p1;
    Phases p2;
    Player player1;
    Player player2;
    Country from;
    Country to;

    @Before
    public void before() {
        ArrayList<ArrayList> tempMap1 = new MapLoader().loadMap("DemoMap-SmallSize.map");
//        CardExchangeView cardExchangeView = new CardExchangeView();
        System.out.println("Inside PhasesTest before");
        p1 = new Phases(tempMap1.get(0), tempMap1.get(1));
        p2 = new Phases(tempMap1.get(0), tempMap1.get(1));
        int[] values = new int[5];
        values[0] = 1;
        values[1] = 0;
        values[2] = 0;
        values[3] = 0;
        values[4] = 0;
        p1.gameSetUp(values);

        values[0] = 2;
        p2.gameSetUp(values);

        player1 = p2.getPlayers().get(0);
        player2 = p2.getPlayers().get(1);
        p2.setCurrent_player(player1);

    }

    @After
    public void after() {
        System.out.println("Inside PhasesTest after");
    }

    /**
     *
     * method: extraArmyFromContinent(Player player)
     */
    @Test
    public void extraArmyFromContinent() {
        assertEquals(p1.extraArmyFromContinent(p1.getCurrent_player()),0);
    }


    /**
     *
     * method: reinforcementArmy(Player player)
     */
    @Test
    public void reinforcementArmy() {
    	
        assertEquals(p1.reinforcementArmy(p1.getCurrent_player()), 2);

    }



    /**
     *
     * method: reinforcementArmy(Player player)
     */
    @Test
    public void extraArmyFromContinent_whenHeOwns1() {
        Player n = new Player(0,0,p1);
        p1.getGraph().get(0).setOwner(n);
        assertEquals(p1.reinforcementArmy(n), 3);

    }
    
    /**
    *
    * method: Attack Country Arm yMore Than One
    */
    
    @Test
      public void Exception_AttackCountryArmyMoreThanOne() {
        p2.reinforcementArmy(player1);
        p2.reinforcementArmy(player2);
        from = player1.getRealms().get(0);
        to = player2.getRealms().get(0);
        from.setArmy(1);
        to.setArmy(1);
        try{
          assertEquals(true,p2.attackValidation(from,to,3,2));
        }catch(Exception ex){
          assertEquals("Models.AttackCountryArmyMoreThanOne",ex.toString());

        }

      }
    
    /**
    *
    * method: Attacking Country Owner
    */
    
      @Test
      public void Exception_AttackingCountryOwner() {
        p2.reinforcementArmy(player1);
        p2.reinforcementArmy(player2);
        from = player1.getRealms().get(0);
        to = player2.getRealms().get(0);
        try{
          assertEquals(true,p2.attackValidation(to,from,3,2));
        }catch(Exception ex){
          assertEquals("Models.AttackingCountryOwner",ex.toString());

        }

      }

      /**
      *
      * method: TargetCountryNotAdjacent
      */ 
      
    @Test
    public void Exception_TargetCountryNotAdjacent() {
      p2.reinforcementArmy(player1);
      p2.reinforcementArmy(player2);
      for(Country tempCountry : player1.getRealms()){
        for (Country notNeighbour : p2.getGraph()){
          if (tempCountry.getOwner() != notNeighbour.getOwner() && !tempCountry.getNeighbours().contains(notNeighbour)){
            from = tempCountry;
            to = notNeighbour;
          }
        }
      }
      try{
        assertEquals(true,p2.attackValidation(from ,to,3,2));
      }catch(Exception ex){
        assertEquals("Models.TargetCountryNotAdjacent",ex.toString());

      }

    }
    
    /**
    *
    * method: WrongDiceNumber
    */ 
    
    @Test
    public void Exception_WrongDiceNumber() {
      p2.reinforcementArmy(player1);
      p2.reinforcementArmy(player2);
      for(Country tempCountry : player1.getRealms()){
        for (Country neighbour : tempCountry.getNeighbours()){
          if (tempCountry.getOwner() != neighbour.getOwner()){
            from = tempCountry;
            to = neighbour;
          }
        }
      }
      try{
        assertEquals(true,p2.attackValidation(from ,to,-1,2));
      }catch(Exception ex){
        assertEquals("Models.WrongDiceNumber",ex.toString());

      }

    }
    /**
    *
    * method: attackValidation
    */ 
    @Test
    public void attackValidation() {
      p2.reinforcementArmy(player1);
      p2.reinforcementArmy(player2);
      for(Country tempCountry : player1.getRealms()){
        for (Country neighbour : tempCountry.getNeighbours()){
          if (tempCountry.getOwner() != neighbour.getOwner()){
            from = tempCountry;
            to = neighbour;
          }
        }
      }
      System.out.println(1);
      from.setArmy(5);
      to.setArmy(3);
      try{
        assertEquals(true,p2.attackValidation(from ,to,3,2));
      }catch(Exception ex){

      }

    }

    /**
    *
    * method: checkAttackingIsPossible()
    */ 
    @Test
    public void testAttackingIsPossible(){
        for (Country c: player1.getRealms()){
            c.setArmy(10);
        }
        for (Country c: player2.getRealms()){
            c.setArmy(10);
        }

        p2.checkAttackingIsPossible();

        assertTrue(p2.getAttackingIsPossible());
    }

    /**
    *
    * method: nextPhase();
    */ 
    @Test
    public void testNextPhase() throws Exception{
        int prev = p1.getCurrentPhase();
        p1.setCurrent_player(p1.getPlayers().get(0));
        p1.nextPhase();
        p1.nextPhase();
        System.out.println(p1.getPlayers().get(0).getRealms().size());
        assertEquals(prev+1, p1.getCurrentPhase());
    }
    /**
    *
    * method: checkWinner();
    */ 
    @Test
    public void checkWinner() {
      assertEquals(true,p1.checkWinner());
    }

  /**
   * method: isGameOver();
   */
    @Test
    public void isGameOver(){
      assertEquals(false,p1.isGameOver());
    }

}