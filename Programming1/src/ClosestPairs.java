import java.lang.Math;
import java.util.Arrays;
import java.util.Comparator;

/**
 * ClosestPairs - This java class provides implementation for finding the closest pair of points in a graph.
 * Provided are two solutions to this problem - one brute force method that runs in O(n^2) time and 
 * an efficient solution TODO
 * 
 * @author Hunter Caskey
 *
 */
public class ClosestPairs {
	
	private final static boolean DEBUG = true;
	private double[][] graph;
	
	/**
	 * @param graph
	 */
	public ClosestPairs(double[][] graph) {
		this.graph = graph; // assumed to be a n-size, 2D array
	}
	
	
	/**
	 * @return
	 */
	private double[][] closestPairBruteForce() {
		double minDistance = Double.POSITIVE_INFINITY;
		double[][] closestPair = null;
		
		for(int i = 0; i < this.graph.length - 1; i++) {
			debugPrint("(" + this.graph[i][0] + ", " + this.graph[i][1] + ")");
			for(int j = i + 1; j <= this.graph.length - 1; j++) {
				debugPrint("\t(" + this.graph[j][0] + ", " + this.graph[j][1] + ")");
				double[] p1 = this.graph[i];
				double[] p2 = this.graph[j];
				double distance = distanceBetweenPoints(p1, p2);
				debugPrint("\t\tDistance between points: " + distance + ". Current minimum: " + minDistance);
				if(distance < minDistance) {
					minDistance = distance;
					closestPair = new double[][] { p1, p2 };
				}
			}
		}
		
		return closestPair;
	}
	
	private double[][] getSortedGraphByXDimension() {
		double[][] clone = this.graph.clone();
		Arrays.sort(clone, new Comparator<double[]>() {
			@Override
			public int compare(double[] p1, double[] p2) {
				Double p1X = p1[0];
				Double p2X = p2[0];
				return p1X.compareTo(p2X);
			}
		});
		
		debugPrint("Printing this.graph sorted along x-dimension:");
		for(int i = 0; i < clone.length - 1; i++) {
			debugPrint("\t(" + clone[i][0] + ", " + clone[i][1] + ")");
		}
		
		return clone;
	}
	
	private double[][] closestPairEfficient() {
		// Sort the points by their x-dimension
		double[][] sortedGraph = getSortedGraphByXDimension();
		
		// Split the set of points into two equal-sized subsets by a vertical line x=x_mid
		
		// Recursively solve the left and right subsets. This yield the left side (d_Lmin) and right side (d_Rmin) minimum distances 
		
		// Find the minimal distance d_LRmin among the set of pairs of points in which one point lies on the left of the diving vertical and the other point lies to the right.
		 
		// The final answer is the minimum among d_Lminm d_Rmin, and d_LRmin
		
		return null;
	}
	
	/**
	 * @param p1
	 * @param p2
	 * @return
	 */
	private static double distanceBetweenPoints(double[] p1, double[] p2) {
		// Euclidian distance formula
		double xSquareComponent = Math.pow(p2[0] - p1[0], 2);
		double ySquareComponent = Math.pow(p2[1] - p1[1], 2);
		double distance = Math.sqrt(xSquareComponent + ySquareComponent);
		return distance;
	}
	
	/**
	 * Prints a string to console if DEBUG is set to true.
	 * 
	 * @param msg The debug message to print.
	 */
	private static void debugPrint(String msg) {
		if(ClosestPairs.DEBUG) {
			System.out.println(msg);
		}
	}
	
	
	/**
	 * The entrypoint into the program.
	 * 
	 * @param arg Command line arguments for the main driver of this program.
	 */
	public static void main(String[] args) {
		double[][] inputGraph = {
				{2, 3},
				{12, 30},
				{40, 50},
				{5, 1},
				{12, 10},
				{3, 4}
		};
		
		ClosestPairs algoObj = new ClosestPairs(inputGraph);
		
		//double[][] points = algoObj.closestPairBruteForce();
		//System.out.println("Closest pairs of points of the input set are: (" + points[0][0] + ", " + points[0][1] + ") and (" + points[1][0] + ", " + points[1][1] + ").");
		//System.out.println("Distance 1: " + ClosestPairs.distanceBetweenPoints(points[0], points[1]));
	}
	
	
}
