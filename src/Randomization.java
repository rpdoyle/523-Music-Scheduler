import java.util.ArrayList;
import java.util.Random;

public class Randomization {

	// This is the score that we deem "good enough"
	public static final int SUFFICIENTSCORE = 10000;
	// This causes a timeout if we haven't generated a better schedule yet
	public static final int MAXTIMESINCEIMPROVEMENT = 500;
	// This causes a timeout if we haven't found a new pair yet
	public static final int MAXTIMESINCENEWPAIR = 500;
	// This is the number of pairs in addition to the number of RoomDayTimes
	// that we will pass to the Hungarian algorithm
	public static final int NUMEXTRAPAIRS = 20;

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

	public ArrayList<Pair> getMandatoryPairs() {
		return mandatoryPairs;
	}

	public Pair[][] getScores() {
		return scores;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public HungarianResult schedule() {
		int maxScore = 0, timeSinceImprovement = 0, timeSinceNewPair, numScheduledStudents, numScheduledTeachers;
		// This is used to test whether or not we should call Hungarian
		boolean feasiblePairs = true;
		HungarianResult currentResult, bestResult = null;

		int numStudents = students.size();
		int numTeachers = teachers.size();

		ArrayList<Pair> possiblePairs = new ArrayList<>();
		ArrayList<Student> siblings = new ArrayList<>();
		ArrayList<Teacher> tmpTeachers = new ArrayList<>();
		ArrayList<Student> tmpStudents = new ArrayList<>();

		int numRoomDayTimes = HelperMethods.getRoomDayTimes(rooms).size();

		Random randS = new Random();
		Random randT = new Random();

		// TODO: what if we continue making awesome schedules and this never
		// terminates?

		// Continue generating new possible schedules until you have made a
		// schedule that scores sufficiently, or you haven't improved for a
		// certain period of time
		while (maxScore < SUFFICIENTSCORE
				&& timeSinceImprovement < MAXTIMESINCEIMPROVEMENT) {
			possiblePairs.clear();
			siblings.clear();

			// Check whether any mandatory pair students have siblings. If so,
			// add them to the siblings arraylist for future checking
			for (Pair mandatoryPair : mandatoryPairs) {
				if (mandatoryPair.getStudent().getSiblings().size() > 0) {
					for (Student sibling : mandatoryPair.getStudent()
							.getSiblings()) {
						siblings.add(sibling);
					}
				}
			}
			// Add the mandatory pairs to the possible pairs arraylist for
			// scheduling
			possiblePairs.addAll(mandatoryPairs);

			// Make a copy of the teachers arraylist since we will be
			// manipulating it
			tmpTeachers.clear();
			for (Teacher teacher : teachers) {
				tmpTeachers.add(teacher);
			}
			// Make a copy of the students arraylist since we will be
			// manipulating it
			tmpStudents.clear();
			for (Student student : students) {
				tmpStudents.add(student);
			}

			timeSinceNewPair = 0;
			numScheduledStudents = 0;
			numScheduledTeachers = 0;

			// Keep generating new pairs until your possiblePairs arraylist is
			// too big, or you haven't found a new pair within a certain period
			// of time, or there are no teachers or no students left to pair
			while (possiblePairs.size() < (numRoomDayTimes + NUMEXTRAPAIRS)
					&& timeSinceNewPair < MAXTIMESINCENEWPAIR
					&& numScheduledTeachers < numTeachers
					&& numScheduledStudents < numStudents) {
				feasiblePairs = true;

				// Get random pointers into the students and teachers arraylists
				int studentIndex = randS.nextInt() * numStudents;
				int teacherIndex = randT.nextInt() * numTeachers;

				// Check whether this student or this teacher have been
				// scheduled already
				if (tmpStudents.get(studentIndex) == null
						|| tmpTeachers.get(teacherIndex) == null) {
					timeSinceNewPair++;
					continue;
				}

				// If their score in greater than zero, add them!
				if (scores[teacherIndex][studentIndex].getScore() > 0) {
					possiblePairs.add(scores[teacherIndex][studentIndex]);
					// If the student has siblings, add them to the siblings
					// arraylist
					if (tmpStudents.get(studentIndex).getSiblings().size() > 0) {
						for (Student sibling : tmpStudents.get(studentIndex)
								.getSiblings()) {
							siblings.add(sibling);
						}
					}
					// Set this student and this teacher to nul to indicate that
					// they are scheduled. We can't just remove them because
					// that throws off the indices
					tmpTeachers.set(teacherIndex, null);
					tmpStudents.set(studentIndex, null);
					numScheduledStudents++;
					numScheduledTeachers++;
					timeSinceNewPair = 0;
				} else {
					// If the pair didn't work, increment our timeout test value
					timeSinceNewPair++;
				}
			}

			// Check whether the siblings of scheduled students were also scheduled
			for (Student sibling : siblings) {
				for (Pair siblingPair : possiblePairs) {
					if (siblingPair.getStudent().getID() == sibling.getID()) {
						break;
					}
				}
				feasiblePairs = false;
				break;
			}

			// If a sibling wasn't scheduled, then don't even bother calling Hungarian on this one
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
}
