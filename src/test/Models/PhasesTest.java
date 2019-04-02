package test.Models;

import Game.MapLoader;
import Models.*;
import View_Components.CardExchangeView;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

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
        System.out.println("Inside before");
        p1 = new Phases(tempMap1.get(0), tempMap1.get(1));
        p2 = new Phases(tempMap1.get(0), tempMap1.get(1));
        p1.gameSetUp(1);
        p2.gameSetUp(2);
        player1 = p2.getPlayers().get(0);
        player2 = p2.getPlayers().get(1);
        p2.setCurrent_player(player1);

    }

    @After
    public void after() {
        System.out.println("Inside after");
    }

    /**
     *
     * method: extraArmyFromContinent(Player player)
     */
    @Test
    public void extraArmyFromContinent() {
        assertEquals(p1.extraArmyFromContinent(p1.getCurrent_player()),44);
    }


    /**
     *
     * method: reinforcementArmy(Player player)
     */
    @Test
    public void reinforcementArmy() {
        assertEquals(p1.reinforcementArmy(p1.getCurrent_player()), 63);

    }

//<<<<<<< HEAD
//    @Test
//    public void checkVictory(){
//        p.gameSetUp(1);
//        assertTrue(p.(p.getCurrent_player()));
//    }
//=======
////    @Test
////    public void checkVictory(){
////        p.gameSetUp(1);
////        assertEquals(p.isOwnerOfAllCountries(p.getCurrent_player()), true);
////    }
//>>>>>>> Xiyun

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

    @Test
    public void checkWinner() {
      assertEquals(true,p1.checkWinner());
    }

}