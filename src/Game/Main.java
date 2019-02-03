package Game;//this is main thread
import View_Components.Window;

import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {
        Controller controller = new Controller(new Window());
        controller.start();
        System.out.println("end");



    }
}
