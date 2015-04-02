/*
 * File: StudentTest.java
 * Description: Implements JUnit tests for Student.java
 * 	
 */

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

//This class implements tests for the methods in Student.java
public class StudentTest {
	
	private Student createTestStudent() {
		int id = 123;
		String name = "Test Name";
		String age = "31";
		String gender = "Male";
		String returningTeacher = "Some Teacher";
		String instrumentOfReturningStudent = "Guitar";
		String[] instruments = new String[] {"Guitar, Flute, Violin"};
		String[] instrumentYears = new String[] {"Less than 1 year", "More than 4 years"};
		String language = "English";
		int[] availableTimes = new int[] {500, 1247, 5383};
		
		return new Student (id, name, age, gender, returningTeacher, instrumentOfReturningStudent, instruments, instrumentYears, language, availableTimes);
	}

	@Test
	public void testStudent() {
		createTestStudent();
	}

	@Test
	public void testGetID() {
		Student testStudent = createTestStudent();
		assertEquals(123, testStudent.getID());
	}

	@Test
	public void testGetName() {
		Student testStudent = createTestStudent();
		assertEquals("Test Name", testStudent.getName());
	}

	@Test
	public void testGetAge() {
		Student testStudent = createTestStudent();
		assertEquals("31", testStudent.getAge());
	}

	@Test
	public void testGetGender() {
		Student testStudent = createTestStudent();
		assertEquals("Male", testStudent.getGender());
	}

	@Test
	public void testGetReturningTeacher() {
		Student testStudent = createTestStudent();
		assertEquals("Some Teacher", testStudent.getReturningTeacher());
	}

	@Test
	public void testGetInstrumentOfReturningStudent() {
		Student testStudent = createTestStudent();
		assertEquals("Guitar", testStudent.getInstrumentOfReturningStudent());
	}

	@Test
	public void testGetInstruments() {
		Student testStudent = createTestStudent();
		assertArrayEquals(new String[] {"Guitar, Flute, Violin"}, testStudent.getInstruments());
	}

	@Test
	public void testGetInstrumentYears() {
		Student testStudent = createTestStudent();
		assertArrayEquals(new String[] {"Less than 1 year", "More than 4 years"}, testStudent.getInstrumentYears());
	}

	@Test
	public void testGetLanguage() {
		Student testStudent = createTestStudent();
		assertEquals("English", testStudent.getLanguage());
	}

	@Test
	public void testGetAvailableTimes() {
		Student testStudent = createTestStudent();
		assertArrayEquals(new int[] {500, 1247, 5383}, testStudent.getAvailableTimes());
	}

	@Test
	public void testGetSiblings() {
		Student testStudent = createTestStudent();
		assertArrayEquals(new ArrayList<Student>().toArray(), testStudent.getSiblings().toArray());
		
		Student sibling1 = createTestStudent();
		Student sibling2 = createTestStudent();
		
		testStudent.getSiblings().add(sibling1);
		testStudent.getSiblings().add(sibling2);
		
		ArrayList<Student> siblings = new ArrayList<Student>();
		siblings.add(sibling1);
		siblings.add(sibling2);
		
		assertArrayEquals(siblings.toArray(), testStudent.getSiblings().toArray());
	}

}
