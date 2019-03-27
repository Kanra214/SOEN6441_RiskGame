/**
 * <h1>Player</h1>
 * This class give the player with specific characteristics
 */
package Models;
import View_Components.Window;

import java.awt.*;
import java.util.*;

/**
 * <h1>Player</h1>
 * This class that controls logic of the Player
 */
public class Player {
    private static Color[] ALL_COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.LIGHT_GRAY, Color.ORANGE};
    private Phases p;
    private Color playerColor;
    private int unassigned_armies;
    private int mapArmies = 0;//the total number of armies this player owns on the world map(excluding unassigned_armies)
    private int id;//this is primary key for players
    private Card cards;
    private int numOfDice;
    protected ArrayList<Integer> dice = new ArrayList<>();
    protected ArrayList<Country> realms;





    /**
     * Constructor
     * @param id player's id
     * @param army player's army number
     * @param p player's current phase
     */
    public Player(int id, int army, Phases p) {
        this.id = id;
        this.realms = new ArrayList<>();
        this.unassigned_armies = army;
        this.playerColor = ALL_COLORS[id];
        this.p = p;
        this.cards =new Card(p);

    }
    /**
     * get all the cards of the player
     * @return cards
     */
    public Card getCards() {
        //p.updateWindow();
        //System.out.println("update card");
        return cards;
    }


    /**
     * Getter
     * @return dice
     */
    public ArrayList<Integer> getDice() {
        return dice;
    }


    /**
     * Gets all the controlled countries of the player
     * @return ArrayList of countries he controls
     */
    public ArrayList<Country> getRealms() {
        return realms;
    }


    /**
     * Gets the player color
     * @return Color of the player
     */
    public Color getPlayerColor() {
        return playerColor;
    }


    /**
     * Gets the player ID
     * @return Player ID
     */
    public int getId() {
        return id;
    }


    /**
     * Determines if the player still have unassigned armies
     * @return True if there is still some army left and False otherwise
     */
    public boolean isArmyLeft(){
        return unassigned_armies > 0;
    }

    /**
     * Set unassigned armies to the player
     * @param extra number of army give to the player
     */
    protected void getReinforcement(int extra){
        this.setUnassigned_armies(unassigned_armies + extra);
    }


    /**
     * Get unassigned army
     * @return number of army left
     */
    public int getUnassigned_armies() {
        return unassigned_armies;
    }

    /**
     * Get all armies currently on the map
     * @return number of armies currently assigned to countries owned by the player
     */
    public int getMapArmies(){
        return mapArmies;
    }
    public String showPlayerCardsName(int cardID) {
        return cards.showCardsName(cardID);

    }

    public int showPlayerCards(int cardID) {
        return cards.showCardsNumber(cardID);

    }



    public void addPlayerOneCard() {
        cards.addCard();
       // p.updateWindow();
    }

    public void receiveEnemyCards(Player enemy) {
      //  p.updateWindow();
        cards.addCard(enemy);
    }

    public void addPlayerArmyBySameCards(int cardId) {
        unassigned_armies+=Card.getCardTurn()*5;
        cards.exchangeSameCards(cardId);
        p.updateWindow();
        //p.updateWindow();
        //System.out.println("update card");
    }

    public void addPlayerArmyByDiffCards(){
        unassigned_armies+=Card.getCardTurn()*5;
        cards.exchangeDiffCards();
        p.updateWindow();
    }


    /**
     * Send army to the country
     * @param country to where army will be sent
     */
    protected void deployArmy(Country country) throws OutOfArmyException {
        if(country.getOwner() == this) {
            if(isArmyLeft()) {
                setUnassigned_armies(unassigned_armies - 1);
                incrementMapArmies();
                country.incrementArmy();
            }
            else{
                System.out.println("out of armies in deploy army");
                throw new OutOfArmyException();
            }

        }
        else{
            System.out.println("not a country of current player");
        }
    }




    private void incrementMapArmies() {
        mapArmies++;
        p.updateWindow();


    }

    private void setUnassigned_armies(int unassigned_armies) {
        this.unassigned_armies = unassigned_armies;
        p.updateWindow();

    }

    /**
     * This function is used during the fortification phase to decide whether a player can move army from one country to another;
     * @param sourceCountry The source country
     * @param targetCountry The target country
     * @return True if it is possible to transfer between given countries
     * @throws CountryNotInRealms   country does not belong to the current player
     * @throws SourceIsTargetException  source and target are the same
     */
    public boolean findPath(Country sourceCountry, Country targetCountry) throws CountryNotInRealms, SourceIsTargetException {

        if (sourceCountry.getOwner() == this && targetCountry.getOwner() == this) {
            if(sourceCountry != targetCountry){
                Queue<Country> queue = new LinkedList<Country>();
                HashSet<Country> set = new HashSet<>();
                queue.offer(sourceCountry);
                set.add(sourceCountry);
                while (!queue.isEmpty()) {
                    Country tempCountry = queue.poll();
                    if (tempCountry == targetCountry) {
                        return true;
                    }
                    for (Country loopCountry : realms) {
                        for (Country neighbour : tempCountry.getNeighbours()){
                            if (neighbour != loopCountry){
                                continue;
                            }
                            else{
                                if (!set.contains(neighbour)) {
                                    queue.offer(neighbour);
                                    set.add(neighbour);
                                }
                            }
                        }
                    }

                }
                return false;
            }
            else{
                throw new SourceIsTargetException();
            }
        }
        else{
            throw new CountryNotInRealms();
        }


    }


    /**
     * Transfers army from one country to another during the fortification phase
     * @param from - from which country army is being taken
     * @param to - to which country army is being send
     * @param num - number of the army to transfer
     * @throws CountryNotInRealms   country not owned by the player
     * @throws OutOfArmyException   not enough army to transfer
     * @throws NoSuchPathException  no path from owned countries between country
     * @throws SourceIsTargetException  source country and target country is the same
     * @throws MoveAtLeastOneArmyException  0 army chosen to move
     */
    public void fortificate(Country from, Country to, int num) throws CountryNotInRealms, OutOfArmyException, NoSuchPathException, SourceIsTargetException, MoveAtLeastOneArmyException {

        if(findPath(from, to)){
            from.decreaseArmy(num);
            to.increaseArmy(num);
//            p.nextPhase();
        }
        else{
            throw new NoSuchPathException();

        }
    }





    protected void rollDice (int digits){
        numOfDice = digits;
//        ArrayList<Integer> DiceArray = new ArrayList<Integer>();
        for (int i =0; i < digits; i++){
            int Dice = (int)(Math.random()*6)+1;
            dice.add(Dice);
        }
        Collections.sort(dice, Collections.reverseOrder());
        p.updateWindow();
//        return dice;
    }
    protected void removeDice(){


        dice.remove(0);
        p.updateWindow();
    }
    protected int consumeDice(){
        int usedDice = dice.get(0);
        removeDice();
        return usedDice;
    }
    public int getNumOfDice(){
        return numOfDice;
    }

    protected void loseArmy(Country country) throws OutOfArmyException {
        mapArmies--;

        country.decrementArmy();





    }





    }


