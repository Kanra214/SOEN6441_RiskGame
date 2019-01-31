package Models;
import java.awt.Color;
import java.util.ArrayList;
import Game.Window;

public class Continent {
    private final static Color[] ALL_COLORS = {Color.ORANGE, Color.PINK, Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW};
    private String name;
    private ArrayList<Country> countries;
    private int control_value;
    private Color contColor;
    private static int colorSelector = 0;
//    boolean check;
    public Continent(String name, int control_value){

        this.name = name;

        this.control_value = control_value;
        this.contColor = ALL_COLORS[colorSelector];
        colorSelector = (colorSelector+1) % 6;
        countries = new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public void addCountry(Country cont){
        countries.add(cont);
    }

}
