package gr.aueb.cf.ch10;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Reads integers from a file
 * until it finds sentinel -1.
 * The file must contain more than 6 integers, within the 1 - 49 range (inclusive).
 * The integers are inserted to an array and are sorted.
 * The program produces all possible combinations of 6 integers
 * that follow the following criteria:
 * 1. the combination can have up to 4 even integers
 * 2. the combination can have up to 4 odd integers
 * 3. the combination can have up to 2 consecutive integers
 * 4. the combination can have up to 3 integers with the same last digit
 * 5. the combination can have up to 3 integers within the same decade (eg. 1 - 9, 11 - 19 etc.).
 * The final list of combinations are printed in a .txt file.
 *
 * @author demitra
 */
public class Project01Lotto6App {
    static final int MAX_ODD = 4;
    static final int MAX_EVEN = 4;
    static final int MAX_CONSECUTIVE = 2;
    static final int MAX_ENDING = 3;
    static final int MAX_DECADE = 3;

    public static void main(String[] args) {
        try (Scanner in = new Scanner(new File("/Users/demitra/Documents/lotto6in.txt"));
             PrintStream ps = new PrintStream("/Users/demitra/Documents/lotto6out.txt", StandardCharsets.UTF_8)) {

            final int LOTTO_SIZE = 6;
            final int SENTINEL = -1;
            final int MAX_PIVOT = 48; //max index of the fileNumbers array
            int[] fileNumbers = new int[49];
            int pivot = 0; //position of first free position
            int num;

            while ((num = in.nextInt()) != SENTINEL && pivot <= MAX_PIVOT) {
                if (num >= 1 && num <= 49) { // check if num is within the 1 - 49 range
                    fileNumbers[pivot] = num;
                    pivot++;
                }
            }

            int[] numbers = Arrays.copyOfRange(fileNumbers, 0, pivot);
            int n = numbers.length;

            try {
                if (n > 6) {
                    Arrays.sort(numbers);
                } else {
                    throw new IllegalArgumentException("File contains less numbers than required (under 7).");
                }
            } catch (IllegalArgumentException e) {
                log(e);
                throw e;
            }

            printCombinations(ps, numbers, n, LOTTO_SIZE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a temporary array, result, to store each combination.
     * Calls assignResults to print all combinations.
     *
     * @param ps            PrintStream for output file
     * @param numbers       array of sorted numbers, provided by input file
     * @param n             length of numbers array
     * @param lotto_size    number of elements in each combination
     */
    public static void printCombinations(PrintStream ps, int[] numbers, int n, int lotto_size) {
        int[] result = new int[lotto_size];
        assignResults(ps, numbers, result, 0, n - 1, 0, lotto_size);
    }

    /**
     * Creates all possible combinations from the numbers array provided.
     * Prints each combination that passes the criteria.
     *
     * @param ps                PrintStream for output file
     * @param numbers           array of sorted numbers, provided by input file
     * @param result            temporary array to store each combination
     * @param start             pointer of first index in the numbers array
     * @param end               pointer of last index in the numbers array
     * @param result_index      pointer of current index in result array
     * @param lotto_size        number of elements in each combination
     */
    public static void assignResults(PrintStream ps, int[] numbers, int[] result, int start, int end , int result_index, int lotto_size) {
        // When a combination is ready to be printed
        if (result_index == lotto_size) {
            if (!isEvenExceeding(result, MAX_EVEN) && !isOddExceeding(result, MAX_ODD) && !isConsecutiveExceeding(result,MAX_CONSECUTIVE)
                    && !isSameEndingExceeding(result, MAX_ENDING) && !isInSameDecadeExceeding(result, MAX_DECADE)) {
                for (int i = 0; i < lotto_size; i++) {
                    ps.print(result[i] + " ");
                }
                ps.println();
            }
            return;
        }

        //recursion
        //end - j + 1 >= lotto_size - result_index ensures that there are enough remaining elements in the numbers array to form a complete combination.
        for (int j = start; j <= end && end - j + 1 >= lotto_size - result_index; j++) {
            result[result_index] = numbers[j];
            assignResults(ps, numbers, result, j + 1, end, result_index + 1,  lotto_size);
        }
    }

    /**
     * Returns true if the number of even integers is greater than a threshold limit.
     *
     * @param arr           the input array.
     * @param threshold     the upper limit of the constraint.
     * @return              true, if the number of even integers is greater than threshold
     *                      false, if the number of even integers is less than threshold or equal to the threshold.
     */
    public static boolean isEvenExceeding(int[] arr, int threshold) {
        int evenCount = 0;

        for (int num : arr) {
            if (num % 2 == 0) evenCount++;
        }
        return evenCount > threshold;
    }

    /**
     * Returns true if the number of odd integers is greater than a threshold limit.
     *
     * @param arr       the input array.
     * @param threshold the upper limit of the constraint.
     * @return  true, if the number of odd integers is greater than threshold
     *          false, if the number of odd integers is less than threshold or equal to the threshold.
     */
    public static boolean isOddExceeding(int[] arr, int threshold) {
        int oddCount = 0;

        for (int num : arr) {
            if (num % 2 != 0) oddCount++;
        }
        return oddCount > threshold;
    }

    /**
     * Returns true if the number of consecutive integers is greater than a threshold limit.
     * For two numbers to be consecutive, the absolute value of their difference should be 1.
     *
     * @param arr       the input array.
     * @param threshold the upper limit of the constraint.
     * @return  true, if the number of consecutive integers is greater than threshold
     *          false, if the number of consecutive integers is less than threshold or equal to the threshold.
     */
    public static boolean isConsecutiveExceeding(int[] arr, int threshold) {
        int consecutiveCount = 0;
        for (int i = 1; i < arr.length; i++) {
            if (Math.abs(arr[i] - (arr[i-1])) == 1 ){
                consecutiveCount++;
            }
        }

        return consecutiveCount > threshold;
    }

    /** Returns true if the number of integers with the same last digit is greater than a threshold limit.
     *
     * @param arr       the input array.
     * @param threshold the upper limit of the constraint.
     * @return          true, if the number of integers with the same last digit is greater than threshold
     *                  false, if the number of integers with the same last digit is less than threshold or equal to the threshold.
     */
    public static boolean isSameEndingExceeding(int[] arr, int threshold) {
        boolean isExceeding = false;
        int[] helperArr = new int[10];
        for (int i : arr) {
            helperArr[(i % 10)]++;
        }
        for (int num : helperArr) {
            if (num > threshold) {
                isExceeding = true;
                break;
            }
        }
        return isExceeding;
    }

    /** Returns true if the number of integers within the same decade (eg. 1 - 9, 10 - 19, 20 - 29 etc.)
     *  is greater than a threshold limit.
     *
     * @param arr       the input array.
     * @param threshold the upper limit of the constraint.
     * @return          true, if the number of integers with the same last digit is greater than threshold
     *                  false, if the number of integers with the same last digit is less than threshold or equal to the threshold.
     */
    public static boolean isInSameDecadeExceeding(int[] arr, int threshold) {
        boolean isExceeding = false;
        int[] helperArr = new int[5];
        for (int i : arr) {
            helperArr[(i / 10)]++;
        }
        for (int num : helperArr) {
            if (num > threshold) {
                isExceeding = true;
                break;
            }
        }
        return isExceeding;
    }

    /**
     * Error logging.
     * @param e     Exception
     * @param message Exception message
     */
    public static void log(Exception e, String...message) {
        Path path = Paths.get("/Users/demitra/Documents/log.txt");

        try (PrintStream ps = new PrintStream(new FileOutputStream(path.toFile(), true))) {
            ps.println(LocalDateTime.now() + "\n" + e);
            ps.printf("%s", message.length == 1 ? message[0] : "");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
