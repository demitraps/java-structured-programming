package gr.aueb.cf.ch10;

import java.util.Arrays;

/**
 * * The {@link Project06LargestSumContiguousSubArrayApp} class
 * is responsible for finding
 * the largest sum of a contiguous sub-array
 * within a given array.
 * For example, if the given array is {-2, 1, -3, 4, -1, 2, 1, -5, 4},
 * the largest sum is 6, of the sub-array {4, -1, 2, 1}.
 * The app uses <b>Kadane's algorithm</b>
 * and initially checks the following:
 * a. if the array is empty
 * b. if the array contains all non-negative numbers
 * c. if the array contains all non-positive numbers.
 * In the case of b., the problem is trivial, as a maximum sub-array is the entire array.
 * In the case of c., the solution is any sub-array, of size one,
 * containing the maximal value of the array.
 * The program prints the maximum sum if none of the above cases is true,
 * along with the starting and ending index of the sub-array.
 *
 * @author demitra
 */
public class Project06LargestSumContiguousSubArrayApp {
    /**
     * The globalMaximum variable stores
     * the maximum sum found during the execution of the maxSubArraySum method.
     */
    public static int globalMaximum = Integer.MIN_VALUE;

    /**
     * The main method is the entry point of the program.
     * It initializes test arrays and calls the maxSubArraySum method.
     * @param args      The command-line arguments passed to the program.
     */
    public static void main(String[] args) {
        int testArray[] = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        maxSubArraySum(testArray);
    }

    /**
     * The maxSubArraySum method finds
     * the largest sum of a contiguous sub-array within the given array.
     * It prints the globalMaximum, start, and end indices
     * of the sub-array with the largest sum.
     *
     * @param array     The input array in which the largest sum
     *                  of a contiguous sub-array needs to be found.
     */
    public static void maxSubArraySum(int array[]) {
        int localMaximum = 0;
        int start = 0;
        int end = 0;
        int pivot = 0;
        int positiveCount = 0;
        int negativeCount = 0;

        // Initial checks
        if (array.length == 0) {
            System.out.println("Zero elements in array.");
            System.exit(0);
        }

        // Count the number of positive and negative elements in the array
        for (int i = 0; i < array.length; i ++) {
            if (array[i] > 0) {
                positiveCount++;
            } else if (array[i] < 0) {
                negativeCount++;
            } else {
                positiveCount++;
                negativeCount++;
            }
        }

        // Handle special cases where all elements are positive or negative
        if (positiveCount == array.length) {
            System.out.println("All elements in the array are non-negatives.");
            System.out.println("The maximum sub-array is the entire array.");
            System.out.println("The sum of the array is " + Arrays.stream(array).sum() + ".");
            System.exit(0);
        }
        if (negativeCount == array.length) {
            System.out.println("All elements in the array are non-positives.");
            System.out.println("The solution is any sub-array, of size one, containing the maximal value of the array.");
            System.out.println("The maximum value is " + Arrays.stream(array).max().getAsInt() + ".");
            System.exit(0);
        }

        // Find the largest sum of a contiguous sub-array
        for (int i = 0; i < array.length; i ++) {
            localMaximum += array[i];

            if (globalMaximum < localMaximum) {
                globalMaximum = localMaximum;
                start = pivot;
                end = i;
            }

            if (localMaximum < 0) {
                localMaximum = 0;
                pivot = i + 1;
            }
        }

        // Print the largest sum and the indices of the sub-array
        System.out.println("The largest sum of a contiguous sub-array is " + globalMaximum + ".");
        System.out.println("The starting index of the sub-array is: " + start + ".");
        System.out.println("The ending index of the sub-array is: " + end + ".");
    }
}

