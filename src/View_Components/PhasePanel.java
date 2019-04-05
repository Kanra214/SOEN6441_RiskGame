package View_Components;

import Models.Phases;
import Models.Player;

import javax.swing.*;
import java.util.ArrayList;

/**
 * <h1>PhasePanel</h1>
 * This is a JPanel showing information about current phase
 */
public class PhasePanel extends JPanel {
    private JLabel currentPhaseLabel;
    private JLabel currentPlayerLabel;
    private JLabel detailLabel;
    public JButton completePhaseButton;
    public JButton saveButton;
    private CardExchangeView cev;

    /**
     * This is constructor
     */
    protected PhasePanel(CardExchangeView cev){
        currentPlayerLabel = new JLabel();
        currentPhaseLabel = new JLabel();
        detailLabel = new JLabel();
        completePhaseButton = new JButton("complete this phase");
        saveButton = new JButton("save game");
        this.cev = cev;
        add(completePhaseButton);
        add(saveButton);
        add(currentPlayerLabel);
        add(currentPhaseLabel);
        add(detailLabel);
    }

    /**
     * Set context of this PhasePanel
     * @param p Phase object
     */
    protected void setContext(Phases p){
        Player currentPlayer = p.getCurrent_player();
        int currentPhase = p.getCurrentPhase();

        this.setBackground(currentPlayer.getPlayerColor());
        currentPhaseLabel.setText("<html><body>" +
                "<h3>Current Phase : " + phaseToString(currentPhase) + "</h3>" +
                "<p>" + phaseInstruction(currentPhase) + "</p>" +
                "</body><html>");
        currentPlayerLabel.setText("<html><body><h3>Current Player : "  + currentPlayer.getId() + "</h3></body><html>");
        detailLabel.setText("<html><body><h3>Unassigned armies : "  + currentPlayer.getUnassigned_armies() + "</h3></body><html>");




        if(currentPhase == 2){
            if(p.getInBattle()) {
                completePhaseButton.setEnabled(false);
                saveButton.setEnabled(false);
            }
            else{
                completePhaseButton.setEnabled(true);
                saveButton.setEnabled(true);

            }

        }
        //phase 0 and phase 1 when no army, enable button
        else if((currentPlayer.isArmyLeft())) {


            completePhaseButton.setEnabled(false);
            saveButton.setEnabled(true);
        }
        else if(cev.isVisible()){
            completePhaseButton.setEnabled(false);
            saveButton.setEnabled(false);
        }

        else{
            completePhaseButton.setEnabled(true);
            saveButton.setEnabled(true);
        }
    }

    /**
     * Convert currentPhase int representation to string representation
     * @param currentPhase int representing current phase
     * @return string representation
     */
    private String phaseToString(int currentPhase){
        switch (currentPhase){
            case 0: return "Startup Phase";
            case 1: return "Reinforcement Phase";
            case 2: return "Attack Phase";
            case 3: return "Fortification Phase";
            default: return "UnKnown";
        }
    }

    /**
     * Return phase instructions
     * @param currentPhase int representing current phase
     * @return string instructions
     */
    private String phaseInstruction(int currentPhase){
        switch(currentPhase){
            case 0: return "Please deploy army by clicking on your own countries until unassigned armies is 0";
            case 1: return "Please deploy army by clicking on your own countries until unassigned armies is 0";
            case 2: return "Please choose one of your own country with armies more than one to attack on another country of other players";
            case 3: return "Please choose two of your own countries and type in the number of armies you want to move";
            default: return "Unknown";

        }
    }
//    private String diceToString(ArrayList<Integer> dice){
//        String s = "--------";
//        for(int i = 0; i < dice.size(); i++){
//            s += dice.get(i) + "--------";
//        }
//        return s;
//
//
//    }
}
