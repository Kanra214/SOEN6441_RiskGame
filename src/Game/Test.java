package Game;
import javax.swing.*;

public class Test {
    public static void main(String args[]){
        JFrame window = new JFrame("test");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setSize(600,800);
        window.setResizable(false);

        JButton b = new JButton("button");
        JPanel a = new JPanel();
        a.setLayout(null);
//        window.setLayout(null);
        b.setBounds(100,100,100,100);
        a.add(b);
        window.add(a);
        window.setVisible(true);

    }
}
