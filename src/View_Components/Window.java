package View_Components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Window extends JFrame {
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





    public JPanel mainPanel;
    public MapPanel mapPanel;
    public PhasePanel phasePanel;
    public SidePanel sidePanel;

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






        JLabel playerLabel1 = new JLabel("player1: ");
        JLabel playerLabel2 = new JLabel("player2: ");
        JLabel playerLabel3 = new JLabel("player3: ");
        JLabel playerLabel4 = new JLabel("player4: ");
        JLabel playerLabel5 = new JLabel("player5: ");
        JLabel playerLabel6 = new JLabel("player6: ");


        sidePanel.add(playerLabel1);
        sidePanel.add(playerLabel2);
        sidePanel.add(playerLabel3);
        sidePanel.add(playerLabel4);
        sidePanel.add(playerLabel5);
        sidePanel.add(playerLabel6);




        JLabel currentPlayerLabel = new JLabel("current player: ");
        JLabel currentPhaseLabel = new JLabel("curent phase: ");
        JLabel unitLeftLabel = new JLabel("unit left: ");
        JButton completePhaseButton = new JButton("complete this phase");

        phasePanel.add(completePhaseButton);
        phasePanel.add(currentPlayerLabel);
        phasePanel.add(currentPhaseLabel);
        phasePanel.add(unitLeftLabel);

        mainPanel.add(mapPanel);
        mainPanel.add(sidePanel);
        mainPanel.add(phasePanel);
        add(mainPanel);





    }

//    public void welcome() throws IOException {
//
//        Image background = ImageIO.read(new File("welcome.jpeg"));
//        ImagePanel welcomePanel = new ImagePanel(background);
//        add(welcomePanel);
//        setVisible(true);
//
//
//    }

    public String promptPlayer(String dialog) {
        String input = JOptionPane.showInputDialog(this, dialog);
        System.out.println("number of players: " + input);
        return input;

    }





}
