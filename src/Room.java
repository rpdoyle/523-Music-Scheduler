import java.util.ArrayList;

/**
 * Stores data for a Room object
 *
 */
public class Room {
	private String name;
	private ArrayList<String> specialInstruments;
	private int[] availableTimes;

	/**
	 * Constructor for a Room
	 * 
	 * @param name Name of the Room
	 * @param specialInstruments ArrayList of special instruments supported by the Room
	 * @param availableTimes int[] of times supported by the Room
	 */
	public Room(String name, ArrayList<String> specialInstruments, int[] availableTimes) {
		this.name = name;
		this.specialInstruments = specialInstruments;
		this.availableTimes = availableTimes;
	}
	
	/**
	 * @return Name of the Room
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return ArrayList of special instruments supported by the Room
	 */
	public ArrayList<String> getSpecialInstruments() {
		return specialInstruments;
	}
	
	
	/**
	 * @return int[] of times supported by the Room
	 */
	public int[] getAvailableTimes() {
		return availableTimes;
	}
}
