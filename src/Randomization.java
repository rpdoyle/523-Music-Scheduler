import java.util.ArrayList;
import java.util.Random;


public class Randomization {

	public static final int SUFFICIENTSCORE = 10000;
	public static final int MAXTIMESINCEIMPROVEMENT = 500;
	public static final int MAXTIMESINCENEWPAIR = 500;
	
	private ArrayList<Pair> mandatoryPairs;
	private Pair[][] scores;
	private ArrayList<Room> rooms;
	
	public Randomization(ArrayList<Pair> mandatoryPairs, Pair[][] scores, ArrayList<Room> rooms) {
		this.mandatoryPairs = mandatoryPairs;
		this.scores = scores;
		this.rooms = rooms;
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
		ArrayList<Pair> possiblePairs = new ArrayList<>();
		possiblePairs.addAll(mandatoryPairs);
		int maxScore = 0, timeSinceImprovement = 0, timeSinceNewPair = 0;
		
		int numTeachers = scores.length;
		int numStudents = scores[0].length;
		int numRoomDayTimes = HelperMethods.getRoomDayTimes(rooms).size();
		
		Random randS = new Random();
		Random randT = new Random();
		
		while (maxScore < SUFFICIENTSCORE && timeSinceImprovement < MAXTIMESINCEIMPROVEMENT) {
			
			while (possiblePairs.size() < numRoomDayTimes + 20 && timeSinceNewPair < MAXTIMESINCENEWPAIR) {
				int studentIndex = randS.nextInt() * numStudents;
				int teacherIndex = randT.nextInt() * numTeachers;

				if (scores[teacherIndex][studentIndex].getScore() > 0) {
					possiblePairs.add(scores[teacherIndex][studentIndex]);
					
					timeSinceNewPair = 0;
				} else {
					timeSinceNewPair++;
				}
			}
		}
	}
}
