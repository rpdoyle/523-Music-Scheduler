import java.util.ArrayList;
import java.util.Comparator;

public class PairTimeComparator {
	 
	public int compare(PairTime pt1, PairTime pt2){
		PairComparator pc = new PairComparator();
		RoomDayTimeComparator rdtc = new RoomDayTimeComparator();
		// Check to see if the two pairs are the same
		if (pc.compare(pt1.getPair(),pt2.getPair()) == 0){
			// Check to see if the two RoomDayTimes are the same
			if (rdtc.compare(pt1.getRoomDayTime(), pt2.getRoomDayTime()) == 0){
				return 0;
			}
		}
		return 1;
	}

	public int compareLists(ArrayList<PairTime> pt1, ArrayList<PairTime> pt2){
		System.out.println("I know I am in compareLists");
		if (pt1.size() != pt2.size()){
			System.out.println("The Array sizes are not equal");
			return 1;
		}
		
		for (int i = 0; i<pt1.size(); i++){
			if (compare(pt1.get(i), pt2.get(i)) == 1){
				System.out.println("First one: " + pt1.get(i).getPair().getStudent().getName() + " Second one: " + pt2.get(i).getPair().getStudent().getName());
				return 1;
			}
		}
		return 0;
	}
}
