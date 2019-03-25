package Game;//this is main thread
import View_Components.Window;

import java.io.IOException;

/**
 * In Game package and this class is for Main class
 * 
 * @author Team36
 * @version 1.1
 */
public class Main {
    /**
     * This is main
     * @param args args
     */
    public static void main(String args[]) {
        Controller controller = new Controller();
        controller.startManu();

    }
}
