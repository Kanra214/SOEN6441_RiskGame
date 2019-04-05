package Models;

import java.util.ArrayList;
import java.util.Comparator;

import Models.Benevolent.WeakestCountryComparator;



public class Aggressive implements Strategy {


    //private int id;

	
    @Override
    public void execute(Phases p){
    	
        Player player = p.getCurrent_player();
        Country firstCountry = player.getRealms().get(0);
        
        if (p.getCurrentPhase() == 0) {
        //Phase 0
        System.out.println("inside aggerssive start up");
        while(player.isArmyLeft()) {
        	
        	try {
				player.reinforce( firstCountry );
			} catch (OutOfArmyException e) {
				
				e.printStackTrace();
			}
        	
        }
        }
        
        //Phase 1
        System.out.println("inside aggerssive reinforce");
        
        exchangeCards(p);

        p.phaseOneFirstStep();
        
        ArrayList<Country> ownedCountries= player.getRealms();
        
        Comparator cp = new WeakestCountryComparator();
        int ith = player.getRealms().size()-1;
        Country chosen = player.getRealms().get(ith);
        /*
        Country chosen=new Country(player.getId(), player.getId(), null, null);

        //If all the countries are the same number,the chosen will be the first country
        int max=0;
        for(Country c: ownedCountries) {
            if(c.getArmy()>max) {
                chosen = c;
                max=c.getArmy();
            }
        }
         */
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
        p.nextPhase();
        
                      
        //Phase 3
        //Comparator cp = new WeakestCountryComparator();
        //int ith = player.getRealms().size()-1;
        Country secondStrong = player.getRealms().get(ith-1);
        
        int armiesToMove = secondStrong.getArmy() - 1;
        try {
			player.fortify(secondStrong, chosen, armiesToMove);
		} catch (CountryNotInRealms | OutOfArmyException | NoSuchPathException | SourceIsTargetException
				| MoveAtLeastOneArmyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        p.nextPhase();
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

        for(Country t: neighbours) {
            if(t.getOwner()!=player) {

                target=t;

                //attack until this country has 1 army or the target has been conquered
                while(chosen.getArmy()>1||t.getOwner()==player) {
                    player.attack(chosen, target, 1, 1);
                    //TODO need to decide he number of dice
                }

            }
            //finish attack
            //return true;
        }

        //no enemy
        //return false;

    }


    
    
    
    public void fortificate(Player player) {
        // TODO Auto-generated method stub

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

}
