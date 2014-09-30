import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Chans {

	public static List<String> convexHull(List<String> points,
			boolean calledFromApplet, Graphics graphics)
			throws InterruptedException {
		float a = 25;
		float b = (float) 0.27;
		float c = (float) 0.46;
		ChanApplet chanApplet = new ChanApplet();
		System.out.println("input points");
		String pot = new String();
		for (String s : points) {
			pot = pot.concat("," + s);
		}
		System.out.println(pot);
		System.out.println("end of input points");
		for (int t = 0; t < points.size(); t++) {
			int m = (int) Math.pow(2, Math.pow(2, t));
			if (m > points.size()) {
				m = points.size();
			}
			List<List<String>> hulls = new ArrayList<List<String>>();
			for (int i = 0; i < points.size(); i = i + m) {
				int k = i + m;
				if (i + m > points.size()) {
					k = points.size();
				}
				if (calledFromApplet) {
					chanApplet.showSubPoints(points.subList(i, k), graphics,
							Color.getHSBColor(a, b, c));
				}
				List<String> grahamScanHull = GrahamScan.grahamScan(points
						.subList(i, k));
				hulls.add(grahamScanHull);
				if (calledFromApplet) {
					chanApplet.showGrahamScanResult(grahamScanHull, graphics,
							Color.getHSBColor(a, b, c));
				}
			}

			List<String> hull = minimumPoint(hulls);

			Set<String> hullSet = new LinkedHashSet<String>();
			List<String> convexHull = new ArrayList<>();
			List<String> ch = new ArrayList<>();
			List<String> showNextHullPoint = new ArrayList<String>();
			for (int throw_away = 0; throw_away < m; throw_away++) {
				String p = nextHullPoint(hulls, hull.get(hull.size() - 1));
				List<String> po = hulls.get(Integer
						.parseInt(convertStringToArray(p)[0]));
				if (calledFromApplet) {
					showNextHullPoint.add(po.get(Integer
							.parseInt(convertStringToArray(p)[1])));
				}
				ch.add(po.get(Integer.parseInt(convertStringToArray(p)[1])));
				if (p.compareTo(hull.get(0)) == 0) {

					hullSet.addAll(po);
					Collections.reverse(showNextHullPoint);
					for (String str : showNextHullPoint) {
						chanApplet.showPoint(str, graphics, Color.BLUE);
					}
					convexHull.addAll(hullSet);

					return ch;
				}

				hull.add(p);
			}
			Collections.reverse(showNextHullPoint);
			for (String str : showNextHullPoint) {
				chanApplet.showPoint(str, graphics, Color.BLUE);
			}
			a = a + 30;
			b = (float) (b + 0.3);
			c = (float) (c + 0.5);
		}
		return null;
	}

	GrahamScan grahamScan = new GrahamScan();

	public static int tangent(List<String> hull, String p) {
		int l = 0, hullSize = hull.size();
		String p1 = p.substring(1, p.length() - 1);
		String[] p2 = p1.split(",");

		String q = hull.get(0);
		String q1 = q.substring(1, q.length() - 1);
		String[] q2 = q1.split(",");

		String r = hull.get(hull.size() - 1);
		String r1 = r.substring(1, r.length() - 1);
		String[] r2 = r1.split(",");

		String s = hull.get((l + 1) % hullSize);
		String s1 = s.substring(1, s.length() - 1);
		String[] s2 = s1.split(",");

		int previous = GrahamScan.turn(p2, q2, r2);
		int next = GrahamScan.turn(p2, q2, s2);

		while (l < hullSize) {
			int c = (l + hullSize) / 2;
			String a = hull.get(c);
			String a1 = a.substring(1, a.length() - 1);
			String[] a2 = a1.split(",");
			for (int i = 0; i < hull.size(); i++) {
			}
			String b = null;
			if ((c - 1) % hull.size() < 0) {
				b = hull.get(-(c - 1) % hull.size());
			} else {
				b = hull.get((c - 1) % hull.size());
			}
			String b1 = b.substring(1, b.length() - 1);
			String[] b2 = b1.split(",");

			String d = hull.get((c + 1) % hull.size());
			String d1 = d.substring(1, d.length() - 1);
			String[] d2 = d1.split(",");

			String e = hull.get(l);
			String e1 = e.substring(1, e.length() - 1);
			String[] e2 = e1.split(",");

			int previous2 = GrahamScan.turn(p2, a2, b2);
			int next2 = GrahamScan.turn(p2, a2, d2);
			int side = GrahamScan.turn(p2, e2, a2);

			if (previous2 != -1 && next2 != -1) {
				return c;
			} else {
				if (side == 1 && (next == -1 || previous == next)
						|| side == -1 && previous2 == -1) {
					hullSize = c;
				} else {
					l = c + 1;
					previous = -next2;
					String f = null;
					try {
						f = hull.get(l);
					} catch (IndexOutOfBoundsException exc) {
					}
					String f1 = f.substring(1, f.length() - 1);
					String[] f2 = f1.split(",");

					String g = hull.get((l + 1) % hull.size());
					String g1 = g.substring(1, g.length() - 1);
					String[] g2 = g1.split(",");

					next = GrahamScan.turn(p2, f2, g2);
				}
			}
		}
		return l;
	}

	public static List<String> minimumPoint(List<List<String>> hull) {
		int h = 0, p = 0;
		for (int i = 0; i < hull.size(); i++) {
			int j = 0;
			String min[] = convertStringToArray(hull.get(i).get(0));
			int m = Integer.parseInt(min[0]);
			for (int k = 1; k < hull.get(i).size(); k++) {
				String min2[] = convertStringToArray(hull.get(i).get(k));
				int m2 = Integer.parseInt(min2[0]);
				if (m > m2) {
					j = k;
				}
			}
			String t1[] = convertStringToArray(hull.get(i).get(j));
			int q1 = Integer.parseInt(t1[0]);
			String t2[] = convertStringToArray(hull.get(h).get(p));
			int q2 = Integer.parseInt(t2[0]);
			if (q1 < q2) {
				h = i;
				p = j;
			}
		}
		List<String> list = new ArrayList<String>();

		list.add("(" + Integer.toString(h) + "," + Integer.toString(p) + ")");
		return list;
	}

	public static String nextHullPoint(List<List<String>> hull, String a) {

		String b = a.substring(1, a.length() - 1);
		String[] pair = b.split(",");

		String retValue = null;
		String p = hull.get(Integer.parseInt(pair[0])).get(
				Integer.parseInt(pair[1]));
		List<Integer> next = new ArrayList<Integer>();
		next.add(Integer.parseInt(pair[0]));
		next.add(Math.abs((Integer.parseInt(pair[1]) + 1)
				% hull.get(Integer.parseInt(pair[0])).size()));

		retValue = "("
				+ Integer.parseInt(pair[0])
				+ ","
				+ Math.abs((Integer.parseInt(pair[1]) + 1)
						% hull.get(Integer.parseInt(pair[0])).size()) + ")";
		for (int i = 0; i < hull.size(); i++) {
			if (i != Integer.parseInt(pair[0])) {
				for (int o = 0; o < hull.size(); o++) {
				}

				int s = tangent(hull.get(i), p);
				if (Integer.parseInt(pair[0]) == 113) {
				}
				String q = hull.get(next.get(0)).get(next.get(1));
				String r = hull.get(i).get(s);
				int t = GrahamScan.turn(convertStringToArray(p),
						convertStringToArray(q), convertStringToArray(r));
				if (Integer.parseInt(pair[0]) == 113) {
				}
				if (t == -1 || t == 0 && distance(p, r) > distance(p, q)) {
					next.clear();
					next.add(i);
					next.add(s);
					retValue = "(" + i + "," + s + ")";
				}
			}
		}
		return retValue;

	}

	private static double distance(String p, String q) {
		String[] p2 = convertStringToArray(p);
		String[] q2 = convertStringToArray(q);
		double dist = ((Integer.parseInt(q2[0]) - Integer.parseInt(p2[0])) * (Integer
				.parseInt(q2[0]) - Integer.parseInt(p2[0])))
				+ ((Integer.parseInt(q2[1]) - Integer.parseInt(p2[1])) * (Integer
						.parseInt(q2[1]) - Integer.parseInt(p2[1])));
		return dist;
	}

	public static String[] convertStringToArray(String q) {
		String q1 = q.substring(1, q.length() - 1);
		String[] q2 = q1.split(",");
		return q2;
	}
}
