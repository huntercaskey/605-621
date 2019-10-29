package quicksort;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Hunter R. Caskey
 * 
 * Quicksort - This class implements two versions of the quicksort algorithm - one using an arbitrary 
 * choice of a pivot and one implementation choosing the median of the first, middle, and last element 
 * of each recursive partition. A test suite is run within the main routine to analysis the running time
 * difference between the two implementations. Consequently, execution of the main function results in 
 * the output for the tests to be written to a file named "quicksort_tests.log".
 */
public class Quicksort {
	
	private final static int MAX_CONSOLE_LOGS = 30;

	/**
	 * The entrypoint into the program.
	 * 
	 * @param args Command line arguments for the main driver of this program.
	 */
	public static void main(String[] args) {

		ArrayList<String> logger = new ArrayList<String>();
		
		int testNum = 1;
		int[] inputArray = new int[] {9,8,7,6,5,4,3,2,1,0};
		runTest(testNum++, 5, inputArray, logger);
		
		inputArray = new int[] {0,1,2,3,4,5,6,7,8,9};
		runTest(testNum++, 5, inputArray, logger);
		
		inputArray = new int[] {5,9,0,4,8,3,7,2,6,1};
		runTest(testNum++, 5, inputArray, logger);

		outputTestResults(logger, "quicksort_tests.log");
	}

	
	public static void runTest(int testNum, int numExecutions, int[] inputArray, ArrayList<String> logger) {
		int inputSize = inputArray.length;
		logger.add("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Test #" + testNum + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		logger.add(String.format("%-40s %-20s", "Input array to both algorithms: ", Arrays.toString(inputArray)));
		
		long medianTotalDuration = 0;
		long textbookTotalDuration = 0;
		
		int[] inputTextbook = null;
		int[] inputMedian = null;
		for(int i = 0; i < numExecutions; i++) {
			inputTextbook = Arrays.copyOf(inputArray, inputSize);
			long startTime = System.nanoTime();
			quicksort_textbook(inputTextbook, 0, inputSize - 1);
			long endTime = System.nanoTime();
			long duration = endTime - startTime;
			textbookTotalDuration += duration;
			logger.add("Textbook Quicksort Test #" + testNum + ", Execution #" + (i + 1) + " Time: " + duration);
			
			inputMedian = Arrays.copyOf(inputArray, inputSize);
			startTime = System.nanoTime();
			quicksort_median_of_three(inputMedian, 0, inputSize - 1);
			endTime = System.nanoTime();
			duration = endTime - startTime;
			medianTotalDuration += duration;
			logger.add("Median of Three Quicksort Test #" + testNum + ", Execution #" + (i + 1) + " Time: " + duration);
			
		}
		
		logger.add("\nTextbook Quicksort Average Time: " + (textbookTotalDuration / numExecutions));
		logger.add("Median of Three Quicksort Average Time: " + (medianTotalDuration / numExecutions));

		logger.add(String.format("%-40s %-20s", "Array after quicksort_textbook:", Arrays.toString(inputTextbook)));
		logger.add(String.format("%-40s %-20s", "Array after quicksort_median_of_three:", Arrays.toString(inputMedian)));
	}

	/**
	 * Performs a quicksort on an array by choosing the upper bound as an arbitrary pivot. 
	 * 
	 * @param inputArray The array to be sorted.
	 * @param lowerIndex The index specifying the lower bound of the range of the inputArray to partition.
	 * @param upperIndex The index specifying the upper bound of the range of the inputArray to partition.
	 */
	public static void quicksort_textbook(int[] inputArray, int lowerIndex, int upperIndex) {
		if(lowerIndex < upperIndex) {
			int pivotIndex = partition_textbook(inputArray, lowerIndex, upperIndex);
			quicksort_textbook(inputArray, lowerIndex, pivotIndex - 1);
			quicksort_textbook(inputArray, pivotIndex + 1, upperIndex);
		}
	}
	
	/**
	 * Partitions a subarray of inputArray such that all elements within the lower half of the subarray 
	 * are less than or equal to the elements within the upper half of the subarray. Arbitrarily
	 * choose the value at the upperIndex as a pivot value.
	 * 
	 * @param inputArray The array for which a range will be partitioned.
	 * @param lowerIndex The lower range bound of the subarray to partition.
	 * @param upperIndex The upper range bound of the subarray to partition.
	 * @return The index of the last element to be swapped from the partition operation.
	 */
	public static int partition_textbook(int[] inputArray, int lowerIndex, int upperIndex) {
		int pivotValue = inputArray[upperIndex];
		int swapIndex = lowerIndex - 1; 
		for(int iter = lowerIndex; iter <= upperIndex - 1; iter++) {
			if(inputArray[iter] <= pivotValue) {
				swapIndex++;
				exchange(inputArray, swapIndex, iter);
			}
		}
		swapIndex++;
		exchange(inputArray, swapIndex, upperIndex);
		return swapIndex;
	}
	
	/**
	 * Performs a quicksort on an array by choosing the median of the upper, middle, and lower 
	 * indices as a pivot.
	 * 
	 * @param inputArray The array to be sorted.
	 * @param lowerIndex The index specifying the lower bound of the range of the inputArray to partition.
	 * @param upperIndex The index specifying the upper bound of the range of the inputArray to partition.
	 */
	public static void quicksort_median_of_three(int inputArray[], int lowerIndex, int upperIndex) {
		if (lowerIndex < upperIndex) {
			int partitionIndex = partition_median_of_three(inputArray, lowerIndex, upperIndex);
			quicksort_median_of_three(inputArray, lowerIndex, partitionIndex-1);
			quicksort_median_of_three(inputArray, partitionIndex, upperIndex);
		}
	}

	/**
	 * Partitions a subarray of inputArray such that all elements within the lower half of the subarray 
	 * are less than or equal to the elements within the upper half of the subarray. Use the median of 
	 * lower, middle, and upper indices of the range to partition as the pivot point.
	 * 
	 * @param inputArray The array for which a range will be partitioned.
	 * @param lowerIndex The lower range bound of the subarray to partition.
	 * @param upperIndex The upper range bound of the subarray to partition.
	 * @return The index of the last element to be swapped from the partition operation.
	 */
	public static int partition_median_of_three(int inputArray[], int lowerIndex, int upperIndex) {
		int pivotValue = median_of_three(inputArray, lowerIndex, upperIndex);
		int swapIndex = lowerIndex - 1;

		for(int iter = lowerIndex; iter < upperIndex; iter++) {
			if (inputArray[iter] <= pivotValue) {
				swapIndex++;
				exchange(inputArray, swapIndex, iter);
			}
		}

		swapIndex++;
		exchange(inputArray, swapIndex, upperIndex);
		return swapIndex;
	}
	
	/**
	 * Arranges the supplied lowerIndex, upperIndex, and middle index between the two
	 * in increasing order and returns the median of the three elements (the middle 
	 * index after all swaps have been made). 
	 * 
	 * @param inputArray The array to be operated upon.
	 * @param lowerIndex The lower index that will be used to calculate the median of three.
	 * @param upperIndex The upper index that will be used to calculate the median of three.
	 * @return The median of three index, this is the de facto middle element of the range provided after swaps have occurred. 
	 */
	public static int median_of_three(int[] inputArray, int lowerIndex, int upperIndex) {
		int middleIndex = (lowerIndex + upperIndex) / 2;

		if(inputArray[lowerIndex] > inputArray[middleIndex]) {
			exchange(inputArray, lowerIndex, middleIndex);
		}
		if(inputArray[lowerIndex] > inputArray[upperIndex]) {
			exchange(inputArray, lowerIndex, upperIndex);
		}
		if(inputArray[middleIndex] > inputArray[upperIndex]) {
			exchange(inputArray, middleIndex, upperIndex);
		}
		return middleIndex;
	}
	
	/**
	 * Swaps two elements in a given array.
	 * 
	 * @param inputArray The array to perform the swap in. 
	 * @param indexA One of the elements that are being swapped.
	 * @param indexB The other element being swapped.
	 */
	public static void exchange(int[] inputArray, int indexA, int indexB) {
		int temp = inputArray[indexA];
		inputArray[indexA] = inputArray[indexB];
		inputArray[indexB] = temp;
	}

	/**
	 * Outputs the given log statements to the command line if the number of lines to print is less than 30, otherwise
	 * writes the lines to the specified file.
	 * 
	 * @param logger ArrayList containing all of the log statements to output.
	 * @param fileName The name of the file that logs should be written to, null value indicates that the output should be printed to the console.
	 */
	public static void outputTestResults(ArrayList<String> logger, String fileName) {
		if(logger.size() < MAX_CONSOLE_LOGS) {
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
