package test.Models;

import Game.MapLoader;
import Models.Phases;
import Models.Player;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * reinforcement Tester.
 *
 * @author <Khang>
 * @since <pre>Mar 3, 2019</pre>
 * @version 1.0
 */

public class PhasesTest {
    Phases p;

    @Before
    public void before() throws Exception {
        System.out.println("1.map");
        ArrayList<ArrayList> tempMap = new MapLoader().loadMap("1.map");
        System.out.println("Inside before");
        p = new Phases(tempMap.get(0), tempMap.get(1));

    }

    @After
    public void after() throws Exception {
        System.out.println("Inside after");
    }

    /**
     *
     * method: extraArmyFromContinent(Player player)
     */
    @Test
    public void extraArmyFromContinent() throws Exception {
        p.gameSetUp(1);
        assertEquals(p.extraArmyFromContinent(p.getCurrent_player()),44);
    }


    /**
     *
     * method: reinforcementArmy(Player player)
     */
    @Test
    public void reinforcementArmy() throws Exception {
        p.gameSetUp(1);
        assertEquals(p.reinforcementArmy(p.getCurrent_player()), 63);

    }

    /**
     *
     * method: reinforcementArmy(Player player)
     */
    @Test
    public void extraArmyFromContinent_whenHeOwns1() throws Exception {
        Player n = new Player(0,0,p);
        p.getGraph().get(0).setOwner(n);
        assertEquals(p.reinforcementArmy(n), 3);

    }
}