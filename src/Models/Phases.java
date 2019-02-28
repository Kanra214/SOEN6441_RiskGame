package Models;//this is logic, controller calls phases parts through the game
import Models.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

public class Phases extends Observable {
    private int numOfPlayers;
    private ArrayList<Player> players;
    private ArrayList<Country> graph;
    private ArrayList<Continent> worldmap;
    private Player current_player;
    private int currentPhase = 0;
    private int currentTurn = -1;
    private boolean viewIsConnected = false;

//    public int innerTurn = 0;

    public Phases(ArrayList<Country> graph, ArrayList<Continent> worldMap) {

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
            default: return 0;
        }
    }

    public void gameSetUp(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
        for(int i = 0; i < numOfPlayers; i++){
            players.add(new Player(i, getInitialArmyCount(numOfPlayers),this));
//            players.add(new Player(i, 7));
        }
        determineOrder();
        countryAssignment();
        nextPhase();
    }
    public ArrayList<Country> getGraph(){
        return graph;
    }
    public ArrayList<Player> getPlayers(){
        return players;
    }

    private int extraArmyFromContinent(Player player){
        int reinforocement = 0;
        for (Continent c: worldmap) {
            if(c.checkOwnership(player)){
                reinforocement+=c.getControl_value();
            }
        }
        return reinforocement;
    }

    public int getCurrentPhase() {
        return currentPhase;
    }



//    public int getCurrentTurn() {
//        return currentTurn;
//    }
    //next player
    private void nextTurn(){
        currentTurn++;
        current_player = players.get(currentTurn % numOfPlayers);//first player is players[0]

//        currentPhase = 1; //this is commented because nextTurn doesnt mean phase = 1, might still phase = 0
        if(currentPhase == 1) {
            phaseOneFirstStep();
        }

//        if (currentPhase == 3){
//            phaseThreeFirstStep();
//        }


    }

    public Player getCurrent_player() {
        return current_player;
    }

    //determine if anyone gets extra armies
    private void phaseOneFirstStep (){
        int extra = extraArmyFromContinent(current_player);
        current_player.getReinforcement(extra);
//        System.out.println();
//        System.out.println("Player "+current_player.getId()+" gets extra: "+extra);
//        System.out.println("Player "+current_player.getId()+" color "+ current_player.getStringColor());
//        System.out.println("Army left "+ current_player.getPlayerArmy());
    }
//what doest this do?
//    public void phaseThreeFirstStep (){
//        System.out.println();
//        System.out.println("Player "+current_player.getId()+" color "+ current_player.getStringColor());
//    }


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

//        if (currentPhase == 3){
//            phaseThreeFirstStep();
//        }

        updateWindow();



    }



    private void determineOrder(){//determine the round robin order for players by changing the order in the players field
        Collections.shuffle(players);

    }
    private void countryAssignment() {
        Collections.shuffle(this.graph);
        int turnReference = 0;
        int turn = 0;
        for (Country country : this.graph) {
            Player player = players.get(turn);
//            player.realms.add(country); // not needed? no because country.setOwner does this too
            country.setOwner(player);
            player.deployArmy(country);
            turnReference++;
            turn = turnReference % players.size();
        }




    }
    protected void updateWindow(){
        if(viewIsConnected) {
            setChanged();
            notifyObservers();
        }
    }

    public void connectView(){
        viewIsConnected = true;
        updateWindow();
    }



    public void startUpPhase(Country chosen){

        current_player.deployArmy(chosen);
    }
    public void reinforcementPhase(Country chosen){
        current_player.deployArmy(chosen);
    }
    public void attackPhase(){
        System.out.println("in phase 2");
    }

    public void fortificatePhase(Country from, Country to, int num) throws SourceIsTargetException, MoveAtLeastOneArmyException, CountryNotInRealms, OutOfArmyException, NoSuchPathException {
        current_player.fortificate(from, to, num);
    }







}
