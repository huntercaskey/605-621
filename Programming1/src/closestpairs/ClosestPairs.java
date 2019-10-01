package closestpairs;

import java.io.FileWriter;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Hunter R. Caskey
 * 
 * ClosestPairs - This java class provides implementation for finding the closest pair of Point in a graph.
 * Provided are there solutions to this problem - one brute force method that runs in O(n^2) time, 
 * an efficient solution that runs in O(nlog(n)) time, and an algorithm that finds the general case of m pairs of closest points
 * which utilizes the efficient solution to the case of a single pair of points.
 */
public class ClosestPairs {
	
	private final static int SIZE_OF_DATASET = 30;

	/**
	 * The entrypoint into the program.
	 * 
	 * @param args Command line arguments for the main driver of this program.
	 */
	public static void main(String[] args) {

		ArrayList<String> logger = new ArrayList<>();
		
		// Generate a random dataset
		Point[] graph = new Point[SIZE_OF_DATASET];
		Random ran = new Random();
		logger.add("Randomized input data set with SIZE_OF_DATASET=" + SIZE_OF_DATASET);
		for (int idx = 0; idx < SIZE_OF_DATASET; idx++){
			graph[idx] = new Point(ran.nextInt(101), ran.nextInt(101));
			logger.add("Point #" + (idx + 1) + ": (" + graph[idx].x + ", " + graph[idx].y + ")");
		}
		
		// Find the brute force solution for the closest pair of points
		Point[] bruteforceSolution = bruteForcePairOfPoints(graph, graph);
		
		// Find the efficient solution for the closest pair of points
		Point[] closestPairSolution = efficientClosestPairOfPoints(graph);
		
		// Find the solution to the closest m=2 pairs of points.
		int m = 2;
		Point[][] closestMPairsOfPoints = closestMPairsOfPoints(graph, m);
		
		logger.add("\nSolution via the brute force O(n^2) implementation of the closestPoints algorithm:");
		logger.add("Point 1: (" + bruteforceSolution[0].x + ", " + bruteforceSolution[0].y + ")");
		logger.add("Point 2: (" + bruteforceSolution[1].x + ", " + bruteforceSolution[1].y + ")");
		logger.add("Distance between Point 1 and Point 2: " + distanceBetweenPoints(bruteforceSolution[0], bruteforceSolution[1]));
		
		logger.add("\nSolution via the efficient O(nlog(n)) implementation of the closestPoints algorithm:");
		logger.add("Point 1: (" + closestPairSolution[0].x + ", " + closestPairSolution[0].y + ")");
		logger.add("Point 2: (" + closestPairSolution[1].x + ", " + closestPairSolution[1].y + ")");
		logger.add("Distance between Point 1 and Point 2: " + distanceBetweenPoints(closestPairSolution[0], closestPairSolution[1]));

		logger.add("\nSolution via the m closest pairs of points algorithm:");
		for(int idx = 0; idx < closestMPairsOfPoints.length; idx++) {
			logger.add("Pair #" + (idx + 1) + ":");
			logger.add("Point 1: (" + closestMPairsOfPoints[idx][0].x + ", " + closestMPairsOfPoints[idx][0].y + ")");
			logger.add("Point 2: (" + closestMPairsOfPoints[idx][1].x + ", " + closestMPairsOfPoints[idx][1].y + ")");
			logger.add("Distance: " + distanceBetweenPoints(closestMPairsOfPoints[idx][0], closestMPairsOfPoints[idx][1]));
		}
		
		outputTestResults(logger, "closestPairs_output.txt");
	}
	
	/**
	 * Calculates the Euclidian distance between two points.
	 * 
	 * @param point1 First component of the pair to calculate distance for.
	 * @param point2 Second component of the pair to calculate distance for.
	 * @return The Euclidian distance between two points.
	 */
	private static double distanceBetweenPoints(Point point1, Point point2){
		double xComponent = Math.pow(point2.x - point1.x, 2);
		double yComponent = Math.pow(point2.y - point1.y, 2);
		return Math.sqrt(xComponent + yComponent);
	}
	
	/**
	 * Implementation of the algorithm to find the closest m pairs of points in a graph. The solution first finds the 
	 * base case via a direct call to efficientClosestPairOfPoints. From then on, the function iterates until m pairs of points 
	 * are found. The successive pairs of points are found by iterating through the collection of minimum pairs, extracting each component point
	 * within the pairs one by one and calculating a set of candidate closest points. One all of the minimum candidates are found the minimum distance pair of 
	 * points from this candidate set is found and added to the firm-collection of closest pairs.
	 * 
	 * @param graph The set of points to find the closest pairs from.
	 * @param m The number of closest pairs of points.
	 * @return A set of m closest pairs of points from the input graph.
	 */
	private static Point[][] closestMPairsOfPoints(Point[] graph, int m) {
		if(m >= (graph.length - 1)) return null;
		
		Point[] sortByX = new Point[graph.length]; 
		Point[] sortByY = new Point[graph.length];
		for (int i = 0;i < graph.length;i++){
			sortByX[i] = graph[i];
			sortByY[i] = graph[i];
		}
		Arrays.sort(sortByX, Point.xComparator);
		Arrays.sort(sortByY, Point.yComparator);
		
		ArrayList<Point[]> closestPairs = new ArrayList<>();
		for(int idx = 0; idx < m; idx++) {
			
			if(closestPairs.size() > 0) {
				ArrayList<Point[]> candidateSet = new ArrayList<>();
				for(Point[] pair : closestPairs) {
					for(Point point : pair) {
						
						List<Point> sortByXList = new ArrayList<Point>(Arrays.asList(sortByX));
						List<Point> sortByYList = new ArrayList<Point>(Arrays.asList(sortByY));
						int pXIdx = sortByXList.indexOf(point);
						int pYIdx = sortByYList.indexOf(point);
						sortByXList.remove(pXIdx);
						sortByYList.remove(pYIdx);
						
						Point[] xArr = new Point[sortByXList.size()];
						Point[] yArr = new Point[sortByYList.size()];
						xArr = sortByXList.toArray(xArr);
						yArr = sortByYList.toArray(yArr);
						
 						candidateSet.add(closestPairOfPoints(xArr, yArr));
					}
				}
				
				Point[] min = null;
				double minimum = Double.MAX_VALUE;
				for(Point[] p : candidateSet) {
					if(distanceBetweenPoints(p[0], p[1]) < minimum) {
						min = p;
						minimum = distanceBetweenPoints(p[0], p[1]);
					}
				}
				closestPairs.add(min);
				
			}
			else {
				closestPairs.add(closestPairOfPoints(sortByX, sortByY));
			}	
		}
		
		Point[][] returnArray = new Point[closestPairs.size()][];
		returnArray = closestPairs.toArray(returnArray);
		return returnArray;
	}
	

	/**
	 * Wrapper function to setup inputs for the efficient implementation to find the closest pair of points in a graph. 
	 * 
	 * @param graph Set of points comprising the graph.
	 * @return The closest pair of points within the graph.
	 */
	public static Point[] efficientClosestPairOfPoints(Point[] graph){
		// Create two graph copies sorted by their X-components and Y-components
		Point[] sortByX = new Point[graph.length]; 
		Point[] sortByY = new Point[graph.length];
		for (int i = 0;i < graph.length;i++){
			sortByX[i] = graph[i];
			sortByY[i] = graph[i];
		}
		Arrays.sort(sortByX, Point.xComparator);
		Arrays.sort(sortByY, Point.yComparator);
		
		return closestPairOfPoints(sortByX, sortByY);
	}

	/**
	 * Efficient implementation for finding the closest pair of points in a graph. This algorithm uses a recursive divide-and-conquer approach.
	 * Idea behind the implementation for this algorithm was obtained from https://www.geeksforgeeks.org/closest-pair-of-points-using-divide-and-conquer-algorithm/
	 * 
	 * @param graphSortedByX The collection of points representing the graph sorted by their X-component.
	 * @param graphSortedByY The collection of points representing the graph sorted by their Y-component.
	 * @return The closest pair of points from within the graph.
	 */
	private static Point[] closestPairOfPoints(Point[] graphSortedByX, Point[] graphSortedByY){
		// Base case of recursion
		if (graphSortedByX.length <=3) return bruteForcePairOfPoints(graphSortedByX, graphSortedByY); 
		
		Point[] minimumPair = new Point[2];
		double minimumDistance;
		
		int graphMidpoint = graphSortedByX.length/2;
		
		Point[] leftHalfGraphX = new Point[graphMidpoint];
		Point[] rightHalfGraphX = new Point[graphSortedByX.length - graphMidpoint];

		Point[] leftHalfGraphY = new Point[graphMidpoint];
		Point[] rightHalfGraphY = new Point[graphSortedByX.length - graphMidpoint];

		for (int idx = 0; idx < graphMidpoint; idx++){
			leftHalfGraphX[idx] = graphSortedByX[idx];
			leftHalfGraphY[idx] = graphSortedByY[idx];
		}

		for (int idx = graphMidpoint; idx < graphSortedByX.length; idx++){
			rightHalfGraphX[idx - graphMidpoint] = graphSortedByX[idx];
			rightHalfGraphY[idx - graphMidpoint] = graphSortedByY[idx];
		}
		
		Point[] leftHalfMinPair = closestPairOfPoints(leftHalfGraphX, leftHalfGraphY);
		double leftHalfMinDistance = distanceBetweenPoints(leftHalfMinPair[0],leftHalfMinPair[1]);
		Point[] rightHalfMinPair = closestPairOfPoints(rightHalfGraphX, rightHalfGraphY);
		double rightHalfMinDistance = distanceBetweenPoints(rightHalfMinPair[0],rightHalfMinPair[1]);
		

		if (leftHalfMinDistance < rightHalfMinDistance) {
			minimumPair = leftHalfMinPair;
			minimumDistance = leftHalfMinDistance;
		}
		else {
			minimumPair = rightHalfMinPair;
			minimumDistance = rightHalfMinDistance;
		}
		

		ArrayList<Point> filtered = new ArrayList<Point>();
		double leftBoundary = graphSortedByX[graphMidpoint].x - minimumDistance; 
		double rightBoundary = graphSortedByX[graphMidpoint].x + minimumDistance;
		for (int idx = 0; idx < graphSortedByY.length; idx++){
			if (leftBoundary < graphSortedByY[idx].x && graphSortedByY[idx].x < rightBoundary){
				filtered.add(graphSortedByY[idx]);
			}
		}
		
		for (int idx = 0; idx < (filtered.size() - 1); idx++){
			for (int j = idx + 1; j < Math.min(filtered.size(), idx + 7); j++){
				double dist = distanceBetweenPoints(filtered.get(idx),filtered.get(j));
				if (dist < minimumDistance) {
					minimumDistance = dist;
					minimumPair[0] = filtered.get(idx);
					minimumPair[1] = filtered.get(j);
				}
			}
		}
		return minimumPair;
	}
	
	/**
	 * Brute force (O(n^2)) implementation to find the closest pair of points in a graph. This method is used
	 * as a base case routine in the efficient solution for solving the closest pair of points.
	 * 
	 * @param pointsSortedByX The collection of points representing a graph sorted by their X-component.
	 * @param pointsSortedByY The collection of points representing a graph sorted by their Y-component.
	 * @return An array of exactly 2 Point objects that comprise the closest pair of points in the given graph.
	 */
	private static Point[] bruteForcePairOfPoints(Point[] pointsSortedByX, Point[] pointsSortedByY){
		
		// Initialize an arbitrary minimum pair of points and their distance
		double min = distanceBetweenPoints(pointsSortedByX[0], pointsSortedByX[1]); 
		Point[] pair = new Point[2];
		pair[0] = pointsSortedByX[0];
		pair[1] = pointsSortedByX[1];
		
		// Cycle through each point and calculate the distance to every other point in the graph.
		for (int i = 0;i < pointsSortedByX.length;i++){
			for (int j = i + 1;j < pointsSortedByX.length;j++){
				
				double dist1 = distanceBetweenPoints(pointsSortedByX[i],pointsSortedByX[j]);
				double dist2 = distanceBetweenPoints(pointsSortedByY[i],pointsSortedByY[j]);
				
				if (dist1 < min) {
					min = dist1;
					pair[0] = pointsSortedByX[i];
					pair[1] = pointsSortedByX[j];
				}
				if (dist2 < min) {
					min = dist1;
					pair[0] = pointsSortedByY[i];
					pair[1] = pointsSortedByY[j];
				}
			}
		}
		return pair;
	}
	
	/**
	 * Outputs the given log statements to the command line if the number of lines to print is less than 30, otherwise
	 * writes the lines to the specified file.
	 * 
	 * @param logger ArrayList containing all of the log statements to output.
	 * @param fileName The name of the file that logs should be written to, null value indicates that the output should be printed to the console.
	 */
	public static void outputTestResults(ArrayList<String> logger, String fileName) {
		if(fileName == null) {
			for(String msg : logger) {
				System.out.println(msg);
			}
		}
		else {
			FileWriter writer;
			try {
				writer = new FileWriter(fileName);
				for(String msg : logger) {
					writer.write(msg + System.lineSeparator());
				}
				writer.close();
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
}
