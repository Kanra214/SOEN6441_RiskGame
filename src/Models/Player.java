package Models;

import java.awt.*;
import java.util.ArrayList;

public class Player {
    private static Color[] ALL_COLORS = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.ORANGE};

    private Color playerColor;
    int unassigned_armies;
    public ArrayList<Country> realms;
    int army = 0;
    int id;//this is primary key for players

    public Player(int id) {
        this.id = id;
        this.realms = new ArrayList<>();

        this.playerColor = ALL_COLORS[id];

    }
    public Color getPlayerColor() {
        return playerColor;
    }
    public int getPlayerArmy() {
        return army;
    }
}
