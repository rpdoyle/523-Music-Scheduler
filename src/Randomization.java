/* 
 * File: Randomization.java
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Randomization is the class that is ultimately used for scheduling music
 * lessons. This class will be used for two particular purposes. First,
 * Randomization will generate groups of Student object/Teacher objects pairs
 * (aka Pair objects). These pairs will be used for generating music lesson
 * schedules. The Hungarian algorithm, implemented in another class, will take
 * these groupings and create a schedule consisting of some or all of those
 * partnerships.
 * 
 * Secondly, Randomization will call the Hungarian algorithm and check the
 * schedule that is returned. Randomization will monitor the score of each
 * generated schedule. It will ensure that the highest scoring schedule is the
 * one selected and outputted.
 *
 */
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

	/**
	 * The constructor for a Randomization object.
	 * 
	 * @param mandatoryPairs
	 *            student/teacher pairs that must be scheduled
	 * @param scores
	 *            the scores of each student/teacher pair
	 * @param rooms
	 *            the rooms available for lessons
	 * @param students
	 *            the students who are not in mandatory pairs
	 * @param teachers
	 *            the teachers who are not in mandatory pairs
	 */
	public Randomization(ArrayList<Pair> mandatoryPairs, Pair[][] scores,
			ArrayList<Room> rooms, ArrayList<Student> students,
			ArrayList<Teacher> teachers) {
		this.mandatoryPairs = mandatoryPairs;
		this.scores = scores;
		this.rooms = rooms;
		this.students = students;
		this.teachers = teachers;
	}

	/**
	 * Generates the best possible schedule. This method will create an
	 * ArrayList of Pair objects that will be the basis for schedule creation.
	 * 
	 * First, this method will ensure that all mandatory pairs are in this
	 * ArrayList. Next, it will randomly choose Student/Teacher Pairs to be
	 * added to this list. If the Pair has a non-zero score (given by the
	 * ScoringEngine), then the Pair will be added. This process will continue
	 * until either a new Pair has not been found within a certain number of
	 * iterations, there are no more possible Pairs to be added, or the number
	 * of Pairs is greater than some multiple of the number of RoomDayTimes.
	 * 
	 * The Hungarian algorithm will be executed on this list. When that returns,
	 * this method tracks the highest scoring schedule generated by the
	 * Hungarian algorithm. This generation and testing process will continue
	 * until an improvement has not been seen within a certain number of
	 * iterations.
	 * 
	 * @return The highest scoring schedule
	 */
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

	/**
	 * Iterates through each student that must be paired and adds their
	 * siblings' IDs (if any) to a HashSet. This method views all the mandatory
	 * pairs and checks whether any of the students have siblings. If so, the
	 * IDs of those siblings are added to a HashSet (to avoid duplicates).
	 * Siblings must be scheduled together, so this HashSet helps check this
	 * eventually.
	 * 
	 * @param mandatoryPairs
	 *            the Pairs that must be scheduled
	 * @return HashSet of mandatory pair students' siblings' IDs
	 */
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
