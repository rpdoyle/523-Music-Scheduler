/* 
 * File: RandomizationTest.java
 * Description: Implements JUnit tests for Randomization.java
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;


public class RandomizationTest {

	private Student createStudent1() {
		Student testStudent1 = new Student(123, "Joe", "31", "Male", "", "",
				new String[] { "Guitar", "Flute", "Violin" }, new String[] {
						"0-2 years (beginner)", "0-2 years (beginner)",
						"4+ years (advanced)" }, "English", new int[] { 500 });
		return testStudent1;
	}

	private Student createStudent2() {
		// Should be mandatory paired with Peter (1)
		Student testStudent2 = new Student(456, "Jane", "7", "Female",
				"Peter Thomas", "Guitar", new String[] { "Guitar", "Piano",
						"Clarinet" },
				new String[] { "Less than 1 year", "2-4 years (intermediate)",
						"2-4 years (intermediate)" }, "English", new int[] {
						650, 1812, 4356 });
		return testStudent2;
	}

	private Student createStudent3() {
		Student testStudent3 = new Student(789, "Greg", "16", "Male", "Janice",
				"Guitar", new String[] { "Piano", "Tuba", "Flute" },
				new String[] { "4+ years (advanced)", "4+ years (advanced)",
						"4+ years (advanced)" }, "English", new int[] { 150,
						2005 });
		return testStudent3;
	}
	
	private Teacher createTeacher1() {
		// Should be mandatory paired with Jane (2)
		Teacher testTeacher1 = new Teacher(123, "Peter Thomas", "Jane",
				"Guitar", "Yes", new String[] { "Guitar", "Bass", "Viola" },
				new String[] { "2-4 years", "<1 year", "<1 year" }, "Female",
				"No preference", "No preference", new String[] { "Mandarin",
						"English" }, "No", new int[] { 650, 1293, 1812, 8272 });
		return testTeacher1;
	}

	@Test
	public void testGetMandatoryPairSiblings() {
		Student student1 = createStudent1();
		student1.getSiblings().add(createStudent2());
		student1.getSiblings().add(createStudent3());
		
		ArrayList<Pair> mandatoryPairs = new ArrayList<>();
		mandatoryPairs.add(new Pair(student1, createTeacher1(), 300, null, ""));
		
		HashSet<Integer> siblings = new HashSet<>();
		siblings.add(456);
		siblings.add(789);
		
		assertEquals(siblings, Randomization.getMandatoryPairSiblings(mandatoryPairs));
		
		mandatoryPairs.clear();
		assertEquals(true, Randomization.getMandatoryPairSiblings(mandatoryPairs).isEmpty());
	}
}
