package View_Components;


import Models.Card;
import Models.Country;
import Models.Phases;
import Models.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * <h1>Window</h1>
 * This class is used as the window to contain game view components
 */
public class Window extends JFrame implements Observer {
    private final static int X = 0;
    private final static int Y = 0;
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

//    private final static int WIDTH = 1060;
//    private final static int HEIGHT = 685;
    private final static int WIDTH = (int) screenSize.getWidth();
    private final static int HEIGHT = (int) screenSize.getHeight();

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
    public CardExchangeView cardExchangeView;


    /**
     * This is constructor
     */
    public Window(){
        //window settings
        super("Risk Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setSize(WIDTH,HEIGHT);

        
        //escape the program by pressing escape key
        InputMap im = getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getRootPane().getActionMap();
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
        am.put("cancel", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setResizable(false);

        //card exchange view settings
        cardExchangeView = new CardExchangeView();


        //main panel settings
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(X,Y,WIDTH,HEIGHT);


        //map panel settings
        mapPanel = new MapPanel();
        mapPanel.setLayout(null);
        mapPanel.setBounds(MAP_X,MAP_Y,MAP_WIDTH, MAP_HEIGHT);


        //phase panel settings
        phasePanel = new PhasePanel(cardExchangeView);

        phasePanel.setBounds(PHASE_PANEL_X,PHASE_PANEL_Y,PHASE_PANEL_WIDTH,PHASE_PANEL_HEIGHT );
        phasePanel.setBackground(Color.YELLOW);
        phasePanel.setLayout(new GridLayout(1,5));


        //side panel settings
        sidePanel = new SidePanel();
        sidePanel.setBounds(SIDE_PANEL_X,SIDE_PANEL_Y,SIDE_PANEL_WIDTH,SIDE_PANEL_HEIGHT );
        sidePanel.setBackground(Color.WHITE);
        sidePanel.setLayout(new GridLayout(7,1));







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


    /**
     * Update this window
     */
    @Override
    public void update(Observable o, Object arg) {
        mapPanel.repaint();
        Phases p = (Phases) o;
        if(p.getCurrent_player().getStrategy() != null){
            //disable all the buttons and popping outs

            phasePanel.completePhaseButton.setEnabled(false);


        }
        else {
            for(JButton b : mapPanel.cbs){
                b.setEnabled(true);
            }
            cardExchangeView.setContext(p);

            if (arg instanceof String) {
                String CName = (String) arg;
                showMsg("This card will add to " + CName);
            } else if (arg instanceof Card) {//receive enemy card
                Card cards = (Card) arg;
                showMsg("You received these cards from conquering: " + cardsToString(cards));
            }
        }


            //mapPanel is not a part of observer pattern
            //update phasePanel

            phasePanel.setContext(p);
            sidePanel.setContext(p.getPlayers(), mapPanel.comps, p.getWorldmap());

            if (p.getCurrentPhase() == 2) {
                if (arg instanceof Player) {
                    Player player = (Player) arg;
                    String msg = "<html><body>" +
                            "<h1>Attacker: </h1>" +
                            "<h3>" + "Dice:" + "</h3>" +
                            "<p>" + diceToString(p.getCurrent_player().getDice()) + "</p>" +
                            "<br>" +
                            "<h1>Defender: </h1>" +
                            "<h3>" + "Dice:" + "</h3>" +
                            "<p>" + diceToString(p.getRival().getDice()) + "</p>" +
                            "<br>" +
                            "<h1>" + "Result:" + "</h1>" +
                            "<br>";

                    if (player.getId() == p.getCurrent_player().getId()) {
                        msg += "<h3>" + "Attacker loses one army" + "</h3>";
                    } else {
                        msg += "<h3>" + "Defender loses one army" + "</h3>";

                    }
                    msg += "<br>" + "</body><html>";

                    showMsg(msg);

                }

                if (!p.getAttackingIsPossible()) {
                    showMsg("No attacking can be made. Moving onto the next phase...");
                }


            }










    }

    private String diceToString(ArrayList<Integer> dice){
        String s = "";

        for(int i = 0; i < dice.size(); i++){
            if(i == 0){
                s += "<strong>" + dice.get(i) + "</strong>";
            }
            else {


                s += "----" + dice.get(i);
            }
        }
        return s;


    }
    private String cardsToString(Card cards){
        String s = "";

        for(int i = 0; i < 3; i++){

            s += cards.showCardsName(i) + ": " + cards.showCardsNumber(i) + "; ";


        }
        return s;
    }

    /**
     * Draw countries on MapPanel
     * @param p Phases object
     */
    public void drawMapPanel(Phases p){
        for(Country country : p.getGraph()){
            JLabel label = new JLabel();
            label.setText("<html><body><p><font size='2'>" + country.getName() + "(" + country.getCont().getName() + ")</font></p></body></html>");
            label.setBounds(country.getX(), country.getY() - 20,150,20);
            mapPanel.add(label);
            CountryButton cb = new CountryButton(country);
            mapPanel.add(cb);
            mapPanel.addCb(cb);
            mapPanel.comps.add(country);
        }
    }

    public int[] decidePlayers(){
        int[] values = new int[5];
        //test
        values[0] = 1;//human
        values[1] = 0;//aggressive
        values[2] = 1;//benevolent
        values[3] = 0;
        values[4] = 0;
        //TODO: retrieve user input. values[0] = number of human players, value[1] = number of aggressive players etc..
        return values;
    }



    

}
