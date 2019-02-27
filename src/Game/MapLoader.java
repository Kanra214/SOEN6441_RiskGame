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
    public String author;
    public String mapname;
    public MapEdit map;
    ArrayList<Continent> worldmap = new ArrayList<>();
    ArrayList<Country> graph = new ArrayList<>();
    ArrayList<ArrayList> result = new ArrayList<>();
    public MapLoader() {
        map = new MapEdit("a");
    }


   //Don't use this function
    public ArrayList<ArrayList> load(String filePath) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("entry.txt"));
        String input = reader.readLine();
        if (input.equals("[Map]")) {
            author = reader.readLine().trim();
            mapname = reader.readLine().trim();
            if (reader.readLine().trim().equals("[Continents]")) {
                int numberOfContinents = Integer.parseInt(reader.readLine().trim());
                for (int i = 0; i < numberOfContinents; i++) {
                    String country_value = reader.readLine();
                    String[] parts = country_value.split(" ");
                    int control_value = Integer.parseInt(parts[1]);


                    worldmap.add(new Continent(parts[0], control_value));
                }
                if (reader.readLine().equals("[Territories]")) {
                    int numberOfTerritories = Integer.parseInt(reader.readLine().trim());
                    for (int i = 0; i < numberOfTerritories; i++) {
                        input = reader.readLine();
                        String[] parts = input.split(" ");
                        Continent tempcont = findContinent(worldmap, parts[3]);
                        Country tempCountry = new Country((int)(Integer.parseInt(parts[1])*0.9)-100, (int)(Integer.parseInt(parts[2])*0.9), parts[0], tempcont);
                        tempcont.addCountry(tempCountry);
                        graph.add(tempCountry);



                    }
                    if (reader.readLine().equals("[Relationship]")) {
                        input = reader.readLine();
                        while (input != null) {
                            String[] parts = input.split(" ");
                            Country tm1 = null, tm2 = null;
                            for (Country o : graph) {

                                if (parts[0].equals(o.getName())) {
                                    tm1 = o;
                                }
                                if (parts[1].equals(o.getName())) {
                                    tm2 = o;
                                }

                            }
                            if (tm1 != null && tm2 != null) {
//                                handler.addAtPos(new Relations((int) tm1.getX() + tm1.getValue() * 5 / 2, (int) tm1.getY() + tm1.getValue() * 5 / 2, (int) tm2.getX() + tm1.getValue() * 5 / 2, (int) tm2.getY() + tm1.getValue() * 5 / 2, ID.Relation), current);
//                                    handler.addObject(new Relations((int)tm1.getX()+tm1.getValue()*5/2, (int)tm1.getY()+tm1.getValue()*5/2, (int)tm2.getX()+tm1.getValue()*5/2, (int)tm2.getY()+tm1.getValue()*5/2, ID.Relation));
                                tm1.addNeighbour(tm2);
                                tm2.addNeighbour(tm1);

                            }

                            input = reader.readLine();
                        }
//                        ArrayList<ArrayList> result = new ArrayList<>();
                        result.add(graph);
                        result.add(worldmap);
                        return result;

                    }
                } else {
                    System.err.println("Wrong Format3");
                    System.exit(0);
                }
            } else {
                System.err.println("Wrong Format2");
                System.exit(0);
            }
        } else {
            System.err.println("Wrong Format1");
            System.exit(0);
        }
        return null;


    }





    Continent findContinent(ArrayList<Continent> worldmap, String contname){
        for(Continent cont : worldmap){
            if(cont.getName().equals(contname)){
                return cont;
            }
        }
        return null;
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



