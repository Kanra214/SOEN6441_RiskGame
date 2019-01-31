package Panels;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image icon;
    public ImagePanel(Image im){
        this.icon = im;

    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(this.icon, 0,0,null);
    }
}
