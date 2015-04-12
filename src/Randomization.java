import java.util.ArrayList;


public class Randomization {

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
		
	}
}
