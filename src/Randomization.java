/* 
 * File: Randomization.java
 * Description: This class randomly generates Student/Teacher pairs to be
 * 				scheduled by the Hungarian algorithm.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Randomization {

	// This causes a timeout if we haven't generated a better schedule yet
	public static final int IMPROVEMENTTIMEOUT = 100;
	// This causes a timeout if we haven't found a new pair yet
	public static final int NEWPAIRTIMEOUT = 100;
	// This determines how many pairs we will pass to Hungarian. We want to
	// pass more than the number of RoomDayTimes
	public static final double EXTRAPAIRSMULTIPLIER = 1.5;

	private ArrayList<Pair> mandatoryPairs;
	private Pair[][] scores;
	private ArrayList<Room> rooms;
	private ArrayList<Student> students;
	private ArrayList<Teacher> teachers;
	
	public Randomization(ArrayList<Pair> mandatoryPairs, Pair[][] scores,
			ArrayList<Room> rooms, ArrayList<Student> students,
			ArrayList<Teacher> teachers) {
		this.mandatoryPairs = mandatoryPairs;
		this.scores = scores;
		this.rooms = rooms;
		this.students = students;
		this.teachers = teachers;
	}

	// Generate random groups of Pair objects that constitute a possible
	// schedule. Pass an ArrayList containing these Pair objects to the
	// Hungarian algorithm for the generation of a schedule. Maintain the max
	// score to determine the best schedule. This selection of additional Pair
	// objects for scheduling and the generation of new schedules is monitored
	// by a timeout. If new Pair objects are not acquired or better schedules
	// aren't generated, then the loop will break.
	public HungarianResult schedule() {
		int maxScore = 0, timeSinceImprovement = 0, timeSinceNewPair;
		// This is used to test whether or not we should call Hungarian
		// boolean feasibleSchedule = true;
		HungarianResult currentResult, bestResult = null;

		// These ArrayLists contain valid indices into the student and teacher
		// ArrayLists. In other words, the students and teachers at these
		// indices are yet to be paired
		ArrayList<Integer> studentIndices = new ArrayList<>();
		ArrayList<Integer> teacherIndices = new ArrayList<>();

		int numStudents = students.size();
		int numTeachers = teachers.size();

		ArrayList<Pair> possiblePairs = new ArrayList<>();
		
		int numRoomDayTimes = HelperMethods.getRoomDayTimes(rooms).size();

		Random randS = new Random();
		Random randT = new Random();

		int randStudentIndex, randTeacherIndex, randStudentIndexIndex, randTeacherIndexIndex;

		// Continue generating new possible schedules until you have made a
		// schedule that scores sufficiently, or you haven't improved for a
		// certain period of time

		while (timeSinceImprovement < IMPROVEMENTTIMEOUT) {
			System.out.println("Time Since Improvement:   "
					+ timeSinceImprovement);
			possiblePairs.clear();
			studentIndices.clear();
			teacherIndices.clear();

			// Recreate the ArrayLists containing the indices into the student
			// and teacher ArrayLists
			for (int i = 0; i < numStudents; i++) {
				studentIndices.add(i);
			}
			for (int i = 0; i < numTeachers; i++) {
				teacherIndices.add(i);
			}

			// Add the mandatory pairs to the possible pairs ArrayList for
			// scheduling
			possiblePairs.addAll(mandatoryPairs);

			timeSinceNewPair = 0;

			// Keep generating new pairs until your possiblePairs ArrayList is
			// too big, or you haven't found a new pair within a certain period
			// of time, or there are no teachers or no students left to pair
			while (possiblePairs.size() < (numRoomDayTimes * EXTRAPAIRSMULTIPLIER)
					&& timeSinceNewPair < NEWPAIRTIMEOUT
					&& studentIndices.size() > 0 && teacherIndices.size() > 0) {
				// Get random pointers into the students and teachers ArrayLists
				randStudentIndexIndex = randS.nextInt(studentIndices.size());
				randTeacherIndexIndex = randT.nextInt(teacherIndices.size());
				randStudentIndex = studentIndices.get(randStudentIndexIndex);
				randTeacherIndex = teacherIndices.get(randTeacherIndexIndex);

				// If their score in greater than zero, add them!
				if (scores[randTeacherIndex][randStudentIndex].getScore() > 0) {
					possiblePairs
							.add(scores[randTeacherIndex][randStudentIndex]);
					studentIndices.remove(randStudentIndexIndex);
					teacherIndices.remove(randTeacherIndexIndex);
					timeSinceNewPair = 0;
				} else {
					// If the pair didn't work, increment our timeout test value
					timeSinceNewPair++;
				}
			}

			currentResult = Hungarian.run(possiblePairs,
					HelperMethods.getRoomDayTimes(rooms),
					HelperMethods.getAllPossibleSpecialInstruments(rooms));

			// Check whether the generated schedule was invalid
			if (currentResult == null) {
				timeSinceImprovement++;
				continue;
			}

			// If this latest result beats all our past results, save it
			if (currentResult.getScore() > maxScore) {
				maxScore = currentResult.getScore();
				bestResult = currentResult;
			} else {
				timeSinceImprovement++;
			}
		}

		return bestResult;
	}

	// Check whether any mandatory pair students have siblings. If so,
	// add them to the siblings ArrayList for future checking
	public static HashSet<Integer> getMandatoryPairSiblings(
			ArrayList<Pair> mandatoryPairs) {
		

		HashSet<Integer> siblings = new HashSet<>();

		for (Pair mandatoryPair : mandatoryPairs) {
			if (mandatoryPair.getStudent().getSiblings().size() > 0) {
				for (Student sibling : mandatoryPair.getStudent().getSiblings()) {
					siblings.add(sibling.getID());
				}
			}
		}
		return siblings;
	}
}
