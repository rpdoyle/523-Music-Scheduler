import java.util.ArrayList;
import java.util.Comparator;

/**
 * Implements methods necessary to compare two RoomDayTime objects
 */
public class RoomDayTimeComparator implements Comparator<RoomDayTime> {

	// Compares two RoomDayTime objects for equality
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(RoomDayTime rdt1, RoomDayTime rdt2) {

		// If the two RoomDayTimes have the same time, keep checking
		if (rdt1.getTime() == rdt2.getTime()) {
			Room r1 = rdt1.getRoom();
			Room r2 = rdt2.getRoom();

			// Check to see if the two Rooms have the same name. If so, continue
			// to check to see if they have the same Special Instruments
			if (r1.getName().equals(r2.getName())) {
				int r1SpecialInstrumentSize, r2SpecialInstrumentSize;
				if (r1.getSpecialInstruments() != null) {
					r1SpecialInstrumentSize = r1.getSpecialInstruments().size();
				} else {
					r1SpecialInstrumentSize = 0;
				}
				if (r2.getSpecialInstruments() != null) {
					r2SpecialInstrumentSize = r2.getSpecialInstruments().size();
				} else {
					r2SpecialInstrumentSize = 0;
				}

				if (r1SpecialInstrumentSize != r2SpecialInstrumentSize) {
					return 1;
				}
				for (int i = 0; i < r1SpecialInstrumentSize; i++) {
					if (!r1.getSpecialInstruments().get(i)
							.equals(r2.getSpecialInstruments().get(i))) {
						return 1;
					}
				}

				// Check to see if the two rooms have the same Available Times
				int r1AT, r2AT;
				if (r1.getAvailableTimes().length != 0) {
					r1AT = r1.getAvailableTimes().length;
				} else {
					r1AT = 0;
				}
				if (r2.getAvailableTimes().length != 0) {
					r2AT = r2.getAvailableTimes().length;
				} else {
					r2AT = 0;
				}
				if (r1AT != r2AT) {
					return 1;
				}
				for (int i = 0; i < r1AT; i++) {
					if (!(r1.getAvailableTimes()[i] == r2.getAvailableTimes()[i])) {
						return 1;
					}
				}
				return 0;

			}
			return 0;

		}
		return 1;

	}

	/**
	 * Compares elements in two ArrayLists
	 * 
	 * @param a1
	 *            ArrayList of RoomDayTimes
	 * @param a2
	 *            ArrayList of RoomDayTimes
	 * @return 1 if they are not equal, 0 if they are equal
	 */
	public int compareLists(ArrayList<RoomDayTime> a1, ArrayList<RoomDayTime> a2) {
		if (a1.size() != a2.size()) {
			return 1;
		}

		for (int i = 0; i < a1.size(); i++) {
			if (compare(a1.get(i), a2.get(i)) == 1) {
				return 1;
			}
		}
		return 0;
	}

}
