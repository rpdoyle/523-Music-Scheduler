/*
 * File: RoomDayTimeTest.java
 * Description: Implements JUnit tests for RoomDayTime.java
 */

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class RoomDayTimeTest {
	Room testRoom;
	RoomDayTime testRoomDayTime;
	
	@Before
	public void setUp(){
		testRoom = new Room("Test Name 110", new ArrayList<String>(Arrays.asList("Piano", "Drums")), new int[] {500, 1670, 4309});
		testRoomDayTime = new RoomDayTime(testRoom, 500);
	}

	@Test
	public void testGetRoom(){
		assertEquals(testRoom, testRoomDayTime.getRoom());
	}
	
	@Test
	public void testGetTime(){
		assertEquals(500, testRoomDayTime.getTime());
	}
}
