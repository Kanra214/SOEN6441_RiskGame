package View_Components;

import Models.Player;

import javax.swing.*;

public class PhasePanel extends JPanel {
    private JLabel currentPhaseLabel;
    private JLabel currentPlayerLabel;
    private JLabel unitLeftLabel;
    public JButton completePhaseButton;

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

    protected void setContext(int currentPhase, Player currentPlayer){
        this.setBackground(currentPlayer.playerColor);
        currentPhaseLabel.setText("<html><body><h1>Current Phase : " + phaseToString(currentPhase) + "</h1></body><html>");
        currentPlayerLabel.setText("<html><body><h1>Current Player : "  + currentPlayer.getId() + "</h1></body><html>");
        unitLeftLabel.setText("<html><body><h1>Unassigned Armies: " + currentPlayer.getPlayerArmy()+ "</h1></body><html>");

    }

    private String phaseToString(int currentPhase){
        switch (currentPhase){
            case 1: return "Reinforcement Phase";
            case 2: return "Attack Phase";
            case 3: return "Fortification Phase";
            default: return "UnKnown";
        }
    }




}
