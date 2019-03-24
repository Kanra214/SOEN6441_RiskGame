package test.Models;

import Game.MapLoader;
import Models.Phases;
import Models.Player;
import View_Components.CardExchangeView;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * reinforcement Tester.
 *
 * @author Khang
 * @since <pre>Mar 3, 2019</pre>
 * @version 1.0
 */

public class PhasesTest {
    Phases p;

    @Before
    public void before() {
        System.out.println("1.map");
        ArrayList<ArrayList> tempMap = new MapLoader().loadMap("1.map");
        CardExchangeView cardExchangeView = new CardExchangeView();
        System.out.println("Inside before");
        p = new Phases(tempMap.get(0), tempMap.get(1));

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
        p.gameSetUp(1);
        assertEquals(p.extraArmyFromContinent(p.getCurrent_player()),44);
    }


    /**
     *
     * method: reinforcementArmy(Player player)
     */
    @Test
    public void reinforcementArmy() {
        p.gameSetUp(1);
        assertEquals(p.reinforcementArmy(p.getCurrent_player()), 63);

    }

//<<<<<<< HEAD
//    @Test
//    public void checkVictory(){
//        p.gameSetUp(1);
//        assertTrue(p.isOwnerOfAllCountries(p.getCurrent_player()));
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
        Player n = new Player(0,0,p);
        p.getGraph().get(0).setOwner(n);
        assertEquals(p.reinforcementArmy(n), 3);

    }
}