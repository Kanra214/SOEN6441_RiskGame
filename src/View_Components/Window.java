package View_Components;


import Models.Country;
import Models.Phases;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * <h1>Window</h1>
 * This class is used as the window to contain game view components
 */
public class Window extends JFrame implements Observer {
    private final static int X = 0;
    private final static int Y = 0;
    private final static int WIDTH = 1260;
    private final static int HEIGHT = 785;
    private final static int MAP_X = X;
    private final static int MAP_Y = Y;
    private final static int MAP_WIDTH = (int)WIDTH*3/4;
    private final static int MAP_HEIGHT = (int)HEIGHT*3/4;
    private final static int SIDE_PANEL_X = MAP_X + MAP_WIDTH;
    private final static int SIDE_PANEL_Y = Y;
    private final static int SIDE_PANEL_WIDTH = WIDTH - SIDE_PANEL_X;
    private final static int SIDE_PANEL_HEIGHT = HEIGHT - SIDE_PANEL_Y;
    private final static int PHASE_PANEL_X = MAP_X;
    private final static int PHASE_PANEL_Y = Y + MAP_HEIGHT;
    private final static int PHASE_PANEL_WIDTH = MAP_WIDTH ;
    private final static int PHASE_PANEL_HEIGHT = HEIGHT - MAP_HEIGHT;










    private JPanel mainPanel;
    public MapPanel mapPanel;
    public PhasePanel phasePanel;
    private SidePanel sidePanel;


    /**
     * This is constructor
     */
    public Window(){
        //window settings
        super("Risk Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(WIDTH,HEIGHT);
        setResizable(false);


        //main panel settings
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(X,Y,WIDTH,HEIGHT);


        //map panel settings
        mapPanel = new MapPanel();
        mapPanel.setLayout(null);
        mapPanel.setBounds(MAP_X,MAP_Y,MAP_WIDTH, MAP_HEIGHT);


        //phase panel settings
        phasePanel = new PhasePanel();

        phasePanel.setBounds(PHASE_PANEL_X,PHASE_PANEL_Y,PHASE_PANEL_WIDTH,PHASE_PANEL_HEIGHT );
        phasePanel.setBackground(Color.YELLOW);
        phasePanel.setLayout(new GridLayout(1,4));


        //side panel settings
        sidePanel = new SidePanel();
        sidePanel.setBounds(SIDE_PANEL_X,SIDE_PANEL_Y,SIDE_PANEL_WIDTH,SIDE_PANEL_HEIGHT );
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setLayout(new GridLayout(6,1));





        mainPanel.add(mapPanel);
        mainPanel.add(sidePanel);
        mainPanel.add(phasePanel);
        add(mainPanel);





    }


    /**
     * Prompt for user input
     * @param dialog dialog shown to the user
     * @return user input
     */
    public String promptPlayer(String dialog) {
        String input = JOptionPane.showInputDialog(this, dialog);

        return input;

    }

    /**
     * Show message on the screen
     * @param dialog dialog shown to the user
     */
    public void showMsg(String dialog){
        JOptionPane.showMessageDialog(this, dialog);
    }


    @Override
    /**
     * update this window
     * @param o Observer object
     * @param arg other argument
     */
    public void update(Observable o, Object arg) {

        Phases p = (Phases)o;
        //mapPanel is not a part of observer pattern
        //update phasePanel
        phasePanel.setContext(p.getCurrentPhase(), p.getCurrent_player());
        sidePanel.setContext(p.getPlayers());





    }

    /**
     * Draw countries on MapPanel
     * @param p Phases object
     */
    public void drawMapPanel(Phases p){
        for(Country country : p.getGraph()){
//            country.setPhase(p);


            JLabel label = new JLabel(country.getName());
            label.setBounds(country.getX(), country.getY() - 20,150,20);
            mapPanel.add(label);
            CountryButton cb = new CountryButton(country);
            mapPanel.add(cb);
            mapPanel.addCb(cb);
//            country.countryButton.addActionListener(lis);
            mapPanel.comps.add(country);


        }
    }


}
