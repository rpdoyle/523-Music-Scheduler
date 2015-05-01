/**
 * Stores data representing a Room at a given time that it is available
 */
public class RoomDayTime {
	private Room room;
	private int time;
	
	/**
	 * Constructor for RoomDayTime
	 * 
	 * @param room Room that can hold lessons
	 * @param time Time that Room is available in integer format (as produced by ExcelReader)
	 */
	public RoomDayTime(Room room, int time) {
		this.room = room;
		this.time = time;
	}
	
	/**
	 * @return Room
	 */
	public Room getRoom() {
		return room;
	}
	
	/**
	 * @return Time
	 */
	public int getTime() {
		return time;
	}
}
