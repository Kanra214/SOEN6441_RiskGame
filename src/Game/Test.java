package Game;
import javax.swing.*;
import java.awt.*;

public class Test {
    public static void main(String args[]){
        JFrame window = new JFrame("test");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setSize(1260,785);
        window.setResizable(false);
        window.setLayout(new GridBagLayout());
        GridBagConstraints r =  new GridBagConstraints();


//        JButton b = new JButton("button");
        JPanel a = new JPanel();

        JPanel b = new JPanel();
        JPanel c = new JPanel();
//        JPanel d = new JPanel();
        a.setBackground(Color.BLACK);
        b.setBackground(Color.RED);
        c.setBackground(Color.BLUE);
//        d.setBackground(Color.YELLOW);
//        a.setLayout(null);
//        window.setLayout(null);
//        b.setBounds(100,100,100,100);

        r.ipady = 600;
        r.ipadx = 1000;
        r.gridx = 0;
        r.gridy = 0;
        r.gridheight = 3;
        r.gridwidth = 3;


        window.add(a,r);
        r.ipady = 785;
        r.ipadx = 260;
        r.gridx = 3;
        r.gridy = 0;
        r.gridheight = 4;
        r.gridwidth = 1;


        window.add(b,r);
        r.ipady = 185;
        r.ipadx = 1000;
        r.gridx = 0;
        r.gridy = 3;
        r.gridheight = 1;
        r.gridwidth = 3;

        window.add(c,r);
//        window.pack();


        window.setVisible(true);

    }
}
