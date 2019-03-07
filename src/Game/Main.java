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
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {
        Controller controller = new Controller(new Window());
        controller.startManu();

    }
}
