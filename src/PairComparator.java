import java.util.ArrayList;
import java.util.Comparator;

public class PairComparator implements Comparator<Pair> {

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
