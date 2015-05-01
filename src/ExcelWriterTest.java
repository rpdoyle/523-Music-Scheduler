/*
 * File: ExcelWriterTest.java
 * Description: Implements JUnit tests for ExcelWriter.java
 * 	
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

// This class implements tests for the methods in ExcelWriter.java
public class ExcelWriterTest {

	@Test
	// This method tests ExcelWriter.prepareDataToWriteToExcel(HungarianResult
	// hungarianResult, HashSet<Integer> roomDayTimeInts)
	public void testPrepareDataToWriteToExcel() {

		Student testStudent = new Student(1, "Alex Frankfort", "21", "Male",
				"Bob", "Piano", new String[] { "Piano", "Violin", "Trumpet" },
				new String[] { "0-2", "0-2", "0-2" }, "spanish", new int[] {
						1500, 1670, 4309 });
		Student testStudent2 = new Student(2, "Bryan Head", "20", "Male",
				"Jim", "Drums", new String[] { "Drums", "Violin", "Trumpet" },
				new String[] { "0-2", "0-2", "0-2" }, "spanish", new int[] {
						1700, 2670, 3309 });
		Student testStudent3 = new Student(3, "Amanda Thompson", "22",
				"Female", "Taylor", "Voice", new String[] { "Voice", "Violin",
						"Trumpet" }, new String[] { "0-2", "0-2", "0-2" },
				"mandarin", new int[] { 600, 3670, 5309 });

		Teacher testTeacher = new Teacher(1, "Bob Person", "Alex Frankfort",
				"Piano", "Yes", new String[] { "Piano" },
				new String[] { "12 years" }, "male", "10+", "beginner",
				new String[] { "spanish" }, "no",
				new int[] { 1500, 1670, 4309 });
		Teacher testTeacher2 = new Teacher(2, "Jim Halpert", "Bryan Head",
				"Drums", "Yes", new String[] { "Drums" },
				new String[] { "12 years" }, "male", "10+", "beginner",
				new String[] { "spanish" }, "no",
				new int[] { 1700, 2670, 3309 });
		Teacher testTeacher3 = new Teacher(3, "Taylor Killion",
				"Amanda Thompson", "Voice", "Yes", new String[] { "Voice" },
				new String[] { "12 years" }, "female", "10+", "beginner",
				new String[] { "mandarin" }, "no",
				new int[] { 600, 31670, 5309 });

		Pair testPair = new Pair(testStudent, testTeacher, 300,
				new ArrayList<Integer>(Arrays.asList(1500, 1670, 4309)),
				"piano");
		Pair testPair2 = new Pair(testStudent2, testTeacher2, 310,
				new ArrayList<Integer>(Arrays.asList(1700, 2670, 3309)),
				"drums");
		Pair testPair3 = new Pair(testStudent3, testTeacher3, 230,
				new ArrayList<Integer>(Arrays.asList(600, 3670, 5309)), "voice");

		Room testRoom = new Room("Test Name 110", new ArrayList<String>(
				Arrays.asList("Piano", "Drums")), new int[] { 1500, 1700, 1670,
				4309 });
		Room testRoom2 = new Room("Test Name 210", new ArrayList<String>(
				Arrays.asList("Piano", "Drums")), new int[] { 600, 1234, 3670,
				5309 });

		RoomDayTime testRoomDayTime = new RoomDayTime(testRoom, 980);
		RoomDayTime testRoomDayTime2 = new RoomDayTime(testRoom2, 980);
		RoomDayTime testRoomDayTime3 = new RoomDayTime(testRoom2, 3860);

		PairTime testPairTime = new PairTime(testPair, testRoomDayTime);
		PairTime testPairTime2 = new PairTime(testPair2, testRoomDayTime2);
		PairTime testPairTime3 = new PairTime(testPair3, testRoomDayTime3);

		ArrayList<PairTime> pairTime = new ArrayList<PairTime>();
		pairTime.add(testPairTime);
		pairTime.add(testPairTime2);
		pairTime.add(testPairTime3);

		ArrayList<RoomDayTime> roomDayTimes = new ArrayList<RoomDayTime>();
		roomDayTimes.add(testRoomDayTime);
		roomDayTimes.add(testRoomDayTime2);
		roomDayTimes.add(testRoomDayTime3);

		HashSet<Integer> roomDayTimeInts = new HashSet<Integer>();

		for (int i = 0; i < roomDayTimes.size(); i++) {
			roomDayTimeInts.add(roomDayTimes.get(i).getTime());
		}

		HungarianResult testHungarianResult = new HungarianResult(pairTime,
				1390);

		String[][] data = ExcelWriter.prepareDataToWriteToExcel(
				testHungarianResult, roomDayTimeInts);

		// Test that the header row is being correctly written
		assertEquals("Monday (teacher, student, instrument)", data[0][1]);
		assertEquals("Tuesday (teacher, student, instrument)", data[0][2]);
		assertEquals("Wednesday (teacher, student, instrument)", data[0][3]);
		assertEquals("Thursday (teacher, student, instrument)", data[0][4]);
		assertEquals("Friday (teacher, student, instrument)", data[0][5]);

		// Test that the writer is skipping a line after the header row
		assertEquals(null, data[1][0]);

		// Test that the first time row is being correctly written
		assertEquals("4:20 PM", data[2][0]);

		// Test that the first room and teacher/student pair is being correctly
		// written
		assertEquals("Test Name 110", data[3][0]);
		assertEquals("Bob Person, Alex Frankfort, piano", data[3][1]);

		// Test that the second room and teacher/student pair is being correctly
		// written
		// This pair has the same day and time as the last one but in a
		// different room
		assertEquals("Test Name 210", data[4][0]);
		assertEquals("Jim Halpert, Bryan Head, drums", data[4][1]);

		// Test that the third teacher/student pair is being correctly written
		// This pair has the same room and time as the last one but on a
		// different day
		assertEquals("Taylor Killion, Amanda Thompson, voice", data[4][3]);

	}
}
