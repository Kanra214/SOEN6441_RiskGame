package Models;

import java.util.ArrayList;
import java.util.Comparator;

import Models.Benevolent.WeakestCountryComparator;



public class Aggressive implements Strategy {


    //private int id;

    
    @Override
    public void execute(Phases p){
        
        Player player = p.getCurrent_player();
        player.setNumOfDice(1);
        Country firstCountry = player.getRealms().get(0);
        System.out.println("the current turn is"+p.getCurrentTurn());
        if (p.getCurrentPhase()== 0) {
        //Phase 0
        System.out.println("player"+player.getId()+"  inside aggerssive start up");
        System.out.println("the current phase is"+p.getCurrentPhase());
        while(player.isArmyLeft()) {
            
            try {
                player.reinforce( firstCountry );
               // System.out.println("the current turn is"+p.getCurrentTurn());
            } catch (OutOfArmyException e) {
                
                e.printStackTrace();
            }
            
        }
        p.nextPhase();
        }else {
          if (p.checkWinner()) {//this attacker conquered all the countries
            p.gameOver = true;

        }
          
        //Phase 1
          System.out.println("player"+player.getId()+"  inside aggerssive reinforce");
          System.out.println("the current phase is"+p.getCurrentPhase());
          exchangeCards(p);

          p.phaseOneFirstStep();
          
          ArrayList<Country> ownedCountries= player.getRealms();
          
          Comparator cp = new WeakestCountryComparator();
          int ith = player.getRealms().size()-1;
          Country chosen = player.getRealms().get(ith);

          while(player.getUnassigned_armies()>0) {
         
              try {
                  player.reinforce(chosen);
              } catch (OutOfArmyException e) {
                  e.printStackTrace();
              }


          }
          p.nextPhase();
          
          //Phase 2
          try {
              attack(player,ownedCountries);
          } catch (AttackingCountryOwner | AttackedCountryOwner | WrongDiceNumber | AttackCountryArmyMoreThanOne
                  | TargetCountryNotAdjacent e) {
              
              e.printStackTrace();
          }
          
          player.setNumOfDice(1);
          p.nextPhase();
          
                        
          //Phase 3
          System.out.println("player"+player.getId()+"  inside aggerssive fortify");
          Country secondStrong;
          //Comparator cp = new WeakestCountryComparator();
          //int ith = player.getRealms().size()-1;
           ith = player.getRealms().size()-1;
          if(ith-1==0||ith-1==-1) {
           p.nextPhase();
          }else {
            System.out.println(player.getRealms().size()+"  Realmssize");
            System.out.println(ith-1+"  Index");
           secondStrong = player.getRealms().get(ith-1);
           int armiesToMove = secondStrong.getArmy() - 1;
           
           if(armiesToMove==0) {
             p.nextPhase();
           }else {
             try {
               player.fortify(secondStrong, chosen, armiesToMove);
           } catch (CountryNotInRealms | OutOfArmyException | NoSuchPathException | SourceIsTargetException
                   | MoveAtLeastOneArmyException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }

           p.nextPhase();
           }
           
          
          
        }
        
        

        
        }
        

    }
    @Override
    public void defend(Player pl){
        pl.setNumOfDice(1);//TODO
    }




    private void attack(Player player,ArrayList<Country> allCountries) throws AttackingCountryOwner, AttackedCountryOwner, WrongDiceNumber, AttackCountryArmyMoreThanOne, TargetCountryNotAdjacent {

        Country chosen=new Country(player.getId(), player.getId(), null, null);
        Country target=new Country(player.getId(), player.getId(), null, null);
        System.out.println("inside aggressive attack");
        //ArrayList<Country> allCountries= player.getRealms();
        int max=0;
      
        for(Country t: allCountries) {
            if(t.getArmy()>max) {
                chosen = t;
                max=t.getArmy();
            }
        }

        //first check if there is enemy country in neighbourhood
        ArrayList<Country>neighbours =chosen.getNeighbours();
        boolean neighbourEnemy=false;
        for(Country t: neighbours) {
            if(t.getOwner()!=player) {
              neighbourEnemy=false;
                target=t;

                //attack until this country has 1 army or the target has been conquered
                while(chosen.getArmy()>0||t.getOwner()==player) {
                  
                  System.out.println(chosen.getOwner().getId()+"before attack"+target.getOwner().getId());
                   
                  player.attack(chosen, target);
                  // player.attack(chosen, target, Math.min(3, chosen.getArmy()), Math.min(2, target.getArmy()));
                    if(t.getOwner()==player) {
                      try {
                        player.fortify(chosen, target, chosen.getArmy()-1);
                        
                        this.attack(player, allCountries);
                        
                      } catch (CountryNotInRealms | OutOfArmyException | NoSuchPathException
                          | SourceIsTargetException | MoveAtLeastOneArmyException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                      }
                    }
                    System.out.println(chosen.getOwner()+"after attack"+target.getOwner());
                    //TODO need to decide he number of dice
                }

            }
            //finish attack
            //return true;
        }
        System.out.println("Neighbout enemy?"+neighbourEnemy);
        //no enemy
        //return false;

    }


    
    


    public void exchangeCards(Phases p){
        Card cards = p.getCurrent_player().getCards();
        for(int i = 0; i < 3; i++){
            if(cards.cardBigger3(i)){
                p.getCurrent_player().addPlayerArmyBySameCards(i);
                System.out.println("changed 3 same cards: " + cards.showCardsName(i));
                return;
            }
        }
        if(cards.checkThreeDiffCards()){
            p.getCurrent_player().addPlayerArmyByDiffCards();
            System.out.println("changed 3 diff cards");

        }


    }
    
    
    class WeakestCountryComparator implements Comparator<Country> {
        @Override
        public int compare(Country a, Country b) {
            return a.getArmy() - b.getArmy();
        }
    }


    @Override
    public void defend(Player pl) {
      // TODO Auto-generated method stub
      pl.setNumOfDice(1);
    }




    @Override
    public String getName() {
      return this.getClass().getName().substring(this.getClass().getName().indexOf(".") + 1);
     
    }

}
