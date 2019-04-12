package Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <h1>Random</h1>
 * This class that controls the behavior of the Random Player
 */


public class Random implements Strategy, Serializable {
    @Override
    public void execute(Phases p){
        Player player = p.getCurrent_player();
        ArrayList<Country> realms = player.getRealms();


          if(p.getCurrentPhase() == 0){
              System.out.println("Random phase 0");
            assignArmyTocountry(player,realms);
            p.nextPhase();
          }
          else{

            //phase 1
              System.out.println("Random phase 1");
              if(!p.cardExchanged) {
                  exchangeCards(p);
              }
            p.phaseOneFirstStep();
            assignArmyTocountry(player,realms);
            p.nextPhase();

            //phase 2
              System.out.println("Random phase 2");
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
                  int Allout = getRandom(0,2);
                  if (Allout == 1) {
                      System.out.println("Random: allout");
                    boolean conquer = player.attack(sourceCountry,targetCountry);
                    if (conquer == true) {
                        System.out.println("Random: attack true");
                        checkWinner(p);
                      int assignAfterConquer = assignAfterConquer(sourceCountry,player);
                      System.out.println("Random: assign after couquer: " + assignAfterConquer);

                        player.deploymentAfterConquer(sourceCountry,targetCountry,assignAfterConquer);

                        if (p.checkWinner()) {//this attacker conquered all the countries
                            p.gameOver = true;

                            //p.winner.add("Random");
                            p.winner = "Random";

                        }
                    }
                  }
                  else {
                      System.out.println("Random: not allout");
                    int numOfattack = getRandom(1,4);
                    boolean conquer = player.attack(sourceCountry,targetCountry,numOfattack);
                    if (conquer == true) {
                      checkWinner(p);
                      int assignAfterConquer = assignAfterConquer(sourceCountry,player);
                      player.deploymentAfterConquer(sourceCountry,targetCountry,assignAfterConquer);
                      System.out.println("Random: assign after couquer: " + assignAfterConquer);
                      if (p.checkWinner()) {//this attacker conquered all the countries
                        p.gameOver = true;

                        //p.winner.add("Random");
                        p.winner = "Random";

                      }

                    }
                  }

                }catch(TargetCountryNotAdjacent e) {
                  System.out.println("Random target country is not adjacent to the attacking country.");
                }catch(AttackCountryArmyMoreThanOne e) {
                  System.out.println("Random attacking country must have at least two armies.");
                  continue;
                }catch(WrongDiceNumber e) {
                  System.out.println("Random wrong dice number.");
                  continue;
                } catch (AttackingCountryOwner attackingCountryOwner) {
                  System.out.println("Random attackingCountryOwner error");
                } catch (AttackedCountryOwner attackedCountryOwner) {
                  System.out.println("Random attackedCountryOwner error");
                } catch (SourceIsTargetException e) {
                  System.out.println("Random SourceIsTargetException error");
                } catch (MoveAtLeastOneArmyException e) {
                  System.out.println("Random MoveAtLeastOneArmyException");
                } catch (OutOfArmyException e) {
                  System.out.println("Random OutOfArmyException");
                } catch (CountryNotInRealms countryNotInRealms) {
                  System.out.println("Random CountryNotInRealms countryNotInRealms");
                } catch (NoSuchPathException e) {
                  System.out.println("Random NoSuchPathException");
                } catch (MustBeEqualOrMoreThanNumOfDice mustBeEqualOrMoreThanNumOfDice) {
                  System.out.println("Random MustBeEqualOrMoreThanNumOfDice");
                  continue;
                }
                num--;

              }
              else{
                System.out.println("Phase2 finished");
                break;
              }
            }
            p.nextPhase();

            //phase 3
            System.out.println("Inside Phase3");
            int numOfphase3 = 6;
              boolean flagFindpath = false;
            while(flagFindpath == false && numOfphase3 > 0){
              numOfphase3--;
              Country sourceCountry = realms.get(getRandom(0,realms.size()));
              Country targetCountry = realms.get(getRandom(0,realms.size()));
              int assignArmy = getRandom(1,sourceCountry.getArmy());
              try{
                flagFindpath = player.findPath(sourceCountry,targetCountry);
                player.fortify(sourceCountry,targetCountry,assignArmy);

                System.out.println("Random: fortify: "  + assignArmy);
              } catch (SourceIsTargetException e) {
                System.out.println("Random source can not be target");
                continue;
              } catch (CountryNotInRealms countryNotInRealms) {
                System.out.println("Random not in realms");
              } catch (MoveAtLeastOneArmyException e) {
                System.out.println("Random move at least 1 army");
              } catch (NoSuchPathException e) {
                System.out.println("Random NoSuchPathException");
                continue;
              } catch (OutOfArmyException e) {
                System.out.println("Random OutOfArmyException");
                continue;
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
        int maxDice = Math.min(c.getArmy(),2);
        c.getOwner().setNumOfDice(getRandom(1,maxDice));
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

    
    /**
     * assign army to country
     * @param player current player
     * @param realms the countries owned by player
     * 
     */

    
    public void assignArmyTocountry(Player player, ArrayList<Country> realms){
        while (player.isArmyLeft()){
            System.out.println("Random: assigning armies to countries");
                Country tempCountry = realms.get(getRandom(0,realms.size()));
                int assignedArmy = getRandom(1,player.getUnassigned_armies() + 1);
                while( assignedArmy > 0){
                    try{
                        player.reinforce(tempCountry);
                        assignedArmy--;
                    }
                    catch (OutOfArmyException e) {
                        System.out.println("Random out of army");
                    }
                }
            }

    }

    /**
     * assign army to the conquered country
     * @param player current player
     * @param sourceCountry the attack country 
     * 
     */
    
    public int assignAfterConquer(Country sourceCountry,Player player){
      int sourceCountryArmy = sourceCountry.getArmy() ;
      int numOfDice = player.getNumOfDice();
      int assignAfterConquer;
      if (sourceCountryArmy > numOfDice + 1){
        assignAfterConquer = getRandom(numOfDice + 1, sourceCountryArmy);
      }
      else {
        assignAfterConquer = sourceCountryArmy - 1;

      }
      System.out.println("Random: assign after couquer: " + assignAfterConquer);

      return  assignAfterConquer;
    }
    
    /**
     * check winner
     * @param p phase
     *  
     */
    
    public void checkWinner(Phases p){
        if(p.checkWinner()){
            if(p.tournament){
                if (p.winner != "Draw") {
                    p.winner = "Cheater";
                    System.out.println("Player " + p.getCurrent_player().getId() + " wins the game!");
                }
                return;
            }


        }

    }

}