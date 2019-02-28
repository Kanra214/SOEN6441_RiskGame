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
    String country_value;
    public MapLoader() {
        map = new MapEdit("a");
    }


   //Don't use this function
    public ArrayList<ArrayList> load(String filePath) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String input = reader.readLine();
        if (input.equals("[Map]")) {
        	
            author = reader.readLine().trim();
            mapname = reader.readLine().trim();
            System.out.println("readmap");
            if (reader.readLine().trim().equals("[Continents]")) {
            	System.out.println("cont");
                //int numberOfContinents = Integer.parseInt(reader.readLine().trim());
                //while(reader.readLine().trim().equals("[Territories]")==false)
                	while ((country_value = reader.readLine()) != null) {
                    //country_value = reader.readLine();
                    String[] parts = country_value.split("=");
                    System.out.println(parts.length);
                    if(parts.length==1)break;
                    int control_value = Integer.parseInt(parts[1]);
                    System.out.println(parts[0]+" "+parts[1]);
                    worldmap.add(new Continent(parts[0], control_value));
                }
                if (reader.readLine().equals("[Territories]")) {
                	
                   // int numberOfTerritories = Integer.parseInt(reader.readLine().trim());
                	while ((input = reader.readLine()) != null) {
                        //input = reader.readLine();
                        System.out.println(input);
                        String[] parts = input.split(",");
                        System.out.println(parts.length);
                        if(parts.length==1)break;
                        Continent tempcont = findContinent(worldmap, parts[3]);
                        Country tempCountry = new Country((int)(Integer.parseInt(parts[1])*0.9)-100, (int)(Integer.parseInt(parts[2])*0.9), parts[0], tempcont);                      
                        tempcont.addCountry(tempCountry);
                        graph.add(tempCountry);

                    }
                	BufferedReader reader2 = new BufferedReader(new FileReader(filePath));
                	boolean rflag=true;
					while(rflag==true) {
                		input = reader2.readLine();
                		System.out.println(input);
                		if(input.equals("[Territories]")){
                			rflag=false;
                		}
                		System.out.println(rflag);
                	}
                   
                     	while ((input = reader2.readLine()) != null) {
                     		 System.out.println(input);
                     		Country tm1 = null, tm2 = null;
                            //input = reader2.readLine();
                            String[] parts = input.split(",");
                    	  for(int i=4;i<parts.length;i++) {                          	
                            for (Country o : graph) {

                                if (parts[0].equals(o.getName())) {
                                    tm1 = o;
                                }
                                if (parts[i].equals(o.getName())) {
                                    tm2 = o;
                                }
                                if (tm1 != null && tm2 != null) {
//                                  handler.addAtPos(new Relations((int) tm1.getX() + tm1.getValue() * 5 / 2, (int) tm1.getY() + tm1.getValue() * 5 / 2, (int) tm2.getX() + tm1.getValue() * 5 / 2, (int) tm2.getY() + tm1.getValue() * 5 / 2, ID.Relation), current);
//                                      handler.addObject(new Relations((int)tm1.getX()+tm1.getValue()*5/2, (int)tm1.getY()+tm1.getValue()*5/2, (int)tm2.getX()+tm1.getValue()*5/2, (int)tm2.getY()+tm1.getValue()*5/2, ID.Relation));
                                  tm1.addNeighbour(tm2);
                                  tm2.addNeighbour(tm1);

                              }

                            }
                          }
                     	}
                     	/*
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
//                        
 * 
 */
                     	ArrayList<ArrayList> result = new ArrayList<>();
                        result.add(graph);
                        result.add(worldmap);
                        return result;

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



