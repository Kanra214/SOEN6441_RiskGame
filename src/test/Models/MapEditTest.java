package test.Models; 

import Models.MapEdit;
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
* 
*/ 
@Test
public void testLoadMapFile() throws Exception {
    assertEquals(true,map.loadMapFile("1.map"));
    map.clear();
    assertEquals(false,map.loadMapFile("2.map"));
    map.clear();
    assertEquals(false,map.loadMapFile("3.map"));
    map.clear();
    assertEquals(false,map.loadMapFile("4.map"));
    map.clear();
    assertEquals(false,map.loadMapFile("5.map"));
    map.clear();
}

/** 
* 
* Method: findCountry(String countryName) 
* 
*/ 
@Test
public void testFindCountry() throws Exception { 
//TODO: Test goes here...
    map.loadMapFile("1.map");
    assertEquals(null,map.findCountry("Territory01"));
    assertEquals(null,map.findCountry("Left Wing"));
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
    map.loadMapFile("1.map");
    assertEquals(null,map.findContinent("Source Ameriacn"));
    map.clear();
} 

/** 
* 
* Method: addCountry(String countryName, String continentName, int coordinateX, int coordinateY) 
* 
*/ 
@Test
public void testAddCountry() throws Exception {
//TODO: Test goes here...
    map.loadMapFile("1.map");
    assertEquals(false,map.addCountry("Left_Wing1","Left Wing",100,200));
    assertEquals(true,map.addCountry("Left Wing1","Left Wing",100,200));
    map.clear();


} 

/** 
* 
* Method: addContinent(String continentName, int controlNum) 
* 
*/ 
@Test
public void testAddContinent() throws Exception { 
//TODO: Test goes here...
    map.loadMapFile("1.map");
    assertEquals(false,map.addContinent("Fuselage",10));
    assertEquals(true,map.addContinent("Ameriacn",10));
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
    map.loadMapFile("1.map");
    assertEquals(true,map.addConnection("Left_Wing1","Left_Wing2"));
    assertEquals(false,map.addConnection("Territory01","Territory03"));
    map.clear();


}



/** 
* 
* Method: checkValid() 
* 
*/ 
@Test
public void testCheckValid() throws Exception {
//TODO: Test goes here...
    map.loadMapFile("1.map");
    assertEquals(true,map.checkValid());
    map.clear();
    map.loadMapFile("6.map");
    assertEquals(false,map.checkValid());
    map.clear();
    map.loadMapFile("7.map");
    assertEquals(false,map.checkValid());
    map.clear();
} 




} 
