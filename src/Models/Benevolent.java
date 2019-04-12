package Models;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;


/**
 * <h1>Benevolent</h1>
 * This class that controls the behavior of the Benevolent Player
 */


public class Benevolent implements Strategy,Serializable {

    @Override
    public void execute(Phases p) {

        Player player = p.getCurrent_player();



            if (p.getCurrentPhase() == 0) {
                System.out.println("benevolent phase 0");
                //TODO: phase 0
                int countryCount = 0;
                int countryTurn = 0;
                while (player.isArmyLeft()) {
                    Country currentCountry = player.getRealms().get(countryTurn);
                    try {
                        player.reinforce(currentCountry);
                        System.out.println("Benevolent: reinforce" + currentCountry.getName());
                    } catch (OutOfArmyException e) {
                        System.out.println("benevolent phase 0 out of army");
                    }
                    countryCount++;
                    countryTurn = countryCount % player.getRealms().size();
                }
                p.nextPhase();
            } else {

                //must be in phase 1
                if(!p.cardExchanged) {
                    exchangeCards(p);
                }
                System.out.println("benevolent card exchange");
                p.phaseOneFirstStep();
                System.out.println("benevolent phase one first step, unassigned army = " + player.getUnassigned_armies());
                Comparator cp = new WeakestCountryComparator();

                while (player.isArmyLeft()) {
                    Collections.sort(player.getRealms(), cp);
                    Country weakest = player.getRealms().get(0);
                    System.out.println("weakest: " + weakest.getName());

                    try {
                        player.reinforce(weakest);
                    } catch (OutOfArmyException e) {
                        System.out.println("benevolent out of army");
                    }
                }
                p.nextPhase();
                //no attack
                //this player might not be able to attack, next phase automatically, to avoid next phase twice, check AttackingIsPossible
                if (p.checkWinner()) {//this attacker conquered all the countries
                    p.gameOver = true;
                    //p.winner.add("Benevolent");
                    p.winner = "Benevolent";
                }

                if (p.isGameOver()) {
                    if (p.winner != "Draw"){System.out.println("Player " + p.getCurrent_player().getId() + " wins the game!");}
                    return;
                    //System.exit(0);
                }
                p.nextPhase();
                //phase 3
                int ith = player.getRealms().size() - 1;
                Collections.sort(player.getRealms(), cp);
                Country weakest = player.getRealms().get(0);
                while (true) {
                    try {
                        Country strongest = player.getRealms().get(ith);
                        int armiesToMove = (strongest.getArmy() - weakest.getArmy()) / 2;
                        player.fortify(strongest, weakest, armiesToMove);
                        System.out.println("benevolent fortify: " + strongest.getName() + " to " + weakest.getName() + " num: " + armiesToMove);
                        break;
                    } catch (CountryNotInRealms countryNotInRealms) {
                        System.out.println("benevolent not in reamls");
                    } catch (OutOfArmyException e) {
                        System.out.println("benevolent fortify out of army");
                    } catch (NoSuchPathException e) {
                        ith--;
                        continue;
                    } catch (SourceIsTargetException e) {
                        break;
                    } catch (MoveAtLeastOneArmyException e) {
                        break;
                    }
                }
                p.nextPhase();


            }
        }

    
    /**
     * Set the number of dice when defending
     * @param beingAttacked the attacked country
     * 
     */

    @Override
    public void defend(Country c) {
        c.getOwner().setNumOfDice(1);
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
    
    
    /**
     * Implement the Comparator
     * 
     */
    
    class WeakestCountryComparator implements Comparator<Country> {
        @Override
        public int compare(Country a, Country b) {
            return a.getArmy() - b.getArmy();
        }
    }







    }








