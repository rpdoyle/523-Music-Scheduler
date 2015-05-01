/*
 * File: PairTest.java
 * Description: Implements JUnit tests for Pair.java
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class PairTest {
	Student testStudent;
	Teacher testTeacher;
	Pair testPair;

	// Not sure what to do with the siblings of a student? Does not have
	// anything to do with a Pair...
	@Before
	public void setUp() {
		testStudent = new Student(1, "Faith Collins", "21", "Female", "Selena",
				"Piano", new String[] { "Piano", "Violin", "Trumpet" },
				new String[] { "0-2", "0-2", "0-2" }, "spanish", new int[] {
						500, 1670, 4309 });
		testTeacher = new Teacher(1, "Selena Gomez", "Faith Collins", "Piano",
				"Yes", new String[] { "Piano" }, new String[] { "12 years" },
				"female", "10+", "beginner", new String[] { "spanish" }, "no",
				new int[] { 500, 1670, 4309 });
		testPair = new Pair(testStudent, testTeacher, 120,
				new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
	}

	@Test
	public void testGetStudent() {
		assertEquals(testStudent, testPair.getStudent());
	}

	@Test
	public void testGetTeacher() {
		assertEquals(testTeacher, testPair.getTeacher());
	}

	@Test
	public void testGetScore() {
		assertEquals(120, testPair.getScore());
	}

	@Test
	public void testGetMutualTimes() {
		assertEquals(new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)),
				testPair.getMutualTimes());
	}

	@Test
	public void testGetInstrument() {
		assertEquals("piano", testPair.getInstrument());
	}

}
