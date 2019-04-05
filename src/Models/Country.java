package Models;
import java.io.Serializable;
import java.util.ArrayList;

import View_Components.*;

/**
 * <h1>Country</h1>
 * This class is for country object
 */
public class Country implements Serializable {


    private String name;
    private Phases p;
    private Continent cont;
    private Player owner;
    private int army = 0;
    private int X = 0;
    private int Y = 0;


    private ArrayList<Country> neighbours;

//public constructor because mapLoader needs this

    /**
     * Constructor
     * @param x coordinate x
     * @param y coordinate y
     * @param name name of country
     * @param continent owner to which continent
     */
    public Country(int x, int y, String name, Continent continent) {
        this.name = name;
        this.cont = continent;
        X=x;
        Y=y;

        this.neighbours = new ArrayList<>();


    }

    /**
     * Set current phase
     * @param p current phase
     */
    protected void setPhase(Phases p){
        this.p = p;
    }

    /**
     * Increase army number in panel
     */
    protected void incrementArmy(){
        this.army++;
        p.updateWindow();
    }
    protected void decrementArmy() throws OutOfArmyException {
        if(this.army <= 1){
            if(p.getCurrentPhase() == 2){
                this.army = 0;
                p.updateWindow();
                throw new OutOfArmyException();
            }

        }
        else{
            this.army --;
            p.updateWindow();
        }

    }

    /**
     * Increase army number by specific amount
     * @param i The number of army moved
     * @throws MoveAtLeastOneArmyException
     */
    protected void increaseArmy(int i) throws MoveAtLeastOneArmyException {
        if(i < 1){

            throw new MoveAtLeastOneArmyException();


        }
        else{
            this.army += i;
            p.updateWindow();
        }




    }

    /**
     * Decrease army number by specific amount
     * @param i The number of army moved
     * @throws OutOfArmyException
     * @throws MoveAtLeastOneArmyException
     */
    protected void decreaseArmy(int i) throws OutOfArmyException, MoveAtLeastOneArmyException {
        if(i < 1){


            throw new MoveAtLeastOneArmyException();


        }
        if(this.army <= i){

            throw new OutOfArmyException();

        }


        else {
            this.army -= i;
            p.updateWindow();
        }


    }


    /**
     * Set owner to country
     * @param player owner is a object of player
     */
    public void setOwner(Player player) {
        this.owner = player;
        player.realms.add(this);
        p.updateWindow();
    }

    /**
     * change ownership for contry
     * @param p1 player1
     * @param p2 player2
     */
    protected void swapOwnership(Player p1, Player p2){
        this.setOwner(p2);
        this.deleteOwner(p1);

    }

    /**
     * delete ownership for country
     * @param p1 player1
     */
    protected void deleteOwner(Player p1){
        p1.getRealms().remove(this);
        p.updateWindow();
    }

    /**
     * Get country's name
     * @return name of country
     */
    public String getName(){
        return name;
    }

    /**
     * Get neighbours relationships
     * @return neighnours arraylist
     */
    public ArrayList<Country> getNeighbours(){return neighbours;}

    /**
     * Output neighbors relationships
     * @return  neighbors relationships as one string
     */
    public String printNeighbors() {
    	String reNei="";
    	for(Country nei: getNeighbours()) {
    		System.out.println(nei.getName()+" "+getNeighbours().size());
    		reNei=reNei+nei.getName()+",";

    		System.out.println(reNei);
    	}
    	return reNei;
    }

    /**
     * Add neighbour relationship
     * @param country another country object
     */
    public void addNeighbour(Country country) {

        this.neighbours.add(country);

    }

    /**
     * Get continent
     * @return continent object
     */
    public Continent getCont() {
        return cont;
    }

    /**
     * Get continent name
     * @return name of continent
     */
    public String getContName() {
        return cont.getName();
    }

    /**
     * Get country's owner
     * @return player object
     */
    public Player getOwner () {
        return owner;

    }

    /**
     * Get army number
     * @return army number
     */
    public int getArmy(){
        return army;
    }

    /**
     * Get coordinate X
     * @return X
     */
    public int getX(){
        return X;
    }

    /**
     * Get coordiante Y
     * @return Y
     */
    public int getY(){
        return Y;
    }

    /**
     * get Phase object
     * @return get current phase
     */
    public Phases getP() {
        return p;
    }

    /**
     * set army
     * @param army number of army
     */
    public void setArmy(int army) {
        this.army = army;
    }
}

