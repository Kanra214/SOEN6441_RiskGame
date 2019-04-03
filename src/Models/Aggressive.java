package Models;

import java.util.ArrayList;



public class Aggressive implements Strategy {


  @Override
  public void execute(Player player){

    
    try {
      this.reinforce(player);
    } catch (OutOfArmyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    try {
      this.attack(player);
    } catch (AttackingCountryOwner | AttackedCountryOwner | WrongDiceNumber
        | AttackCountryArmyMoreThanOne | TargetCountryNotAdjacent e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

  public void reinforce(Player player) throws OutOfArmyException  {
    
    System.out.println("inside aggerssive reinforce");
    ArrayList<Country> tt= player.getRealms();
    //Country chosen=new Country(player.getId(), player.getId(), null, null);
    Country chosen=null;
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
          player.attack(chosen, target, 1, 1);
          //TODO need to decide he number of dice
        }
        
      }
      //finish attack
      return true;
    }
    
    //no enemy
    return false;
                   
  }
}


  

  
  /*
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
          
=======
    //private int id;
    @Override
    public void execute(Phases p) throws OutOfArmyException {
        Player player = p.getCurrent_player();
        System.out.println("inside aggerssive reinforce");
        ArrayList<Country> tt= player.getRealms();
        Country chosen=new Country(player.getId(), player.getId(), null, null);
>>>>>>> branch 'master' of https://github.com/Kanra214/SOEN6441_RiskGame.git

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
        p.nextPhase();



    }

<<<<<<< HEAD
    
=======


    @Override
>>>>>>> branch 'master' of https://github.com/Kanra214/SOEN6441_RiskGame.git
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
<<<<<<< HEAD
      }
      
      //first check if there is enemy country in neighbourhood 
      ArrayList<Country>neighbours =chosen.getNeighbours();
      
      for(Country t: neighbours) {
        if(t.getOwner()!=player) {
          
          target=t;
          
          //attack until this country has 1 army or the target has been conquered
          while(chosen.getArmy()>0||t.getOwner()==player) {
            player.attack(chosen, target, 1, 1);
            //TODO need to decide he number of dice
          }
          
=======

        //first check if there is enemy country in neighbourhood
        ArrayList<Country>neighbours =chosen.getNeighbours();

        for(Country t: neighbours) {
            if(t.getOwner()!=player) {

                target=t;

                //attack until this country has 1 army or the target has been conquered
                while(chosen.getArmy()>0||t.getOwner()==player) {
                    player.attack(chosen, target, 1, 1);
                    //TODO need to decide he number of dice
                }

            }
            //finish attack
            return true;
>>>>>>> branch 'master' of https://github.com/Kanra214/SOEN6441_RiskGame.git
        }

        //no enemy
        return false;

    }

<<<<<<< HEAD

*/
      
      
      
    
=======
    @Override
    public void fortificate(Player player) {
        // TODO Auto-generated method stub

    }
>>>>>>> branch 'master' of https://github.com/Kanra214/SOEN6441_RiskGame.git


<<<<<<< HEAD
    
    

=======


}
>>>>>>> branch 'master' of https://github.com/Kanra214/SOEN6441_RiskGame.git
