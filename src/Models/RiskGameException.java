package Models;

import java.io.Serializable;

/**
 * Customized exceptions for this game
 */
public class RiskGameException extends Exception implements Serializable {
    public String errMsg;

    /**
     * Constructor
     */
    public RiskGameException(){

    }
}

/**
 * Exception thrown when player trying to deploy army when no unassigned armies left
 */
class OutOfArmyException extends RiskGameException{

    /**
     * Constructor
     */
    public OutOfArmyException() {

        errMsg = "Out of army.";


    }
}

/**
 * Exception thrown when no valid path between two chosen countries in fortificate phase
 */
class NoSuchPathException extends RiskGameException{

    /**
     * Constructor
     */

    public NoSuchPathException(){

        errMsg = "No such path.";
    }
}


class CountryNotInRealms extends RiskGameException{
    /**
     * Constructor
     */
    public CountryNotInRealms() {
        errMsg = "Not in realms.";

    }
}

/**
 * Exception thrown when source country and target country are the same in fortificate phase
 */
class SourceIsTargetException extends RiskGameException{

    /**
     * Constructor
     */
    public SourceIsTargetException() {
        errMsg = "Souce cannot be the target.";
    }
}


/**
 * Exception thrown when player trying to move less than 1 army
 */
class MoveAtLeastOneArmyException extends RiskGameException{
    /**
     * Constuctor
     */
    public MoveAtLeastOneArmyException() {

        errMsg = "At lease move one army.";

    }
}

/**
 * Exception thrown when attack phase, army at least two in attacking country
 */
class AttackCountryArmyMoreThanOne extends RiskGameException{

    /**
     * Constuctor
     */
    public AttackCountryArmyMoreThanOne() {
        errMsg = "Attacking country must have at least two armies.";


    }
}


/**
 * Exception thrown when attack phase, owner must current player
 */
class AttackingCountryOwner extends RiskGameException{
    /**
     * Constuctor
     */
    public AttackingCountryOwner() {
        errMsg = "Cannot attack your own country.";

    }
}



/**
 * Exception thrown when attack phase, owner must current player
 */
class AttackedCountryOwner extends RiskGameException{

    /**
     * Constuctor
     */
    public AttackedCountryOwner() {
        errMsg = "Invalid target, the target country must be adjancent to attacking country, and not your own country.";

    }
}



/**
 * Exception thrown when attack phase, owner must current player
 */
class WrongDiceNumber extends RiskGameException{
    protected Player player;

    /**
     * Constuctor
     * @param player Player who entered the wrong dice number
     */
    public WrongDiceNumber(Player player) {

        this.player = player;
        errMsg = "Player " + player.getId() + " entered wrong dice number, type proper integer according to the instruction in the message box.";

    }
}
/**
 * Exception thrown when attack phase, owner must current player
 */
class TargetCountryNotAdjacent extends RiskGameException{
    /**
     * Constuctor
     */
    public TargetCountryNotAdjacent() {


        errMsg = "Target country is not adjacent to the attacking country.";

    }
}
class MustBeEqualOrMoreThanNumOfDice extends RiskGameException{
    /**
     * Constuctor
     */

    public MustBeEqualOrMoreThanNumOfDice(){


        errMsg = "Number of armies must be equal or more than the number of dice used that resulted in conquering the country.";

    }
}

