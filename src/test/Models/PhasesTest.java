package test.Models;

import Game.MapLoader;
import Models.Phases;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PhasesTest {
    Phases p;

    @Before
    public void before() throws Exception {
        System.out.println("1.map");
        ArrayList<ArrayList> tempMap = new MapLoader().loadMap("1.map");
        System.out.println("Inside before");
        p = new Phases(tempMap.get(0), tempMap.get(1));
        p.gameSetUp(1);
    }

    @After
    public void after() throws Exception {
        System.out.println("Inside after");
    }

    @Test
    public void extraArmyFromContinent() throws Exception {
        assertEquals(p.extraArmyFromContinent(p.getCurrent_player()),44);
    }
}