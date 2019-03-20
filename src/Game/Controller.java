package Game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import Models.Player;
import View_Components.CardExchangeView;
import View_Components.CountryButton;
import View_Components.Window;
import View_Components.StartManu;
import java.util.ArrayList;

import Models.Phases;
import Models.Country;
import Models.RiskGameException;
import MapEditor.MapEditorGUI;

import javax.swing.*;

/**
 * <h1>Controller</h1>
 * This class is for controller, control the process of game
 */
public class Controller {
    Window window;
    Phases p;
    StartManu startmanu;
    MapEditorGUI mapeditor;
    CardExchangeView cardexchange;

    String filename;

    int count_conquest = 0;
    boolean allOut = false;
    private boolean conquest_flag = false;
    /**
     * Constructor
     * @param window current panel
     */
    public Controller(Window window) {
        this.window = window;
        this.cardexchange = new CardExchangeView();
    }

    /**
     * <h1>Listener</h1>
     * This class is for listen process of game
     */
    class Listener implements ActionListener {
        private Country chosenFrom = null;
        private Country chosenTo = null;

        /**
         * Pop-up window
         * @param e ActionEvent
         */
        public void actionPerformed(ActionEvent e) {
            System.out.println();
            if (e.getSource() == window.phasePanel.completePhaseButton) {
                System.out.println("Complete is called");
                p.nextPhase();
            }
            if (e.getSource() instanceof CountryButton) {
                Country chosen = ((CountryButton) e.getSource()).getCountry();
                if (p.getCurrentPhase() == 0) {
                    p.startUpPhase(chosen);
                } else if (p.getCurrentPhase() == 1) {
                    p.reinforcementPhase(chosen);
                } else if (p.getCurrentPhase() == 3) {
                    if (chosenFrom == null) {
                        chosenFrom = chosen;
                    } else {
                        chosenTo = chosen;
                        int num = Integer.parseInt(window.promptPlayer("How many armies to move? max: " + (chosenFrom.getArmy() - 1) + ", min: 1"));
                        try {
                            p.fortificationsPhase(chosenFrom, chosenTo, num);
                            chosenFrom = null;
                            chosenTo = null;
                        } catch (RiskGameException ex) {
                            window.showMsg(ex.errMsg + " Try again please.");
                            chosenFrom = null;
                            chosenTo = null;
                        }

                    }
                } else {
                	
                	cardexchange.setVisible(false);
                    System.out.println("Phase attack");

                    if (chosenFrom == null) {
                        chosenFrom = chosen;
                    } else {
                        chosenTo = chosen;
                        int numAttack = Integer.parseInt(window.promptPlayer("Attacker: How many armies? max: 3, min: 1"));
                        int numDefence = Integer.parseInt(window.promptPlayer("Defender: How many armies to choose? max: 2, min: 1"));
                        try {
                            if (numAttack > 3 || numAttack < 1) {
                                window.showMsg("You should select attack arimes between 1-3 ");
//                                System.out.println("You should select attack arimes between 1-3 ");
                            }
                            if (numDefence > 2 || numDefence < 1) {
                                window.showMsg("You should select defend arimes between 1-2 ");
//                                System.out.println("You should select defend arimes between 1-2 ");
                            }
                            if (allOut){
                                conquest_flag = p.attackAll(chosenFrom, chosenTo, chosenFrom.getArmy() - 1);
                            }else {
                                conquest_flag = p.attack(chosenFrom, chosenTo, numAttack, numDefence);
                            }
                            if (conquest_flag){
                                int assignArmy = Integer.parseInt(window.promptPlayer("How many armies to move? max: " + (chosenFrom.getArmy() - 1) + ", min: 1"));
                                try {
                                    p.attackAssign(chosenFrom, chosenTo, assignArmy);
                                }catch (RiskGameException ex){

                                    window.showMsg(ex.errMsg + " Already set to default army 1.");
                                }
                            }
                            System.out.println("Conquest");
                            chosenFrom = null;
                            chosenTo = null;
                        } catch (RiskGameException ex) {
                            window.showMsg(ex.errMsg + " Try again please.");
                            chosenFrom = null;
                            chosenTo = null;
                        }
                        if(p.isOwnerOfAllCountries(p.getCurrent_player())){
                            System.out.println("Winner is Player "+p.getCurrent_player().getId());
                            System.exit(0);
                        }
                        if(!checkAttack(p.getCurrent_player())){
                            p.nextPhase();
                        }
                    }
                }
            }
        }
    }

    protected boolean checkAttack(Player player){
        boolean val = true;
        int count_army = 0;
        int count_owner = 0;
        for (Country country : player.getRealms()){
            if (country.getArmy() == 1){
                count_army ++;
            }
            for (Country nei : country.getNeighbours()){
                if (nei.getOwner() != player){
                    count_owner ++;
                }
            }
        }
        if (count_army == player.getRealms().size()){
            val = false;
        }
        if (count_owner == 0){
            val = false;
        }
        return val;
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
            System.exit(0);
        }

        int numOfPlayers = Integer.parseInt(window.promptPlayer("how many players?"));
        if (numOfPlayers > 6 || numOfPlayers < 2) {
            JOptionPane.showMessageDialog(null, "Wrong number of Players");
            System.exit(0);
        }
        p = new Phases(tempMap.get(0), tempMap.get(1), cardexchange);
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
        
    }
}