package Models;
import java.awt.Color;
import java.util.ArrayList;

public class Continent {
    private final static Color[] ALL_COLORS = {Color.lightGray, Color.MAGENTA, Color.CYAN, Color.GREEN, Color.YELLOW, Color.PINK};
    private String name;
    private ArrayList<Country> countries;
    private int control_value;
    private Color contColor;
    private static int colorSelector = 0;

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
    public Color getContColor(){return contColor;}

    public boolean checkOwnership(Player playerOwner){
        for (Country c: countries) {
            if (c.getOwner() != playerOwner) return false;
        }
        return true;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public int getControl_value() {
        return control_value;
    }
}
