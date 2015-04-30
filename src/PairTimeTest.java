/*
 * File: PairTimeTest.java
 * Description: Implements JUnit tests for PairTime.java
 */

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class PairTimeTest {
	
	Student testStudent;
	Teacher testTeacher;
	Pair testPair;
	Room testRoom;
	RoomDayTime testRoomDayTime;
	PairTime testPairTime;
	
	@Before
	public void setUp(){
		testStudent = new Student(1, "Faith Collins", "21", "Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"}, new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		testTeacher = new Teacher(1, "Selena Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		testPair = new Pair(testStudent, testTeacher, 120, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		testRoom = new Room("Test Name 110", new ArrayList<String>(Arrays.asList("Piano", "Drums")), new int[] {500, 1670, 4309});
		testRoomDayTime = new RoomDayTime(testRoom, 500);
		testPairTime = new PairTime(testPair, testRoomDayTime);
	}

	@Test
	public void testGetPair(){
		assertEquals(testPair, testPairTime.getPair());
	}
	
	@Test
	public void testGetRoomDayTime(){
		assertEquals(testRoomDayTime, testPairTime.getRoomDayTime());
	}
}
