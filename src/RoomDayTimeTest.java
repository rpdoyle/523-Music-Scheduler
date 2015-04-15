/*
 * File: RoomDayTimeTest.java
 * Description: Implements JUnit tests for Room.java
 * 	
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class RoomDayTimeTest {

	@Test
	public void testGetRoom(){
		Room testRoom = new Room("Test Name 110",new ArrayList<String>(Arrays.asList("Piano", "Drums")), new int[] {500, 1670, 4309});
		RoomDayTime  testRoomDayTime = new RoomDayTime(testRoom, 500);
		assertEquals(testRoom, testRoomDayTime.getRoom());
	}
	
	@Test
	public void testGetTime(){
		Room testRoom = new Room("Test Name 110",new ArrayList<String>(Arrays.asList("Piano", "Drums")), new int[] {500, 1670, 4309});
		RoomDayTime  testRoomDayTime = new RoomDayTime(testRoom, 500);
		assertEquals(500, testRoomDayTime.getTime());
	}
}
