package Models;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Player {
    private static Color[] ALL_COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.LIGHT_GRAY, Color.ORANGE};

    private Color playerColor;
    private int unassigned_armies;
    private ArrayList<Country> realms;
    private int id;//this is primary key for players
    private int unitsOnMap = 0;

    public Player(int id, int army) {
        this.id = id;
        this.realms = new ArrayList<>();
        this.unassigned_armies = army;
        this.playerColor = ALL_COLORS[id];

    }

    public String getStringColor(){
        if (playerColor == Color.RED) return "red";
        if (playerColor == Color.BLUE) return "blue";
        if (playerColor == Color.GREEN) return "green";
        if (playerColor == Color.YELLOW) return "yellow";
        if (playerColor == Color.LIGHT_GRAY) return "light gray";
        if (playerColor == Color.ORANGE) return "orange";
        return "uknown";
    }

    public int getId() {
        return id;
    }

    public boolean armyLeft(){
        return unassigned_armies != 0;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public void getReinforcement(int extra){
        this.unassigned_armies += extra;
    }

    public int getUnitLeft() {
        return unassigned_armies;
    }

    public void deployArmy(Country country){
        if(armyLeft()) {
            country.incrementArmy();
            unassigned_armies--;
            unitsOnMap++;
        }
    }

    public int getUnitsOnMap() {
        return unitsOnMap;
    }

    public ArrayList<Country> getRealms() {
        return realms;
    }

    //This function is used to in Phase3 whether player can move army from one country to another;
    public boolean findPath(Country sourceCountry, Country targetCountry) {
        for (Country loopCountry : realms) {
            if (!loopCountry.getName().equals(sourceCountry.getName())) {
                JOptionPane.showMessageDialog(null, "Country  '" + loopCountry.getName() + "' does not belong to this player");
                return false;
            }
            if (!loopCountry.getName().equals(targetCountry.getName())) {
                JOptionPane.showMessageDialog(null, "Country  '" + loopCountry.getName() + "' does not belong to this player");
                return false;
            }
        }

        Queue<Country> queue = new LinkedList<Country>();
        HashSet<String> set = new HashSet<String>();
        queue.offer(sourceCountry);
        set.add(sourceCountry.getName());
        while (!queue.isEmpty()) {
            Country tempCountry = queue.poll();
            if (tempCountry.getName().equals(targetCountry.getName())) {
                return true;
            }
            for (Country loopCountry : realms) {
                    if (!set.contains(loopCountry.getName())) {
                        queue.offer(loopCountry);
                        set.add(loopCountry.getName());
                    }
            }

        }
        JOptionPane.showMessageDialog(null,"There isn't existing path between two countries");
        return false;
    }
}
