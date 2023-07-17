package gr.aueb.cf.ch10;

import java.util.Arrays;

/**
 * {@link Project07Array2DShallowAndDeepCopyApp} class
 provides methods to create shallow and deep copies of a 2D array.
 * It demonstrates how both methods work and
 * prints their string representations.
 *
 * @author demitra
 */
public class Project07Array2DShallowAndDeepCopyApp {

    public static void main(String[] args) {
        int[][] array = {{5, 10, 15, 20}, {25, 30, 35, 40, 45}, {50, 55, 60}, {65, 70}, {75, 80, 85, 90, 95}};

        // Shallow copy demonstration
        int[][] shallowCopyArray = shallowCopy(array);
        System.out.println("Original array: " + Arrays.deepToString(array));
        System.out.println("Shallow copy array: " + Arrays.deepToString(shallowCopyArray));

        // Deep copy demonstration
        int[][] deepCopyArray = deepCopy(array);
        System.out.println("Original array: " + Arrays.deepToString(array));
        System.out.println("Deep copy array: " + Arrays.deepToString(deepCopyArray));

        // Modification of the original array
        array[0][0] = 100;

        // Check the changes in each array
        System.out.println("Original array after modification: " + Arrays.deepToString(array));
        System.out.println("Shallow copy array after modification: " + Arrays.deepToString(shallowCopyArray));
        System.out.println("Deep copy array after modification: " + Arrays.deepToString(deepCopyArray));
    }

    /**
     * Creates a shallow copy of a 2D array.
     *
     * @param array      the 2D array provided.
     * @return           a shallow copy of the 2D array provided.
     */
    public static int[][] shallowCopy(int[][] array) {
        int[][] shallowCopiedArray = Arrays.copyOf(array, array.length);
        return shallowCopiedArray;
    }

    /**
     * Creates a deep copy of a 2D array.
     *
     * @param array     the 2D array provided.
     * @return          a deep copy of the 2D array provided.
     */
    public static int[][] deepCopy(int[][] array) {
        int[][] deepCopiedArray = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            deepCopiedArray[i] = new int[array[i].length];
            for (int j = 0; j < array[i].length; j++) {
                deepCopiedArray[i][j] = array[i][j];
            }
        }
        return deepCopiedArray;
    }
}
