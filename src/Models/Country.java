package Models;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import Game.Window;
import Panels.*;
import javax.swing.*;
import javax.swing.border.Border;



public class Country extends JButton {

    private int x;
    private int y;
    private final int width = 50;
    private final int height = 30;
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
        this.setBounds(x,y,width,height);
//        setBorder(new RoundedBorder(10));




    }
    public int getX(){return x;}
    public int getY(){return y;}
    public int getWidth(){return width;}
        public int getHeight(){return height;}


    public void setOwner(Player player) {
        this.owner = player;
        this.setOpaque(true);
        this.setForeground(owner.getPlayerColor());




    }
    public String getName(){
        return name;
    }
    public ArrayList<Country> getNeighbours(){return neighbours;}
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
        super.paintComponent(g);


        g.setColor(cont.getContColor());
        g.fillRoundRect(5,5,width-10, height-10,0,0);
        g.setColor(owner.getPlayerColor());
        g.fillOval(5, 5, getSize().width-10, getSize().height-10);

        g.setColor(Color.BLACK);
        g.setFont(new Font("default", Font.BOLD, 14));
        g.drawString(""+army,20,20);
    }
//    private static class RoundedBorder implements Border {
//
//        private int radius;
//
//
//        RoundedBorder(int radius) {
//            this.radius = radius;
//        }
//
//
//        public Insets getBorderInsets(Component c) {
//            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
//        }
//
//
//        public boolean isBorderOpaque() {
//            return true;
//        }
//
//
//        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
//            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
//        }
//    }







}

