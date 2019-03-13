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
    private Card card;

    /**
     * Array list of controlled territories of the player
     */
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
        this.card=new Card();

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
    	return card.showCardsName(cardID);
    	
    }
    
    public int showPlayerCards(int cardID) {
    	return card.showCardsNumber(cardID);
    	
    }
    
    public void addPlayerCard() {
    	card.addCard();
    }
    
    
    /**
     * Send army to the country
     * @param country to where army will be sent
     */
    protected void deployArmy(Country country)  {
        if(realms.contains(country)) {
            if(isArmyLeft()) {
                setUnassigned_armies(unassigned_armies - 1);
                incrementMapArmies();
                country.increaseArmy();
            }
            else{
                System.out.println("out of armies");
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
    private boolean findPath(Country sourceCountry, Country targetCountry) throws CountryNotInRealms, SourceIsTargetException {

        if (realms.contains(sourceCountry) && realms.contains(targetCountry)) {
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
                throw new SourceIsTargetException(3);
            }
        }
        else{
            throw new CountryNotInRealms(2);
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
    protected void fortificate(Country from, Country to, int num) throws CountryNotInRealms, OutOfArmyException, NoSuchPathException, SourceIsTargetException, MoveAtLeastOneArmyException {

        if(findPath(from, to)){
            from.decreaseArmy(num);
            to.increaseArmy(num);
            p.nextPhase();
        }
        else{
            throw new NoSuchPathException(1);
        }
    }

    protected void attackAssign(Country from, Country to, int num) throws MoveAtLeastOneArmyException, OutOfArmyException{
        System.out.println("attackAssign:" + num);
        from.decreaseArmy(num);
        to.increaseArmy(num);
    }

    /**
     * For checking validation in attack phase
     * @param sourceCountry source country
     * @param targetCountry target country
     * @param num number of attacking army
     * @return true for validation
     * @throws AttackMoveAtLeastOneArmy army at least one
     * @throws AttackOutOfArmy out of army number in attacking country
     * @throws AttackCountryArmyMoreThanOne the number of army in attacking country must more than one
     * @throws AttackingCountryOwner the owner of attacking country must be current player
     * @throws AttackedCountryOwner the owner of attacked country must be the enemy
     */
    private boolean countryValidation(Country sourceCountry, Country targetCountry, int num) throws AttackCountryArmyMoreThanOne, AttackingCountryOwner, AttackedCountryOwner, AttackOutOfArmy, AttackMoveAtLeastOneArmy{
        if (sourceCountry.getArmy() > 1){
            if (sourceCountry.getOwner() == this){
                if (targetCountry.getOwner() != this){
                    if (num < sourceCountry.getArmy()){
                        if (num > 0){
                            return true;
                        }else{
                            throw new AttackMoveAtLeastOneArmy( 9);
                        }
                    }else {
                        throw new AttackOutOfArmy( 8);
                    }
                }else {
                    throw new AttackedCountryOwner( 7);
                }
            }else {
                throw new AttackingCountryOwner( 6);
            }
        }else{
            throw new AttackCountryArmyMoreThanOne( 5);
        }
    }

    /**
     * Attack phase
     * @param from source country
     * @param to target country
     * @param num the number of attacking army
     * @return true for conquest successful
     * @throws AttackMoveAtLeastOneArmy army at least one
     * @throws AttackOutOfArmy out of army number in attacking country
     * @throws AttackCountryArmyMoreThanOne the number of army in attacking country must more than one
     * @throws AttackingCountryOwner the owner of attacking country must be current player
     * @throws AttackedCountryOwner the owner of attacked country must be the enemy
     */
    protected boolean attack(Country from, Country to, int num) throws AttackedCountryOwner, AttackingCountryOwner, AttackCountryArmyMoreThanOne, AttackOutOfArmy, AttackMoveAtLeastOneArmy {

        boolean conquest = false;
        if (countryValidation(from, to, num)){
            System.out.println("Attack army:" +num);
            ArrayList<Integer> results = attackSimulation(to, num);

            //Conquest
            if (results.get(0) > 0){
                System.out.println("Win");
                from.attackDecreaseArmy(num - results.get(0));
                to.setOwner(this);
                to.attackDecreaseArmy(to.getArmy());
                conquest = true;

            } else {
                System.out.println("Lose");
                from.attackDecreaseArmy(num);
                to.attackDecreaseArmy(to.getArmy() - results.get(1));
            }

        }
        return conquest;
    }

    protected ArrayList<Integer> DiceArray (int digits){
        ArrayList<Integer> DiceArray = new ArrayList<Integer>();
        for (int i =0; i < digits; i++){
            int Dice = (int)(Math.random()*6)+1;
            DiceArray.add(Dice);
        }
        Collections.sort(DiceArray, Collections.reverseOrder());
        return DiceArray;
    }
//Results: first: rest of attacking second: rest of attacked
    protected ArrayList<Integer> attackSimulation(Country to, int num){
        ArrayList<Integer> results = new ArrayList<>();
        int liveArmy = num;
        int defenceArmy = to.getArmy();

        while (liveArmy != 0 && defenceArmy !=0){
            if (liveArmy > 2) {
                if (defenceArmy >= 2) {
                    ArrayList<Integer> attackDice = DiceArray(3);
                    ArrayList<Integer> defenceDice = DiceArray(2);
                    if (attackDice.get(0) > defenceDice.get(0)) {
                        defenceArmy --;
                    } else {
                        liveArmy --;
                    }
                    if (attackDice.get(1) > defenceDice.get(1)) {
                        defenceArmy --;
                    } else {
                        liveArmy --;
                    }
                } else {
                    ArrayList<Integer> attackDice = DiceArray(3);
                    int defenceDice1 = (int) (Math.random() * 6) + 1;
                    if (attackDice.get(0) > defenceDice1) {
                        defenceArmy --;
                    } else {
                        liveArmy --;
                    }
                }
            } else if (liveArmy == 2) {
                if (defenceArmy >= 2) {
                    ArrayList<Integer> attackDice = DiceArray(2);
                    ArrayList<Integer> defenceDice = DiceArray(2);
                    if (attackDice.get(0) > defenceDice.get(0)) {
                        defenceArmy --;
                    } else {
                        liveArmy --;
                    }
                    if (attackDice.get(1) > defenceDice.get(1)) {
                        defenceArmy --;
                    } else {
                        liveArmy --;
                    }
                } else {
                    ArrayList<Integer> attackDice = DiceArray(2);
                    int defenceDice1 = (int) (Math.random() * 6) + 1;
                    if (attackDice.get(0) > defenceDice1) {
                        defenceArmy --;
                    } else {
                        liveArmy --;
                    }
                }
            } else if (liveArmy == 1) {
                if (defenceArmy >= 2) {
                    int attackDice1 = (int) (Math.random() * 6) + 1;
                    ArrayList<Integer> defenceDice = DiceArray(2);
                    if (attackDice1 > defenceDice.get(0)) {
                        defenceArmy --;
                    } else {
                        liveArmy --;
                    }
                } else {
                    int attackDice1 = (int) (Math.random() * 6) + 1;
                    int defenceDice1 = (int) (Math.random() * 6) + 1;
                    if (attackDice1 > defenceDice1) {
                        defenceArmy --;
                    } else {
                        liveArmy --;
                    }
                }
            }
        }
        results.add(liveArmy);
        results.add(defenceArmy);
        System.out.println("attack:"+liveArmy);
        System.out.println("defence:"+defenceArmy);
        return results;
    }

}
