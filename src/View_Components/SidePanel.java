package View_Components;

import Game.MapLoader;
import Models.Continent;
import Models.Country;
import Models.Player;


import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * <h1>SidePanel</h1>
 * This class is to used to show information about players
 */
public class SidePanel extends JPanel {
    private JLabel[] playerLabels = new JLabel[6];
    private JLabel continentsLabel;
    /**
     * This is constructor
     */
    protected SidePanel(){
        for(int i = 0; i < 6; i++) {
            playerLabels[i] = new JLabel();
            playerLabels[i].setOpaque(true);

            add(playerLabels[i]);

        }
        continentsLabel = new JLabel();
        add(continentsLabel);
    }

    /**
     * Set context of this SidePanel
     * @param players ArrayList of Players
     */
    protected void setContext(ArrayList<Player> players, ArrayList<Country> country, ArrayList<Continent> continents){
        for(int i = 0; i < players.size(); i++){
            playerLabels[i].setBackground(players.get(i).getPlayerColor());
            playerLabels[i].setText(getPlayerInfo(players.get(i), country.size()));
        }
        continentsLabel.setText(getContinentInfo(continents));
    }

    /**
     * Get player's information
     * @param player info from whom you want to show
     * @return String representation of player
     */
    private String getPlayerInfo(Player player, int countryNumber){
        float playerCountryNumber = (float) player.getRealms().size();
        float countryNumberDouble = (float) countryNumber;
        return    "<html><body>" +
                "<h3>Player " + player.getId() + "(" + strategyToString(player) + ")</h3>" +
                "<p><font size='2'>Countries: " + player.getRealms().size() +
                "<br>" +
                "Percentage: " + playerCountryNumber / countryNumberDouble * 100 +"%"+
                "<br>" +
                "Total number of armies in the map: " + (player.getMapArmies()) + "<br></font></p>" +
                "<p><font size='2'>Cards:" + cardToString(player) + "</font></p> " +
                "</body></html>";

    }
    private String cardToString(Player player){
        String output = "";
        for(int i = 0; i < 3; i++){
            output += player.getCards().showCardsName(i) + ": " + player.getCards().showCardsNumber(i) + "; ";
        }
        return output;
    }
    private String getContinentInfo(ArrayList<Continent> continents){
        String continentOwner = "";
        for(int i = 0; i < continents.size(); i++){
            
            if(continents.get(i).getOwner() == null){
                continentOwner += continents.get(i).getName() + ": null" + "; ";
            }
            else {
                continentOwner += continents.get(i).getName() + ":Player " + continents.get(i).getOwner().getId() + "; ";
            }
        }
        return    "<html><body>" +
                "<h3>Continents Info</h3>" +
                "<p><font size='2'>" +continentOwner + "<br><br><br><br><br></font></p>" +
                "</body></html>";

    }
    private String strategyToString(Player player){
        if(player.getStrategy().getName().equals("Human")){
            return "Human";
        }
        else{
            return player.getStrategy().getClass().getName().substring(player.getStrategy().getClass().getName().indexOf(".") + 1);
        }
    }

}
