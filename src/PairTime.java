
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
