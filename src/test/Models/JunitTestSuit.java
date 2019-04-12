
package test.Models;
import test.Game.ControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        ControllerTest.class,
        CardTest.class,
        MapEditTest.class,
        PhasesTest.class,
        PlayerTest.class,
        RefactoringTest.class
})
public class JunitTestSuit {

}

