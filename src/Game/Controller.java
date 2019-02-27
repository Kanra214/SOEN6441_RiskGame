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
        private Country chosenFrom = null;
        private Country chosenTo = null;
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
                    if (chosen.getOwner() == phase.getCurrent_player() && phase.current_player.armyLeft()){ // this country belong to a player
                        phase.getCurrent_player().deployArmy();
                        chosen.sendArmy();
                        phase.innerTurn ++;
                        System.out.println("Inner turn "+phase.innerTurn);
                        System.out.println("Army left "+ phase.current_player.getPlayerArmy());
                    } else {
                        System.out.println("This is not your country");
                    }
                }

                if(!phase.current_player.armyLeft()){ // out of army to assign
                    if (turnNow <= phase.numOfPlayers){
                        phase.nextTurn();
                        phase.innerTurn = 0;
                    } else {
                        System.out.println("\nMoving to Phase 2");
                        phase.nextPhase();
                    }
                }

            }

            if (phaseNow == 3){
//                phase.phaseTwoFirstStep();
                // start phase 2 player chooses his country (territory)
                if (chosenFrom == null){
                    if(e.getSource() instanceof CountryButton) {
                        chosenFrom = ((CountryButton) e.getSource()).getCountry();
                        if (chosenFrom.getOwner() == phase.getCurrent_player()){
                            if(chosenFrom.getArmy() > 1) { // this country belong to a player and has more than 1 army
                                // from here on we already chosen 1 country from
                                chosenFrom.deployArmy();
                                phase.innerTurn++; // counter to keep track of operations
                            } else {
                                System.out.println("This country doesn't have enough army");
                            }
                        } else {
                            chosenFrom = null;
                            System.out.println("This is not your country, pick another one");
                            System.out.println("Current player "+phase.current_player.getId());
                        }
                    }
                } else {
                    // here if from was chosen
                    if(e.getSource() instanceof CountryButton) {
                        chosenTo = ((CountryButton) e.getSource()).getCountry();
                        if (chosenTo != chosenFrom && chosenTo.getOwner() == phase.getCurrent_player()
                                && phase.getCurrent_player().findPath(chosenFrom, chosenTo) // there is a path
                        ){
//                            chosenFrom.deployArmy();
                            chosenTo.sendArmy();
                            System.out.println("Successfully sent army from "+chosenFrom.getName() + " to " + chosenTo.getName());

                            phase.innerTurn ++; // counter to keep track of operations
                            chosenFrom = null;
                            chosenTo = null;
                        } else {
                            //somehow we chose the same country or not belonging to us
                            //for now we only send 1 army each time
                            chosenTo = null;
                        }
                    }
                }

            }

            if (phaseNow == 2){
                System.out.println("in phase 2");
            }
            // button van only be used not on phase 1
            if (e.getActionCommand() == "complete this phase" && phaseNow != 1){
                System.out.println("Complete is called");

                if (chosenFrom != null){
                    chosenFrom.sendArmy();
                }

                if (phaseNow == 2) phase.nextPhase();
                if (phaseNow == 3) phase.nextTurn();
            }


        }
    }







}
