package Game;


import java.io.IOException;
import java.util.ArrayList;
import Models.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

/**
 * In Game package and this class is for loading the map
 * @author Team36
 * @version 1.1
 */
public class MapLoader {
    public MapEdit map;
    ArrayList<Continent> worldmap = new ArrayList<>();
    ArrayList<Country> graph = new ArrayList<>();
    ArrayList<ArrayList> result = new ArrayList<>();
    public MapLoader() {
        map = new MapEdit("a");
    }

    // This function is used to load map start game(include map validation)
    public ArrayList<ArrayList> loadMap(String mapName){
        Map<String,ArrayList<Country>> countries ;
        Map<String,ArrayList<String>> adjacencyList;
        map.loadMapFile(mapName);
        
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
        System.out.println(1);
        return result;
    }


}



