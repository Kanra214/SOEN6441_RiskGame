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
    public Phases(ArrayList<Country> graph, ArrayList<Continent> worldmap){

        this.graph = graph;
        this.worldmap = worldmap;
        players = new ArrayList<>();



    }
    public void addPlayers(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
        for(int i = 0; i < numOfPlayers; i++){
            players.add(new Player(i));
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
            country.setOwner(player);
            turnReference++;
            turn = turnReference % players.size();

        }
    }



}
