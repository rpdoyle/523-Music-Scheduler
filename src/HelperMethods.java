import java.util.ArrayList;
import java.util.HashSet;


public class HelperMethods {
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
}
