/*
 * File: HungarianResultTest.java
 * Description: Implements JUnit tests for Room.java
 * 	
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class HungarianResultTest {
	Student testStudent;
	Teacher testTeacher;
	Pair testPair;
	Room testRoom;
	RoomDayTime testRoomDayTime;
	RoomDayTime testRoomDayTime2;
	PairTime testPairTime;
	PairTime testPairTime2;
	ArrayList<PairTime> pairTime;
	HungarianResult testHR;
	
	@Before
	public void setUp(){
		testStudent = new Student(1, "Faith Collins", "21","Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"}, new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		testTeacher = new Teacher(1, "Selena", "Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		testPair = new Pair(testStudent, testTeacher, 120, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		testRoom = new Room("Test Name 110", new ArrayList<String>(Arrays.asList("Piano", "Drums")), new int[] {500, 1670, 4309});
		testRoomDayTime = new RoomDayTime(testRoom, 500);
		testRoomDayTime2 = new RoomDayTime(testRoom, 1670);
		testPairTime = new PairTime(testPair, testRoomDayTime);
		testPairTime2 = new PairTime(testPair, testRoomDayTime2);
		pairTime = new ArrayList<PairTime>(Arrays.asList(testPairTime, testPairTime2));
		testHR = new HungarianResult(pairTime, 300);	
	}
	
	@Test
	public void testGetPairTimes(){
		assertEquals(pairTime, testHR.getPairTimes());
	}
	
	@Test
	public void testGetScore(){
		assertEquals(300, testHR.getScore());
	}
}
