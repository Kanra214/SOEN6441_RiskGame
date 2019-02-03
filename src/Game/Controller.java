//this is center controller
package Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import Models.*;
import View_Components.Window;

import java.util.ArrayList;

import javax.swing.*;



public class Controller {
    Window window;
    Phases p;
    public Controller(Window window) throws IOException {
        this.window = window;
//        this.window.welcome();

    }
    public void start() throws IOException {
        Listener lis = new Listener();


        ArrayList<ArrayList> tempMap = new MapLoader().load("entry.txt");

        int numOfPlayers = Integer.parseInt(window.promptPlayer("how many players?"));
        p = new Phases(tempMap.get(0), tempMap.get(1));

        p.addPlayers(numOfPlayers);
        p.determineOrder();
        p.countryAssignment();
        for(Country country : p.graph){

            JLabel label = new JLabel(country.getName());
            label.setBounds(country.countryButton.getX(), country.countryButton.getY() - 20,150,20);
            window.mapPanel.add(label);
            window.mapPanel.add(country.countryButton);
            country.countryButton.addActionListener(lis);
            window.mapPanel.comps.add(country);


        }

        window.setVisible(true);

        String input = window.promptPlayer("change color");
        //controller通过manipulate model来改变view的一个例子
        for(Country country : p.graph){
            country.setOwner(p.players.get(1));
        }







    }
    private class Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
        }
    }







}
