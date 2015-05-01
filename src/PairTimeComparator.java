/*
 * File: PairTimeComparator.java
 */

import java.util.ArrayList;
import java.util.Comparator;

/**
 * PairTimeComparator is used to compare two PairTime objects. Since two
 * PairTime objects may have identical field values but be references to
 * different objects, this class implements methods to test these objects for
 * equality.
 *
 */
public class PairTimeComparator implements Comparator<PairTime> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(PairTime pt1, PairTime pt2) {
		PairComparator pc = new PairComparator();
		RoomDayTimeComparator rdtc = new RoomDayTimeComparator();

		// Check to see if the two pairs are the same
		if (pc.compare(pt1.getPair(), pt2.getPair()) == 0) {

			// Check to see if the two RoomDayTimes are the same
			if (rdtc.compare(pt1.getRoomDayTime(), pt2.getRoomDayTime()) == 0) {
				return 0;
			}
		}
		return 1;
	}

	/**
	 * Compares two ArrayLists of PairTime objects for equality. This method
	 * will ensure that, if a PairTime object exists in one ArrayList, a
	 * PairTime object with identical fields exists in the other ArrayList.
	 * 
	 * @param pt1
	 *            the first PairTime object to be compared
	 * @param pt2
	 *            the second PairTime object to be compared
	 * @return 0 if the ArrayLists are equal; 1 if the ArrayLists are not equal
	 */
	public int compareLists(ArrayList<PairTime> pt1, ArrayList<PairTime> pt2) {
		if (pt1.size() != pt2.size()) {
			return 1;
		}

		for (int i = 0; i < pt1.size(); i++) {
			if (compare(pt1.get(i), pt2.get(i)) == 1) {
				return 1;
			}
		}
		return 0;
	}
}
