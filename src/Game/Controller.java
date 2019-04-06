package Game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import Models.*;
import View_Components.CardExchangeView;
import View_Components.CountryButton;
import View_Components.Window;
import View_Components.StartManu;

import java.util.Collections;
import java.lang.reflect.Array;
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
    String mapFileName, loadFileName, saveFileName;
    
    String[] tournamentMapName=new String[5];
    int tMapNum=0;
    int tStrategyNum=0;
    int tGameNum=0;
    int tTurnsNum=0;
    int tNum;



    /**
     * Constructor
     */
    public Controller() {
        this.window = new Window();
        
    }
///////







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
                //This is used to set the flag back to true, so that next player can get armies in reinforcementArmy; 


                System.out.println(p.getCurrentPhase());
                System.out.println(p.getCurrentTurn());
                p.nextPhase();
                if((p.getCurrentPhase() == 1) && (p.getCurrentTurn() < p.getNumOfPlayers()*2)) {
                    p.phaseOneFirstStep();
                }
            }

            if(e.getSource() == window.phasePanel.saveButton){
                if(ChooseFile(6)){
                    writeToFile(saveFileName);
                }
            }


            if (e.getSource() instanceof CountryButton) {
                Country chosen = ((CountryButton) e.getSource()).getCountry();

                try {
                    if (p.getCurrentPhase() == 0) {
                        p.startUpPhase(chosen);
                    } else if (p.getCurrentPhase() == 1) {
                        p.getCurrent_player().reinforce(chosen);
                    } else if (p.getCurrentPhase() == 3) {
                        if (chosenFrom == null) {
                            chosenFrom = chosen;
                        } else {
                            chosenTo = chosen;
                            String input = window.promptPlayer("How many armies to move? max: " + (chosenFrom.getArmy() - 1) + ", min: 1");

                            if (input != null) {
                                int num = Integer.parseInt(input);

                                p.getCurrent_player().fortify(chosenFrom, chosenTo, num);
                                p.nextPhase();

                            }
                            chosenFrom = null;
                            chosenTo = null;
                        }
                    } else {

                        if (chosenFrom == null) {
                            chosenFrom = chosen;

                        } else {
                            chosenTo = chosen;
                            String attackerInput = window.promptPlayer("How many dice for attacker to roll? max: " + Math.min(chosenFrom.getArmy() - 1, 3) + ", min: 1. Input nothing to turn on the all-out mode.");
                            if (attackerInput != null) {


                                if (attackerInput.isEmpty()) {//all out mode
                                    System.out.println("all out");
                                    if (p.getCurrent_player().attack(chosenFrom, chosenTo)) {
                                        if (p.isGameOver()) {
                                            window.showMsg("Player " + p.getCurrent_player().getId() + " wins the game!");
                                            System.exit(0);
                                        }
                                        forceUserInputCorrectlyForDeploymentAfterConquer();


                                    } else {
                                        window.showMsg("attacker did not win");

                                    }

                                } else {
                                    int attackDice = Integer.parseInt(attackerInput);


                                    System.out.println("not all out");
                                    int defendDice;
                                    if(chosenTo.getOwner().getStrategy().getName().equals("Human")) {//human defender
                                        String defenderInput = window.promptPlayer("How many dice for defender to roll? max: " + Math.min(chosenTo.getArmy(), 2) + ", min: 1");
                                        defendDice = Integer.parseInt(defenderInput);
                                        chosenTo.getOwner().setNumOfDice(defendDice);
                                    }

                                    if (p.getCurrent_player().attack(chosenFrom, chosenTo, attackDice)) {
                                        if (p.isGameOver()) {
                                            window.showMsg("Player " + p.getCurrent_player().getId() + " wins the game!");
                                            System.exit(0);
                                        }

                                        forceUserInputCorrectlyForDeploymentAfterConquer();

                                    } else {
                                        window.showMsg("attacker did not win");

                                    }

                                }

                            }
                            chosenFrom = null;
                            chosenTo = null;
                        }

                    }

                } catch (RiskGameException ex1) {
                    window.showMsg(ex1.errMsg + "Try again please.");
                    chosenFrom = null;
                    chosenTo = null;

                } catch (NumberFormatException ex2) {
                    window.showMsg("Wrong input format, try again please.");
                    chosenFrom = null;
                    chosenTo = null;

                }


            }

            if (e.getSource() == window.cardExchangeView.Exchange3Infantry) {
                p.getCurrent_player().addPlayerArmyBySameCards(0);
                p.phaseOneFirstStep();

            }
            if (e.getSource() == window.cardExchangeView.Exchange3Cavalry) {

                p.getCurrent_player().addPlayerArmyBySameCards(1);
                p.phaseOneFirstStep();
            }
            if (e.getSource() == window.cardExchangeView.Exchange3Artillery) {

                p.getCurrent_player().addPlayerArmyBySameCards(2);
                p.phaseOneFirstStep();
            }

            if (e.getSource() == window.cardExchangeView.Exchange3Diff) {

                p.getCurrent_player().addPlayerArmyByDiffCards();
                p.phaseOneFirstStep();

            }
            if (e.getSource() == window.cardExchangeView.Cancel) {
                p.cardExchanged = true;
                p.phaseOneFirstStep();
            }




        }

        /**
         * After Conquer Check User Input Correctly For Deployment
         */
        private void forceUserInputCorrectlyForDeploymentAfterConquer() {
            while (true) {//loops until the player's input is correct, other wise keeps on popping out
                String input = window.promptPlayer("Attacker wins! How many armies to place in the new country? min: " + p.getCurrent_player().getNumOfDice() + ", max: " + (chosenFrom.getArmy() - 1));
                if (input != null && !input.isEmpty()) {

                    try {
                        int numDeploy = Integer.parseInt(input);
                        if (p.getCurrent_player().deploymentAfterConquer(chosenFrom, chosenTo, numDeploy)) {
                            break;
                        }
                    } catch (RiskGameException ex) {
                        window.showMsg(ex.errMsg + "Try again please.");
                        continue;

                    } catch (NumberFormatException ex2) {
                        window.showMsg("Wrong input format, try again please.");
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
            startManuAction lisTorna = new startManuAction(2);
            startManuAction lisEditMap = new startManuAction(3);
            startManuAction lisLoadMap = new startManuAction(5);   
            startManuAction lisInstruction = new startManuAction(4);
            startManuAction lisExit = new startManuAction(6);
         

            startmanu.startGame.addActionListener(lisStart);
            startmanu.editMap.addActionListener(lisEditMap);
            startmanu.loadMap.addActionListener(lisLoadMap);
            startmanu.instructions.addActionListener(lisInstruction);
            startmanu.exit.addActionListener(lisExit);
            startmanu.startTournament.addActionListener(lisTorna);
        }

        /**
         * Check file is correct or not
         * @return boolean
         */

        public boolean ChooseFile(int i) {

            JFileChooser jfc = new JFileChooser(".");

            int returnValue = JFileChooser.CANCEL_OPTION;


            if (i== 6) {
                jfc.setDialogTitle("Save");
                returnValue = jfc.showSaveDialog(null);
            }
            else if(i == 1){
                jfc.setDialogTitle("Open a map file");
                returnValue = jfc.showOpenDialog(null);
            }
            else if(i == 5){
                jfc.setDialogTitle("Load a SER file");
                returnValue = jfc.showOpenDialog(null);
            }
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String path = jfc.getSelectedFile().getAbsolutePath();
                if (i == 1) {
                    mapFileName = path;
                } else if (i == 5) {
                    loadFileName = path;
                } else if (i == 6) {// save file option
                    saveFileName = path;
                }

                else if(i == 2){
                	System.out.println("tNumIn file choose"+tNum);
                	
                	 tournamentMapName[tNum] = path;
                }

                return true;



            }

            return false;
        }



        /**
         * Start Menu action control
         */
        public class startManuAction implements ActionListener {

            private int buttonFlag;

            /**
             * sets the buttonFlag
             *
             * @param buttonFlag int representing which button was pressed
             */
            public startManuAction(int buttonFlag) {
                this.buttonFlag = buttonFlag;
            }


            /**
             * performs different operation depending on which button was pressed in the start menu
             *
             * @param e button
             */
            @Override
            public void actionPerformed(ActionEvent e){
                switch (buttonFlag) {
                    case 1:

                        if (ChooseFile(1)) {

                            startmanu.dispose();
                            try {
                                start();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            startmanu.dispose();
                        }
                        break;

                    case 3:
                        mapeditor = new MapEditorGUI();
                        mapeditor.frame.setVisible(true);
                        startmanu.dispose();
                        break;

                    case 4:
                        startmanu.dispose();
                        break;

                    case 5:

                        if (ChooseFile(5)) {

                            startmanu.dispose();

                            loadGame();

                            startmanu.dispose();
                        }
                        break;
                     //Tournament Mode
                    case 2:
                    	//,int tMapNum
                        ArrayList<Integer> tournamentArray = tournament();
                        ArrayList<Integer> strategyArray = new ArrayList<>();
                        if (tournamentArray.size() != 4){
                            window.infoBox("Unknow error", "Warning");
                            System.exit(0);
                        }
                        if (tournamentArray.get(1) != 4){
                            while (strategyArray.size() != tournamentArray.get(1)){
                                int i = (int) (Math.random() * 4 + 1);;
                                if (!strategyArray.contains(i)){
                                    strategyArray.add(i);
                                }
                            }
                        }else {
                            strategyArray.add(1);
                            strategyArray.add(2);
                            strategyArray.add(3);
                            strategyArray.add(4);
                        }
                        Collections.sort(strategyArray);
                        System.out.println(strategyArray);

                        int[] playerValues = new int[5];
                        playerValues[0] = 0;
                        for(int i = 0;i < strategyArray.size(); i++){
                            playerValues[strategyArray.get(i)] = 1;
                        }
                        startTournament(tournamentArray.get(0), playerValues, tournamentArray.get(2), tournamentArray.get(3));
                    	//First Choose Maps
//                    	String mapNumString=window.decideMaps("How many maps for the Tournament?(Please input between 1-5)");
//                    	tMapNum =Integer.parseInt(mapNumString);
//                    	System.out.println(Integer.parseInt(mapNumString));
//                    	if(tMapNum<1||tMapNum>5) {
//                    		window.infoBox("Please input between 1-5", "Warning");
//                    		mapNumString=window.decideMaps("How many maps for the Tournament?(Please input between 1-5)");
//                    		tMapNum=Integer.parseInt(mapNumString);
//                            System.out.println(Integer.parseInt(mapNumString));
//                    	}
//                    	System.out.println(tMapNum+"tmapnum");
//                    	for(tNum=0;tNum<tMapNum;tNum++) {
//
//                    		System.out.println("tUnm"+tNum);
//
//                    		ChooseFile(2);
//                    	}
//
//                    	System.out.println("tname"+tournamentMapName[0]);
//                    	System.out.println("tname"+tournamentMapName[1]);
//                    	System.out.println("tname"+tournamentMapName[2]);
                    	
//                    	//Choose Players
//
////                    	int[] playersInT = window.decidePlayers();
                    	
                }
            }
        }


        
        /**
         * Start game
         *
         * @throws IOException map loading exception
         */

        public boolean coorrect = false;

        public void start() throws IOException {

            System.out.println(mapFileName);

            ArrayList<ArrayList> tempMap = new MapLoader().loadMap(mapFileName);
            if (tempMap.isEmpty()) {
                window.showMsg("Empty map");
                System.exit(0);
            }
//            while (!coorrect) window.displayGUI(this);


//            int numOfPlayers = Integer.parseInt(window.promptPlayer("how many players?"));
//            if (numOfPlayers > 6 || numOfPlayers < 2) {
//                window.showMsg("Wrong number of Players");
//                System.exit(0);
//            }
            p = new Phases(tempMap.get(0), tempMap.get(1));


            p.addObserver(window);
            while (!coorrect){
                window.displayGUI(this);
            }
            int[] playerValues = window.decidePlayers();
            System.out.println(playerValues);
            p.gameSetUp(playerValues);
            addListeners();

        }

        public void startTournament(int numMaps, int[] playerValues, int numGames, int numTurns){
//                for (int i = 0; i < numMaps * numGames; i++){
//
//                }
            ArrayList<Integer> winners = new ArrayList<>();
            ArrayList<String> maps = new ArrayList<>();
            String[] mapArray = {"DemoMap-SmallSize.map",
                                "DemoMap-SmallSize.map",
                                "DemoMap-SmallSize.map",
                                "DemoMap-SmallSize.map",
                                "DemoMap-SmallSize.map"};
            for (int i = 0; i < numMaps; i++){
                maps.add(mapArray[i]);
            }
            ArrayList<ArrayList> tempMap = new MapLoader().loadMap(mapArray[0]);
            p = new Phases(tempMap.get(0), tempMap.get(1));
            p.gameSetUp(playerValues);
//            if (p.checkWinner()){
//                winners.add(p.getCurrent_player().getId());
//            }

        }

        public void loadGame(){

            p = loadPhases();//TODO: implement this class to create phases object with map
            if(p == null){
                window.showMsg("Wrong file");
                System.exit(0);
            }
            else {
                p.addObserver(window);
                p.resume();//TODO: implement this class to pass datas from txt file to phases
                addListeners();
            }

        }

        public ArrayList<Integer> tournament(){
            ArrayList<Integer> tournamentArray = new ArrayList<>();
            String mapNumString=window.decideMaps("How many maps for the Tournament?(Please input between 1-5)");
            tMapNum =Integer.parseInt(mapNumString);
            if(tMapNum<1||tMapNum>5) {
                do {
                    window.infoBox("Please input between 1-5", "Warning");
                    mapNumString=window.decideMaps("How many maps for the Tournament?(Please input between 1-5)");
                    tMapNum=Integer.parseInt(mapNumString);
                }
                while (tMapNum < 1 || tMapNum > 5);
            }
            tournamentArray.add(tMapNum);
            System.out.println("Maps:" + tMapNum);

            String strategyNumString=window.decideMaps("How many strategies for the Tournament?(Please input between 2-4)");
            tStrategyNum =Integer.parseInt(strategyNumString);
            if(tStrategyNum<2||tStrategyNum>4) {
                do {
                    window.infoBox("Please input between 2-4", "Warning");
                    strategyNumString=window.decideMaps("How many strategies for the Tournament?(Please input between 2-4)");
                    tStrategyNum =Integer.parseInt(strategyNumString);
                }
                while (tStrategyNum < 2 || tStrategyNum > 4);
            }
            tournamentArray.add(tStrategyNum);
            System.out.println("Strategies:" + tStrategyNum);

            String gameNumString=window.decideMaps("How many games for the Tournament?(Please input between 1-5)");
            tGameNum =Integer.parseInt(gameNumString);
            if(tGameNum<1||tGameNum>5) {
                do {
                    window.infoBox("Please input between 1-5", "Warning");
                    gameNumString=window.decideMaps("How many games for the Tournament?(Please input between 1-5)");
                    tGameNum =Integer.parseInt(gameNumString);
                }
                while (tGameNum < 1 || tGameNum > 5);
            }
            tournamentArray.add(tGameNum);
            System.out.println("Games:" + tGameNum);

            String turnNumString=window.decideMaps("How many turns for the Tournament?(Please input between 10-50)");
            tTurnsNum =Integer.parseInt(turnNumString);
            if(tTurnsNum<10||tTurnsNum>50) {
                do {
                    window.infoBox("Please input between 10-50", "Warning");
                    turnNumString=window.decideMaps("How many turns for the Tournament?(Please input between 10-50)");
                    tTurnsNum =Integer.parseInt(turnNumString);
                }
                while (tTurnsNum < 10 || tTurnsNum > 50);
            }
            tournamentArray.add(tTurnsNum);
            System.out.println("Turns:" + tTurnsNum);

            return tournamentArray;
        }



        public void addListeners(){
            Listener lis = new Listener();
//            p.gameSetUp(numOfPlayers);

            window.drawMapPanel(p);

            for (CountryButton cb : window.mapPanel.cbs) {
                cb.addActionListener(lis);
            }
            window.phasePanel.completePhaseButton.addActionListener(lis);
            window.phasePanel.saveButton.addActionListener(lis);
            p.connectView(); //after this updating window is enabled
            window.setVisible(true);
            window.cardExchangeView.Exchange3Infantry.addActionListener(lis);
            window.cardExchangeView.Exchange3Artillery.addActionListener(lis);
            window.cardExchangeView.Exchange3Cavalry.addActionListener(lis);
            window.cardExchangeView.Exchange3Diff.addActionListener(lis);
            window.cardExchangeView.Cancel.addActionListener(lis);

        }

        public void writeToFile(String saveFileName){
            try {
                FileOutputStream f = new FileOutputStream(new File(saveFileName));
                ObjectOutputStream o = new ObjectOutputStream(f);

                // Write objects to file
                o.writeObject(p);

                o.close();
                f.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        public Phases loadPhases(){
            Phases ph = null;
            try {
                FileInputStream fi = new FileInputStream(new File(loadFileName));
                ObjectInputStream oi = new ObjectInputStream(fi);
                ph = (Phases)oi.readObject();


                oi.close();
                fi.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return ph;


        }





}

