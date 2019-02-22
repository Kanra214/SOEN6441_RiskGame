package Models;
import java.util.ArrayList;

import View_Components.*;


public class Country{


    private String name;
    private int X,Y;

    private Continent cont;
    private Player owner;
    private int army = 0;

    public CountryButton countryButton;

    private ArrayList<Country> neighbours;


    public Country(int x, int y, String name, Continent continent) {
        this.name = name;
        this.cont = continent;
        this.X = x;
        this.Y = y;
        countryButton = new CountryButton(x,y,this);

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

    public ArrayList<Country> getNeighbours(){return neighbours;}
    public void addNeighbour(Country country) {
        this.neighbours.add(country);
    }

    public Continent getCont() {
        return cont;
    }


    public Player getOwner () {
        return owner;

    }
    public int getArmy(){
        return army;
    }
    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }






}

