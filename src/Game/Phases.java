package Game;//this is logic, controller calls phases parts through the game
import Models.*;
import java.util.ArrayList;
import java.util.Collections;

public class Phases {
    protected int numOfPlayers;
    protected ArrayList<Player> players;
    protected ArrayList<Country> graph;
    protected ArrayList<Continent> worldmap;
    protected Player current_player;
    protected int currentPhase;
    protected int currentTurn = 0;

    protected int innerTurn = 0;

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
//            players.add(new Player(i, getInitialArmyCount(numOfPlayers)));
            players.add(new Player(i, 7));
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
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void nextTurn(){
        currentTurn++;
        current_player = players.get(currentTurn % numOfPlayers);
        if (currentPhase == 1){
            phaseOneFirstStep(current_player);
        }
        if (currentPhase == 3){
            phaseThreeFirstStep();
        }

    }

    public Player getCurrent_player() {
        return current_player;
    }

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
        currentPhase = 1;
        nextTurn();
    }

    public void nextPhase(){
        this.currentPhase ++;
        if (currentPhase == 4) this.currentPhase = 1;

        if (currentPhase == 3){
            phaseThreeFirstStep();
        }
    }



    protected void determineOrder(){//determine the round robin order for players by changing the order in the players field
        Collections.shuffle(players);

    }
    protected void countryAssignment() {
        Collections.shuffle(this.graph);
        int turnReference = 0;
        int turn = 0;
        for (Country country : this.graph) {
            Player player = players.get(turn);
            player.realms.add(country);
            player.deployArmy();
            country.setOwner(player);
            turnReference++;
            turn = turnReference % players.size();
        }

        gameStart();

    }
}
