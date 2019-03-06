package View_Components;

import Models.Player;

import javax.swing.*;

/**
 * <h1>PhasePanel</h1>
 * This is a JPanel showing information about current phase
 */
public class PhasePanel extends JPanel {
    private JLabel currentPhaseLabel;
    private JLabel currentPlayerLabel;
    private JLabel unitLeftLabel;
    public JButton completePhaseButton;

    /**
     * This is constructor
     */
    protected PhasePanel(){
        currentPlayerLabel = new JLabel();
        currentPhaseLabel = new JLabel();
        unitLeftLabel = new JLabel();
        completePhaseButton = new JButton("complete this phase");
        add(completePhaseButton);
        add(currentPlayerLabel);
        add(currentPhaseLabel);
        add(unitLeftLabel);
    }

    /**
     * Set context of this PhasePanel
     * @param currentPhase int representing current phase
     * @param currentPlayer Player which is current player
     */
    protected void setContext(int currentPhase, Player currentPlayer){
        this.setBackground(currentPlayer.getPlayerColor());
        currentPhaseLabel.setText("<html><body><h1>Current Phase : " + phaseToString(currentPhase) + "</h1></body><html>");
        currentPlayerLabel.setText("<html><body><h1>Current Player : "  + currentPlayer.getId() + "</h1></body><html>");
        unitLeftLabel.setText("<html><body><h1>Unassigned Armies: " + currentPlayer.getUnassigned_armies()+ "</h1></body><html>");

        if(currentPhase == 2 || currentPhase == 3){
            completePhaseButton.setEnabled(true);

        }
        //phase 0 and phase 1 when no army, enable button
        else if(!currentPlayer.isArmyLeft()){
            completePhaseButton.setEnabled(true);
        }
        else{
            completePhaseButton.setEnabled(false);
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




}
