package Models;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import Game.Window;
import Panels.*;
import javax.swing.*;
import javax.swing.border.Border;



public class Country{


    private String name;


    private Continent cont;
    private Player owner;
    private int army = 1;

    public CountryButton countryButton;

    private ArrayList<Country> neighbours;


    public Country(int x, int y, String name, Continent continent, int innerDiameter) {
        this.name = name;
        this.cont = continent;
        countryButton = new CountryButton(x,y,this);

        this.neighbours = new ArrayList<>();

    }




    public void setOwner(Player player) {
        this.owner = player;


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






}

