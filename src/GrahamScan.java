import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrahamScan {

	public static List<String> grahamScan(List<String> points) {
		Collections.sort(points, new PointsComparator());

		List<String> lowerHull = new ArrayList<String>();
		List<String> upperHull = new ArrayList<String>();
		for (String str : points) {
			keepLeft(lowerHull, str);
		}

		Collections.reverse(points);
		for (String str : points) {
			keepLeft(upperHull, str);
		}

		for (int y = 1; y < upperHull.size() - 1; y++) {
			lowerHull.add(upperHull.get(y));
		}
		return lowerHull;
	}

	public static List<String> keepLeft(List<String> hull, String r) {
		String[] p2 = null;
		String[] q2 = null;
		String p = null, p1 = null, q = null, q1 = null;
		if (hull.size() > 1) {
			p = hull.get(hull.size() - 2);
			p1 = p.substring(1, p.length() - 1);
			p2 = p1.split(",");

			q = hull.get(hull.size() - 1);
			q1 = q.substring(1, q.length() - 1);
			q2 = q1.split(",");
		}
		String r1 = r.substring(1, r.length() - 1);
		String[] r2 = r1.split(",");
		while ((hull.size() > 1) && (turn(p2, q2, r2) != 1)) {
			hull.remove(hull.size() - 1);
			if (hull.size() > 1) {
				p = hull.get(hull.size() - 2);
				p1 = p.substring(1, p.length() - 1);
				p2 = p1.split(",");
				q = hull.get(hull.size() - 1);
				q1 = q.substring(1, q.length() - 1);
				q2 = q1.split(",");
			}

		}
		if (hull.size() == 0
				|| (hull.size() > 0 && (hull.get(hull.size() - 1).compareTo(r) != 0))) {
			hull.add(r);
		}
		return hull;
	}

	public static int turn(String[] p, String[] q, String[] r) {

		if (((Integer.parseInt((q[0])) - Integer.parseInt(p[0])) * (Integer
				.parseInt(r[1]) - Integer.parseInt(p[1])))
				- ((Integer.parseInt(r[0]) - Integer.parseInt(p[0])) * (Integer
						.parseInt(q[1]) - Integer.parseInt(p[1]))) > 0) {
			return 1;
		}
		if (((Integer.parseInt((q[0])) - Integer.parseInt(p[0])) * (Integer
				.parseInt(r[1]) - Integer.parseInt(p[1])))
				- ((Integer.parseInt(r[0]) - Integer.parseInt(p[0])) * (Integer
						.parseInt(q[1]) - Integer.parseInt(p[1]))) == 0) {
			return 0;
		}
		if (((Integer.parseInt((q[0])) - Integer.parseInt(p[0])) * (Integer
				.parseInt(r[1]) - Integer.parseInt(p[1])))
				- ((Integer.parseInt(r[0]) - Integer.parseInt(p[0])) * (Integer
						.parseInt(q[1]) - Integer.parseInt(p[1]))) < 0) {
			return -1;
		}
		return 0;

	}
}
