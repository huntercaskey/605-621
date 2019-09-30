package closestpairs;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * ClosestPairs - This java class provides implementation for finding the closest pair of Point in a graph.
 * Provided are two solutions to this problem - one brute force method that runs in O(n^2) time and 
 * an efficient solution TODO
 * 
 * @author Hunter Caskey
 *
 */
public class ClosestPairs {
	
	private final static boolean DEBUG = true;
	
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

		Point[] a = {
				new Point(2, 3),
				new Point(12, 30),
				new Point(40, 50),
				new Point(5, 1),
				new Point(12, 10),
				new Point(3, 4)
		};
//		Random ran = new Random();
//		for (int i=0;i<30;i++){
//			a[i] = new Point(ran.nextInt(101),ran.nextInt(101));
//		}
		
		Point[] closestPair = closestPair(a);
		Point[] bruteforce = bruteForce(a,a);
		
		System.out.println("By the Brute Force method of O(n^2), the closest points are");
		System.out.println("Point:    x = " + bruteforce[0].x + " y= " + bruteforce[0].y);
		System.out.println("Point:    x = " + bruteforce[1].x + " y= " + bruteforce[1].y);
		System.out.println("The distances between them is " + distance(bruteforce[0],bruteforce[1]));
		System.out.println();
		System.out.println("By the divide and conquer method of O(nlog(n)), the closest Point are:");
		System.out.println("Point:    x = " + closestPair[0].x + " y= " + closestPair[0].y);
		System.out.println("Point:    x = " + closestPair[1].x + " y= " + closestPair[1].y);
		System.out.println("The distances between them is " + distance(closestPair[0],closestPair[1]));
		
	}
	
	private static double distance(Point a, Point b){
		double xComponent = Math.pow(b.x - a.x, 2);
		double yComponent = Math.pow(b.y - a.y, 2);
		return Math.sqrt(xComponent + yComponent);
	}
	
	public static Point[] closestPair(Point[] a){
		//create two copies of the original array 
		//sort each array according to the x coordinates and coordinates
		Point[] sortByX = new Point[a.length]; 
		Point[] sortByY = new Point[a.length];
		for (int i = 0;i < a.length;i++){
			sortByX[i] = a[i];
			sortByY[i] = a[i];
		}
		Arrays.sort(sortByX, Point.sortX);
		Arrays.sort(sortByY, Point.sortY);
		return closest(sortByX, sortByY);
	}
	
	private static Point[] closest(Point[] sortByX, Point[] sortByY){
		if (sortByX.length <=3) return bruteForce(sortByX, sortByY); //base case of recursion: brute force when size is small
		
		Point[] pair = new Point[2];
		double min;
		int center = sortByX.length /2;
		
		//separate the two arrays to left half and right half
		Point[] leftHalfX = new Point[center];
		Point[] rightHalfX = new Point[sortByX.length - center];

		Point[] leftHalfY = new Point[center];
		Point[] rightHalfY = new Point[sortByX.length - center];

		for (int i = 0;i < center;i++){
			leftHalfX[i] = sortByX[i];
			leftHalfY[i] = sortByY[i];
		}

		for (int i = center;i < sortByX.length;i++){
			rightHalfX[i-center] = sortByX[i];
			rightHalfY[i-center] = sortByY[i];
		}
		
		//independently find the closest pair of left half and right half
		Point[] pair1 = closest(leftHalfX, leftHalfY);
		double min1 = distance(pair1[0],pair1[1]);
		Point[] pair2 = closest(rightHalfX, rightHalfY);
		double min2 = distance(pair2[0],pair2[1]);
		
		//set the closest pair of the smaller of the previous two
		//calculate the closest distance
		if (min1 < min2) {
			pair = pair1;
			min = min1;
		}else{
			pair = pair2;
			min = min2;
		}
		
		//find closest "split" pairs
		//generate a strip of Point whose x-coordinates are within closest distance of the center of sortByX
		//using ArrayList instead of Arrays for filtered data to allow for dynamic size
		ArrayList<Point> filtered = new ArrayList<Point>();
		double leftBoundary = sortByX[center].x - min; 
		double rightBoundary = sortByX[center].x + min;
		for (int i = 0; i<sortByY.length; i++){
			if (leftBoundary < sortByY[i].x && sortByY[i].x < rightBoundary){
				filtered.add(sortByY[i]);
			}
		}
		
		//if the closest pair p and q is a "split" pair, their positions in filtered data is at most 7 positions apart
		for (int i = 0; i < (filtered.size()-1);i++){
			for (int j = i + 1; j < Math.min(filtered.size(), i + 7);j++){
				double dist = distance(filtered.get(i),filtered.get(j));
				if (dist < min){
					min = dist;
					pair[0] = filtered.get(i);
					pair[1] = filtered.get(j);
				}
			}
		}
		return pair;
	}

	private static Point[] bruteForce(Point[] sortByX, Point[] sortByY){
		//brute force to find the closest pair when the size is small enough
		double min = distance(sortByX[0],sortByX[1]);
		Point[] pair = new Point[2];
		pair[0] = sortByX[0];
		pair[1] = sortByX[1];
		for (int i = 0;i < sortByX.length;i++){
			for (int j = i + 1;j < sortByX.length;j++){
				double dist1 = distance(sortByX[i],sortByX[j]);
				double dist2 = distance(sortByY[i],sortByY[j]);
				if (dist1 < min) {
					min = dist1;
					pair[0] = sortByX[i];
					pair[1] = sortByX[j];
				}
				
				if (dist2 < min) {
					min = dist1;
					pair[0] = sortByY[i];
					pair[1] = sortByY[j];
				}
			}
		}
		return pair;
	}
	
}
