package closestpairs;
import java.util.Comparator;

public class Point {
	public final double x;
	public final double y;
	public static final Comparator<Point> sortX = new compareX();
	public static final Comparator<Point> sortY = new compareY();

	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}

	private static class compareX implements Comparator<Point>{
		public int compare(Point o1, Point o2) {
			int returnVal = (o1.x < o2.x) ? -1 : (o1.x > o2.x) ? 1 : 0;
			return returnVal;
		}
	}
	
	private static class compareY implements Comparator<Point>{
		public int compare(Point o1, Point o2) {
			int returnVal = (o1.y < o2.y) ? -1 : (o1.y > o2.y) ? 1 : 0;
			return returnVal;
		}
	}

}