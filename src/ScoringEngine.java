/*
 * File: ScoringEnginge.java
 * Description: This class stores data for a ScoringEngine.
 */

import java.util.ArrayList;

public class ScoringEngine {

	private ArrayList<Student> students;
	private ArrayList<Teacher> teachers;

	public ScoringEngine(ArrayList<Student> students,
			ArrayList<Teacher> teachers) {
		this.students = students;
		this.teachers = teachers;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public ArrayList<Teacher> getTeachers() {
		return teachers;
	}

	// Searches the students and teachers ArrayLists for returning pairs, and
	// returns an ArrayList of Pair objects that encapsulate these mandatory
	// pairings
	public ArrayList<Pair> getMandatoryPairs() {
		ArrayList<Pair> mandatoryPairs = new ArrayList<Pair>();
		ArrayList<Student> returningStudents = new ArrayList<Student>();
		ArrayList<Teacher> returningTeachers = new ArrayList<Teacher>();

		// Get all returning students whose returning instrument is their first
		// choice
		for (int i = 0; i < students.size(); i++) {
			if (!"".equals(students.get(i).getReturningTeacher())
					&& students.get(i).getInstrumentOfReturningStudent()
							.equals(students.get(i).getInstruments()[0])) {
				returningStudents.add(students.remove(i));
				// Decrement the index to account for the lost element
				i--;
			}
		}

		// Get all returning teachers who want to keep their previous students
		for (int i = 0; i < teachers.size(); i++) {
			if (!"".equals(teachers.get(i).getReturningStudent())
					&& "Yes".equals(teachers.get(i).getKeepReturningStudent())) {
				returningTeachers.add(teachers.remove(i));
				i--;
			}
		}

		for (int i = 0; i < returningStudents.size(); i++) {
			for (int j = 0; j < returningTeachers.size(); j++) {
				// If the student's name is the same as the teacher's returning
				// student's name...
				if (returningStudents
						.get(i)
						.getName()
						.equalsIgnoreCase(
								returningTeachers.get(j).getReturningStudent())) {
					// Take advantage of times being in sorted order when
					// checking for mutual times
					ArrayList<Integer> mutualTimes = new ArrayList<Integer>();
					// These are indices into the availableTimes arrays for
					// teachers and students
					int k = 0, l = 0;

					int numStudentTimes = returningStudents.get(i)
							.getAvailableTimes().length;
					int numTeacherTimes = returningTeachers.get(j)
							.getAvailableTimes().length;

					// Keep looping until you have reached the end of either the
					// student's or the teacher's availableTimes array
					while (k < numStudentTimes && l < numTeacherTimes) {
						// If the times are equal, add that time to the
						// mutualTimes ArrayList. Also, increment k and l to
						// check each person's next time
						if (returningStudents.get(i).getAvailableTimes()[k] == returningTeachers
								.get(j).getAvailableTimes()[l]) {
							mutualTimes.add(returningTeachers.get(j)
									.getAvailableTimes()[l]);
							k++;
							l++;
						} else if (returningStudents.get(i).getAvailableTimes()[k] < returningTeachers
								.get(j).getAvailableTimes()[l]) {
							// If the student's time is less than the teacher's
							// time, increment k to find the student's next time
							k++;
						} else {
							// Otherwise, the teacher's time was less than the
							// student's time, in which case, increment l to
							// find the teacher's next time
							l++;
						}
					}

					// If a mutual time was found, create a new Pair object
					// containing the student, the teacher, their score of 2000
					// as a mandatory pair, and their mutual times. Also, add
					// these indices to the returned students and teachers
					// ArrayLists so that they can be removed afterward
					if (mutualTimes.size() > 0) {
						mandatoryPairs.add(new Pair(returningStudents.get(i),
								returningTeachers.get(j), 2000, mutualTimes,
								returningStudents.get(i).getInstruments()[0]));
						returningStudents.remove(i);
						i--;
						returningTeachers.remove(j);
						j--;
						break;
					}
				}
			}
		}

		// If a returning student couldn't be paired with a previous teacher,
		// add them back to the students ArrayList
		for (Student unpairedStudent : returningStudents) {
			students.add(unpairedStudent);
		}

		// If a returning teacher couldn't be paired with a previous student,
		// add them back to the teachers ArrayList
		for (Teacher unpairedTeacher : returningTeachers) {
			teachers.add(unpairedTeacher);
		}

		return mandatoryPairs;
	}

	// Assign a score to every possible Student/Teacher pair (except returning
	// students and teachers who are paired using getMandatoryPairs). This
	// method should be run after getMandatoryPairs since getMandatoryPairs will
	// remove from the students and teachers ArrayLists the students and
	// teachers who are placed into mandatory pairs
	public Pair[][] scoreNonMandatoryPairs() {
		Pair[][] scores = new Pair[teachers.size()][students.size()];
		int score;
		int numStudents = students.size(), numTeachers = teachers.size();
		String returningInstrument = "";

		for (int i = 0; i < numTeachers; i++) {
			for (int j = 0; j < numStudents; j++) {
				score = 0;

				// Take advantage of times being in sorted order when
				// checking for mutual times
				ArrayList<Integer> mutualTimes = new ArrayList<Integer>();
				// These are indices into the availableTimes arrays for
				// teachers and students
				int k = 0, l = 0;

				int numStudentTimes = students.get(j).getAvailableTimes().length;
				int numTeacherTimes = teachers.get(i).getAvailableTimes().length;

				// Keep looping until you have reached the end of either the
				// student's or the teacher's availableTimes array
				while (k < numStudentTimes && l < numTeacherTimes) {
					int studentTime = students.get(j).getAvailableTimes()[k];
					int teacherTime = teachers.get(i).getAvailableTimes()[l];

					// If the times are equal, add that time to the
					// mutualTimes ArrayList. Also, increment k and l to
					// check each person's next time
					if (studentTime == teacherTime) {
						mutualTimes.add(studentTime);
						k++;
						l++;
					} else if (studentTime < teacherTime) {
						// If the student's time was less than the teacher's
						// time, increment k to find the student's next time
						k++;
					} else {
						// Otherwise, the teacher's time was less than the
						// student's time, in which case, increment l to
						// find the teacher's next time
						l++;
					}
				}

				// If a mutual time was not found, then this pair is
				// incompatible. Give them a score of -1
				if (mutualTimes.size() == 0) {
					scores[i][j] = new Pair(students.get(j), teachers.get(i),
							-1, null, "");
					continue;
				}

				// If the student is advanced, then only check their first
				// instrument preference against the teacher
				if ("4+ years (advanced)".equals(students.get(j)
						.getInstrumentYears())) {
					// Look at each instrument that this teacher can teach. If
					// there is a match, add 300 to the score and stop
					// searching.
					for (String instrument : teachers.get(i).getInstruments()) {
						if (students.get(j).getInstruments()[0]
								.equalsIgnoreCase(instrument)) {
							score += 300;
							returningInstrument = students.get(j)
									.getInstruments()[0];
							break;
						}
					}

					// If the score isn't 300, then an instrument match wasn't
					// found, and this pair won't work.
					if (score != 300) {
						scores[i][j] = new Pair(students.get(j),
								teachers.get(i), -1, null, "");
						continue;
					}
				} else { // If the student is not advanced, check all instrument
							// preferences for a match. Check first preference
							// here.
					for (String instrument : teachers.get(i).getInstruments()) {
						if (students.get(j).getInstruments()[0]
								.equalsIgnoreCase(instrument)) {
							score += 300;
							returningInstrument = students.get(j)
									.getInstruments()[0];
							break;
						}
					}

					// If no match was found for the first instrument
					// preference, check the second preference
					if (score == 0) {
						for (String instrument : teachers.get(i)
								.getInstruments()) {
							if (students.get(j).getInstruments()[1]
									.equalsIgnoreCase(instrument)) {
								score += 200;
								returningInstrument = students.get(j)
										.getInstruments()[1];
								break;
							}
						}
					}

					// If no match was found for the first or second instrument
					// preference, check the third preference
					if (score == 0) {
						for (String instrument : teachers.get(i)
								.getInstruments()) {
							if (students.get(j).getInstruments()[2]
									.equalsIgnoreCase(instrument)) {
								score += 100;
								returningInstrument = students.get(j)
										.getInstruments()[2];
								break;
							}
						}
					}

					// If score is still zero, no instruments matched, so this
					// pair won't work
					if (score == 0) {
						scores[i][j] = new Pair(students.get(j),
								teachers.get(i), -1, null, "");
						continue;
					}
				}

				// If the student can speak English, then add 10 points for
				// language
				if ("".equals(students.get(j).getLanguage())) {
					score += 10;
				} else { // If the student doesn't speak English, look for
							// common languages
					for (String language : teachers.get(i).getLanguages()) {
						if (language.equals(students.get(j).getLanguage())) {
							score += 10;
							break;
						}
					}
				}

				// If no gender preference for the teacher or student's gender
				// matches teacher's preference, +5
				if ("No preference".equals(teachers.get(i)
						.getGenderPreference())
						|| students.get(j).getGender()
								.equals(teachers.get(i).getGenderPreference())) {
					score += 5;
				}

				// If teacher's preferred student level matches the student's
				// level, +1
				if (("0-2 years (beginner)".equals(students.get(j)
						.getInstrumentYears()) && "Beginner".equals(teachers
						.get(i).getLevelPreference()))
						|| (("2-4 years (intermediate)".equals(students.get(j)
								.getInstrumentYears()) || "4+ years (advanced)"
								.equals(students.get(j).getInstrumentYears())) && "Non-beginner"
								.equals(teachers.get(i).getLevelPreference()))
						|| "No preference".equals(teachers.get(i)
								.getLevelPreference())) {
					score += 1;
				}

				// If the student's age matches the teacher's preference, +1
				int age = Integer.parseInt(students.get(j).getAge());
				if (("Younger than 10-years-old".equals(teachers.get(i)
						.getAgePreference()) && age < 10)
						|| ("Ten or more years old".equals(teachers.get(i)
								.getAgePreference()) && age >= 10)
						|| "No preference".equals(teachers.get(i)
								.getAgePreference())) {
					score += 1;
				}

				scores[i][j] = new Pair(students.get(j), teachers.get(i),
						score, mutualTimes, returningInstrument);
			}
		}

		return scores;
	}
}
