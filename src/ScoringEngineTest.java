import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ScoringEngineTest {

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
		Teacher testTeacher1 = new Teacher(123, "Peter", "Thomas", "Jane",
				"Guitar", "Yes", new String[] { "Guitar", "Bass", "Viola" },
				new String[] { "2-4 years", "<1 year", "<1 year" }, "Female",
				"No preference", "No preference", new String[] { "Mandarin",
						"English" }, "No", new int[] { 650, 1293, 1812, 8272 });
		return testTeacher1;
	}

	private Teacher createTeacher2() {
		Teacher testTeacher2 = new Teacher(456, "Kim", "Lewis", "", "", "",
				new String[] { "Guitar", "Trumpet" }, new String[] {
						"2-4 years", "<1 year", "<1 year" }, "Male",
				"No preference", "No preference", new String[] { "Mandarin",
						"English" }, "No", new int[] { 500, 1293, 1812, 8272 });
		return testTeacher2;
	}

	private Teacher createTeacher3() {
		Teacher testTeacher3 = new Teacher(789, "Alex", "Parker", "Tori",
				"Piano", "Yes", new String[] { "Piano", "Flute" },
				new String[] { "2-4 years", "2-4 years" }, "Female",
				"Younger than 10-years-old", "No preference",
				new String[] { "English" }, "No", new int[] { 150, 1489 });
		return testTeacher3;
	}

	private ArrayList<Student> createStudents() {
		Student student1 = createStudent1();
		Student student2 = createStudent2();
		Student student3 = createStudent3();
		ArrayList<Student> students = new ArrayList<>();
		students.add(student1);
		students.add(student2);
		students.add(student3);
		return students;
	}

	private ArrayList<Teacher> createTeachers() {
		Teacher teacher1 = createTeacher1();
		Teacher teacher2 = createTeacher2();
		Teacher teacher3 = createTeacher3();
		ArrayList<Teacher> teachers = new ArrayList<>();
		teachers.add(teacher1);
		teachers.add(teacher2);
		teachers.add(teacher3);
		return teachers;
	}

	private ScoringEngine createScoringEngine() {
		ArrayList<Student> students = createStudents();
		ArrayList<Teacher> teachers = createTeachers();
		ScoringEngine scoringEngine = new ScoringEngine(students, teachers);
		return scoringEngine;
	}

	@Test
	public void testGetMandatoryPairs() {
		ScoringEngine scoringEngine = createScoringEngine();
		ArrayList<Pair> mandatoryPairs = new ArrayList<>();
		ArrayList<Integer> mutualTimes = new ArrayList<>();
		mutualTimes.add(650);
		mutualTimes.add(1812);
		mandatoryPairs.add(new Pair(createStudent2(), createTeacher1(), 2000,
				mutualTimes));
		PairComparator pc = new PairComparator();
		assertEquals(pc.compareLists(scoringEngine.getMandatoryPairs(),
				mandatoryPairs), 0);
	}

	@Test
	public void testScoreNonMandatoryPairs() {
		ScoringEngine scoringEngine = createScoringEngine();
		scoringEngine.getMandatoryPairs();

		// Mutual times for [0][0]
		ArrayList<Integer> mutualTimes1 = new ArrayList<>();
		mutualTimes1.add(500);
		// Mutual times for [0][1]
		ArrayList<Integer> mutualTimes2 = new ArrayList<>();
		// Mutual times for [1][0]
		ArrayList<Integer> mutualTimes3 = new ArrayList<>();
		// Mutual times for [1][1]
		ArrayList<Integer> mutualTimes4 = new ArrayList<>();
		mutualTimes4.add(150);
		// 00 is 317
		// 01 is -1
		// 10 is -1
		// 11 is 311
		Pair[][] pairs = {
				{
						new Pair(createStudent1(), createTeacher2(), 317,
								mutualTimes1),
						new Pair(createStudent3(), createTeacher2(), -1,
								mutualTimes2) },
				{
						new Pair(createStudent1(), createTeacher3(), -1,
								mutualTimes3),
						new Pair(createStudent3(), createTeacher3(), 311,
								mutualTimes4) } };
		PairComparator pc = new PairComparator();
		assertEquals(
				pc.compareArrays(scoringEngine.scoreNonMandatoryPairs(), pairs),
				0);
	}
}
