package View_Components;

import javax.swing.*;
import java.awt.*;

/**
 * <h1>ImagePanel</h1>
 * This class is for showing images on the window
 */
public class ImagePanel extends JPanel {
    private Image icon;

    /**
     * This is constructor
     * @param im image to show
     */
    public ImagePanel(Image im){
        this.icon = im;

    }

    /**
     * Drawing method to draw the image
     * @param g Graphics object
     */
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(this.icon, 0,0,null);
    }
}
