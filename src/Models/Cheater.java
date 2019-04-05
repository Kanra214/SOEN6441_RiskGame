package Models;


import java.util.ArrayList;

public class Cheater implements Strategy {

    @Override
    public void execute(Phases p) {

        Player player = p.getCurrent_player();
        ArrayList<Country> realms = player.getRealms();
        player.setNumOfDice(1);


        if (p.getCurrentPhase() == 0) {
            System.out.println("Cheater phase 0");
            //TODO: phase 0
            int countryCount = 0;
            int countryTurn = 0;
            while(player.isArmyLeft()){
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

            //must be in phase 1
            exchangeCards(p);
            while (player.isArmyLeft()) {
                for (Country tempCountry : realms) {
                    int armyNum = tempCountry.getArmy() * 2;
                    while (armyNum > 0) {
                        try {
                            player.reinforce(tempCountry);
                        } catch (OutOfArmyException e) {
                            System.out.println("cheater phase 1 out of army");
                            break;
                        }
                    }
                }
            }
            p.nextPhase();

            //phase2
            //this player might not be able to attack, next phase automatically, to avoid next phase twice, check AttackingIsPossible
            p.checkAttackingIsPossible();
            if (p.getAttackingIsPossible()) {
                p.nextPhase();
            }
            //phase 3
            ArrayList<Country> sourceCountries = new ArrayList<>();
            ArrayList<Country> supportCountries = new ArrayList<>();
            for (Country tempCountry : realms) {
                for (Country neighbour : tempCountry.getNeighbours()) {
                    if (tempCountry.getOwner() != neighbour.getOwner()) {
                        sourceCountries.add(tempCountry);
                    }
                }
            }
            for (Country tempCountry : realms) {
                if (!sourceCountries.contains(tempCountry)) {
                    supportCountries.add(tempCountry);
                }
            }
            try {
                if (supportCountries != null) {
                    for (Country tempCountry : sourceCountries) {
                        Country supportCountry = supportCountries.get(0);
                        int moveArmy = tempCountry.getArmy() * 2;
                        if (moveArmy < supportCountry.getArmy()) {
                            player.fortify(supportCountry, tempCountry, moveArmy);
                        }
                    }

                }//
            } catch (CountryNotInRealms countryNotInRealms) {
                countryNotInRealms.printStackTrace();
            } catch (NoSuchPathException e) {
                e.printStackTrace();
            } catch (SourceIsTargetException e) {
                e.printStackTrace();
            } catch (MoveAtLeastOneArmyException e) {
                e.printStackTrace();
            } catch (OutOfArmyException e) {
                e.printStackTrace();
            }
            p.nextPhase();
        }




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
}
