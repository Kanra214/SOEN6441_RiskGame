package Models;//this is logic, controller calls phases parts through the game
import Models.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

public class Phases extends Observable {
    public int numOfPlayers;
    public ArrayList<Player> players;
    public ArrayList<Country> graph;
    public ArrayList<Continent> worldmap;
    public Player current_player;
    public int currentPhase;
    public int currentTurn = 0;
    protected boolean viewIsConnected = false;

    public int innerTurn = 0;

    public Phases(ArrayList<Country> graph, ArrayList<Continent> worldMap){

        this.graph = graph;
        this.worldmap = worldMap;
        players = new ArrayList<>();
    }

    int getInitialArmyCount(int number){
        switch (number){
            case 6: return 20;
            case 5: return 25;
            case 4: return 30;
            case 3: return 35;
            case 2: return 45;
            default: return 0;
        }
    }

    public void addPlayers(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
        for(int i = 0; i < numOfPlayers; i++){
            players.add(new Player(i, getInitialArmyCount(numOfPlayers),this));
//            players.add(new Player(i, 7));
        }
    }

    public int extraArmyFromContinent(Player player){
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

    public void setCurrentPhase(int currentPhase) {

        this.currentPhase = currentPhase;

        updateWindow();

    }

    public int getCurrentTurn() {
        return currentTurn;
    }
    //next player
    public void nextTurn(){
        currentTurn++;
        current_player = players.get(currentTurn % numOfPlayers);//first player is players[1]
        //check extra armies
        if (currentPhase == 1){
            phaseOneFirstStep(current_player);
        }

        if (currentPhase == 3){
            phaseThreeFirstStep();
        }
        updateWindow();

    }

    public Player getCurrent_player() {
        return current_player;
    }

    //determine if anyone gets extra armies
    public void phaseOneFirstStep (Player p){
        int extra = extraArmyFromContinent(p);
        p.getReinforcement(extra);
        System.out.println();
        System.out.println("Player "+p.getId()+" gets extra: "+extra);
        System.out.println("Player "+p.getId()+" color "+ current_player.getStringColor());
        System.out.println("Army left "+ current_player.getPlayerArmy());
    }

    public void phaseThreeFirstStep (){
        System.out.println();
        System.out.println("Player "+current_player.getId()+" color "+ current_player.getStringColor());
    }

    public void gameStart(){
        setCurrentPhase(1);
        nextTurn();
    }

    public void nextPhase(){
        this.currentPhase ++;
        if (currentPhase == 4) this.currentPhase = 1;

        if (currentPhase == 3){
            phaseThreeFirstStep();
        }
    }



    public void determineOrder(){//determine the round robin order for players by changing the order in the players field
        Collections.shuffle(players);

    }
    public void countryAssignment() {
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


        gameStart();

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





}
