package Models;
import java.awt.*;
import java.util.ArrayList;
import Game.Window;
import Panels.*;
public class Country extends CountryButton {

    private int x;
    private int y;
    private String name;


    private Continent cont;
    private Player owner;
    private int innerDiameter;
    private final int outerDiameter = 2;



    private ArrayList<Country> neighbours;


    public Country(int x, int y, String name, Continent continent, int innerDiameter) {


        this.x = x;
        this.y = y;
        this.innerDiameter = innerDiameter;
        this.name = name;
        this.neighbours = new ArrayList<>();
        this.cont = cont;
        this.setBounds(x,y,100,100);

    }


    public void setOwner(Player player) {
        this.owner = player;
        this.setForeground(owner.getPlayerColor());




    }
    public String getName(){
        return name;
    }
    public void addNeighbour(Country country){
        this.neighbours.add(country);
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval((int)this.x, (int)this.y, this.innerDiameter * 5 + 2, this.innerDiameter * 5 + 2);
        g.setColor(owner.getPlayerColor());
//        System.out.println(this.name + " color is set");
//        if (this.isClicked) {
//            g.setColor(Color.GREEN);
//        }

        g.fillOval((int)this.x + 1, (int)this.y + 1, this.innerDiameter * 5, this.innerDiameter * 5);
//        g.setColor(contColor);
        g.drawString(this.name, (int)this.x, (int)this.y);

        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(owner.army),(int)(x+innerDiameter*2),(int)(y+ innerDiameter * 3));
    }
}
