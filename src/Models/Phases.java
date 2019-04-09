package Models;
//import View_Components.CardExchangeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
/**
 * <h1>Phase</h1>
 * This class that controls logic of the game
 *
 */
public class Phases extends Observable implements Serializable {
    private int numOfPlayers = 0;
    private ArrayList<Player> players;
    private ArrayList<Country> graph;
    private ArrayList<Continent> worldmap;
    private Player current_player;
    protected Player rival;//the player being attacked in the attack phase
    protected Country beingAttacked;
    private int currentPhase = 0;
    private int currentTurn = -1;
    private boolean viewIsConnected = false;
    protected boolean gameOver = false;
    protected boolean inBattle = false;//used to enable complete button, when during the dice consuming battle, player can't go to the next phase
    public boolean cardExchanged = false;
    protected boolean fortified = false;
    private boolean attackingIsPossible = true;//if false, the game automatically skip the attack phase
    public int maxTurn;
    public boolean tournament = false;

    public String winner = null;//TODO



    protected boolean at_least_once = false;//used to determine the current player is qualified  to receive a card

    public boolean isGameOver() {
        if (winner != null){
            System.out.println(winner);
        }
        return gameOver;
    }



    public boolean isFortified() {
        return fortified;
    }



    /**
     * Constructor
     * @param graph    List of countries on the map
     * @param worldMap List of Continent on the map
     */
    public Phases(ArrayList<Country> graph, ArrayList<Continent> worldMap) {
        this.graph = graph;
        this.worldmap = worldMap;
        players = new ArrayList<>();
        //give each country access to this so they can update the view
        for (Country country : graph) {
            country.setPhase(this);
        }
    }

    /**
     * Give each player initial armies of which the number is depended on the number of players
     * @param number the number of players
     * @return the number of initial armies
     */
    private int getInitialArmyCount(int number) {
        switch (number) {
            case 6:
                return 20;
            case 5:
                return 25;
            case 4:
                return 30;
            case 3:
                return 35;
            case 2:
                return 45;//TODO 45
            default:
                return 100;
        }
    }

    /**
     * Set up new game: instantiate players, determine the order of players, randomly assign countries and start start up phase
     * @param playerValue the number and type of players
     */
    public void gameSetUp(int[] playerValue) {
        int playerIdCount = 0;
        for(int i = 0; i < playerValue.length; i++){
            numOfPlayers += playerValue[i];
        }

        for(int i = 0; i < playerValue.length; i++){
            for(int j = 0; j < playerValue[i]; j++){
                Player player = new Player(playerIdCount++, getInitialArmyCount(numOfPlayers), this);
                player.setStrategy(intToStrategy(i));
                players.add(player);
            }
        }

        if(playerValue[0] == -1){
            tournament = true;
        }

        determineOrder();
        countryAssignment();
        current_player = players.get(0);
        connectView();
//        nextPhase();
    }
    private Strategy intToStrategy(int i){
      
        
        switch(i){
            case 0:
                return new Human();
            case 1:
                return new Aggressive();
            case 2:
                return new Benevolent();
            case 3:
                return new Random();
            case 4:
                return new Cheater();
            default:
                return null;


        }
    }

    /**
     * Gets list of countries
     *
     * @return ArrayList of country
     */
    public ArrayList<Country> getGraph() {
        return graph;
    }


    /**
     * Gets list of Players
     *
     * @return ArrayList of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }


    /**
     * Gets reinforcements from all continents that the player owns
     *
     * @param player which player to check
     * @return int Reinforcements that the player gets from owning the whole continent
     */
    public int extraArmyFromContinent(Player player) {
        int reinforcement = 0;
        for (Continent c : worldmap) {
            if (checkContinentOwner(c, player)) {
                reinforcement += c.getControl_value();
            }
        }
        return reinforcement;
    }


    /**
     * Get reinforcements according to the number of countries and continents that the player owns
     *
     * @param player which player to check
     * @return int  number of all countries devided by 3 plus army from continent
     */
    public int reinforcementArmy(Player player) {

        int fromContinent = extraArmyFromContinent(player);
    	int reinforcement = player.realms.size() / 3 + fromContinent;
    	if(player.getUnassigned_armies()+reinforcement < 3) {
    		System.out.println(player.getUnassigned_armies());
    		reinforcement = 3;
    	}
    	System.out.println("from card: " + current_player.getUnassigned_armies());
    	System.out.println("reinforcement: " + reinforcement + "(continent value = " + fromContinent + ")");


        return reinforcement;
    }


    /**
     * get current Phase
     *
     * @return current phase
     */
    public int getCurrentPhase() {
        
    	return currentPhase;
    }

    /**
     * next player's turn
     */
    private void nextTurn() {


        if (at_least_once){
            System.out.println("got a card");
            current_player.addPlayerOneCard();
        }
        at_least_once = false;

        currentTurn++;
        System.out.println("-------------current turn: " + currentTurn + "---------------");

        if (tournament){
            if (currentTurn > maxTurn){
                gameOver = true;
                //winner.add("draw");
                winner = "Draw";
                return;
            }
        }
//        if (gameOver){
//            //System.exit(0);
//            return;
//        }

        current_player = players.get(currentTurn % numOfPlayers);//first player is players[0]

        if (current_player.getRealms().size() == 0) {//if the player is ruled out of the game
            System.out.println("current player " + current_player.getId() +" is dead");
            nextTurn();

        }



    }



    /**
     * Gets current player
     *
     * @return current player
     */
    public Player getCurrent_player() {
    	
    	return current_player;
    }

    /**
     * First step of phase one where amount of the reinforcement army is being determined where min he gets is 3
     */
    public void phaseOneFirstStep() {

        current_player.getReinforcement(reinforcementArmy(current_player));


    }


    /**
     * Move to the next phase and updates the window
     */
    public void nextPhase() {
        System.out.println("nextPhase()");

        switch (currentPhase) {
            case 0:

                nextTurn();
                if (gameOver){
                    updateWindow();
                    return;
                }

                if (currentTurn > numOfPlayers - 1) {
                    currentPhase = 1;
                }
                current_player.executeStrategy();

                break;
            case 1:
                currentPhase = 2;
                checkAttackingIsPossible();//every beggining of phase two needs to be checked

                break;
            case 2:

                currentPhase = 3;



                break;
            case 3:
//                if (current_player.getRealms().size() == 0) {
//                    nextPhase();
//                }
//                else {
                    nextTurn();
                    if (gameOver){
                        updateWindow();
                        return;
                    }
                    fortified = false;
                    cardExchanged = false;
                    currentPhase = 1;
                    current_player.executeStrategy();

//                }
                break;
        }

        updateWindow();
    }


    /**
     * Determine the round robin order for players by changing the order in the players field
     */
    private void determineOrder() {
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
            try {
                player.reinforce(country);
            } catch (OutOfArmyException e) {
                System.out.println("Not possible");
            }
            checkContinentOwner(country.getCont(),player);
            turnReference++;
            turn = turnReference % players.size();
        }

    }


    /**
     * Notifies connected observers
     */
    protected void updateWindow() {
        if (viewIsConnected) {
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Notifies connected observers
     */
    protected void updateWindow(Object o) {
        if (viewIsConnected) {
            setChanged();
            notifyObservers(o);
        }
    }


    /**
     * Connect view
     */
    public void connectView() {
        viewIsConnected = true;
        updateWindow();
    }


    /**
     * Sends 1 army from current player to chosen Country during start up phase
     *
     * @param chosen Country where to send army to
     */
    public void startUpPhase(Country chosen) {
        try {
            current_player.reinforce(chosen);
        } catch (RiskGameException e) {
            System.out.println("out of army in start phase");

        }
    }




    public void checkturn(int turn){
        maxTurn = turn;
    }




    protected void attackSimulation(Country from, Country to, int attackDice, int defendDice) throws OutOfArmyException {

        inBattle(true);
        current_player.setNumOfDice(attackDice);


        current_player.rollDice();
        rival.rollDice();

        while (current_player.dice.size() > 0 && rival.dice.size() > 0) {


            try {


                if (current_player.dice.get(0) > rival.dice.get(0)) {

                    losesAnArmy(rival, to);
                } else {
                    losesAnArmy(current_player, from);
                }
                current_player.dice.remove(0);
                rival.dice.remove(0);

            } catch (OutOfArmyException e) {
//                inBattle(false);
                current_player.dice.clear();
                rival.dice.clear();

                throw e;
            }

        }
        current_player.dice.clear();
        rival.dice.clear();
        inBattle(false);


    }


    /**
     * check winner
     * @return true for win the game
     */
    public boolean checkWinner() {//check if current player win the whole game
        System.out.println("Checking winner");
        if(current_player.realms.size() == graph.size()){
            gameOver = true;
            updateWindow();
            System.out.println("we have a winner");
            return true;
        }
        else{
            return false;
        }

    }

    /**
     * check attack possible or not
     */
    public void checkAttackingIsPossible() {
        //check if the current player is still possible to continue attacking phase
        //return false if no such possibility

        for (Country attack : current_player.realms) {
            if (attack.getArmy() > 1) {
                for (Country defend : attack.getNeighbours()) {
                    if (defend.getOwner() != current_player) {
                        attackingIsPossible = true;
                        return;

                    }
                }
            }

        }

        attackingIsPossible = false;
        System.out.println("attacking not possible");
        updateWindow();
        if(current_player.getStrategy().getName().equals("Human")) {
            System.out.println("auto nextphase");
            nextPhase();
        }

    }



    /**
     * For checking validation in attack phase
     *
     * @param sourceCountry source country
     * @param targetCountry target country
     * @param attackDice    number of attacker's dice
     * @param defendDice    number of defender's dice
     * @return true for validation
     * @throws AttackCountryArmyMoreThanOne the number of army in attacking country must more than one
     * @throws AttackingCountryOwner        the owner of attacking country must be current player
     * @throws AttackedCountryOwner         the owner of attacked country must be the enemy
     * @throws WrongDiceNumber the wrong dice number
     * @throws TargetCountryNotAdjacent the Target Country Not Adjacent
     */
    public boolean attackValidation(Country sourceCountry, Country targetCountry, int attackDice, int defendDice) throws AttackingCountryOwner, AttackedCountryOwner, WrongDiceNumber, AttackCountryArmyMoreThanOne, TargetCountryNotAdjacent {
        if (sourceCountry.getArmy() >= 2) {
            if (sourceCountry.getOwner() == current_player) {
                if (targetCountry.getOwner() != current_player) {
                    if (sourceCountry.getNeighbours().contains(targetCountry)) {
                        if (attackDice <= sourceCountry.getArmy() - 1 && attackDice <= 3 && attackDice > 0) {
                            if (defendDice <= targetCountry.getArmy() && defendDice <= 2 && defendDice > 0) {
                                return true;


                            } else {
                                throw new WrongDiceNumber(targetCountry.getOwner());

                            }
                        } else {
                            throw new WrongDiceNumber(current_player);
                        }
                    } else {
                        throw new TargetCountryNotAdjacent();
                    }
                } else {
                    throw new AttackedCountryOwner();
                }
            } else {
                throw new AttackingCountryOwner();
            }
        } else {
            throw new AttackCountryArmyMoreThanOne();
        }

    }

    /**
     * deployment After Conquer
     * @param from current country
     * @param to conquered country
     * @param num number of army assigned
     * @return true for assign success
     * @throws MustBeEqualOrMoreThanNumOfDice throw exception
     * @throws SourceIsTargetException throw exception
     * @throws NoSuchPathException throw exception
     * @throws CountryNotInRealms throw exception
     * @throws OutOfArmyException throw exception
     * @throws MoveAtLeastOneArmyException throw exception
     */


    /**
     * get whole the countries in the map
     * @return countries arraylist
     */
    public ArrayList<Continent> getWorldmap(){
        return worldmap;
    }

    private void losesAnArmy(Player player, Country country) throws OutOfArmyException {
        updateWindow(player);
        try {
            player.loseArmy(country);


        } catch (OutOfArmyException e) {

            throw e;
        }

    }

    void inBattle(boolean flag) {
        inBattle = flag;
        updateWindow();
    }

    /**
     * get in battle
     * @return true for in
     */
    public boolean getInBattle() {
        return inBattle;
    }

    /**
     * get rival
     * @return player rival
     */
    public Player getRival() {
        return rival;
    }

    /**
     * get attack possible or not
     * @return boolean
     */
    public boolean getAttackingIsPossible() {
        return attackingIsPossible;
    }

    /**
     * get current turn
     * @return turn number
     */
    public int getCurrentTurn(){return currentTurn;
    }

    /**
     * get number of player in the game
     * @return number of player
     */
    public int getNumOfPlayers(){return numOfPlayers;}
    protected boolean checkContinentOwner(Continent cont, Player player){
        boolean flag = cont.checkOwnership(player);
        updateWindow();
        return flag;
    }

    /**
     * change current player
     * @param current_player current player
     */
    public void setCurrent_player(Player current_player) {
        this.current_player = current_player;
    }


    public void resume(){
        current_player.executeStrategy();
        updateWindow();

    }

    public Country getBeingAttacked(){
        return beingAttacked;
    }
}