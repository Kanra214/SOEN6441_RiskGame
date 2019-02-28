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


    public CountryButton countryButton;

    private ArrayList<Country> neighbours;


    public Country(int x, int y, String name, Continent continent) {
        this.name = name;
        this.cont = continent;
        X=x;
        Y=y;
        countryButton = new CountryButton(x,y,this);

        this.neighbours = new ArrayList<>();


    }
    public void setPhase(Phases p){
        this.p = p;
    }
    public void increaseArmy(){
        this.army++;
        p.updateWindow();
    }

    public void increaseArmy(int i) throws IncreaseZeroArmyException {
        if(i < 1){
            throw new IncreaseZeroArmyException(4);
        }

        this.army += i;

        p.updateWindow();
    }

//whats this
    public void decreaseArmy(int i) throws OutOfArmyException {
        if(this.army <= i){
            throw new OutOfArmyException(0);
        }
        else {
            this.army -= i;
            p.updateWindow();
        }

    }

    public void setOwner(Player player) {

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

