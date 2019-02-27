package View_Components;

import Models.Player;

import javax.swing.*;
import java.util.ArrayList;

public class SidePanel extends JPanel {
    private JLabel[] playerLabels = new JLabel[6];

    protected SidePanel(){
        playerLabels[0] = new JLabel();
        playerLabels[1] = new JLabel();
        playerLabels[2] = new JLabel();
        playerLabels[3] = new JLabel();
        playerLabels[4] = new JLabel();
        playerLabels[5] = new JLabel();
        add(playerLabels[0]);
        add(playerLabels[1]);
        add(playerLabels[2]);
        add(playerLabels[3]);
        add(playerLabels[4]);
        add(playerLabels[5]);
    }

    protected void setContext(ArrayList<Player> players){
        for(int i = 0; i < players.size(); i++){
            playerLabels[i].setBackground(players.get(i).playerColor);
            playerLabels[i].setText(getPlayerInfo(players.get(i)));


        }
    }
    private String getPlayerInfo(Player player){
          return    "<html><body>" +
                    "<h3>Player " + player.getId() + "</h3>" +
                    "<p>Countries: " + player.realms.size() + "<br>" +
                    "Total number of armies in the map: " + player.getMapArmies() + "<br>" +
                    "Card " + "</p>" +
                    "</body></html>";

    }




}
