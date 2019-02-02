//this is center controller
package Game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import Models.*;
import java.util.ArrayList;
import Panels.*;
import org.w3c.dom.events.EventListener;

import javax.swing.*;



public class Controller {
    Window window;
    Phases p;
    public Controller(Window window){
        this.window = window;

    }
    public void start() throws IOException {
//        window.welcome();
//        window.showLayout();
        Listener lis = new Listener();




        ArrayList<ArrayList> tempMap = new MapLoader().load("entry.txt");

        int numOfPlayers = Integer.parseInt(window.promptPlayer("how many players?"));
        p = new Phases(tempMap.get(0), tempMap.get(1));

        p.addPlayers(numOfPlayers);
        p.determineOrder();
        p.countryAssignment();
        for(Country country : p.graph){
            JLabel label = new JLabel(country.getName());
            label.setBounds(country.getX(), country.getY() - 20,150,20);
            window.mapPanel.add(label);
            window.mapPanel.add(country);
            country.addActionListener(lis);
            window.mapPanel.comps.add(country);

        }

        window.setVisible(true);









    }
    private class Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
        }
    }







}
