/*
 * File: HelperMethodsTest.java
 * Description: Implements JUnit tests for Room.java
 * 	
 */

import static org.junit.Assert.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class HelperMethodsTest {
	
	@Test
	public void testGetAllPossibleSpecialInstruments(){
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(Arrays.asList("Piano", "Drums")), new int[] {500, 1670, 4309});
		Room testRoom2 = new Room("Test Name 210", new ArrayList<String>(Arrays.asList("Piano", "Drums")), new int[] {500, 1670, 4309});
		Room testRoom3 = new Room("Test Name 310", new ArrayList<String>(Arrays.asList("Piano", "Drums")), new int[] {500, 1670, 4309});
		ArrayList<Room> rooms = new ArrayList<Room>(Arrays.asList(testRoom, testRoom2, testRoom3));
		assertEquals(new ArrayList<String>(Arrays.asList("Drums", "Piano")), HelperMethods.getAllPossibleSpecialInstruments(rooms));
	}
	
	@Test
	public void testGetAllPossibleSpecialInsruments2(){
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(Arrays.asList("Drums")), new int[] {500, 1670, 4309});
		Room testRoom2 = new Room("Test Name 210", new ArrayList<String>(Arrays.asList("Piano")), new int[] {500, 1670, 4309});
		Room testRoom3 = new Room("Test Name 310", new ArrayList<String>(Arrays.asList("Organ")), new int[] {500, 1670, 4309});
		ArrayList<Room> rooms = new ArrayList<Room>(Arrays.asList(testRoom, testRoom2, testRoom3));
		assertEquals(new ArrayList<String>(Arrays.asList("Drums", "Piano", "Organ")), HelperMethods.getAllPossibleSpecialInstruments(rooms));
	}
		
	@Test
	public void testGetAllPossibleSpecialInsruments3(){
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(Arrays.asList("Drums", "Piano")), new int[] {500, 1670, 4309});
		Room testRoom2 = new Room("Test Name 210", new ArrayList<String>(Arrays.asList("Piano", "Saxophone")), new int[] {500, 1670, 4309});
		ArrayList<Room> rooms = new ArrayList<Room>(Arrays.asList(testRoom, testRoom2));
		assertEquals(new ArrayList<String>(Arrays.asList("Drums", "Piano", "Saxophone")), HelperMethods.getAllPossibleSpecialInstruments(rooms));
	}
	// Need to double check that this test reall works. Perhaps pointers are being used?
	@Test
	public void testgetRoomDayTimes(){
		Room testRoom = new Room("Test Name 110", new ArrayList<String>(Arrays.asList("Drums", "Piano")), new int[] {500, 1670, 4309});
		Room testRoom2 = new Room("Test Name 210", new ArrayList<String>(Arrays.asList("Piano", "Saxophone")), new int[] {500, 1670, 4309});
		ArrayList<Room> rooms = new ArrayList<Room>(Arrays.asList(testRoom, testRoom2));
		RoomDayTime testrdt = new RoomDayTime(testRoom, testRoom.getAvailableTimes()[0]);
		RoomDayTime testrdt2 = new RoomDayTime(testRoom, testRoom.getAvailableTimes()[1]);
		RoomDayTime testrdt3 = new RoomDayTime(testRoom, testRoom.getAvailableTimes()[2]);
		RoomDayTime testrdt4 = new RoomDayTime(testRoom2, testRoom2.getAvailableTimes()[0]);
		RoomDayTime testrdt5 = new RoomDayTime(testRoom2, testRoom2.getAvailableTimes()[1]);
		RoomDayTime testrdt6 = new RoomDayTime(testRoom2, testRoom2.getAvailableTimes()[2]);
		ArrayList<RoomDayTime> rdt = new ArrayList<RoomDayTime>(Arrays.asList(testrdt, testrdt2, testrdt3, testrdt4, testrdt5, testrdt6));
		assertEquals(rdt, HelperMethods.getRoomDayTimes(rooms));
	
	}

}
