/* 
 * File: PairTime.java
 * Description: This class stores a Pair object and the time
 * 				at which that pair is scheduled.
 */

public class PairTime {
	private Pair pair;
	private RoomDayTime roomDayTime;
	
	public PairTime(Pair pair, RoomDayTime roomDayTime) {
		this.pair = pair;
		this.roomDayTime = roomDayTime;
	}
	
	public Pair getPair() {
		return pair;
	}
	
	public RoomDayTime getRoomDayTime() {
		return roomDayTime;
	}
}
