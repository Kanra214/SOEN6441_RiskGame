package Models;
import java.awt.Color;
import java.util.ArrayList;

/**
 * <h1>Continent</h1>
 * This class is for continent object
 */
public class Continent {
    private final static Color[] ALL_COLORS = {Color.lightGray, Color.MAGENTA, Color.CYAN, Color.GREEN, Color.YELLOW, Color.PINK};
    private String name;
    private ArrayList<Country> countries;
    private int control_value;
    private Color contColor;
    private static int colorSelector = 0;
//public because mapLoader needs this

    /**
     * This is constructor
     * @param name name of continent
     * @param control_value control value of continent
     */
    public Continent(String name, int control_value){

        this.name = name;

        this.control_value = control_value;
        this.contColor = ALL_COLORS[colorSelector];
        colorSelector = (colorSelector+1) % 6;
        countries = new ArrayList<>();
    }

    /**
     * Get name of continent
     * @return continent's name
     */
    public String getName() {
        return name;
    }

    /**
     * Add country to continent
     * @param cont country object
     */
    public void addCountry(Country cont){
        countries.add(cont);
    }

    /**
     * Get color of continent
     * @return continent's color
     */
    public Color getContColor(){return contColor;}

    /**
     * Check ownership between continent and player
     * @param playerOwner player
     * @return boolean
     */
    protected boolean checkOwnership(Player playerOwner){
        for (Country c: countries) {
            if (c.getOwner() != playerOwner) return false;
        }
        return true;
    }

    /**
     * Get countries in continent
     * @return countries ArrayList
     */
    public ArrayList<Country> getCountries() {
        return countries;
    }

    /**
     * Get control value of continent
     * @return control value
     */
    public int getControl_value() {
        return control_value;
    }
}
