package test.Game; 

import static org.junit.Assert.assertEquals;

import Game.Controller;
import Game.MapLoader;
import Models.Phases;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.io.InputStream;

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
  String[] mapArray = {"DemoMap-SmallSize.map",
          "DemoMap-BigSize.txt",
          "1.txt",
          "Aden.map",
          "Asia.map"};
  ArrayList<String> maps = new ArrayList<>();
  boolean flag = false;
  int mapNum = 5;
  for (int i = 0; i < mapNum; i++){
    int r = (int) (Math.random() * 5);
    if (!maps.contains(mapArray[r])){
      maps.add(mapArray[r]);
    }else {
      i--;
    }
  }
  int[] playerValues = {-1,1,1,1,1};
  int numGame = 5;
  ArrayList<String> winners = controller.startTournament(maps ,playerValues,numGame,50);
  for(String winner : winners){
    if(winner.equals("Random") || winner.equals("Cheater") || winner.equals("Aggressive") || winner.equals("Benevolent") || winner.equals("Draw")) {
      flag = true;
    }else{
      flag = false;
    }
  }
  assertEquals(true,flag);

  assertEquals(winners.size(),maps.size() * numGame);
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
  String[] mapNumString= {"6", "7", "0", "5"};
  int tMapNum;
  int i = 0;
  do {
      tMapNum =Integer.parseInt(mapNumString[i]);
      i++;
    }
    while (tMapNum < 1 || tMapNum > 5);

  assertEquals(5, tMapNum);
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
