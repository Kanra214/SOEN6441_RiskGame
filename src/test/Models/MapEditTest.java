package test.Models; 

import MapEditor.MapEdit;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import static org.junit.Assert.assertEquals;

/** 
* MapEdit Tester. 
* 
* @author Bin
* @since <pre>Mar 3, 2019</pre> 
* @version 1.0 
*/ 
public class MapEditTest {
    MapEdit map;

@Before
public void before() throws Exception {
    System.out.println("Inside before");
    map = new MapEdit("test1");
} 

@After
public void after() throws Exception {
    System.out.println("Inside after");
} 

/** 
* 
* Method: loadMapFile(String mapFileName) 
* @throws Exception throw exception
* 
*/ 
@Test
public void testLoadMapFile() {
    assertEquals(true,map.loadMapFile("1.txt"));
    map.clear();
}

/** 
* 
* Method: findCountry(String countryName) 
* @throws Exception throw exception
* 
*/ 
@Test
public void testFindCountry() throws Exception { 
//TODO: Test goes here...
    map.loadMapFile("1.txt");
    assertEquals(null,map.findCountry("k"));
    map.clear();
} 

/** 
* 
* Method: findContinent(String continentName) 
* 
*/ 
@Test
public void testFindContinent() throws Exception { 
//TODO: Test goes here...
    map.loadMapFile("1.txt");
    assertEquals(null,map.findContinent("pop"));
    map.clear();
} 

/** 
* 
* Method: addCountry(String countryName, String continentName, int coordinateX, int coordinateY) 
* @throws Exception throw exception
*/ 
@Test
public void testAddCountry() throws Exception {
    map.loadMapFile("1.txt");
    assertEquals(false,map.addCountry("Left_Wing1","Left Wing",100,200));
    assertEquals(true,map.addCountry("m","z",100,200));
    map.clear();


} 

/** 
* 
* Method: addContinent(String continentName, int controlNum) 
* @throws Exception throw exception
* 
*/ 
@Test
public void testAddContinent() throws Exception { 
//TODO: Test goes here...
    map.loadMapFile("1.txt");
    assertEquals(false,map.addContinent("z",10));
    assertEquals(true,map.addContinent("America",10));
    map.clear();


} 

/**
*
* Method: addConnection(String countryNameFrom, String countryNameTo)
*
*/
@Test
public void testAddConnection() throws Exception {
//TODO: Test goes here...
    map.loadMapFile("1.txt");
    assertEquals(true,map.addConnection("f","e"));
    assertEquals(false,map.addConnection("a","e"));
    map.clear();


}



/** 
* 
* Method: checkValid() 
* @throws Exception throw exception
* 
*/ 
@Test
public void testCheckValid() throws Exception {
//TODO: Test goes here...
    map.loadMapFile("1.txt");
    assertEquals(true,map.checkValid());
    map.clear();
    map.loadMapFile("WrongFormat1-NotConnectedGraph.map");
    assertEquals(false,map.checkValid());
    map.clear();
    map.loadMapFile("WrongFormat2-DupicateConnections.map");
    assertEquals(false,map.checkValid());
    map.clear();
} 




} 
