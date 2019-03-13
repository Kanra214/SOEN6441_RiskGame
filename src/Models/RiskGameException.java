package Models;

/**
 * Customized exceptions for this game
 */
public class RiskGameException extends Exception {
    public String errMsg;

    /**
     * Constructor
     * @param type the integer representation of exceptions
     */
    public RiskGameException(){

    }
}
//0

/**
 * Exception thrown when player trying to deploy army when no unassigned armies left
 */
class OutOfArmyException extends RiskGameException{

    /**
     * Constructor
     * @param type 0 for this type
     */
    public OutOfArmyException() {
        super("Out of army.");
    }
}
//1
/**
 * Exception thrown when no valid path between two chosen countries in fortificate phase
 */
class NoSuchPathException extends RiskGameException{

    /**
     * Constructor
     * @param type 1 for this type
     */
    public NoSuchPathException(int type) {
        super(type);
    }
}
//2


class CountryNotInRealms extends RiskGameException{
    /**
     * Constructor
     * @param type 2 for this type
     */
    public CountryNotInRealms(int type) {
        super(type);
    }
}

//3
/**
 * Exception thrown when source country and target country are the same in fortificate phase
 */
class SourceIsTargetException extends RiskGameException{
    /**
     * Constructor
     * @param type 3 for this type
     */
    public SourceIsTargetException(int type) {
        super(type);
    }
}
//4

/**
 * Exception thrown when player trying to move less than 1 army
 */
class MoveAtLeastOneArmyException extends RiskGameException{
    /**
     * Constuctor
     * @param type 4 for this type
     */
    public MoveAtLeastOneArmyException(int type) {
        super(type);
    }
}

//5

/**
 * Exception thrown when attack phase, army at least two in attacking country
 */
class AttackCountryArmyMoreThanOne extends RiskGameException{
    /**
     * Constuctor
     * @param type 5 for this type
     */
    public AttackCountryArmyMoreThanOne(int type) {
        super(type);
    }
}

//6

/**
 * Exception thrown when attack phase, owner must current player
 */
class AttackingCountryOwner extends RiskGameException{
    /**
     * Constuctor
     * @param type 6 for this type
     */
    public AttackingCountryOwner(int type) {
        super(type);
    }
}

//7

/**
 * Exception thrown when attack phase, owner must current player
 */
class AttackedCountryOwner extends RiskGameException{
    /**
     * Constuctor
     * @param type 7 for this type
     */
    public AttackedCountryOwner(int type) {
        super(type);
    }
}

//8

/**
 * Exception thrown when attack phase, owner must current player
 */
class AttackOutOfArmy extends RiskGameException{
    /**
     * Constuctor
     * @param type 8 for this type
     */
    public AttackOutOfArmy(int type) {
        super(type);
    }
}

//9

/**
 * Exception thrown when attack phase, owner must current player
 */
class AttackMoveAtLeastOneArmy extends RiskGameException{
    /**
     * Constuctor
     * @param type 9 for this type
     */
    public AttackMoveAtLeastOneArmy(int type) {
        super(type);
    }
}
//10

/**
 * Exception thrown when attack phase, owner must current player
 */
class WrongDiceNumber extends RiskGameException{
    protected Player player;
    /**
     * Constuctor
     * @param type 9 for this type
     */
    public WrongDiceNumber(int type, Player player) {
        super(type);
        this.player = player;

    }
}
