/**
 * Stores data representing a Room at a given time that it is available
 */
public class RoomDayTime {
	private Room room;
	private int time;

	/**
	 * Constructor for RoomDayTime
	 * 
	 * @param room
	 *            Room that can hold lessons
	 * @param time
	 *            Time that Room is available in integer format (as produced by
	 *            ExcelReader)
	 */
	public RoomDayTime(Room room, int time) {
		this.room = room;
		this.time = time;
	}

	/**
	 * Returns the Room associated with the RoomDayTime
	 * 
	 * @return Room associated with the RoomDayTime
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * Returns the time associated with the RoomDayTime
	 * 
	 * @return Time associated with the RoomDayTime
	 */
	public int getTime() {
		return time;
	}
}
