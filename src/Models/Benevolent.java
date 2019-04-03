package Models;

import java.util.Collections;
import java.util.Comparator;

public class Benevolent implements Strategy {
    @Override
    public void execute(Phases p) {
        Player player = p.getCurrent_player();

            if (p.getCurrentPhase() == 0) {
                //TODO: phase 0
            } else {

                //must be in phase 1
                exchangeCards(p);
                Comparator cp = new StrongestCountryComparator();

                while (player.isArmyLeft()) {
                    Collections.sort(player.getRealms(), cp);
                    Country weakest = player.getRealms().get(player.getRealms().size() - 1);

                    try {
                        player.reinforce(weakest);
                    } catch (OutOfArmyException e) {
                        System.out.println("benevolent out of army");
                    }
                }
                p.nextPhase();
                //no attack
                //this player might not be able to attack, next phase automatically, to avoid next phase twice, check AttackingIsPossible
                p.checkAttackingIsPossible();
                if (p.getAttackingIsPossible()) {
                    p.nextPhase();
                }
                //phase 3
                int ith = 0;
                Collections.sort(player.getRealms(), cp);
                Country weakest = player.getRealms().get(player.getRealms().size() - 1);
                while(true) {
                    try {
                        Country strongest = player.getRealms().get(ith);
                        int armiesToMove = (strongest.getArmy() - weakest.getArmy()) / 2;
                        player.fortify(strongest, weakest, armiesToMove);
                        break;
                    } catch (CountryNotInRealms countryNotInRealms) {
                        System.out.println("benevolent not in reamls");
                    } catch (OutOfArmyException e) {
                        System.out.println("benevolent fortify out of army");
                    } catch (NoSuchPathException e) {
                        ith++;
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

    public void exchangeCards(Phases p){
        Card cards = p.getCurrent_player().getCards();
        for(int i = 0; i < 3; i++){
            if(cards.cardBigger3(i)){
                p.getCurrent_player().addPlayerArmyBySameCards(i);
                return;
            }
        }
        if(cards.checkThreeDiffCards()){
            p.getCurrent_player().addPlayerArmyByDiffCards();
        }

    }
    class StrongestCountryComparator implements Comparator<Country> {
        @Override
        public int compare(Country a, Country b) {
            return a.getArmy() - b.getArmy();
        }
    }







    }





}
