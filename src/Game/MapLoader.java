package Game;


import java.io.IOException;
import java.util.ArrayList;
import Models.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class MapLoader {
    public String author;
    public String mapname;
    ArrayList<Continent> worldmap = new ArrayList<>();
    ArrayList<Country> graph = new ArrayList<>();

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
                        Country tempCountry = new Country((int)(Integer.parseInt(parts[1])*0.9)-100, (int)(Integer.parseInt(parts[2])*0.9), parts[0], tempcont, Integer.parseInt(parts[4]));
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
                        ArrayList<ArrayList> result = new ArrayList<>();
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




}



