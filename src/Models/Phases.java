package Models;
import Game.Controller;
import View_Components.CardExchangeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
/**
 * <h1>Phase</h1>
 * This class that controls logic of the game
 *
 */
public class Phases extends Observable {
    private int numOfPlayers = 1;
    private ArrayList<Player> players;
    private ArrayList<Country> graph;
    private ArrayList<Continent> worldmap;
    private Player current_player;
    private int currentPhase = 0;
    private int currentTurn = -1;
    private boolean viewIsConnected = false;
    private CardExchangeView cardView;
    private boolean at_least_once = false;
    public boolean gameOver = false;
    public boolean inBattle = false;//used to enable complete button
    private boolean attackingIsPossible = true;


    /**
     * Constructor
     * @param graph List of countries on the map
     * @param worldMap List of Continent on the map
     */
    public Phases(ArrayList<Country> graph, ArrayList<Continent> worldMap, CardExchangeView cardView) {
        this.cardView = cardView;
        this.graph = graph;
        this.worldmap = worldMap;
        players = new ArrayList<>();
        //give each country access to this so they can update the view
        for (Country country : graph) {
            country.setPhase(this);
        }
    }

    /**
     * Returns initial value of army for players
     * @param number of player
     * @return initial number
     */
    private int getInitialArmyCount(int number){
        switch (number){
            case 6: return 20;
            case 5: return 25;
            case 4: return 30;
            case 3: return 35;
            case 2: return 10;
            default: return 100;
        }
    }

    /**
     * Set up new game
     * @param numOfPlayers  int
     */
    public void gameSetUp(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
        for(int i = 0; i < numOfPlayers; i++){
           
			players.add(new Player(i, getInitialArmyCount(numOfPlayers),this));
        }
        determineOrder();
        countryAssignment();
        nextPhase();
    }

    /**
     * Gets list of countries
     * @return ArrayList of country
     */
    public ArrayList<Country> getGraph(){
        return graph;
    }


    /**
     * Gets list of Players
     * @return ArrayList of players
     */
    public ArrayList<Player> getPlayers(){
        return players;
    }


    /**
     * Gets reinforcements from all continents that the player owns
     * @param player    which player to check
     * @return int Reinforcements that the player gets from owning the whole continent
     */
    public int extraArmyFromContinent(Player player){
        int reinforcement = 0;
        for (Continent c: worldmap) {
            if(c.checkOwnership(player)){
                reinforcement+=c.getControl_value();
            }
        }
        return reinforcement;
    }


    /**
     * Get reinforcements according to the number of countries and continents that the player owns
     * @param player    which player to check
     * @return int  number of all countries devided by 3 plus army from continent
     */
    public int reinforcementArmy(Player player){
        int reinforcement = player.realms.size() / 3 + extraArmyFromContinent(player);
        if (reinforcement < 3) reinforcement = 3;
        return reinforcement;
    }


    /**
     * get current Phase
     * @return current phase
     */
    public int getCurrentPhase() {
        return currentPhase;
    }


    private void nextTurn(){
        currentTurn++;
        if (at_least_once){
            System.out.println("got a card");
            current_player.getCards().addCard();
        }
        at_least_once = false;
        current_player = players.get(currentTurn % numOfPlayers);//first player is players[0]
        if(currentPhase == 1) {
            phaseOneFirstStep();
        }
    }


    /**
     * Gets current player
     * @return current player
     */
    public Player getCurrent_player() {
        return current_player;
    }

    /**
     * First step of phase one where amount of the reinforcement army is being determined where min he gets is 3
     */
    private void phaseOneFirstStep() {
        cardView.setVisible(true);
        if (current_player.getCards().checkCardSum()){
            //TODO: force to change cards for now I will make automatic take cards


        }
        int reinforce = reinforcementArmy(current_player);
        if (reinforce == 0) {
            current_player.getReinforcement(3);
        }
        current_player.getReinforcement(reinforce);
    }


    /**
     * Move to the next phase and updates the window
     */
    public void nextPhase(){
        switch(currentPhase){
            case 0:
                if(currentTurn >= numOfPlayers - 1){
                    currentPhase = 1;
                }
                nextTurn();
                break;
            case 1:
                currentPhase++;
                if(!checkAttack(current_player)){
                    currentPhase++;
                }
                //TODO: if cant attack just go to pahse 3
                break;
            case 2:
                currentPhase++;
                break;
            case 3:

                currentPhase = 1;
                nextTurn();
        }
        updateWindow();
    }


    /**
     * Determine the round robin order for players by changing the order in the players field
     */
    private void determineOrder(){
        Collections.shuffle(players);

    }

    /**
     * Assign countries to players randomly in a round robin
     */
    private void countryAssignment() {
        Collections.shuffle(this.graph);
        int turnReference = 0;
        int turn = 0;
        for (Country country : this.graph) {
            Player player = players.get(turn);
            country.setOwner(player);
            player.deployArmy(country);
            turnReference++;
            turn = turnReference % players.size();
        }
    }


    /**
     * Notifies connected observers
     */
    protected void updateWindow(){
        if(viewIsConnected) {
            setChanged();
            notifyObservers();
        }
    }


    /**
     * Connect view
     */
    public void connectView(){
        viewIsConnected = true;
        updateWindow();
    }


    /**
     * Sends 1 army from current player to chosen Country during start up phase
     * @param chosen Country where to send army to
     */
    public void startUpPhase(Country chosen){
        current_player.deployArmy(chosen);
    }


    /**
     * Sends 1 army from current player to chosen Country during reinforcement phase
     * @param chosen Country where to send army to
     */
    public void reinforcementPhase(Country chosen){
        current_player.deployArmy(chosen);
        
    }
    /**
     * Attack phase
     */
    public boolean attackPhase(Country from, Country to) throws AttackingCountryOwner, AttackedCountryOwner, WrongDiceNumber, AttackCountryArmyMoreThanOne, TargetCountryNotAdjacent {
        boolean validated = false;//only first validateAttack() will throw exceptions to controller, after that, exceptions thrown by validateAttack() will be caught



        while (true) {
            try {
                int attackDice = Math.min(from.getArmy() - 1, 3);
                int defendDice = Math.min(to.getArmy(), 2);

                if(attackPhase(from, to, attackDice, defendDice)) {

                    return true;
                }
                validated = true;//any exception after this will be caught and break the while




            }

            catch(RiskGameException e) {
                if (validated) {
                    return false;
                }
                else {
                    throw e;
                }

            }




        }


    }

    /**
     * Attack phase
     * @param from  Country from where army will attacking
     * @param to    Country from where army will be attacked
     * @param attackDice   int number of dice to roll for attacker
     * @throws AttackCountryArmyMoreThanOne the number of army in attacking country must more than one
     * @throws AttackingCountryOwner the owner of attacking country must be current player
     * @throws AttackedCountryOwner the owner of attacked country must be the enemy
     * @return true if country is conquered, false otherwise
     */
    public boolean attackPhase(Country from, Country to, int attackDice, int defendDice) throws AttackingCountryOwner, AttackedCountryOwner, WrongDiceNumber, AttackCountryArmyMoreThanOne, TargetCountryNotAdjacent {
        try {
            if (attackValidation(from, to, attackDice,defendDice)) {

                attackSimulation(from, to, attackDice, defendDice);

            }
        }
        catch (OutOfArmyException e) {
            to.setOwner(current_player);
            if (checkWinner()) {//this attacker conquered all the countries
                gameOver = true;

            }
            return true;
        }
        checkAttackingIsPossible();

        return false;
    }

    /**
     * Attack phase
     * @param from  Country from where army will attacking
     * @param to    Country from where army will be attacked
     * @param num   int number of armies to choose
     * @throws AttackMoveAtLeastOneArmy army at least one
     * @throws AttackOutOfArmy out of army number in attacking country
     * @throws AttackCountryArmyMoreThanOne the number of army in attacking country must more than one
     * @throws AttackingCountryOwner the owner of attacking country must be current player
     * @throws AttackedCountryOwner the owner of attacked country must be the enemy
     */
    public boolean attackAll(Country from, Country to, int num) throws AttackMoveAtLeastOneArmy, AttackOutOfArmy, AttackCountryArmyMoreThanOne, AttackingCountryOwner, AttackedCountryOwner {
        boolean flag = current_player.attack(from, to, num, -1);
        if (flag) at_least_once = true;
        return flag;
    }

    public boolean attack(Country from, Country to, int numAttack, int numDefence) throws AttackMoveAtLeastOneArmy, AttackOutOfArmy, AttackCountryArmyMoreThanOne, AttackingCountryOwner, AttackedCountryOwner {
        boolean flag = current_player.attack(from, to, numAttack, numDefence);
        if (flag) at_least_once = true;
        return flag;
    }

    public void attackAssign(Country from, Country to, int num) throws MoveAtLeastOneArmyException, OutOfArmyException{
        current_player.attackAssign(from, to, num);
    }

    /**
     * Sends army from one country to another
     * @param from  Country from where army will be deducted
     * @param to    Country from where army will be sent
     * @param num   int number of armies to send
     * @throws CountryNotInRealms   country not owned by the player
     * @throws OutOfArmyException   not enough army to transfer
     * @throws NoSuchPathException  no path from owned countries between country
     * @throws SourceIsTargetException  source country and target country is the same
     * @throws MoveAtLeastOneArmyException  0 army chosen to move
     */
    public void fortificationsPhase(Country from, Country to, int num) throws SourceIsTargetException, MoveAtLeastOneArmyException, CountryNotInRealms, OutOfArmyException, NoSuchPathException {
        current_player.fortificate(from, to, num);
    }


    public boolean isOwnerOfAllCountries(Player p){
        for (Country c: graph) {
            if (c.getOwner() != p) {
                return false;
            }
        }
        return true;
    }


    public Player victoryCheck(){
        Player victor = null;
        for (Player p: players){
            if (isOwnerOfAllCountries(p)){
                victor = p;
                break;
            }
        }
        return victor;
    }



    protected boolean checkAttack(Player player){
        boolean val = true;
        int count_army = 0;
        int count_owner = 0;
        for (Country country : player.getRealms()){
            if (country.getArmy() == 1){
                count_army ++;
            }
            for (Country nei : country.getNeighbours()){
                if (nei.getOwner() != player){
                    count_owner ++;
                }
            }
        }
        if (count_army == player.getRealms().size()){
            val = false;
        }
        if (count_owner == 0){
            val = false;
        }
        return val;
    }


}
