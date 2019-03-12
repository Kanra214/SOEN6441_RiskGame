package View_Components;

import javax.swing.*;
import java.awt.*;
import Models.*;
import java.util.*;

/**
 * <h1>MapPanel</h1>
 * JPanel as a container for the map(countries and their relations)
 */
public class MapPanel extends JPanel {
    public ArrayList<Country> comps = new ArrayList<>();//world map
    public ArrayList<CountryButton> cbs = new ArrayList<>();

    /**
     * Add CountryButton to this MapPanel
     * @param cb CountryButton object to be added to this MapPanel
     */
    public void addCb(CountryButton cb){
        cbs.add(cb);
    }



    @Override
    /**
     * Drawing method to draw the relations between countries
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for(Country comp : comps){
            for(Country cont : comp.getNeighbours()){
                g.drawLine(cont.getX()+CountryButton.WIDTH/2+15,cont.getY()+CountryButton.HEIGHT/2+15,comp.getX()+CountryButton.WIDTH/2+15,comp.getY()+CountryButton.HEIGHT/2+15);
            }
        }
    }
}
