package View_Components;

import javax.swing.*;
import java.awt.*;
import Models.*;
import java.util.*;

public class MapPanel extends JPanel {
    public ArrayList<Country> comps = new ArrayList<>();

//    public MapPanel(){
//
//    }




    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for(Country comp : comps){
            for(Country cont : comp.getNeighbours()){
                g.drawLine(cont.countryButton.getX()+cont.countryButton.getWidth()/2,cont.countryButton.getY()+cont.countryButton.getHeight()/2,comp.countryButton.getX()+comp.countryButton.getWidth()/2,comp.countryButton.getY()+comp.countryButton.getHeight()/2);
            }
        }


    }




}
