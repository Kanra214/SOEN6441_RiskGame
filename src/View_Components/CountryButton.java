package View_Components;

import Models.Country;

import javax.swing.*;
import java.awt.*;

public class CountryButton extends JButton {
    private Country country;
    private final int width = 50;
    private final int height = 30;
    private int innerDiameter = 10;
    private int x;
    private int y;

    public CountryButton(int x, int y, Country country){
        super(country.getName());
        this.x = x;
        this.y = y;
        this.country = country;
        setContentAreaFilled(false);
        setBounds(x,y,width,height);




    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }


//    protected void paintComponent(Graphics g) {
//        // if the button is pressed and ready to be released
//
//        if (getModel().isArmed()) {
//            g.setColor(Color.lightGray);
//        } else {
//            g.setColor(getBackground());
//        }
//
//
////        g.fillOval(0, 0, getSize().width-1, getSize().height-1);
//
//        super.paintComponent(g);
//    }
    protected void paintBorder(Graphics g) {
        super.paintComponent(g);


        g.setColor(country.getCont().getContColor());
        g.fillRoundRect(5,5,width-10, height-10,0,0);
        g.setColor(country.getOwner().getPlayerColor());
        g.fillOval(5, 5, getSize().width-10, getSize().height-10);

        g.setColor(Color.BLACK);
        g.setFont(new Font("default", Font.BOLD, 14));
        g.drawString(""+country.getArmy(),20,20);
//        System.out.println("aaa");
    }

}
