package Panels;

import javax.swing.*;
import java.awt.*;
import Models.*;
import java.util.*;

public class MapPanel extends JPanel {
    public ArrayList<Country> comps = new ArrayList<>();




    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for(Country comp : comps){
            for(Country cont : comp.getNeighbours()){
                g.drawLine(cont.getX()+cont.getWidth()/2,cont.getY()+cont.getHeight()/2,comp.getX()+comp.getWidth()/2,comp.getY()+comp.getHeight()/2);
            }
        }
        System.out.println("aaa");

    }


}
