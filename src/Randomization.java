import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Randomization {

	// This causes a timeout if we haven't generated a better schedule yet
	public static final int IMPROVEMENTTIMEOUT = 500;
	// This causes a timeout if we haven't found a new pair yet
	public static final int NEWPAIRTIMEOUT = 500;
	// This determines how many pairs we will pass to Hunagarian. We want to
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

	public HungarianResult schedule() {
		int maxScore = 0, timeSinceImprovement = 0, timeSinceNewPair, sumScores = 0, count = 0;
		// This is used to test whether or not we should call Hungarian
		boolean siblingFound = false, feasiblePairs = true;
		HungarianResult currentResult, bestResult = null;

		// These arraylists contain valid indices into the student and teacher
		// arraylists. In other words, the students and teachers at these
		// indices are yet to be paired
		ArrayList<Integer> studentIndices = new ArrayList<>();
		ArrayList<Integer> teacherIndices = new ArrayList<>();

		int numStudents = students.size();
		int numTeachers = teachers.size();

		ArrayList<Pair> possiblePairs = new ArrayList<>();
		Set<Integer> siblings = new HashSet<>();

		int numRoomDayTimes = HelperMethods.getRoomDayTimes(rooms).size();

		Random randS = new Random();
		Random randT = new Random();

		// Continue generating new possible schedules until you have made a
		// schedule that scores sufficiently, or you haven't improved for a
		// certain period of time

		while (maxScore < getSufficientScore(sumScores, count) && count < 100
				&& timeSinceImprovement < IMPROVEMENTTIMEOUT) {
			possiblePairs.clear();
			siblings.clear();

			// Recreate the arraylists containing the indices into the student
			// and teacher arraylists
			for (int i = 0; i < numStudents; i++) {
				studentIndices.add(i);
			}
			for (int i = 0; i < numTeachers; i++) {
				teacherIndices.add(i);
			}

			// Check whether any mandatory pair students have siblings. If so,
			// add them to the siblings arraylist for future checking
			for (Pair mandatoryPair : mandatoryPairs) {
				if (mandatoryPair.getStudent().getSiblings().size() > 0) {
					for (Student sibling : mandatoryPair.getStudent()
							.getSiblings()) {
						siblings.add(sibling.getID());
					}
				}
			}
			// Add the mandatory pairs to the possible pairs arraylist for
			// scheduling
			possiblePairs.addAll(mandatoryPairs);

			timeSinceNewPair = 0;

			// Keep generating new pairs until your possiblePairs arraylist is
			// too big, or you haven't found a new pair within a certain period
			// of time, or there are no teachers or no students left to pair
			while (possiblePairs.size() < (numRoomDayTimes * EXTRAPAIRSMULTIPLIER)
					&& timeSinceNewPair < NEWPAIRTIMEOUT
					&& studentIndices.size() > 0 && teacherIndices.size() > 0) {
				// Get random pointers into the students and teachers arraylists
				int randStudentIndex = studentIndices.get(randS
						.nextInt(studentIndices.size()));
				int randTeacherIndex = teacherIndices.get(randT
						.nextInt(teacherIndices.size()));

				// If their score in greater than zero, add them!
				if (scores[randTeacherIndex][randStudentIndex].getScore() > 0) {
					possiblePairs
							.add(scores[randTeacherIndex][randStudentIndex]);
					// If the student has siblings, add them to the siblings
					// arraylist
					if (students.get(randStudentIndex).getSiblings().size() > 0) {
						for (Student sibling : students.get(randStudentIndex)
								.getSiblings()) {
							siblings.add(sibling.getID());
						}
					}
					studentIndices.remove(randStudentIndex);
					teacherIndices.remove(randTeacherIndex);
					timeSinceNewPair = 0;
				} else {
					// If the pair didn't work, increment our timeout test value
					timeSinceNewPair++;
				}
			}
			
			feasiblePairs = true;
			siblingFound = false;

			// Check whether the siblings of scheduled students were also
			// scheduled
			for (Integer siblingID : siblings) {
				for (Pair siblingPair : possiblePairs) {
					if (siblingPair.getStudent().getID() == siblingID) {
						siblingFound = true;
						break;
					}
				}
				if (!siblingFound) {
					feasiblePairs = false;
					break;
				}
			}

			// If a sibling wasn't scheduled, then don't even bother calling
			// Hungarian on this one
			if (!feasiblePairs) {
				continue;
			}

			currentResult = Hungarian.run(possiblePairs,
					HelperMethods.getRoomDayTimes(rooms),
					HelperMethods.getAllPossibleSpecialInstruments(rooms));

			// Check whether the generated schedule was invalid
			if (currentResult == null) {
				timeSinceImprovement++;
				continue;
			}

			sumScores += currentResult.getScore();
			count++;

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

	public int getSufficientScore(int sumScores, int count) {
		int avgScore = sumScores / count;
		return 3 * avgScore / 2;
	}
}
