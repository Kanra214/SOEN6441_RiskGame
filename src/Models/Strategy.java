
package Models;

import sun.plugin2.message.Serializer;


public interface Strategy {
  /*** Method whose implementation varies depending on the strategy adopted. */
  void execute(Phases p);
  void defend(Player pl);
  String getName();


}



