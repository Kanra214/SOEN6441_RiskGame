package Models;
import java.awt.*;
import java.util.ArrayList;
import Game.Window;
import Panels.*;
import javax.swing.*;


public class Country extends JButton {

    private int x;
    private int y;
    private String title;


    private Continent cont;
    private Player owner;
    private int innerDiameter;
    private final int outerDiameter = 2;



    private ArrayList<Country> neighbours;


    public Country(int x, int y, String title, Continent continent, int innerDiameter) {
        super(title);



        this.x = x;
        this.y = y;
        this.innerDiameter = innerDiameter;
        this.neighbours = new ArrayList<>();
        this.cont = cont;
//        this.setSize(new Dimension(innerDiameter, innerDiameter));
        this.setBounds(x,y,100,20);
        


    }


    public void setOwner(Player player) {
        this.owner = player;
        this.setOpaque(true);
        this.setForeground(owner.getPlayerColor());




    }
    public String getName(){
        return title;
    }
    public void addNeighbour(Country country){
        this.neighbours.add(country);
    }




}

