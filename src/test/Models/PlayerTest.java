package test.Models;

import Game.MapLoader;
import Models.*;
import View_Components.CardExchangeView;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;


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
        ArrayList<ArrayList> tempMap = new MapLoader().loadMap("1.txt");
        System.out.println("Inside before");
        p = new Phases(tempMap.get(0), tempMap.get(1));
        int[] values = new int[5];
        values[0] = 2;
        values[1] = 0;
        values[2] = 0;
        values[3] = 0;
        values[4] = 0;
        p.gameSetUp(values);
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
     * @throws Exception throw exception
     *
     */
    @Test
    public void testAddPlayerOneCard() throws Exception {
        int initSum = player1.getCards().cardSum();
        player1.addPlayerOneCard();
        assertEquals(player1.getCards().cardSum(), initSum+1);



    }


    /**
     *
     * Method: fortificate(Country from, Country to, int num)
     * @throws Exception throw exception
     *
     */
    @Test
    public void testFortificate() throws Exception {
        boolean flag = false;
        for (Country tempCountries : player1.getRealms() ){
            from = tempCountries;
            for (Country tempCountry : from.getNeighbours() ){
                if (tempCountry.getOwner() == from.getOwner()&&from.getNeighbours()!=null){
                    to = tempCountry;
                    flag = true;
                    break;
                }
            }
            if (flag == true) {
                break;
            }

        }

        if (flag == true) {
            from.setArmy(3);
            to.setArmy(3);
            player1.fortify(from,to,1);
            assertEquals(2,from.getArmy());
            assertEquals(4,to.getArmy());
        }else{
            assertEquals(flag,false);
        }


    }

    /**
     * Method: testShowPlayerCards()
     */
    @Test
    public void testShowPlayerCards(){
        int[] cardIni = {2, 3, 1};
        player1.getCards().setCardNumber(cardIni);
        for (int i = 0; i< 3; i++)
            assertEquals(player1.showPlayerCards(i), cardIni[i]);
    }

    /**
     * Method: testReceiveEnemyCards()
     */
    @Test
    public void testReceiveEnemyCards(){
        int[] cardIni = {2, 3, 1};
        player1.getCards().setCardNumber(cardIni);
        player2.getCards().setCardNumber(cardIni);

        player1.receiveEnemyCards(player2);
        for (int i = 0; i< 3; i++)
            assertEquals(player1.showPlayerCards(i), 2*cardIni[i]);
    }

    /**
     * Method: testAddPlayerArmyBySameCards()
     */
    @Test
    public void testAddPlayerArmyBySameCards(){
        int[] cardIni = {2, 3, 1};
        player1.getCards().setCardNumber(cardIni);
        int army = player1.getUnassigned_armies();
        player1.addPlayerArmyBySameCards(1);

        assertEquals(player1.getUnassigned_armies(), army + 5);
    }

    /**
     * Method: testAddPlayerArmyByDiffCards()
     */
    @Test
    public void testAddPlayerArmyByDiffCards(){
        int[] cardIni = {2, 3, 1};
        player1.setUnassigned_armies(1);
        player1.getCards().setCardNumber(cardIni);

        player1.addPlayerArmyByDiffCards();

        int m = player1.getCards().getCardTurn() - 1;



        assertEquals(player1.getUnassigned_armies(), 1 + 5*m);
    }



    /**
     *
     * Method: findPath(Country sourceCountry, Country targetCountry)
     */
    @Test
    public void testFindPath() {
        from = player1.getRealms().get(0);
        to = from.getNeighbours().get(0);
        try {
            assertEquals(true,player1.findPath(from,to));
            to = player2.getRealms().get(0);
            assertEquals(false,player1.findPath(from,to));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: CountryNotInRealms()
     */
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

    /**
     * Method: SourceIsTargetException()
     */
    @Test
    public void SourceIsTargetException() {
        from = player1.getRealms().get(0);
        try{
            assertTrue(player1.findPath(from,from));
        }catch(Exception ex){
            assertEquals("Models.SourceIsTargetException",ex.toString());

        }

    }

    /**
     * Method: testAttack()
     * @throws Exception
     */
    @Test
    public void testAttack() throws Exception{
        for (Country from : player1.getRealms()){
            for (Country to: from.getNeighbours()){
                if (from.getOwner() != to.getOwner()){
                    from.setArmy(1000);
                    to.setArmy(1);
                    assertFalse(player1.attack(from, to));
                    break;
                }
            }
        }
    }




} 
