package gr.aueb.cf.ch10;

import java.util.Arrays;
import java.util.Comparator;

/**
 * The {@link Project04MaxCarArrivalsApp} class
 * calculates the maximum number of cars parked at the same time in a hypothetical garage.
 * The data is provided in a 2D array of the following format:
 * arr[][] = { {1012, 1136}, {1317, 1417}, {1015, 1020} }
 * where the first car arrived at 10:12 and departed at 11:36,
 * the second car arrived at 13:17 and departed at 14:17 etc.
 * The app creates a helper 2D array, that marks each timestamp as 1 if it refers to an arrival
 * and 0 if it refers to departure.
 * By sorting the helper array in ascending order, it traverses it to count the concurrent cars.
 *
 * Finally, it prints the max count.
 *
 * @author demitra
 */
public class Project04MaxCarArrivalsApp {

    public static void main(String[] args) {
        int[][] carArray = { {1012, 1056}, {1022, 1050}, {1317, 1417}, {1025, 1325}, {1027, 1200}, {1319, 1357} }; //test array

        int[][] organizedArray = organizeArray(carArray);
        sortByTime(organizedArray);

        int maxCount = calculateMaxConcurrentCars(organizedArray);

        System.out.println("The maximum number of cars that are parked at the same in the garage is: " + maxCount + ".");
    }

    /**
     * Organizes the initial array into a new 2D array (2*array.length x 2)
     * where the first column has the timestamps
     * and the second 1, if the time refers to an arrival
     *                0, if the time refers to a departure.
     * @param array     initial array of car arrival and departure information
     * @return          organized array that labels timestamps.
     */
    public static int[][] organizeArray(int[][] array) {
        int[][] organized = new int[array.length * 2][2];

        for (int i = 0; i < array.length; i++) {
            organized[i * 2][0] = array[i][0];      //first position contains arrival time
            organized[i * 2][1] = 1;                //arrival is marked with 1
            organized[i * 2 + 1][0] = array[i][1];  //second position contains departure time
            organized[i * 2 + 1][1] = 0;            //departure is marked with 0
        }
        return organized;
    }

    /**
     * Sorts the organized array by timestamp in ascending order.
     *
     * @param array organized array
     */
    public static void sortByTime(int[][] array) {
        Arrays.sort(array, Comparator.comparing((int[] a) -> a[0]));
    }

    /**
     * Calculates the maximum number of cars parked at the same time.
     * Traverses the organized array and increases the count if the timestamp is an arrival(1)
     * or decreases the count if it is a departure(0).
     *
     * @param array     organized array
     * @return          max count.
     */
    public static int calculateMaxConcurrentCars(int[][] array) {
        int runningCount = 0;
        int maxCount = 0;

        for(int[] row : array) {
            if (row[1] == 1) {      //if the timestamp is arrival
                runningCount++;     // increases count
                if (runningCount > maxCount) maxCount = runningCount; //checks if current count is the max count
            } else runningCount --; // if timestamp is departure
        }

        return maxCount;
    }
}
