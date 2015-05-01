/* 
 * File: PairComparator.java
 */

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Compares two Pair objects for equality. Since two Pair objects might contain
 * the same field values but be references to different objects, this class
 * implements methods that are used to test for equality.
 *
 */
public class PairComparator implements Comparator<Pair> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Pair p1, Pair p2) {
		if (p1.getStudent().getName().equals(p2.getStudent().getName())
				&& p1.getTeacher().getName().equals(p2.getTeacher().getName())
				&& p1.getScore() == p2.getScore()
				&& p1.getInstrument().equals(p2.getInstrument())) {

			int p1TimesSize, p2TimesSize;
			if (p1.getMutualTimes() != null) {
				p1TimesSize = p1.getMutualTimes().size();
			} else {
				p1TimesSize = 0;
			}
			if (p2.getMutualTimes() != null) {
				p2TimesSize = p2.getMutualTimes().size();
			} else {
				p2TimesSize = 0;
			}

			if (p1TimesSize != p2TimesSize) {
				return 1;
			}
			for (int i = 0; i < p1TimesSize; i++) {
				if (!p1.getMutualTimes().get(i)
						.equals(p2.getMutualTimes().get(i))) {
					return 1;
				}
			}
			return 0;
		}
		return 1;
	}

	/**
	 * Compares two ArrayLists of Pair objects for equality. This method will
	 * ensure that, if a Pair object exists in one ArrayList, a Pair object with
	 * identical fields exists in the other ArrayList.
	 * 
	 * @param a1
	 *            the first ArrayList of Pair objects to be compared
	 * @param a2
	 *            the second ArrayList of Pair objects to be compared
	 * @return 0 if the objects are equal; 1 if the objects are not equal
	 */
	public int compareLists(ArrayList<Pair> a1, ArrayList<Pair> a2) {
		if (a1.size() != a2.size()) {
			return 1;
		}

		int a1size = a1.size();
		for (int i = 0; i < a1size; i++) {
			if (compare(a1.get(i), a2.get(i)) == 1) {
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Compares two arrays of Pair objects for equality. This method will ensure
	 * that, given two Pair arrays, the Pairs at each index are equal.
	 * 
	 * @param a1
	 *            the first Pair array to be compared
	 * @param a2
	 *            the second Pair array to be compared
	 * @return 0 if the objects are equal; 1 if the objects are not equal
	 */
	public int compareArrays(Pair[][] a1, Pair[][] a2) {
		if (a1.length != a2.length) {
			return 1;
		}
		for (int i = 0; i < a1.length; i++) {
			if (a1[i].length != a2[i].length) {
				return 1;
			}
		}

		PairComparator pc = new PairComparator();

		for (int i = 0; i < a1.length; i++) {
			for (int j = 0; j < a1[i].length; j++) {
				if (pc.compare(a1[i][j], a2[i][j]) == 1) {
					return 1;
				}
			}
		}

		return 0;
	}
}
