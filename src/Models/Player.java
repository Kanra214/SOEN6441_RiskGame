package Models;

import jdk.nashorn.internal.ir.IfNode;

import java.awt.*;
import java.util.ArrayList;

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
}
