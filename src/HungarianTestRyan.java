/*
 * File: HungarianTestRyan.java
 * Description: Implements JUnit tests for Hungarian.java
 * 	
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

// This class implements tests for the methods in Hungarian.java
public class HungarianTestRyan {

	@Test
	// Note: the Students and Teachers in the pairs in this test are not
	// actually compatible, but the code
	// we are testing only depends on the Pair objects, not the Student and
	// Teacher's attributes.
	public void testCreateOriginalMatrix() {
		ArrayList<Pair> testPairs = new ArrayList<Pair>();
		ArrayList<String> testSpecialInstruments = new ArrayList<String>();
		testSpecialInstruments.add("Drums");
		testSpecialInstruments.add("Piano");

		ArrayList<Integer> testMutualTimes = new ArrayList<Integer>();
		testMutualTimes.add(500);
		testMutualTimes.add(1500);
		testMutualTimes.add(2500);
		testPairs.add(new Pair(createTestStudent(), createTestTeacher(), 251,
				testMutualTimes, "Guitar"));

		testMutualTimes = new ArrayList<Integer>();
		testMutualTimes.add(100);
		testMutualTimes.add(1100);
		testMutualTimes.add(2500);
		testPairs.add(new Pair(createTestStudent(), createTestTeacher(), 300,
				testMutualTimes, "Flute"));

		ArrayList<RoomDayTime> testRoomDayTimes = new ArrayList<RoomDayTime>();

		Room testRoom = new Room("Test Name 110", new ArrayList<String>(
				Arrays.asList("Piano", "Drums")), new int[] { 500, 1670, 4309 });
		RoomDayTime testRoomDayTime = new RoomDayTime(testRoom, 500);
		testRoomDayTimes.add(testRoomDayTime);

		int[][] result = Hungarian.createOriginalMatrix(testPairs,
				testRoomDayTimes, testSpecialInstruments);
		int[][] correct = new int[][] { { 251, 0 }, { 0, 0 } };

		// Test too few RoomDayTimes, the proper scores for compatible RDTs and
		// Pairs,
		// and that incompatible RDTs and Pairs are scored with 0
		assertArrayEquals(result, correct);

		testRoom = new Room("Test Name 110", new ArrayList<String>(
				Arrays.asList("Piano", "Drums")), new int[] { 500, 1100, 4309 });
		testRoomDayTime = new RoomDayTime(testRoom, 1100);
		testRoomDayTimes.add(testRoomDayTime);

		testRoom = new Room("Test Name 110", new ArrayList<String>(
				Arrays.asList("Drums")), new int[] { 500, 2500, 4309 });
		testRoomDayTime = new RoomDayTime(testRoom, 2500);
		testRoomDayTimes.add(testRoomDayTime);

		result = Hungarian.createOriginalMatrix(testPairs, testRoomDayTimes,
				testSpecialInstruments);
		correct = new int[][] { { 251, 0, 251 }, { 0, 300, 300 }, { 0, 0, 0 } };

		// Tests for larger matrices (3x3) and the condition of too few Pairs
		assertArrayEquals(result, correct);

		testMutualTimes = new ArrayList<Integer>();
		testMutualTimes.add(391);
		testMutualTimes.add(1100);
		testMutualTimes.add(2500);
		testPairs.add(new Pair(createTestStudent(), createTestTeacher(), 127,
				testMutualTimes, "Piano"));

		result = Hungarian.createOriginalMatrix(testPairs, testRoomDayTimes,
				testSpecialInstruments);
		correct = new int[][] { { 251, 0, 251 }, { 0, 300, 300 }, { 0, 127, 0 } };

		// Tests for RDTs incompatible based on special instrument and equal
		// number
		// of RDTs and Pairs
		assertArrayEquals(result, correct);
	}

	@Test
	public void testCalculateLineMatrix() {
		int[][] testArray = new int[][] { { 0, 0, 0, 0 }, { 5, 0, 0, 4 },
				{ 1, 0, 0, 3 }, { 0, 0, 0, 0 } };

		int[][] correct = new int[][] { { 1, 1, 1, 1 }, { 1, 1, 1, 1 },
				{ 1, 1, 1, 1 }, { 1, 1, 1, 1 } };

		// Test prioritizing lines on full rows
		assertArrayEquals(correct, Hungarian.calculateLineMatrix(testArray));
		assertEquals(4, Hungarian.getNumLines());

		testArray = new int[][] { { 0, 6, 1, 0 }, { 0, 8, 0, 0 },
				{ 0, 0, 9, 0 }, { 0, 2, 7, 0 } };

		correct = new int[][] { { 1, 0, 0, 1 }, { 2, 1, 1, 2 }, { 2, 1, 1, 2 },
				{ 1, 0, 0, 1 } };

		// Test lines on full columns
		assertArrayEquals(correct, Hungarian.calculateLineMatrix(testArray));
		assertEquals(4, Hungarian.getNumLines());

		testArray = new int[][] { { 48, 32, 0, 13 }, { 63, 0, 51, 0 },
				{ 42, 10, 0, 42 }, { 0, 13, 37, 4 } };

		correct = new int[][] { { 0, 0, 1, 0 }, { 1, 1, 2, 1 }, { 0, 0, 1, 0 },
				{ 1, 1, 2, 1 } };

		// Test crossing lines with no all-zero rows or columns
		assertArrayEquals(correct, Hungarian.calculateLineMatrix(testArray));
		assertEquals(3, Hungarian.getNumLines());
	}

	@Test
	public void testGetRowZerosIntArrayArrayIntArrayArray() {
		int[][] testArray = new int[][] { { 0, 0, 0, 7, 9 }, { 0, 2, 0, 0, 0 },
				{ 0, 0, 0, 0, 0 }, { 0, 0, 0, 5, 0 }, { 9, 0, 1, 0, 0 } };

		int[][] covered = new int[][] { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };

		int[] correct = new int[] { 3, 4, 5, 4, 3 };
		int[] rowZeros = Hungarian.getRowZeros(testArray, covered);

		// Test matrix with no lines (uncovered)
		assertArrayEquals(correct, rowZeros);

		covered = new int[][] { { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1 }, { 1, 2, 1, 1, 1 }, { 1, 1, 1, 1, 1 } };

		correct = new int[] { 0, 0, 0, 0, 0 };
		rowZeros = Hungarian.getRowZeros(testArray, covered);

		// Test fully covered matrix
		assertArrayEquals(correct, rowZeros);

		covered = new int[][] { { 1, 0, 0, 1, 1 }, { 1, 1, 1, 1, 1 },
				{ 1, 1, 0, 1, 1 }, { 0, 2, 1, 1, 1 }, { 0, 1, 1, 1, 1 } };

		correct = new int[] { 2, 0, 1, 1, 0 };
		rowZeros = Hungarian.getRowZeros(testArray, covered);

		// Test a partially covered matrix
		assertArrayEquals(correct, rowZeros);
	}

	@Test
	public void testGetColZerosIntArrayArrayIntArrayArray() {
		int[][] testArray = new int[][] { { 0, 0, 0, 7, 9 }, { 0, 2, 0, 0, 8 },
				{ 0, 0, 0, 0, 0 }, { 0, 0, 0, 5, 3 }, { 9, 0, 0, 0, 1 } };

		int[][] covered = new int[][] { { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };

		int[] correct = new int[] { 4, 4, 5, 3, 1 };
		int[] colZeros = Hungarian.getColZeros(testArray, covered);

		// Test a matrix with no lines (uncovered)
		assertArrayEquals(correct, colZeros);

		covered = new int[][] { { 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1 },
				{ 1, 1, 1, 1, 1 }, { 1, 2, 1, 1, 1 }, { 1, 1, 1, 1, 1 } };

		correct = new int[] { 0, 0, 0, 0, 0 };
		colZeros = Hungarian.getColZeros(testArray, covered);

		// Tests a fully covered matrix
		assertArrayEquals(correct, colZeros);

		covered = new int[][] { { 1, 0, 0, 1, 1 }, { 1, 1, 1, 1, 1 },
				{ 1, 1, 0, 1, 1 }, { 0, 2, 1, 1, 1 }, { 0, 1, 1, 1, 1 } };

		correct = new int[] { 1, 1, 2, 0, 0 };
		colZeros = Hungarian.getColZeros(testArray, covered);

		// Tests a partially covered matrix
		assertArrayEquals(correct, colZeros);
	}

	@Test
	public void testGetRowZerosIntArrayArray() {
		int[][] testArray = new int[][] { { 1, 2, 3, 4, 5 }, { 0, 1, 3, 9, 0 },
				{ 0, 0, 0, 0, 0 }, { 3, 0, 9, 2, 7 }, { 0, 0, 0, 6, 2 } };

		int[] correct = new int[] { 0, 2, 5, 1, 3 };
		int[] rowZeros = Hungarian.getRowZeros(testArray);

		// Tests rows with no zeros, all zeros, and mixtures
		assertArrayEquals(correct, rowZeros);
	}

	@Test
	public void testGetColZerosIntArrayArray() {
		int[][] testArray = new int[][] { { 1, 0, 0, 3, 8 }, { 2, 1, 0, 9, 0 },
				{ 9, 2, 0, 3, 1 }, { 3, 5, 0, 0, 7 }, { 4, 0, 0, 6, 0 } };

		int[] correct = new int[] { 0, 2, 5, 1, 2 };
		int[] colZeros = Hungarian.getColZeros(testArray);

		// Tests columns with no zeros, all zeros, and mixtures
		assertArrayEquals(correct, colZeros);
	}

	@Test
	public void testCheckCompatible() {
		Teacher testTeacher = createTestTeacher();
		Student testStudent = createTestStudent();

		ArrayList<Integer> testTimes = new ArrayList<Integer>();
		testTimes.add(500);
		testTimes.add(6532);

		Pair testPair = new Pair(testStudent, testTeacher, 251, testTimes,
				"Flute");

		Room testRoom = new Room("Test Name 110", new ArrayList<String>(
				Arrays.asList("Piano", "Drums")), new int[] { 500, 1670, 4309 });

		RoomDayTime testRoomDayTime = new RoomDayTime(testRoom, 500);

		ArrayList<String> testSpecialInstruments = new ArrayList<String>();
		testSpecialInstruments.add("Piano");
		testSpecialInstruments.add("Drums");

		// Tests everything fine with a non-special instrument
		assertEquals(true, Hungarian.checkCompatible(testPair, testRoomDayTime,
				testSpecialInstruments));

		testSpecialInstruments.add("Flute");

		// Tests special instrument in unsupporting room
		assertEquals(false, Hungarian.checkCompatible(testPair,
				testRoomDayTime, testSpecialInstruments));

		testRoom = new Room("Test Name 110", new ArrayList<String>(
				Arrays.asList("Piano", "Flute")), new int[] { 500, 1670, 4309 });
		testRoomDayTime = new RoomDayTime(testRoom, 500);

		// Tests special instrument in supporting room
		assertEquals(true, Hungarian.checkCompatible(testPair, testRoomDayTime,
				testSpecialInstruments));

		// Tests non-matching time between the Pair and RoomDayTime
		testRoomDayTime = new RoomDayTime(testRoom, 1670);
		assertEquals(false, Hungarian.checkCompatible(testPair,
				testRoomDayTime, testSpecialInstruments));

	}

	// Creates a dummy Teacher object for use in the above test cases
	private Teacher createTestTeacher() {
		int id = 829;
		String name = "Test Teacher";
		String returningStudent = "Fred Cook";
		String returningInstrument = "Flute";
		String keepReturningStudent = "Yes";
		String[] instruments = new String[] { "Flute", "Bass", "Viola" };
		String[] instrumentExperience = new String[] { "2-4 years", "<1 year",
				"<1 year" };
		String genderPreference = "Female";
		String agePreference = "5-10";
		String levelPreference = "Beginner";
		String[] language = new String[] { "Mandarin", "English" };
		String crimeRecord = "No";
		int[] availableTimes = new int[] { 828, 1293, 9932, 8272 };

		return new Teacher(id, name, returningStudent, returningInstrument,
				keepReturningStudent, instruments, instrumentExperience,
				genderPreference, agePreference, levelPreference, language,
				crimeRecord, availableTimes);
	}

	// Creates a dummy Student object for use in the above test cases
	private Student createTestStudent() {
		int id = 123;
		String name = "Test Name";
		String age = "31";
		String gender = "Male";
		String returningTeacher = "Some Teacher";
		String instrumentOfReturningStudent = "Guitar";
		String[] instruments = new String[] { "Guitar, Flute, Violin" };
		String[] instrumentYears = new String[] { "Less than 1 year",
				"More than 4 years" };
		String language = "English";
		int[] availableTimes = new int[] { 500, 1247, 5383 };

		return new Student(id, name, age, gender, returningTeacher,
				instrumentOfReturningStudent, instruments, instrumentYears,
				language, availableTimes);
	}
}