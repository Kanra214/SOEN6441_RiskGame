package Models;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import Game.Window;
import Panels.*;
import javax.swing.*;



public class Country extends JButton {

    private int x;
    private int y;
    private String name;


    private Continent cont;
    private Player owner;
    private int innerDiameter;
    private int army = 1;




    private ArrayList<Country> neighbours;


    public Country(int x, int y, String title, Continent continent, int innerDiameter) {
        super(title);
        this.name = title;



        this.x = x;
        this.y = y;
        this.innerDiameter = innerDiameter;
        this.neighbours = new ArrayList<>();
        this.cont = continent;
//        this.setSize(new Dimension(innerDiameter, innerDiameter));
        setContentAreaFilled(false);
        this.setBounds(x,y,80,60 );




    }


    public void setOwner(Player player) {
        this.owner = player;
        this.setOpaque(true);
        this.setForeground(owner.getPlayerColor());




    }
    public String getName(){
        return name;
    }
    public void addNeighbour(Country country){
        this.neighbours.add(country);
    }
    protected void paintComponent(Graphics g) {
        // if the button is pressed and ready to be released


        if (getModel().isArmed()) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }


//        g.fillOval(0, 0, getSize().width-1, getSize().height-1);

        super.paintComponent(g);
    }
    protected void paintBorder(Graphics g) {
        g.setColor(cont.getContColor());
        g.fillRoundRect(0, 0, getSize().width-1, getSize().height-1,0,0);
        g.setColor(getForeground());
        g.fillRoundRect(15,15,getSize().width-31, getSize().height-31,0,0);
        g.setColor(Color.BLACK);
        g.setFont(new Font("default", Font.BOLD, 14));
        g.drawString(name,20,10);
        g.drawString("army: "+army,20,50);
    }






}

