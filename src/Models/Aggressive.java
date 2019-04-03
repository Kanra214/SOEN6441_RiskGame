package Models;

import java.util.ArrayList;



public class Aggressive implements Strategy {

    
  //private int id;

    @Override
    public void reinforce(Player player) throws OutOfArmyException {
      
      System.out.println("inside aggerssive reinforce");
      ArrayList<Country> tt= player.getRealms();
      Country chosen=new Country(player.getId(), player.getId(), null, null);
      
      //If all the countries are the same number,the chosen will be the first country
      int max=0;
      for(Country c: tt) {
        if(c.getArmy()>max) {
          chosen = c;
          max=c.getArmy();
        }
      }
      
      while(player.getUnassigned_armies()>0) {
        
   
          //player.deployArmy(chosen);
          player.reinforce(chosen);
          

      }
      
    }

    @Override
    public boolean attack(Player player) throws AttackingCountryOwner, AttackedCountryOwner, WrongDiceNumber, AttackCountryArmyMoreThanOne, TargetCountryNotAdjacent {

      Country chosen=new Country(player.getId(), player.getId(), null, null);
      Country target=new Country(player.getId(), player.getId(), null, null);
      System.out.println("inside aggressive attack");
      ArrayList<Country> allCountries= player.getRealms();
      int max=0;
      for(Country t: allCountries) {
        if(t.getArmy()>max) {
          chosen = t;
          max=t.getArmy();
        }
      }
      
      //first check if there is enemy country in neighbourhood 
      ArrayList<Country>neighbours =chosen.getNeighbours();
      
      for(Country t: neighbours) {
        if(t.getOwner()!=player) {
          
          target=t;
          
          //attack until this country has 1 army or the target has been conquered
          while(chosen.getArmy()>0||t.getOwner()==player) {
            player.attackPhase(chosen, target, 1, 1);
            //TODO need to decide he number of dice
          }
          
        }
        //finish attack
        return true;
      }
      
      //no enemy
      return false;
                     
    }

    @Override
    public void fortificate(Player player) {
      // TODO Auto-generated method stub
      
    }


    
    
}
