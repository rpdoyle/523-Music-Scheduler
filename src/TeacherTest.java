/*
 * File: TeacherTest.java
 * Description: Implements JUnit tests for Teacher.java
 * 	
 */

import static org.junit.Assert.*;

import org.junit.Test;

//This class implements tests for the methods in TeacherTest.java
public class TeacherTest {

	private Teacher createTestTeacher() {
		int id = 829;
		String firstName = "Test";
		String lastName = "Teacher";
		String returningStudent = "Fred Cook";
		String returningInstrument = "Flute";
		String keepReturningStudent = "Yes";
		String[] instruments = new String[] {"Flute", "Bass", "Viola"};
		String[] instrumentExperience = new String[] {"2-4 years", "<1 year", "<1 year"};
		String genderPreference = "Female";
		String agePreference = "5-10";
		String levelPreference = "Beginner";
		String[] language = new String[] {"Mandarin", "English"};
		String crimeRecord = "No";
		int[] availableTimes = new int[] {828, 1293, 9932, 8272};
		
		return new Teacher(id, firstName, lastName, returningStudent, returningInstrument, keepReturningStudent, instruments, instrumentExperience,
						genderPreference, agePreference, levelPreference, language, crimeRecord, availableTimes);				
	}
	
	@Test
	public void testTeacher() {
		createTestTeacher();
	}

	@Test
	public void testGetId() {
		Teacher testTeacher = createTestTeacher();
		assertEquals(829, testTeacher.getId());
	}

	@Test
	public void testGetFirstName() {
		Teacher testTeacher = createTestTeacher();
		assertEquals("Test", testTeacher.getFirstName());
	}

	@Test
	public void testGetLastName() {
		Teacher testTeacher = createTestTeacher();
		assertEquals("Teacher", testTeacher.getLastName());
	}

	@Test
	public void testGetReturningStudent() {
		Teacher testTeacher = createTestTeacher();
		assertEquals("Fred Cook", testTeacher.getReturningStudent());
	}

	@Test
	public void testGetReturningInstrument() {
		Teacher testTeacher = createTestTeacher();
		assertEquals("Flute", testTeacher.getReturningInstrument());
	}

	@Test
	public void testGetKeepReturningStudent() {
		Teacher testTeacher = createTestTeacher();
		assertEquals("Yes", testTeacher.getKeepReturningStudent());
	}

	@Test
	public void testGetInstruments() {
		Teacher testTeacher = createTestTeacher();
		assertArrayEquals(new String[] {"Flute", "Bass", "Viola"}, testTeacher.getInstruments());
	}

	@Test
	public void testGetInstrumentExperience() {
		Teacher testTeacher = createTestTeacher();
		assertArrayEquals(new String[] {"2-4 years", "<1 year", "<1 year"}, testTeacher.getInstrumentExperience());
	}

	@Test
	public void testGetGenderPreference() {
		Teacher testTeacher = createTestTeacher();
		assertEquals("Female", testTeacher.getGenderPreference());
	}

	@Test
	public void testGetAgePreference() {
		Teacher testTeacher = createTestTeacher();
		assertEquals("5-10", testTeacher.getAgePreference());
	}

	@Test
	public void testGetLevelPreference() {
		Teacher testTeacher = createTestTeacher();
		assertEquals("Beginner", testTeacher.getLevelPreference());
	}

	@Test
	public void testGetLanguage() {
		Teacher testTeacher = createTestTeacher();
		assertArrayEquals(new String[] {"Mandarin", "English"}, testTeacher.getLanguage());
	}

	@Test
	public void testGetCrimeRecord() {
		Teacher testTeacher = createTestTeacher();
		assertEquals("No", testTeacher.getCrimeRecord());
	}

	@Test
	public void testGetAvailableTimes() {
		Teacher testTeacher = createTestTeacher();
		assertArrayEquals(new int[] {828, 1293, 9932, 8272}, testTeacher.getAvailableTimes());
	}

}
