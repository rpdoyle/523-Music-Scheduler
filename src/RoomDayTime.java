/*
 * File: RoomDayTime.java
 * Description: This class stores data for a room at a specific time.
 */

public class RoomDayTime {
	private Room room;
	private int time;
	
	public RoomDayTime(Room room, int time) {
		this.room = room;
		this.time = time;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public int getTime() {
		return time;
	}
}
