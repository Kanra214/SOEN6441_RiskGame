package test.Models;
import Game.Controller;
import Game.MapLoader;
import Models.*;
import View_Components.CardExchangeView;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;


public class RefactoringTest {

    Phases p1;
    Phases p2;
    Player player1;
    Player player2;
    Country from;
    Country to;

    @Before
    public void before() {
        ArrayList<ArrayList> tempMap1 = new MapLoader().loadMap("DemoMap-SmallSize.map");
        CardExchangeView cardExchangeView = new CardExchangeView();
        System.out.println("Inside before");
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
        
        from = player1.getRealms().get(0);
        to = player2.getRealms().get(0);
        from.setArmy(1);
        to.setArmy(1);
        from.setOwner(player1);
        to.setOwner(player2);
        

    }

	    @After
	    public void after() {
	        System.out.println("Inside after");
	    }

	    /**
	     *
	     * Method: Test the functions of reinforcementPhase in Player
	     */
	    @Test
	    public void testPlayer_reinforcementPhase() {

			try {
				player1.reinforce(from);
			} catch (Exception e) {
				e.printStackTrace();
			}

			assertEquals(from.getArmy(),2);
	    }
	
	    /**
	     *
	     * Method: Test the functions of fortificationsPhase in Player
	     */
	    @Test
	    public void testPlayer_fortificate() {	    	

	        try {
	        	
	            from.setArmy(3);
	            to.setArmy(3);
	            from.setOwner(player1);
	            to.setOwner(player1);
	        	player1.fortify(from, to, 1);
				
	        	assertEquals(to.getArmy(),4);
			} catch (Exception e) {
	
			} 
     
	    }
	    
	    
}
