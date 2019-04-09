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
    public void defend(Country c) {
        int maxDice = Math.min(c.getArmy(),2);
        int defendDice;
        while(true) {
            String input = JOptionPane.showInputDialog("How many dice for defender to roll? put integer in range [1, " + maxDice + "].");
            try {
                defendDice = Integer.parseInt(input);
                if(defendDice <= maxDice && defendDice >= 1){
                    break;
                }
                else{
                    JOptionPane.showMessageDialog(null,"Wrong number of dice, try again please.");
                    continue;

                }
            }
            catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Wrong number of dice, try again please.");
                continue;
            }
        }
        c.getOwner().setNumOfDice(defendDice);//TODO:how to loop popping out until the defend dice is correct

    }

    @Override
    public String getName() {
        return this.getClass().getName().substring(this.getClass().getName().indexOf(".") + 1);
    }
}
