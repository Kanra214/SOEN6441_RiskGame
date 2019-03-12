package Models;

public class RiskGameException extends Exception {
    public int type;
    public RiskGameException(int type){
        this.type = type;
    }
}
//0
class OutOfArmyException extends RiskGameException{


    public OutOfArmyException(int type) {
        super(type);
    }
}
//1
class NoSuchPathException extends RiskGameException{

    public NoSuchPathException(int type) {
        super(type);
    }
}
//2


class CountryNotInRealms extends RiskGameException{

    public CountryNotInRealms(int type) {
        super(type);
    }
}

//3
class SourceIsTargetException extends RiskGameException{

    public SourceIsTargetException(int type) {
        super(type);
    }
}
//4
class MoveAtLeastOneArmyException extends RiskGameException{

    public MoveAtLeastOneArmyException(int type) {
        super(type);
    }
}
