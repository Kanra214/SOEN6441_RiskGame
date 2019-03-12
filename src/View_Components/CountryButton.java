package View_Components;

import Models.Country;

import javax.swing.*;
import java.awt.*;

/**
 * <h1>CountryButton</h1>
 * This class is for CountryButton object
 * CountryButtons are buttons on the window representing countries and receiving user actions
 */
public class CountryButton extends JButton {
    /**
     * width of the button
     */
    protected final int width = 50;
    /**
     * height of the button
     */
    protected final int height = 30;
    private final int innerDiameter = 5;

    private Country country;
    private int x;
    private int y;

    /**
     * Get country
     * @return country
     */
    public Country getCountry() {
        return country;
    }


    /**
     * This is constructor
     * @param country Country object this instance is associated with
     */
    public CountryButton(Country country){
        super(country.getName());
        this.x = country.getX();
        this.y = country.getY();
        this.country = country;
        setContentAreaFilled(false);
        setBounds(x,y,width,height);

    }

    /**
     * Get x
     * @return x
     */
    public int getX(){
        return x;
    }

    /**
     * Get y
     * @return y
     */
    public int getY(){
        return y;
    }

    /**
     * Drawing method to draw the buttons
     * @param g Graphics object
     */
    protected void paintBorder(Graphics g) {
        super.paintComponent(g);


        g.setColor(country.getCont().getContColor());
        g.fillRoundRect(innerDiameter,innerDiameter,width-2*innerDiameter, height-2*innerDiameter,0,0);
        g.setColor(country.getOwner().getPlayerColor());
        g.fillOval(innerDiameter, innerDiameter, getSize().width-2*innerDiameter, getSize().height-2*innerDiameter);

        g.setColor(Color.BLACK);
        g.setFont(new Font("default", Font.BOLD, 14));

        g.drawString(""+country.getArmy(),20,20);
    }

}
