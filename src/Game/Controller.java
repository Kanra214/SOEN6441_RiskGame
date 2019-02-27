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
    class Listener implements ActionListener{
//        private Phases phase;
        private Country chosenFrom = null;
        private Country chosenTo = null;
        public Listener(){

        }




        public void actionPerformed(ActionEvent e) {


            System.out.println();


//            System.out.println("Phase "+p.currentPhase);
//            System.out.println("Turn "+ p.currentTurn);
            if (p.currentPhase == 1 || p.currentPhase == 0){ // deploy army

                if(e.getSource() instanceof CountryButton) {
//                    System.out.print (e.getActionCommand()+" army count:");
//                    System.out.println(((CountryButton) e.getSource()).getCountry().getArmy());

                    Country chosen = ((CountryButton) e.getSource()).getCountry();
//                    if (chosen.getOwner() == p.current_player){ // this country belong to a player

                    p.current_player.deployArmy(chosen);

//                        chosen.sendArmy();
//                        phase.innerTurn ++;
//                        System.out.println("Inner turn "+phase.innerTurn);
//                        System.out.println("Army left "+ phase.current_player.getPlayerArmy());
//                    }
                }

//                if(!phase.current_player.armyLeft()){ // out of army to assign
////                    if (phaseNow == 0){
////                        phase.nextTurn();
//////                        phase.innerTurn = 0;
////                    } else {
////                        System.out.println("\n Moving to Phase 2");
////                        phase.nextPhase();
////                    }
//                    p.nextPhase();
//                }

            }

            else if (p.currentPhase == 3){
//                phase.phaseTwoFirstStep();
                // start phase 2 player chooses his country (territory)

                if (chosenFrom == null){
                    if(e.getSource() instanceof CountryButton) {
                        chosenFrom = ((CountryButton) e.getSource()).getCountry();
//                        if (chosenFrom.getOwner() == phase.getCurrent_player()) { // this country belong to a player
                            // from here on we already chosen 1 country from and we deduct 1 army from him
//                            chosenFrom.deployArmy();

//                            phase.innerTurn ++; // counter to keep track of operations
//                        } else {
//                            chosenFrom = null;
//                            System.out.println("This is not your country, pick another one");
//                        }
                    }
                }
                else {
                    // here if from was chosen
                    if(e.getSource() instanceof CountryButton) {
                        chosenTo = ((CountryButton) e.getSource()).getCountry();
                        int num = Integer.parseInt(window.promptPlayer("How many armies to move? max: " + (chosenFrom.getArmy() - 1) + ", min: 1"));
                        try {
                            p.current_player.fortificate(chosenFrom,chosenTo,num);
                            chosenFrom = null;
                            chosenTo = null;
                        } catch (RiskGameException ex) {
                            String errorMsg;


                            switch (ex.type) {
                                case 0:
                                    errorMsg = "Out of army.";
                                    break;
                                case 1:
                                    errorMsg = "No such path.";
                                    break;
                                case 2:
                                    errorMsg = "Not in realms.";
                                    break;
                                case 3:
                                    errorMsg = "The source cannot be the target.";
                                    break;
                                case 4:
                                    errorMsg = "At lease move one army.";
                                    break;

                                default:
                                    errorMsg = "Unknown.";

                            }

                            window.showMsg(errorMsg + " Try again please.");
                            chosenFrom = null;
                            chosenTo = null;
                        }

//                        if (chosenTo != chosenFrom && chosenTo.getOwner() == phase.getCurrent_player()){
//                            chosenTo.sendArmy();
//                            System.out.println("Successfully sent army from "+chosenFrom.getName() + " to " + chosenTo.getName());
//
//                            chosenFrom = null;
//                            chosenTo = null;
//                        } else {
//                            //somehow we chose the same country or not belonging to us
//                            //for now we only send 1 army each time
//
//                            chosenTo = null;
//                        }
                    }
                }

            }

            else{
                System.out.println("in phase 2");
            }
            // button van only be used not on phase 1
//            if (e.getActionCommand() == "complete this phase" && phaseNow != 1){
            if(e.getSource() == window.phasePanel.completePhaseButton){
                System.out.println("Complete is called");
                p.nextPhase();
//                if (phaseNow == 2) phase.nextPhase();
//                if (phaseNow == 3) phase.nextTurn();
            }


        }
    }
    public void start() throws IOException {



        ArrayList<ArrayList> tempMap = new MapLoader().load("entry.txt");

        int numOfPlayers = Integer.parseInt(window.promptPlayer("how many players?"));
        p = new Phases(tempMap.get(0), tempMap.get(1));
        p.addObserver(window);

        Listener lis = new Listener();
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








}
