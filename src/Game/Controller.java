package Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import Models.*;
import View_Components.CardExchangeView;
import View_Components.CountryButton;
import View_Components.Window;
import View_Components.StartManu;
import java.util.ArrayList;

import MapEditor.MapEditorGUI;

import javax.swing.*;

/**
 * <h1>Controller</h1>
 * This class is used to coordinate view and model
 */
public class Controller {
    Window window;
    Phases p;
    StartManu startmanu;
    MapEditorGUI mapeditor;

    String filename;
    CardExchangeView cardexchange;
    int CardTurn=0;//flag for how many times players change cards

    /**
     * Constructor
     */
    public Controller() {
        this.window = new Window();
        cardexchange = new CardExchangeView();
       
    }

    /**
     * <h1>Listener</h1>
     * This class is action listener for the game
     */
    class Listener implements ActionListener {
        private Country chosenFrom = null;
        private Country chosenTo = null;

        /**
         * Process the user requests
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == window.phasePanel.completePhaseButton) {
                System.out.println("Complete is called");

                p.nextPhase();
                if ((p.getCurrentPhase() == 1) && (p.getCurrentTurn() >= p.getNumOfPlayers()*2)){
                    cardexchange.setVisible(true);
                }
                else{
                    cardexchange.setVisible(false);
                }
            }
            if (e.getSource() instanceof CountryButton) {
                Country chosen = ((CountryButton) e.getSource()).getCountry();


                try {
                    if (p.getCurrentPhase() == 0) {
                        p.startUpPhase(chosen);
                    }
                    else if (p.getCurrentPhase() == 1) {
                        p.reinforcementPhase(chosen);
                    }
                    else if (p.getCurrentPhase() == 3) {
                        if (chosenFrom == null) {
                            chosenFrom = chosen;
                        }
                        else {
                            chosenTo = chosen;
                            String input = window.promptPlayer("How many armies to move? max: " + (chosenFrom.getArmy() - 1) + ", min: 1");

                            if (input != null) {
                                int num = Integer.parseInt(input);

                                p.fortificationsPhase(chosenFrom, chosenTo, num);


                            }
                            chosenFrom = null;
                            chosenTo = null;
                        }
                    }

                    else {
//                        cardexchange.setVisible(false);

                            if (chosenFrom == null) {
                                chosenFrom = chosen;


                            }
                            else {
                                chosenTo = chosen;
                                String attackerInput = window.promptPlayer("How many dice for attacker to roll? max: " + Math.min(chosenFrom.getArmy() - 1, 3) + ", min: 1. Input nothing to turn on the all-out mode.");
                                if (attackerInput != null) {


                                if (attackerInput.isEmpty()) {//all out mode
                                    System.out.println("all out");
                                    if (p.attackPhase(chosenFrom, chosenTo)) {
                                        if (p.gameOver) {
                                            window.showMsg("Player " + p.getCurrent_player().getId() + " wins the game!");
                                            System.exit(0);
                                        }
                                        forceUserInputCorrectlyForDeploymentAfterConquer();


                                        }
                                        else {
                                            window.showMsg("attacker did not win");

                                        }

                                    } else {
                                        int attackDice = Integer.parseInt(attackerInput);


                                        System.out.println("not all out");
                                        String defenderInput = window.promptPlayer("How many dice for defender to roll? max: " + Math.min(chosenTo.getArmy(), 2) + ", min: 1");

                                        int defendDice = Integer.parseInt(defenderInput);


                                        if (p.attackPhase(chosenFrom, chosenTo, attackDice, defendDice)) {
                                            if (p.gameOver) {
                                                window.showMsg("Player " + p.getCurrent_player().getId() + " wins the game!");
                                                System.exit(0);
                                            }


                                            forceUserInputCorrectlyForDeploymentAfterConquer();


                                        }
                                        else {
                                            window.showMsg("attacker did not win");

                                        }

                                    }


                                }
                                chosenFrom = null;
                                chosenTo = null;
                            }



                            }

                }
                catch(RiskGameException ex1){
                    window.showMsg(ex1.errMsg + "Try again please.");
                    chosenFrom = null;
                    chosenTo = null;

                }
                catch(NumberFormatException ex2) {
                    window.showMsg("Wrong input format, try again please.");
                    chosenFrom = null;
                    chosenTo = null;

                }


            }
            
            //exchange card
            if(e.getSource() == cardexchange.Exchange3Infantry){
                System.out.println("Clicked on exchange");
                int type = p.getCurrent_player().getCards().checkCardType();
                if (type != 1&&type != 2) {
                	 System.out.println(type);
                    window.showMsg("You can't perform this function, you don't have 3 the same cards");
                }else {
                	p.getCurrent_player().addPlayerArmyByCard(CardTurn);
                	window.showMsg("Changed 3 Infantry");
                	CardTurn++;
                }
            }
            if(e.getSource() == cardexchange.Exchange3Diff){
                int type = p.getCurrent_player().getCards().checkCardType();
                if (type != 1) {
                    window.showMsg("You can't perform this function, you don't have 3 different cards");
                }else {
                	p.getCurrent_player().addPlayerArmyByCard(CardTurn);
                	CardTurn++;
                }
            }
            if(e.getSource() == cardexchange.Cancel){
                if (p.getCurrent_player().getCards().checkCardSum()){
                    //TODO: cancel move to the next phase, the player has less than 5
                	cardexchange.setVisible(false);
                } else{
                    window.showMsg("You can't perform this function, you have more than 5 cards");
                }

            }


        }

        private void forceUserInputCorrectlyForDeploymentAfterConquer() {
            while(true){//loops until the player's input is correct, other wise keeps on popping out
                String input = window.promptPlayer("Attacker wins! How many armies to place in the new country? min: " + p.getCurrent_player().getNumOfDice() + ", max: " + (chosenFrom.getArmy() - 1));
                if(input != null && !input.isEmpty()) {
                    int numDeploy = Integer.parseInt(input);
                    try {
                        if (p.deploymentAfterConquer(chosenFrom, chosenTo, numDeploy)) {
//                            p.nextPhase();
                            break;
                        }
                    } catch (RiskGameException ex) {
                        window.showMsg(ex.errMsg + "Try again please.");
                        continue;
                    }
                }

            }
        }
    }



    /**
     * This is a function create Start Menu box.
     */
    public void startManu() {
        startmanu = new StartManu("Risk Manu", 20, 30, 300, 400);
        startmanu.setVisible(true);

        startManuAction lisStart = new startManuAction(1);
        startManuAction lisEditMap = new startManuAction(2);
        startManuAction lisInstruction = new startManuAction(3);
        startManuAction lisExit = new startManuAction(4);

        startmanu.startGame.addActionListener(lisStart);
        startmanu.editMap.addActionListener(lisEditMap);
        startmanu.instructions.addActionListener(lisInstruction);
        startmanu.exit.addActionListener(lisExit);
    }

    /**
     * Check file is correct or not
     * @return boolean
     */
    public boolean ChooseFile() {
        JFileChooser jfc = new JFileChooser(".");

        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            filename = selectedFile.getName();
        }
        return true;
    }

    /**
     * Start Menu action control
     */
    public class startManuAction implements ActionListener {

        private int buttonFlag;

        /**
         * sets the buttonFlag
         * @param buttonFlag    int representing which button was pressed
         */
        public startManuAction(int buttonFlag) {
            this.buttonFlag = buttonFlag;
        }


        /**
         * performs different operation depending on which button was pressed in the start menu
         * @param e button
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (buttonFlag) {
                case 1:
                    if (ChooseFile()) {
                        startmanu.dispose();
                        try {
                            start();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        startmanu.dispose();
                    }
                    break;

                case 2:
                    mapeditor = new MapEditorGUI();
                    mapeditor.frame.setVisible(true);
                    startmanu.dispose();
                    break;

                case 4:
                    startmanu.dispose();
                    break;
            }
        }
    }

    /**
     * Start game
     * @throws IOException map loading exception
     */
    public void start() throws IOException {

        System.out.println(filename);
        ArrayList < ArrayList > tempMap = new MapLoader().loadMap(filename);
        if (tempMap.isEmpty()) {
            window.showMsg("Empty map");
            System.exit(0);
        }

        int numOfPlayers = Integer.parseInt(window.promptPlayer("how many players?"));
        if (numOfPlayers > 6 || numOfPlayers < 2) {
            window.showMsg("Wrong number of Players");
            System.exit(0);
        }
        p = new Phases(tempMap.get(0), tempMap.get(1));
        p.addObserver(window);
        p.addObserver(cardexchange);
        Listener lis = new Listener();
        p.gameSetUp(numOfPlayers);

        window.drawMapPanel(p);

        for (CountryButton cb: window.mapPanel.cbs) {
            cb.addActionListener(lis);
        }
        window.phasePanel.completePhaseButton.addActionListener(lis);
        p.connectView(); //after this updating window is enabled
        window.setVisible(true);
        cardexchange.Exchange3Infantry.addActionListener(lis);
        cardexchange.Exchange3Artillery.addActionListener(lis);
        cardexchange.Exchange3Cavalry.addActionListener(lis);
        cardexchange.Exchange3Diff.addActionListener(lis);
        cardexchange.Cancel.addActionListener(lis);
    }
}