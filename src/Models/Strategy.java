
package Models;

public interface Strategy {
  /*** Method whose implementation varies depending on the strategy adopted. */
  void execute(Phases p);
  void defend(Player pl);


}



