package closestpairs;
import java.util.Comparator;

/**
 * @author Hunter R. Caskey
 *
 * Data structure to represent a point on a Cartesian graph.
 */
public class Point {

	public final double x;
	public final double y;
	public static final Comparator<Point> xComparator = new XComparator();
	public static final Comparator<Point> yComparator = new YComparator();

	/**
	 * @param x X-component of a Cartesian point.
	 * @param y Y-component of a Cartesian point.
	 */
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Helper class to sort points by their X component.
	 */
	private static class XComparator implements Comparator<Point>{
		public int compare(Point point1, Point point2) {
			int returnVal = (point1.x < point2.x) ? -1 : (point1.x > point2.x) ? 1 : 0;
			return returnVal;
		}
	}
	
	/**
	 * Helper class to sort points by their Y component.
	 */
	private static class YComparator implements Comparator<Point>{
		public int compare(Point point1, Point point2) {
			int returnVal = (point1.y < point2.y) ? -1 : (point1.y > point2.y) ? 1 : 0;
			return returnVal;
		}
	}

}