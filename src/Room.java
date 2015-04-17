import java.util.ArrayList;

/*
 * File: Room.java
 * Description: This class stores data for a Room.
 */

public class Room {
	private String name;
	private ArrayList<String> specialInstruments;
	private int[] availableTimes;

	public Room(String name, ArrayList<String> specialInstruments, int[] availableTimes) {
		this.name = name;
		this.specialInstruments = specialInstruments;
		this.availableTimes = availableTimes;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<String> getSpecialInstruments() {
		return specialInstruments;
	}
	
	public int[] getAvailableTimes() {
		return availableTimes;
	}
}
