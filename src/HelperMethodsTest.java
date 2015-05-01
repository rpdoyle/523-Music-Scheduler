/*
 * File: HelperMethodsTest.java
 * Description: Implements JUnit tests for HelperMethods.java
 * 	
 */

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

public class HelperMethodsTest {

	@Test
	public void testGetAllPossibleSpecialInstruments() {
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(
				Arrays.asList("Piano", "Drums")), new int[] { 500, 1670, 4309 });
		Room testRoom2 = new Room("Test Name 210", new ArrayList<String>(
				Arrays.asList("Piano", "Drums")), new int[] { 500, 1670, 4309 });
		Room testRoom3 = new Room("Test Name 310", new ArrayList<String>(
				Arrays.asList("Piano", "Drums")), new int[] { 500, 1670, 4309 });
		ArrayList<Room> rooms = new ArrayList<Room>(Arrays.asList(testRoom,
				testRoom2, testRoom3));
		assertArrayEquals(
				new ArrayList<String>(Arrays.asList("Piano", "Drums"))
						.toArray(),
				HelperMethods.getAllPossibleSpecialInstruments(rooms).toArray());
	}

	@Test
	public void testGetAllPossibleSpecialInsruments2() {
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(
				Arrays.asList("Drums")), new int[] { 500, 1670, 4309 });
		Room testRoom2 = new Room("Test Name 210", new ArrayList<String>(
				Arrays.asList("Piano")), new int[] { 500, 1670, 4309 });
		Room testRoom3 = new Room("Test Name 310", new ArrayList<String>(
				Arrays.asList("Organ")), new int[] { 500, 1670, 4309 });
		ArrayList<Room> rooms = new ArrayList<Room>(Arrays.asList(testRoom,
				testRoom2, testRoom3));
		assertArrayEquals(
				new ArrayList<String>(Arrays.asList("Piano", "Drums", "Organ"))
						.toArray(),
				HelperMethods.getAllPossibleSpecialInstruments(rooms).toArray());
	}

	@Test
	public void testGetAllPossibleSpecialInsruments3() {
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(
				Arrays.asList("Drums", "Piano")), new int[] { 500, 1670, 4309 });
		Room testRoom2 = new Room("Test Name 210", new ArrayList<String>(
				Arrays.asList("Piano", "Saxophone")), new int[] { 500, 1670,
				4309 });
		ArrayList<Room> rooms = new ArrayList<Room>(Arrays.asList(testRoom,
				testRoom2));
		assertArrayEquals(
				new ArrayList<String>(Arrays.asList("Piano", "Saxophone",
						"Drums")).toArray(), HelperMethods
						.getAllPossibleSpecialInstruments(rooms).toArray());
	}

	@Test
	public void testGetRoomDayTimes() {
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(
				Arrays.asList("Drums", "Piano")), new int[] { 500, 1670, 4309 });
		Room testRoom2 = new Room("Test Name 210", new ArrayList<String>(
				Arrays.asList("Piano", "Saxophone")), new int[] { 500, 1670,
				4309 });
		ArrayList<Room> rooms = new ArrayList<Room>(Arrays.asList(testRoom,
				testRoom2));

		RoomDayTime testrdt = new RoomDayTime(testRoom,
				testRoom.getAvailableTimes()[0]);
		RoomDayTime testrdt2 = new RoomDayTime(testRoom,
				testRoom.getAvailableTimes()[1]);
		RoomDayTime testrdt3 = new RoomDayTime(testRoom,
				testRoom.getAvailableTimes()[2]);
		RoomDayTime testrdt4 = new RoomDayTime(testRoom2,
				testRoom2.getAvailableTimes()[0]);
		RoomDayTime testrdt5 = new RoomDayTime(testRoom2,
				testRoom2.getAvailableTimes()[1]);
		RoomDayTime testrdt6 = new RoomDayTime(testRoom2,
				testRoom2.getAvailableTimes()[2]);

		ArrayList<RoomDayTime> rdt = new ArrayList<RoomDayTime>();
		rdt.add(testrdt);
		rdt.add(testrdt2);
		rdt.add(testrdt3);
		rdt.add(testrdt4);
		rdt.add(testrdt5);
		rdt.add(testrdt6);

		RoomDayTimeComparator pc = new RoomDayTimeComparator();
		assertEquals(0,
				pc.compareLists(rdt, HelperMethods.getRoomDayTimes(rooms)));
	}

	@Test
	public void intTimeToString() {
		assertEquals(HelperMethods.intTimeToString(720), "12:00 PM");
		assertEquals(HelperMethods.intTimeToString(0), "12:00 AM");
		assertEquals(HelperMethods.intTimeToString(1440), "12:00 AM");
		assertEquals(HelperMethods.intTimeToString(1441), "12:01 AM");
		assertEquals(HelperMethods.intTimeToString(2161), "12:01 PM");
		assertEquals(HelperMethods.intTimeToString(4827), "8:27 AM");
		assertEquals(HelperMethods.intTimeToString(5150), "1:50 PM");
	}

	@Test
	public void testGetDayOfLesson() {
		assertEquals(HelperMethods.getDayOfLesson(0), 0);
		assertEquals(HelperMethods.getDayOfLesson(1439), 0);
		assertEquals(HelperMethods.getDayOfLesson(1440), 1);
		assertEquals(HelperMethods.getDayOfLesson(2879), 1);
		assertEquals(HelperMethods.getDayOfLesson(2880), 2);
		assertEquals(HelperMethods.getDayOfLesson(4319), 2);
		assertEquals(HelperMethods.getDayOfLesson(4320), 3);
		assertEquals(HelperMethods.getDayOfLesson(5759), 3);
		assertEquals(HelperMethods.getDayOfLesson(5760), 4);
		assertEquals(HelperMethods.getDayOfLesson(7199), 4);
		assertEquals(HelperMethods.getDayOfLesson(7200), 5);
		assertEquals(HelperMethods.getDayOfLesson(8639), 5);
		assertEquals(HelperMethods.getDayOfLesson(8640), 6);
		assertEquals(HelperMethods.getDayOfLesson(10079), 6);
	}

}
