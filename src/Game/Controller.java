//this is center controller
package Game;
import java.io.IOException;
import Models.*;
import java.util.ArrayList;
import Panels.*;
import javax.swing.*;



public class Controller {
    Window window;
    Phases p;
    public Controller(Window window){
        this.window = window;

    }
    public void start() throws IOException {
        window.welcome();
        JPanel mainPanel = new JPanel();
        MapPanel mapPanel = new MapPanel();


        ArrayList<ArrayList> tempMap = new MapLoader().load("entry.txt");

        int numOfPlayers = Integer.parseInt(window.promptPlayer("how many players?"));
        p = new Phases(tempMap.get(0), tempMap.get(1));

        p.addPlayers(numOfPlayers);
        p.determineOrder();
        p.countryAssignment();
        for(Country country : p.graph){
            mapPanel.add(country);
        }
        mainPanel.add(mapPanel);
        window.setContentPane(mainPanel);
        window.setVisible(true);







    }







}
