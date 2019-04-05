package Models;
import java.awt.Color;
import java.io.Serializable;
import java.util.Random;



import java.util.ArrayList;
import java.util.HashSet;

/**
 * <h1>Continent</h1>
 * This class is for continent object
 */
public class Continent implements Serializable {
    private String name;
    private ArrayList<Country> countries;
    private int control_value;
    private Color contColor;
    private static HashSet<Color> colorSet = new HashSet<>();
    private Player owner = null;

    //public because mapLoader needs this
    /**
     * This is constructor
     * @param name name of continent
     * @param control_value control value of continent
     */
    public Continent(String name, int control_value){

        this.name = name;
        this.control_value = control_value;
        this.contColor = chooseColor();
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
    public boolean checkOwnership(Player playerOwner){
        for (Country c: countries) {
            if (c.getOwner() != playerOwner){ return false;}
        }
        owner = playerOwner;

        return true;
    }

    public Player getOwner(){
        return owner;
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

    /**
     * Randomly choose a color for the continent
     * @return chosen color
     */
    private Color chooseColor() {
    	Random rand = new Random();
    	while(true) {
	    	float r = rand.nextFloat();
	    	float g = rand.nextFloat();
	    	float b = rand.nextFloat();
	    	Color randColor = new Color(r,g,b);
	    	if(!colorSet.contains(randColor)) {
	    		colorSet.add(randColor);
	    		return randColor;
	    	}
    	}
    	
    }
    protected void free(){
        owner = null;
    }
}
