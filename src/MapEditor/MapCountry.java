package MapEditor;

import java.util.ArrayList;

import Models.Continent;
import Models.Country;
import Models.Player;
import View_Components.CountryButton;

public class MapCountry {

    private String name;
    private String cont;
    private Player owner;
    private int X;
    private int Y;
    private int army = 0;

    public CountryButton countryButton;

    private ArrayList<String> neighbours;


    public MapCountry(int x, int y, String name, String continent, int innerDiameter) {
        this.name = name;
        this.cont = continent;
        X=x;
        Y=y;
        //countryButton = new CountryButton(x,y,this);

        this.neighbours = new ArrayList<>();

    }

    public void incrementArmy() {
        this.army++;
    }

    public void setOwner(Player player) {
        this.owner = player;
    }

    public void sendArmy() {
        this.army++;
    }


    public String getName(){
        return name;
    }
    
    public int getX(){
        return X;
    }
    public int getY(){
        return Y;
    }
    
    public void setName(String name) {
    	this.name=name;
    }
    public void setContinent(String continent) {
    	this.cont=continent;
    }
    
    public ArrayList<String> getNeighbours(){return neighbours;}
    
    public void printNeighbors() {
    	
    	for(String o: neighbours) {
    		System.out.print(o+" ");
    	}
    }
    
    public void addNeighbour(String country) {
        this.neighbours.add(country);
    }

    public String getCont() {
        return cont;
    }


    public Player getOwner () {
        return owner;

    }
    public int getArmy(){
        return army;
    }
    







}
