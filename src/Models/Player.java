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
     *
     * @param id player ID
     * @param army initial army count for the player
     * @param p Phase for player to update window
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
     * @return ArrayList of countries the player controls
     */
    public ArrayList<Country> getRealms() {
        return realms;
    }


    /**
     * Gets the player's color
     * @return Color of the player
     */
    public Color getPlayerColor() {
        return playerColor;
    }


    /**
     * Gets the player ID
     * @return int - Player ID
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
     *
     * @param reinforcements reinforcement army for the current player
     */
    protected void getReinforcement(int reinforcements){
        this.setUnassigned_armies(unassigned_armies + reinforcements);
    }


    public int getUnassigned_armies() {
        return unassigned_armies;
    }


    /**
     * Get total deployed army belonging to the current player on the map
     * @return the total number of armies this player owns on the world map(excluding unassigned_armies)
     */
    public int getMapArmies(){
        return mapArmies;
    }


    /**
     * Assigns free army to the country
     * @param country to which country is the player assigning his army
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
     * This function is used to in Phase3 whether player can move army from one country to another;
     * @param sourceCountry from country
     * @param targetCountry to country
     * @return True/False if there is a path between source country to the target country by going through the countries he owns
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


    /**
     * Move army from one Country to another
     * @param from from which country
     * @param to to which country
     * @param num what number of army
     * @throws CountryNotInRealms
     * @throws OutOfArmyException
     * @throws NoSuchPathException
     * @throws SourceIsTargetException
     * @throws MoveAtLeastOneArmyException
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

}
