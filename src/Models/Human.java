package Models;

import javax.swing.*;
import java.io.Serializable;

public class Human implements Strategy {
    @Override
    public void execute(Phases p) {

    }

    @Override
    public void defend(Player pl) {
        pl.setNumOfDice(1);//TODO

    }

    @Override
    public String getName() {
        return this.getClass().getName().substring(this.getClass().getName().indexOf(".") + 1);
    }
}
