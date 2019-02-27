//this is center controller
package Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import Models.*;
import View_Components.CountryButton;
import View_Components.Window;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

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
        p.addObserver(window);

        Listener lis = new Listener(p);
        p.addPlayers(numOfPlayers);
        p.determineOrder();

        //draw the countries on the mapPanel

        for(Country country : p.graph){
            country.setPhase(p);


            JLabel label = new JLabel(country.getName());
            label.setBounds(country.countryButton.getX(), country.countryButton.getY() - 20,150,20);
            window.mapPanel.add(label);
            window.mapPanel.add(country.countryButton);
            country.countryButton.addActionListener(lis);
            window.mapPanel.comps.add(country);


        }
        p.countryAssignment();

        window.phasePanel.completePhaseButton.addActionListener(lis);
        p.connectView();//after this updating window is enabled


        window.setVisible(true);








    }
    public class Listener implements ActionListener{
        private Phases phase;
        private Country chosenFrom = null;
        private Country chosenTo = null;
        public Listener(Phases phase){
            this.phase = phase;
        }




        public void actionPerformed(ActionEvent e) {
            int phaseNow = phase.currentPhase;
            int turnNow = phase.currentTurn;

            System.out.println();


            System.out.println("Phase "+phaseNow);
            System.out.println("Turn "+ turnNow);
            if (phaseNow == 1 || phaseNow == 0){ // deploy army

                if(e.getSource() instanceof CountryButton) {
                    System.out.print (e.getActionCommand()+" army count:");
                    System.out.println(((CountryButton) e.getSource()).getCountry().getArmy());

                    Country chosen = ((CountryButton) e.getSource()).getCountry();
                    if (chosen.getOwner() == phase.getCurrent_player()){ // this country belong to a player
                        phase.getCurrent_player().deployArmy(chosen);
//                        chosen.sendArmy();
//                        phase.innerTurn ++;
//                        System.out.println("Inner turn "+phase.innerTurn);
                        System.out.println("Army left "+ phase.current_player.getPlayerArmy());
                    }
                }

                if(!phase.current_player.armyLeft()){ // out of army to assign
                    p.nextPhase();
                }

            }

            if (phaseNow == 3){
                // start phase 2 player chooses his country (territory)
                if (chosenFrom == null){
                    if(e.getSource() instanceof CountryButton) {
                        chosenFrom = ((CountryButton) e.getSource()).getCountry();
                        if (chosenFrom.getOwner() == phase.getCurrent_player()) { // this country belong to a player
                            if (chosenFrom.getArmy() > 1){
                                // from here on we already chosen 1 country from and we deduct 1 army from him
                                chosenFrom.deployArmy();
                            } else {
                                chosenFrom = null;
                                System.out.println("Don't have enough army to send");
                            }
                        } else {
                            chosenFrom = null;
                            System.out.println("This is not your country, pick another one");
                        }
                    }
                } else {
                    // here if from was chosen
                    if(e.getSource() instanceof CountryButton) {
                        chosenTo = ((CountryButton) e.getSource()).getCountry();
                        if (chosenTo != chosenFrom && chosenTo.getOwner() == phase.getCurrent_player()
//                                && phase.getCurrent_player().findPath(chosenFrom, chosenTo) // there is a path
                        ){
                            chosenTo.sendArmy();
                            System.out.println("Successfully sent army from "+chosenFrom.getName() + " to " + chosenTo.getName());

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
//            if (e.getActionCommand() == "complete this phase" && phaseNow != 1){
            if(e.getSource() == window.phasePanel.completePhaseButton){
                System.out.println("Complete is called");

                //deicded to cancel reinforcement turn while a country was selected
                if(chosenFrom != null) {
                    chosenFrom.sendArmy();
                    chosenFrom = null;
                }

                if (phaseNow != 1) p.nextPhase();


//                if (phaseNow == 2) phase.nextPhase();
//                if (phaseNow == 3) phase.nextTurn();
            }


        }
    }







}
