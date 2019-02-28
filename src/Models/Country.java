package Models;
import java.util.ArrayList;

import View_Components.*;


public class Country {


    private String name;
    private Phases p;



    private Continent cont;
    private Player owner;
    private int army = 0;
    private int X = 0;
    private int Y = 0;


    private ArrayList<Country> neighbours;

//public constructor because mapLoader needs this
    public Country(int x, int y, String name, Continent continent) {
        this.name = name;
        this.cont = continent;
        X=x;
        Y=y;

        this.neighbours = new ArrayList<>();


    }
    protected void setPhase(Phases p){
        this.p = p;
    }
    protected void increaseArmy(){
        this.army++;
        p.updateWindow();
    }

    protected void increaseArmy(int i) throws MoveAtLeastOneArmyException {
        if(i < 1){
            throw new MoveAtLeastOneArmyException(4);


        }
        else{
            this.army += i;
            p.updateWindow();
        }




    }

//whats this
    protected void decreaseArmy(int i) throws OutOfArmyException, MoveAtLeastOneArmyException {
        if(i < 1){
            throw new MoveAtLeastOneArmyException(4);
        }
        if(this.army <= i){
            throw new OutOfArmyException(0);
        }


        else {
            this.army -= i;
            p.updateWindow();
        }


    }

    protected void setOwner(Player player) {

        this.owner = player;
        player.realms.add(this);
        p.updateWindow();
    }

//    public void sendArmy() {
//        this.army++;
//    }


    public String getName(){
        return name;
    }

    public ArrayList<Country> getNeighbours(){return neighbours;}
    
    public String printNeighbors() {
    	String reNei="";
    	for(Country nei: getNeighbours()) {
    		System.out.println(nei.getName()+" "+getNeighbours().size());
    		reNei=reNei+nei.getName()+",";

    		System.out.println(reNei);
    	}
    	return reNei;
    }
//public because MapLoader needs this
    public void addNeighbour(Country country) {

        this.neighbours.add(country);

    }

    public Continent getCont() {
        return cont;
    }

    public String getContName() {
        return cont.getName();
    }


    public Player getOwner () {
        return owner;

    }
    public int getArmy(){
        return army;
    }
    public int getX(){
        return X;
    }
    public int getY(){
        return Y;
    }





}

