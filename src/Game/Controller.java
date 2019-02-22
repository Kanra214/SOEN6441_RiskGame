package Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import Models.*;
import View_Components.CountryButton;
import View_Components.Window;

import java.util.ArrayList;

import javax.swing.*;
import View_Components.Window;

/**
 * This class is for Game process displays
 * @author Team36
 * @version 1.1
 */
public class Controller {
    Window window;
    Phases p;
    Listener lis;

    /**
     * Constructor
     * @param window is main window
     * @throws IOException
     */
    public Controller(Window window) throws IOException {
        this.window = new Window();



//        this.window.welcome();

    }

    /**
     * This function is for game start
     * @throws IOException
     */
    public void start() throws IOException {



        ArrayList<ArrayList> tempMap = new MapLoader().load("entry.txt");

        int numOfPlayers = Integer.parseInt(window.promptPlayer("how many players? Please enter an integer from 2 to 6"));
        p = new Phases(tempMap.get(0), tempMap.get(1));



        p.addPlayers(numOfPlayers);
        System.out.println("number of players: " + numOfPlayers);
        prepareGame();


        window.completePhaseButton.addActionListener(lis);

        window.setVisible(true);







    }

    /**
     * This function is for loading the slide bar of window
     */
    private void prepareGame(){
        lis = new Listener(p);
        p.prepare();
        for(Country country : p.graph){
            JLabel label = new JLabel(country.getName());
            label.setBounds(country.countryButton.getX(), country.countryButton.getY() - 20,150,20);
            window.mapPanel.add(label);
            window.mapPanel.add(country.countryButton);
            country.countryButton.addActionListener(lis);
            window.mapPanel.comps.add(country);


        }

        for(int i = 0; i < p.players.size(); i++){
            Player player = p.players.get(i);
            window.playerLabels[i].setText("<html>Player " + player.getId() + ":<br>" +
                    "Units: " + player.getUnitsOnMap() + "<br>" +
                    "Countries: " + player.getRealms().size() + "<br>" +
                    "Cards </html>");//put card info here later
        }
        window.currentPhaseLabel.setText("Current phase: " + p.currentPhase);
        window.currentPlayerLabel.setText("<html>Current player: " + p.current_player.getId() + "<br>Your color: " +
                p.current_player.getStringColor() + "</html");
        window.unitLeftLabel.setText("Unit left: " + p.current_player.getUnitLeft());



    }


    /**
     * This class is for lisenter
     */
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

        /**
         * This function is for phases displays
         * @param e is ActionEvent
         */
        public void actionPerformed(ActionEvent e) {
            int phaseNow = phase.currentPhase;
            int turnNow = phase.currentTurn;
            System.out.println();


            System.out.println("Phase "+phaseNow);
            System.out.println("Turn "+ turnNow);
            if(p.currentPhase == 0 && e.getSource() instanceof CountryButton && ((CountryButton) e.getSource()).getCountry().getOwner() == p.current_player){
                if(p.current_player.armyLeft()){
                    p.current_player.deployArmy(((CountryButton) e.getSource()).getCountry());

                }
                else if(p.current_player == p.players.get(p.players.size()-1)){//all players are ready
                    p.gameStart();
                }
                else{
                    p.nextTurn();

                }

            }
            if (phaseNow == 1){ // reinforcement phase
                window.promptPlayer("phase is one");


                if(e.getSource() instanceof CountryButton) {
                    Country chosen = ((CountryButton) e.getSource()).getCountry();
                    System.out.print (chosen+" army count:" + chosen.getArmy() + "\n");
//                    System.out.println(((CountryButton) e.getSource()).getCountry().getArmy());

//                    Country chosen = ((CountryButton) e.getSource()).getCountry();
                    if (chosen.getOwner() == phase.getCurrent_player()){ // this country belong to current player
                        phase.getCurrent_player().deployArmy(chosen);
//                        chosen.sendArmy();
                        phase.innerTurn ++;
                        System.out.println("Inner turn "+phase.innerTurn);
                        System.out.println("Army left "+ phase.current_player.getUnitLeft());
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
