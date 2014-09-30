import java.util.Comparator;


public class PointsComparator implements Comparator<String> {


	@Override
	public int compare(String o1, String o2) {
		// TODO Auto-generated method stub
		String[] p1 = Chans.convertStringToArray(o1);
		String[] p2 = Chans.convertStringToArray(o2);
		int x1 = Integer.parseInt(p1[0]);
		int y1 = Integer.parseInt(p1[1]);
		int x2 = Integer.parseInt(p2[0]);
		int y2 = Integer.parseInt(p2[1]);
		
		return Integer.compare(x1, x2);
	}
}
