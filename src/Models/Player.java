/**
 * <h1>Player</h1>
 * This class give the player with specific characteristics
 */
package Models;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <h1>Player</h1>
 * This class that controls logic of the Player
 */
public class Player {
    //view needs unassigned_armies, mapArmies, id, playerColor

    private static Color[] ALL_COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.LIGHT_GRAY, Color.ORANGE};
    private Phases p;



    private Color playerColor;
    private int unassigned_armies;
    private int mapArmies = 0;//the total number of armies this player owns on the world map(excluding unassigned_armies)
    protected ArrayList<Country> realms;
    private int id;//this is primary key for players


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
     * @return Color    color of the player
     */
    public Color getPlayerColor() {
        return playerColor;
    }


    /**
     * Gets the player ID
     * @return int  Player ID
     */
    public int getId() {
        return id;
    }


    /**
     * Determines if the player still have unassigned armies
     * @return boolean True if there is still some army left and False otherwise
     */
    public boolean isArmyLeft(){
        return unassigned_armies > 0;
    }

    protected void getReinforcement(int extra){
        this.setUnassigned_armies(unassigned_armies + extra);
    }

    public int getUnassigned_armies() {
        return unassigned_armies;
    }
    public int getMapArmies(){
        return mapArmies;
    }


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
//            if(!isArmyLeft()) {
//
//                p.nextPhase();
//            }



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
     * This function is used to in Phase3 whether player can move army from one country to another;
     * @param sourceCountry The start country
     * @param targetCountry The target country
     * @return boolean
     * @throws CountryNotInRealms
     * @throws SourceIsTargetException
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

}
