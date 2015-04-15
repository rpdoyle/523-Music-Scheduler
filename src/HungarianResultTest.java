/*
 * File: HungarianResultTest.java
 * Description: Implements JUnit tests for Room.java
 * 	
 */

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class HungarianResultTest {

	public void testGetPairTimes(){
		Student testStudent = new Student(1, "Faith Collins", "21","Female", "Selena", "Piano", new String[] {"Piano", "Violin", "Trumpet"},new String[] {"0-2", "0-2", "0-2"}, "spanish", new int[] {500, 1670, 4309} );
		Teacher testTeacher = new Teacher(1, "Selena", "Gomez", "Faith Collins", "Piano", "Yes", new String[] {"Piano"}, new String[] {"12 years"}, "female", "10+", "beginner", new String[] {"spanish"}, "no", new int[] {500, 1670, 4309} );
		Pair testPair = new Pair(testStudent, testTeacher, 120, new ArrayList<Integer>(Arrays.asList(500, 1670, 4309)), "piano");

	}
}
