package Models;

/**
 * Customized exceptions for this game
 */
public class RiskGameException extends Exception {
    public int type;

    /**
     * Constructor
     * @param type the integer representation of exceptions
     */
    public RiskGameException(int type){
        this.type = type;
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
    public OutOfArmyException(int type) {
        super(type);
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
