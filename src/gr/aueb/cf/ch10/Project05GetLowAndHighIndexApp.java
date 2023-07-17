package gr.aueb.cf.ch10;

/**
 * The {@link Project05GetLowAndHighIndexApp} class
 * finds and prints lowest and highest index of a key in a given sorted array,
 * after checking if the key exists in said array.
 * For example, assume the given array is {0, 1, 4, 4, 4, 6, 7, 8, 8, 8, 8, 8}:
 * and the key is 8.
 * The lowest index is in position 7 and the highest in position 11.
 *
 * @author demitra
 */
public class Project05GetLowAndHighIndexApp {

    public static void main(String[] args) {
        int[] array = {0, 1, 4, 4, 4, 6, 7, 8, 8, 8, 8, 8}; //test array
        int key = 8;

        int[] lowHigh = getLowAndHighIndexOf(array, key);

        if (lowHigh.length == 0) {
            System.out.println("The key was not found in given array.");
            System.exit(0);
        }

        System.out.printf("The lowest position of %d is %d and the highest is %d.", key, lowHigh[0], lowHigh[1]);
    }

    /**
     * Finds first (low) index of key, if it exists.
     * The finds the last (high).
     *
     * @param array     given array.
     * @param key       searched key.
     * @return          array of two items, low and high indexes.
     */
    public static int[] getLowAndHighIndexOf(int[] array, int key) {
        int low = -1;
        int high = 0;
        int pivot;

        //check if array provided is null
        if (array == null) return new int[] {};

        for (int i = 0; i < array.length; i++) {
            if(array[i] == key) {
                low = i;
                break;
            }
        }

        //check if key was not found
        if (low == -1) return new int[] {};

        high = low; //if only one occurrence
        pivot = low + 1;

        while ((pivot < array.length) && (array[pivot++] == key)) {
            high++;
        }
        return new int[] { low, high };
    }
}
