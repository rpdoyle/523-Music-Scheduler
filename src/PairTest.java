/*
 * File: PairTest.java
 * Description: Implements JUnit tests for Pair.java
 * 	
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
public class PairTest {
	
	// Not sure what to do with the siblings of a student? Does not have anything to do with a Pair...
	@Test
	public void testPair(){
		Student testStudent = new Student(1, "Faith Collins", "21","Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"},new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		Teacher testTeacher = new Teacher(1, "Selena", "Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		Pair testPair = new Pair(testStudent, testTeacher, 120, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
	}
	
	@Test
	public void testGetStudent(){
		Student testStudent = new Student(1, "Faith Collins", "21","Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"},new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		Teacher testTeacher = new Teacher(1, "Selena", "Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		Pair testPair = new Pair(testStudent, testTeacher, 120, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		assertEquals(testStudent, testPair.getStudent());
	}

	@Test
	public void testGetTeacher(){
		Student testStudent = new Student(1, "Faith Collins", "21","Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"},new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		Teacher testTeacher = new Teacher(1, "Selena", "Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		Pair testPair = new Pair(testStudent, testTeacher, 120, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		assertEquals(testTeacher, testPair.getTeacher());
	}
	
	@Test
	public void testGetScore(){
		Student testStudent = new Student(1, "Faith Collins", "21","Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"},new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		Teacher testTeacher = new Teacher(1, "Selena", "Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		Pair testPair = new Pair(testStudent, testTeacher, 120, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		assertEquals(120, testPair.getScore());
	}
	
	@Test
	public void testGetMutualTimes(){
		Student testStudent = new Student(1, "Faith Collins", "21","Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"},new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		Teacher testTeacher = new Teacher(1, "Selena", "Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		Pair testPair = new Pair(testStudent, testTeacher, 120, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		assertEquals(new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), testPair.getMutualTimes());
	}
	
	@Test
	public void testGetInstrument(){
		Student testStudent = new Student(1, "Faith Collins", "21","Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"},new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		Teacher testTeacher = new Teacher(1, "Selena", "Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		Pair testPair = new Pair(testStudent, testTeacher, 120, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");
		assertEquals("piano", testPair.getInstrument());
	}
	
}
