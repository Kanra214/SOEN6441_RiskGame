package Models;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * <h1>Cheater</h1>
 * This class that controls the behavior of the Cheater Player
 */


public class Cheater implements Strategy, Serializable {

    @Override
    public void execute(Phases p) {

        Player player = p.getCurrent_player();
        ArrayList<Country> realms = player.getRealms();

        if (p.getCurrentPhase() == 0) {
            System.out.println("Cheater phase 0");
            //TODO: phase 0
            int countryCount = 0;
            int countryTurn = 0;
            while (player.isArmyLeft()) {
                Country currentCountry = realms.get(countryTurn);
                try {
                    player.reinforce(currentCountry);
                    System.out.println("Cheater: reinforce" + currentCountry.getName());
                } catch (OutOfArmyException e) {
                    System.out.println("cheater phase 0 out of army");
                }
                countryCount++;
                countryTurn = countryCount % realms.size();
            }
            p.nextPhase();
        } else {
//            if (p.checkWinner()) {//this attacker conquered all the countries
//                p.gameOver = true;
//
//            }
            //must be in phase 1
            System.out.println("Inside cheater phase1");
            if(!p.cardExchanged) {
                exchangeCards(p);
            }
            p.phaseOneFirstStep();
            System.out.println("cheater phase one first step, unassigned army = " + player.getUnassigned_armies());
            player.setUnassigned_armies(0);
            for (int i = 0; i < realms.size(); i++) {
                Country tempCountry = realms.get(i);
                player.setUnassigned_armies(tempCountry.getArmy());
                System.out.println(tempCountry.getName() + " unassigned army = " + player.getUnassigned_armies());
                while (player.isArmyLeft()) {
                    try {
                        player.reinforce(tempCountry);
                    } catch (OutOfArmyException e) {
                        System.out.println("cheater" + tempCountry.getName() + "out of army");
                    }
                }
            }
            p.nextPhase();

            //phase2
            //this player might not be able to attack, next phase automatically, to avoid next phase twice, check AttackingIsPossible
            System.out.println("Inside phase 2");
            p.checkAttackingIsPossible();
            if (p.getAttackingIsPossible()) {
                ArrayList<Country> targetcountries = new ArrayList<>();
                for (Country tempCountry : realms) {
                    for (Country neighbour : tempCountry.getNeighbours()) {
                        if (tempCountry.getOwner() != neighbour.getOwner()) {
                          int attackedArmy = neighbour.getArmy();
                          System.out.println("Inside change army in view");
                          if(!targetcountries.contains(neighbour)){
                            while(attackedArmy > 0) {
                              player.incrementMapArmies();
                              neighbour.getOwner().decrementMapArmies();
                              attackedArmy--;
                            }
                            targetcountries.add(neighbour);

                          }
                          System.out.println(player.getMapArmies() +"  "+ neighbour.getOwner().getMapArmies());


                        }
                    }
                }
                for (Country neighbour : targetcountries) {
                    neighbour.swapOwnership(neighbour.getOwner(), player);

                    if(neighbour.getCont().getOwner() == p.getRival()){
                        neighbour.getCont().free();
                    }


                    if(p.checkWinner()){
                        if(p.tournament){
                            if (p.winner != "Draw") {
                                p.winner = "Cheater";
                                System.out.println("Player " + p.getCurrent_player().getId() + " wins the game!");
                            }
                            return;
                        }


                    }

                    p.at_least_once = true;
                    p.checkContinentOwner(neighbour.getCont(),player);//check if this player gets control of the continent
                    if(neighbour.getOwner().getRealms().size() == 0){
                        player.receiveEnemyCards(neighbour.getOwner());
                    }


                }


            }
            p.nextPhase();

            System.out.println("Inside cheater phase3");
            ArrayList<Country> boaderCountries = new ArrayList<>();
            for (Country tempCountry : realms) {
                for (Country neighbour : tempCountry.getNeighbours()) {
                    if (tempCountry.getOwner() != neighbour.getOwner()) {
                        boaderCountries.add(tempCountry);
                    }
                }
            }
            for (int i = 0; i < boaderCountries.size(); i++) {
                Country tempCountry = boaderCountries.get(i);
                player.setUnassigned_armies(tempCountry.getArmy());
                System.out.println("cheater phase3 " + tempCountry.getName() + " unassigned army = " + player.getUnassigned_armies());
                while (player.isArmyLeft()) {
                    try {
                        player.reinforce(tempCountry);
                    } catch (OutOfArmyException e) {
                        System.out.println("cheater phase3" + tempCountry.getName() + "out of army");
                    }
                }
            }
            p.nextPhase();
        }
    }



    /**
     * Set the number of dice when defending
     * @param beingAttacked the attacked country
     * 
     * 
     */

    @Override
    public void defend(Country c) {
        c.getOwner().setNumOfDice(1);//TODO
    }

    /**
     * get the name of this strategy
     * @return the name of this strategy
     * 
     */
    
    @Override
    public String getName() {
        return this.getClass().getName().substring(this.getClass().getName().indexOf(".") + 1);
    }

    /**
     * Execute the card exchange
     * @param p the phase
     * 
     */

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
}
