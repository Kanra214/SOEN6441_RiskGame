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
        window.welcome();
        Listener lis = new Listener();
//        JPanel mainPanel = new JPanel();
        MapPanel mapPanel = new MapPanel();
//        mapPanel.setOpaque(false);
//        window.setLayout(null);
//        mainPanel.setLayout(null);
        mapPanel.setLayout(null);


        ArrayList<ArrayList> tempMap = new MapLoader().load("entry.txt");

        int numOfPlayers = Integer.parseInt(window.promptPlayer("how many players?"));
        p = new Phases(tempMap.get(0), tempMap.get(1));

        p.addPlayers(numOfPlayers);
        p.determineOrder();
        p.countryAssignment();
        for(Country country : p.graph){
            JLabel label = new JLabel(country.getName());
            label.setBounds(country.getX(), country.getY() - 20,150,20);
            mapPanel.add(label);
            mapPanel.add(country);
            country.addActionListener(lis);
            mapPanel.comps.add(country);
//
//            System.out.println(country.getBounds().x + "y is" + country.getBounds().y);
//            country.setBounds(200,200,200,200);
        }
//        window.add(mainPanel);


        window.add(mapPanel);



        window.setVisible(true);
//        String input = window.promptPlayer("change color");
//        //controller通过manipulate model来改变view的一个例子
//        for(Country country : p.graph){
//            country.setOwner(p.players.get(1));
//        }







    }
    private class Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
        }
    }







}
