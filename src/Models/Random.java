package Models;

import java.util.ArrayList;

public class Random implements Strategy {
    @Override
    public void execute(Phases p)
        throws OutOfArmyException, TargetCountryNotAdjacent, AttackCountryArmyMoreThanOne, AttackingCountryOwner, WrongDiceNumber, AttackedCountryOwner {
        Player player = p.getCurrent_player();
        ArrayList<Country> realms = player.getRealms();
        if(p.getCurrentPhase() == 0){
            assignArmyTocountry(player,realms);
            p.nextPhase();
        }
        else{
            //must be in phase 1
            exchangeCards(p);
            p.phaseOneFirstStep();
            assignArmyTocountry(player,realms);
            p.nextPhase();

            //phase 2
            int num = getRandom(1,6);// number of times for attack
            while(num > 0){
                p.checkAttackingIsPossible();
                if(p.getAttackingIsPossible()){
                    Country sourceCountry = null;
                    Country targetCountry = null;
                    ArrayList<Country> neighbours = new ArrayList<Country>();
                    while(true) {
                        sourceCountry = realms.get(getRandom(0,realms.size()));
                        for (Country neighbour : sourceCountry.getNeighbours()) {
                            if (neighbour.getOwner() != sourceCountry.getOwner()){
                                neighbours.add(neighbour);
                            }
                        }
                        if(neighbours.size() != 0) {
                            targetCountry = neighbours.get(getRandom(0,neighbours.size()));
                            break;
                        }
                        else { continue;}
                    }
                    try {
                        int Allout = getRandom(0,1);
                        if (Allout == 1) {
                            p.attackPhase(sourceCountry,targetCountry);
                        }
                        else {
                            int numOfattack = getRandom(2,4);
                            p.attackPhase(sourceCountry,targetCountry,numOfattack,numOfattack - 1);
                        }

                    }catch(TargetCountryNotAdjacent e) {
                        System.out.println("Random target country is not adjacent to the attacking country.");
                    }catch(AttackCountryArmyMoreThanOne e) {
                        System.out.println("Random attacking country must have at least two armies.");
                        continue;
                    }catch(WrongDiceNumber e) {
                        System.out.println("Random wrong dice number.");
                        continue;
                    }
                    num--;

                }
                else{
                    break;
                }
            }
            p.nextPhase();

            //phase 3
            boolean flagFindpath = false;
            while(flagFindpath == false){
                Country sourceCountry = realms.get(getRandom(0,realms.size()));
                Country targetCountry = realms.get(getRandom(0,realms.size()));
                int assignArmy = getRandom(1,sourceCountry.getArmy() + 1);
                try{
                    flagFindpath = player.findPath(sourceCountry,targetCountry);
                    player.fortify(sourceCountry,targetCountry,assignArmy);
                } catch (SourceIsTargetException e) {
                    System.out.println("Random source can not be target");
                    continue;
                } catch (CountryNotInRealms countryNotInRealms) {
                    System.out.println("Random not in realms");
                } catch (MoveAtLeastOneArmyException e) {
                    System.out.println("Random move at least 1 army");
                } catch (NoSuchPathException e) {
                    continue;
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
     * get a random number from [startNum, endNum), don't include endNum
     * @param startNum
     * @param endNum
     * @return
     */
    public int getRandom(int startNum, int endNum ) {
        int result;
        result = startNum + (int)(Math.random() * (endNum - startNum));
        return result;
    }

    public void assignArmyTocountry(Player player, ArrayList<Country> realms){
        while (player.isArmyLeft()){
                Country tempCountry = realms.get(getRandom(0,realms.size()));
                int assignedArmy = getRandom(0,player.getUnassigned_armies() + 1);
                while( assignedArmy > 0){
                    try{
                        player.reinforce(tempCountry);
                    }
                    catch (OutOfArmyException e) {
                        System.out.println("Random out of army");
                    }
                }
            }

    }

}