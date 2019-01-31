package Game;//this is window class, painting functions here

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import Panels.*;

public class Window extends JFrame {
    final static int width = 1260;
    final static int height = 785;

    public Window(){
        super("Risk Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(width,height);
        setResizable(false);
        setVisible(true);




    }
    public void welcome() throws IOException {

        Image background = ImageIO.read(new File("welcome.jpeg"));
        ImagePanel welcomePanel = new ImagePanel(background);
        getContentPane().add(welcomePanel);
        setVisible(true);


    }

    public String promptPlayer(String dialog) {
        String input = JOptionPane.showInputDialog(this, dialog);
        System.out.println("number of players: " + input);
        return input;

    }




}
