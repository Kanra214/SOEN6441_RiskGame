
package Models;

/**
 * the interface of strategy
 * 
 */
public interface Strategy {
  /*** Method whose implementation varies depending on the strategy adopted. */
  void execute(Phases p);
  void defend(Country beingAttacked);
  String getName();


}



