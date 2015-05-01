/* 
 * File: PairTime.java
 */

/**
 * PairTime is the class that encapsulates a Pair object and the time at which
 * that Pair will be scheduled.
 *
 */
public class PairTime {
	private Pair pair;
	private RoomDayTime roomDayTime;

	/**
	 * Constructor for a PairTime object.
	 * 
	 * @param pair
	 *            the student/teacher pair
	 * @param roomDayTime
	 *            the time at which the pair will have lessons
	 */
	public PairTime(Pair pair, RoomDayTime roomDayTime) {
		this.pair = pair;
		this.roomDayTime = roomDayTime;
	}

	/**
	 * Returns the Pair in this object.
	 * 
	 * @return the Pair object that will be having lessons
	 */
	public Pair getPair() {
		return pair;
	}

	/**
	 * Returns the RoomDayTime object for this lesson.
	 * 
	 * @return the RoomDayTime for this lesson
	 */
	public RoomDayTime getRoomDayTime() {
		return roomDayTime;
	}
}
