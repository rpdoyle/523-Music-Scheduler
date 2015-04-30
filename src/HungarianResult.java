/*
 * File: HungarianResult.java
 * Description: This file contains the format the result
 * 				returned by the Hungarian algorithm.
 */

import java.util.ArrayList;

public class HungarianResult {
	private ArrayList<PairTime> pairTimes;
	private int score;
	
	public HungarianResult(ArrayList<PairTime> pairTimes, int score) {
		this.pairTimes = pairTimes;
		this.score = score;
	}
	
	public ArrayList<PairTime> getPairTimes() {
		return pairTimes;
	}
	
	public int getScore() {
		return score;
	}
}
