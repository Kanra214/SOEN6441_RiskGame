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



    protected Player(int id, int army, Phases p) {
        this.id = id;
        this.realms = new ArrayList<>();
        this.unassigned_armies = army;
        this.playerColor = ALL_COLORS[id];
        this.p = p;

    }

//    public String getStringColor(){
//        if (playerColor == Color.RED) return "red";
//        if (playerColor == Color.BLUE) return "blue";
//        if (playerColor == Color.GREEN) return "green";
//        if (playerColor == Color.YELLOW) return "yellow";
//        if (playerColor == Color.LIGHT_GRAY) return "light gray";
//        if (playerColor == Color.ORANGE) return "orange";
//        return "uknown";
//    }
    public ArrayList<Country> getRealms() {
        return realms;
    }
    public Color getPlayerColor() {
        return playerColor;
    }

    public int getId() {
        return id;
    }

    public boolean armyLeft(){
        return unassigned_armies > 0;
    }

//    public Color getPlayerColor() {
//        return playerColor;
//    }

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
            if(armyLeft()) {
                setUnassigned_armies(unassigned_armies - 1);
                incrementMapArmies();
                country.increaseArmy();

            }
            else{
                System.out.println("out of armies");
            }
//            if(!armyLeft()) {
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

    //This function is used to in Phase3 whether player can move army from one country to another;
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
