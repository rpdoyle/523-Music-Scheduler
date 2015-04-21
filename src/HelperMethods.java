import java.util.ArrayList;
import java.util.HashSet;


public class HelperMethods {
	// Gathers a complete list of all Special Instruments supported by any room
	public static ArrayList<String> getAllPossibleSpecialInstruments(ArrayList<Room> rooms) {
		HashSet<String> specialInstruments = new HashSet<String>();
		
		for (int i = 0; i < rooms.size(); i++) {
			ArrayList<String> roomSpecialInstruments = rooms.get(i).getSpecialInstruments();
			for (int j = 0; j < roomSpecialInstruments.size(); j++) {
				specialInstruments.add(roomSpecialInstruments.get(j));
			}
		}
		
		ArrayList<String> returnedList = new ArrayList<String>();
		returnedList.addAll(specialInstruments);
		
		return returnedList;
	}
	
	// Creates a complete list of all specific room/day/time combinations
	public static ArrayList<RoomDayTime> getRoomDayTimes(ArrayList<Room> rooms) {
		
		ArrayList<RoomDayTime> roomDayTimes = new ArrayList<RoomDayTime>();
		
		for (int i = 0; i < rooms.size(); i++) {
			Room r = rooms.get(i);
			for (int j = 0; j < r.getAvailableTimes().length; j++) {
				RoomDayTime rdt = new RoomDayTime(r, r.getAvailableTimes()[j]);
				roomDayTimes.add(rdt);
			}
		}
		
		return roomDayTimes;
	}
	
	// Returns the string representation of a time in int format
	public static String intTimeToString(int i) {
		boolean isPM = false;
		
		// Ignore the day associated with the time
		i = i % 1440;
		
		if (i >= 720) {
			isPM = true;
			i -= 720;
		}
		
		int hour = i / 60;
		int minute = i % 60;
		
		String result = "";
		
		if (hour == 0) {
			result += "12:";
		} else {
			result = Integer.toString(hour) + ":";
		}
		
		if (minute < 10) {
			result += "0" + Integer.toString(minute) + " ";
		} else {
			result += Integer.toString(minute) + " ";
		}
		
		if (isPM) {
			result += "PM";
		} else {
			result += "AM";
		}

		return result;
	}
}
