package Models;

public class Benevolent implements Strategy {

    @Override
    public void execute(Phases p) throws OutOfArmyException {
        Player player = p.getCurrent_player();
        if(p.getCurrentPhase() == 0){

        }
        else{
            //must be in phase 1
            exchangeCards(p);
            while(player.isArmyLeft()){
                Country weakest = getWeakest(p);
                player.reinforce(weakest);
            }
            p.nextPhase();
            //no attack
            //this player might not be able to attack, next phase automatically, to avoid next phase twice, check AttackingIsPossible
            p.checkAttackingIsPossible();
            if(p.getAttackingIsPossible()){
                p.nextPhase();
            }
            //phase 3

            Country strongest = getStrongest(p);
            Country weakest = getWeakest(p);
            int armiesToMove = (strongest.getArmy() - weakest.getArmy()) / 2;
            player.fortify(strongest, weakest, armiesToMove);








        }







    }

    public Country getWeakest(Phases p){
        Country weakest = p.getCurrent_player().getRealms().get(0);
        int weakestArmy = weakest.getArmy();
        for(Country country : p.getCurrent_player().getRealms()){
            if(country.getArmy() < weakestArmy){
                weakest = country;
                weakestArmy = country.getArmy();

            }
        }
        return weakest;

    }
    public Country getStrongest(Phases p, int ith){
//        Country strongest = p.getCurrent_player().getRealms().get(0);
//        int strongestArmy = strongest.getArmy();
//        for(Country country : p.getCurrent_player().getRealms()){
//            if(country.getArmy() < strongestArmy){
//                strongest = country;
//                strongestArmy = country.getArmy();
//
//            }
//        }
        p.getCurrent_player().getRealms().sort();
        return strongest;

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


}
