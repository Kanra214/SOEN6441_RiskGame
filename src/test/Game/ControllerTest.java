package test.Game; 

import static org.junit.Assert.assertEquals;

import Game.Controller;
import Game.MapLoader;
import Models.Phases;
import java.util.ArrayList;
import java.util.Objects;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* Controller Tester. 
* 
* @author <Authors name> 
* @since <pre>Apr 8, 2019</pre> 
* @version 1.0 
*/ 
public class ControllerTest {
  Phases p;
  Controller controller;

@Before
public void before() throws Exception {
  System.out.println("Inside ControllerTest before");
  controller = new Controller();
  ArrayList<ArrayList> tempMap = new MapLoader().loadMap("DemoMap-SmallSize.map");
  p = new Phases(tempMap.get(0), tempMap.get(1));
  controller.setP(p);


} 

@After
public void after() throws Exception {
  System.out.println("Inside ControllerTest after");

} 

/** 
* 
* Method: startManu() 
* 
*/ 
@Test
public void testStartManu() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: ChooseFile(int i) 
* 
*/ 
@Test
public void testChooseFile() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: start() 
* 
*/ 
@Test
public void testStart() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: startTournament(ArrayList<String> mapArray, int[] playerValues, int numGames, int numTurns) 
* 
*/ 
@Test
public void testStartTournament() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: loadGame() 
* 
*/ 
@Test
public void testLoadGame() throws Exception {
  String test = "testSaveAndLoad";
  controller.writeToFile(test);
  controller.setLoadFileName(test);
  controller.loadPhases();
  assertEquals(true,p.equals(controller.getP()));

//TODO: Test goes here... 
} 

/** 
* 
* Method: tournament() 
* 
*/ 
@Test
public void testTournament() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: addListeners() 
* 
*/ 
@Test
public void testAddListeners() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: writeToFile(String saveFileName) 
* 
*/ 
@Test
public void testWriteToFile() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: loadPhases() 
* 
*/ 
@Test
public void testLoadPhases() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: actionPerformed(ActionEvent e) 
* 
*/ 
@Test
public void testActionPerformedE() throws Exception { 
//TODO: Test goes here... 
}


  /**
* 
* Method: forceUserInputCorrectlyForDeploymentAfterConquer() 
* 
*/ 
@Test
public void testForceUserInputCorrectlyForDeploymentAfterConquer() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = Controller.getClass().getMethod("forceUserInputCorrectlyForDeploymentAfterConquer"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
}
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ControllerTest)) {
      return false;
    }
    ControllerTest that = (ControllerTest) o;
    return Objects.equals(p, that.p);
  }

  @Override
  public int hashCode() {
    return Objects.hash(p);
  }

} 
