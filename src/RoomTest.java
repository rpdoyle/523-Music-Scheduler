/*
 * File: RoomTest.java
 * Description: Implements JUnit tests for Room.java
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

//This class implements tests for the methods in ExcelReader.java
public class RoomTest {

	@Test
	@SuppressWarnings(value = { "unused" })
	public void testRoom() {
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(
				Arrays.asList("Piano", "Drums")), new int[] { 500, 1670, 4309 });
	}

	@Test
	public void testGetName() {
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(
				Arrays.asList("Piano", "Drums")), new int[] { 500, 1670, 4309 });
		assertEquals("Test Name 110", testRoom.getName());
	}

	@Test
	public void testGetSpecialInstruments() {
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(
				Arrays.asList("Piano", "Drums")), new int[] { 500, 1670, 4309 });
		assertEquals(new ArrayList<String>(Arrays.asList("Piano", "Drums")),
				testRoom.getSpecialInstruments());
	}

	@Test
	public void testGetAvailableTimes() {
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(
				Arrays.asList("Piano", "Drums")), new int[] { 500, 1670, 4309 });
		assertArrayEquals(new int[] { 500, 1670, 4309 },
				testRoom.getAvailableTimes());
	}

}
