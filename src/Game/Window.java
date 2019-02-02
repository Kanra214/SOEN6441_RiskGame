package Game;//this is window class, painting functions here

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import Panels.*;

public class Window extends JFrame {
    final static int width = 1260;
    final static int height = 785;
    public JPanel mainPanel;
    public MapPanel mapPanel;
    public PhasePanel phasePanel;
    public SidePanel sidePanel;

    public Window(){
        super("Risk Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setLocationRelativeTo(null);
        setSize(width,height);
        setResizable(false);
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c =  new GridBagConstraints();
        mapPanel = new MapPanel();
        mapPanel.setPreferredSize(new Dimension(1000,600));
        phasePanel = new PhasePanel();
        phasePanel.setPreferredSize(new Dimension(1000,185));
        sidePanel = new SidePanel();
        phasePanel.setPreferredSize(new Dimension(260,785));

        phasePanel.setBackground(Color.YELLOW);
        sidePanel.setBackground(Color.WHITE);

        mapPanel.setLayout(null);

        c.ipady = 600;
        c.ipadx = 1000;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 3;
        c.gridwidth = 3;


        mainPanel.add(mapPanel,c);
        c.ipady = 785;
        c.ipadx = 260;
        c.gridx = 3;
        c.gridy = 0;
        c.gridheight = 4;
        c.gridwidth = 1;


        mainPanel.add(sidePanel,c);
        c.ipady = 185;
        c.ipadx = 1000;
        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 3;

        mainPanel.add(phasePanel,c);
        add(mainPanel);



        JLabel playerLabel1 = new JLabel("player1: ");
        JLabel playerLabel2 = new JLabel("player2: ");
        JLabel playerLabel3 = new JLabel("player3: ");
        JLabel playerLabel4 = new JLabel("player4: ");
        JLabel playerLabel5 = new JLabel("player5: ");
        JLabel playerLabel6 = new JLabel("player6: ");
        sidePanel.setLayout(new GridLayout(6,1));
        sidePanel.setPreferredSize(new Dimension(260,750));
        sidePanel.add(playerLabel1);
        sidePanel.add(playerLabel2);
        sidePanel.add(playerLabel3);
        sidePanel.add(playerLabel4);
        sidePanel.add(playerLabel5);
        sidePanel.add(playerLabel6);
        JLabel currentPlayerLabel = new JLabel("current player: ");
        currentPlayerLabel.setPreferredSize(new Dimension(40,40));
        JLabel currentPhaseLabel = new JLabel("curent phase: ");
        currentPhaseLabel.setPreferredSize(new Dimension(40,40));
        JLabel unitLeftLabel = new JLabel("unit left: ");
        unitLeftLabel.setPreferredSize(new Dimension(40,40));
        JButton completePhaseButton = new JButton("complete this phase");
        completePhaseButton.setSize(new Dimension(20,20));
        phasePanel.setLayout(new GridLayout(2,2));




        phasePanel.add(completePhaseButton);
        phasePanel.add(currentPlayerLabel);
        phasePanel.add(currentPhaseLabel);
        phasePanel.add(unitLeftLabel);

//        pack();















    }
//    public void showLayout(){
//        add(mainPanel);
//        setVisible(true);
//
//    }
    public void welcome() throws IOException {

        Image background = ImageIO.read(new File("welcome.jpeg"));
        ImagePanel welcomePanel = new ImagePanel(background);
        add(welcomePanel);
        setVisible(true);


    }

    public String promptPlayer(String dialog) {
        String input = JOptionPane.showInputDialog(this, dialog);
        System.out.println("number of players: " + input);
        return input;

    }





}
