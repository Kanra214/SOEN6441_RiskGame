package Models;

import javax.swing.*;
import java.io.Serializable;

public class Human implements Strategy {
    @Override
    public void execute(Phases p) {

    }

    @Override
    public void defend(Player pl) {
        String input = JOptionPane.showInputDialog("How many dice for defender to roll? ");
        int defendDice = Integer.parseInt(input);
        pl.setNumOfDice(defendDice);//TODO:how to loop popping out until the defend dice is correct

    }

    @Override
    public String getName() {
        return this.getClass().getName().substring(this.getClass().getName().indexOf(".") + 1);
    }
}
