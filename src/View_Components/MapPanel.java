package View_Components;

import javax.swing.*;
import java.awt.*;
import Models.*;
import java.util.*;

public class MapPanel extends JPanel {
    public ArrayList<Country> comps = new ArrayList<>();//world map
    public ArrayList<CountryButton> cbs = new ArrayList<>();

    public void addCb(CountryButton cb){
        cbs.add(cb);
    }



    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for(Country comp : comps){
            for(Country cont : comp.getNeighbours()){
                g.drawLine(cont.getX()+CountryButton.WIDTH/2,cont.getY()+CountryButton.HEIGHT/2,comp.getX()+CountryButton.WIDTH/2,comp.getY()+CountryButton.HEIGHT/2);
            }


        }



    }




}
