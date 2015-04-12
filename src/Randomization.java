import java.util.ArrayList;
import java.util.Random;


public class Randomization {

	public static final int SUFFICIENTSCORE = 10000;
	public static final int MAXTIMESINCEIMPROVEMENT = 500;
	
	private ArrayList<Pair> mandatoryPairs;
	private Pair[][] scores;
	
	public Randomization(ArrayList<Pair> mandatoryPairs, Pair[][] scores) {
		this.mandatoryPairs = mandatoryPairs;
		this.scores = scores;
	}
	
	public ArrayList<Pair> getMandatoryPairs() {
		return mandatoryPairs;
	}
	
	public Pair[][] getScores() {
		return scores;
	}
	
	public HungarianResult schedule() {
		ArrayList<Pair> possiblePairs = new ArrayList<>();
		possiblePairs.addAll(mandatoryPairs);
		int maxScore = 0, timeSinceImprovement = 0;
		
		int numTeachers = scores.length;
		int numStudents = scores[0].length;
		
		Random randS = new Random();
		Random randT = new Random();
		
		while (maxScore < SUFFICIENTSCORE && timeSinceImprovement < MAXTIMESINCEIMPROVEMENT) {
			int studentIndex = randS.nextInt() * numStudents;
			int teacherIndex = randT.nextInt() * numTeachers;
			
			
		}
	}
}
