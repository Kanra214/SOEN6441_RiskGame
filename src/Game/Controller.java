//this is center controller
package Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import Models.*;
import View_Components.CountryButton;
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



        ArrayList<ArrayList> tempMap = new MapLoader().load("entry.txt");

        int numOfPlayers = Integer.parseInt(window.promptPlayer("how many players?"));
        p = new Phases(tempMap.get(0), tempMap.get(1));
        Listener lis = new Listener(p);

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
        window.completePhaseButton.addActionListener(lis);

        window.setVisible(true);








    }
    public class Listener implements ActionListener{
        private Phases phase;
        public Listener(Phases phase){
            this.phase = phase;
        }

        @Override
//        public void actionPerformed(ActionEvent e) {
//            System.out.print (e.getActionCommand()+" army count:");
//            if(e.getSource() instanceof CountryButton) System.out.println(((CountryButton) e.getSource()).getCountry().getArmy());
//
//        }

        public void actionPerformed(ActionEvent e) {
            int phaseNow = phase.currentPhase;
            int turnNow = phase.currentTurn;
            System.out.println();


            System.out.println("Phase "+phaseNow);
            System.out.println("Turn "+ turnNow);
            if (phaseNow == 1){ // reinforcement phase


                if(e.getSource() instanceof CountryButton) {
                    System.out.print (e.getActionCommand()+" army count:");
                    System.out.println(((CountryButton) e.getSource()).getCountry().getArmy());

                    Country chosen = ((CountryButton) e.getSource()).getCountry();
                    if (chosen.getOwner() == phase.getCurrent_player()){ // this country belong to a player
                        phase.getCurrent_player().deployArmy();
                        chosen.sendArmy();
                        phase.innerTurn ++;
                        System.out.println("Inner turn "+phase.innerTurn);
                        System.out.println("Army left "+ phase.current_player.getPlayerArmy());
                    }
                }

                if(!phase.current_player.armyLeft()){ // out of army to assign
                    if (turnNow / phase.numOfPlayers == 0){
                        phase.nextTurn();
                        phase.innerTurn = 0;
                    } else {
                        System.out.println("Phase 2");
                        phase.nextPhase();
                    }
                }

            }


        }
    }







}
