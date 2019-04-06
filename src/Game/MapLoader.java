package Game;



import java.util.ArrayList;
import Models.*;
import java.util.Map;
import MapEditor.*;

/**
 * <h1>MapLoader</h1>
 * This class is for load the map
 */
public class MapLoader {
    public MapEdit map;
    ArrayList<Continent> worldmap = new ArrayList<>();
    ArrayList<Country> graph = new ArrayList<>();
    ArrayList<ArrayList> result = new ArrayList<>();

    /**
     * Constructor
     */
    public MapLoader() {
        map = new MapEdit("a");
    }

    /**
     * This function is used to load map start game(include map validation)
     * @param mapName name of map
     * @return map swich to a ArrayList
     */
    public ArrayList<ArrayList> loadMap(String mapName){
        Map<String,ArrayList<Country>> countries ;
        Map<String,ArrayList<String>> adjacencyList;
        boolean flag = map.loadMapFile(mapName);
        if (flag == false) {
            return null;
        }
        countries = map.countries;
        adjacencyList = map.adjacencyList;
        worldmap = map.continents;
        
 

        for (ArrayList<Country> tempCountryList : countries.values()){
            for (Country tempCountry : tempCountryList){
                graph.add(tempCountry);
            }
        }

        for (Continent tempContinent : worldmap) {
            for (String continentname : countries.keySet()) {
                if (tempContinent.getName().equals(continentname)) {
                    for (Country tempCountry : countries.get(continentname)){
                        tempContinent.getCountries().add(tempCountry);
                    }
                }
            }

        }
        for (Country tempCountry : graph) {
            for (String countryName : adjacencyList.keySet()) {
                if (tempCountry.getName().equals(countryName)) {
                    for (String neighbourName : adjacencyList.get(countryName)) {
                        for (Country neighbourCountry : graph) {
                            if (neighbourCountry.getName().equals(neighbourName)) {
                                tempCountry.getNeighbours().add(neighbourCountry);
                            }
                        }

                    }
                }
            }
        }

        result.add(graph);
        result.add(worldmap);

//        System.out.println(1);
        return result;
    }


}



