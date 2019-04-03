package Models;



public class Random implements Strategy {


    @Override
    public void reinforce(Player player) throws OutOfArmyException {
      // TODO Auto-generated method stub
      
    }

    @Override
    public boolean attack(Player player) throws AttackingCountryOwner, AttackedCountryOwner,
        WrongDiceNumber, AttackCountryArmyMoreThanOne, TargetCountryNotAdjacent {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public void fortificate(Player player) {
      // TODO Auto-generated method stub
      
    }
}
