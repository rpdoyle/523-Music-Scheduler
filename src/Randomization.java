import java.util.ArrayList;
import java.util.Random;

public class Randomization {

	public static final int SUFFICIENTSCORE = 10000;
	public static final int MAXTIMESINCEIMPROVEMENT = 500;
	public static final int MAXTIMESINCENEWPAIR = 500;
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
		int maxScore = 0, timeSinceImprovement = 0, timeSinceNewPair;
		boolean siblingFound = false, feasiblePairs = true;
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
		while (maxScore < SUFFICIENTSCORE
				&& timeSinceImprovement < MAXTIMESINCEIMPROVEMENT) {
			possiblePairs.clear();
			siblings.clear();
			for (Pair mandatoryPair : mandatoryPairs) {
				if (mandatoryPair.getStudent().getSiblings().size() > 0) {
					for (Student sibling : mandatoryPair.getStudent().getSiblings()) {
						siblings.add(sibling);
					}
				}
			}
			possiblePairs.addAll(mandatoryPairs);
			
			tmpTeachers.clear();
			for (Teacher teacher : teachers) {
				tmpTeachers.add(teacher);
			}
			tmpStudents.clear();
			for (Student student : students) {
				tmpStudents.add(student);
			}

			timeSinceNewPair = 0;

			while (possiblePairs.size() < (numRoomDayTimes + NUMEXTRAPAIRS)
					&& timeSinceNewPair < MAXTIMESINCENEWPAIR
					&& tmpStudents.size() > 0 && tmpTeachers.size() > 0) {
				siblingFound = false;
				feasiblePairs = true;

				int studentIndex = randS.nextInt() * numStudents;
				int teacherIndex = randT.nextInt() * numTeachers;
				
				if (tmpStudents.get(studentIndex) == null || tmpTeachers.get(teacherIndex) == null) {
					timeSinceNewPair++;
					continue;
				}

				if (scores[teacherIndex][studentIndex].getScore() > 0) {
					possiblePairs.add(scores[teacherIndex][studentIndex]);
					if (tmpStudents.get(studentIndex).getSiblings().size() > 0) {
						for (Student sibling : tmpStudents.get(studentIndex)
								.getSiblings()) {
							siblings.add(sibling);
						}
					}
					tmpTeachers.set(teacherIndex, null);
					tmpStudents.set(studentIndex, null);
					timeSinceNewPair = 0;
				} else {
					timeSinceNewPair++;
				}
			}

			for (Student sibling : siblings) {
				for (Pair siblingPair : possiblePairs) {
					if (siblingPair.getStudent().getID() == sibling.getID()) {
						siblingFound = true;
						break;
					}
				}
				feasiblePairs = false;
				break;
			}

			if (!feasiblePairs) {
				continue;
			}

			currentResult = Hungarian.run(possiblePairs,
					HelperMethods.getRoomDayTimes(rooms),
					HelperMethods.getAllPossibleSpecialInstruments(rooms));

			if (currentResult == null) {
				timeSinceImprovement++;
				continue;
			}

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
