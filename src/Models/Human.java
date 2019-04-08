package Models;

import javax.swing.*;
import java.io.Serializable;

public class Human implements Strategy,Serializable {
    @Override
    public void execute(Phases p) {
        if((p.getCurrentPhase() == 1) && (p.getCurrentTurn() < p.getNumOfPlayers()*2)){
            p.phaseOneFirstStep();
        }

    }

    @Override
    public void defend(Player pl) {
        int defendDice;
        while(true) {
            String input = JOptionPane.showInputDialog("How many dice for defender to roll? ");
            try {
                defendDice = Integer.parseInt(input);
                break;
            }
            catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Wrong number of dice, try again please.");
                continue;
            }
        }
        pl.setNumOfDice(defendDice);//TODO:how to loop popping out until the defend dice is correct

    }

    @Override
    public String getName() {
        return this.getClass().getName().substring(this.getClass().getName().indexOf(".") + 1);
    }
}
