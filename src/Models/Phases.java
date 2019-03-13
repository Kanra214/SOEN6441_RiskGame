package Models;
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

    private int getInitialArmyCount(int number){
        switch (number){
            case 6: return 20;
            case 5: return 25;
            case 4: return 30;
            case 3: return 35;
            case 2: return 45;
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
        if (current_player.getCards().checkCardSum()){
            cardView.setVisible(true);
            //TODO: dialog window asking what to do
            cardView.setVisible(false);

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
    //public void attackPhase(){
//        System.out.println("in phase 2");
//    }

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
        return current_player.attack(from, to, num, -1);
    }

    public boolean attack(Country from, Country to, int numAttack, int numDefence) throws AttackMoveAtLeastOneArmy, AttackOutOfArmy, AttackCountryArmyMoreThanOne, AttackingCountryOwner, AttackedCountryOwner {
        return current_player.attack(from, to, numAttack, numDefence);
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



}
