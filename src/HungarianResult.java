import java.util.ArrayList;

/**
 * Contains output from a run of the Hungarian algorithm
 */
public class HungarianResult {
	private ArrayList<PairTime> pairTimes;
	private int score;

	/**
	 * Constructor for HungarianResult
	 * 
	 * @param pairTimes
	 *            PairTimes that were scheduled
	 * @param score
	 *            Score of the schedule
	 */
	public HungarianResult(ArrayList<PairTime> pairTimes, int score) {
		this.pairTimes = pairTimes;
		this.score = score;
	}

	/**
	 * Returns the PairTimes in the schedule
	 * 
	 * @return ArrayList of PairTimes in the schedule
	 */
	public ArrayList<PairTime> getPairTimes() {
		return pairTimes;
	}

	/**
	 * Returns the score of the schedule
	 * 
	 * @return Score of the current schedule
	 */
	public int getScore() {
		return score;
	}
}
