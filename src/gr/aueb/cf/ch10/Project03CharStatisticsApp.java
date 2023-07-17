package gr.aueb.cf.ch10;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

/**
 * The {@link Project03CharStatisticsApp} class
 * reads one-by-one, all characters(any UTF-8 character) of a file and
 * inputs them in a 2D array, 256x2.
 * Each row has two positions, one for the character
 * and one of its occurrence (count) within the text.
 * main() prints out statistics for each character,
 * sorted by character and
 * by count.
 *
 * @author demitra
 */
public class Project03CharStatisticsApp {
    final static Path path = Paths.get("/Users/demitra/Documents/tmp/log.txt");
    final static int[][] characterList = new int[256][2];
    static int pivot = 0; // pointer of index of the first free position in array characterList
    static int totalCount = 0;

    public static void main(String[] args) {
        try {
            readFile();
            printStats();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads text from input file.
     * Each character is saved into characterList array.
     *
     * @throws IOException                  if filename invalid.
     * @throws IllegalArgumentException     if error in assigning/saving character (list full).
     */
    public static void readFile() throws IOException, IllegalArgumentException {
        byte[] buffer = new byte[256];
        int next = 0;

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("/Users/demitra/Documents/pangrams.txt"))) {
            while ((next = bis.read(buffer)) != -1) {
                for (int i = 0; i < next; i++) {
                    if (!assignChar(buffer[i])){
                        throw new IllegalArgumentException("Error bis assigning character.");
                    } else {
                        totalCount++;
                    }
                }
            }
        } catch (IOException | IllegalArgumentException e){
            log(e, "Error in input");
            throw e;
        }
    }

    /**
     * Prints statistics sorted by
     * character and
     * by occurrence,
     * ascending.
     */
    public static void printStats() {
        int[][] finalCharList = Arrays.copyOfRange(characterList, 0, pivot);

        //Statistics sorted by character
        Arrays.sort(finalCharList, Comparator.comparing(a -> a[0]));
        System.out.println("\uD83D\uDCCA Statistics of characters, ascending: ");
        for(int[] elements : finalCharList) {
            System.out.printf("Character: %c found in text %d times, a total percentage of %.2f%%.\n", elements[0], elements[1], (elements[1]/ (double) totalCount));
        }

        //Statistics sorted by occurrence
        Arrays.sort(finalCharList, Comparator.comparing(a -> a[1]));
        System.out.println("\uD83D\uDCCA Statistics by count, ascending: ");
        for(int[] elements : finalCharList) {
            System.out.printf("Character: %c found in text %d times, a total percentage of %.2f%%.\n", elements[0], elements[1], (elements[1]/ (double) totalCount));
        }
    }

    /**
     * Assigns character in a new position, if firstly introduced. Increases its count of occurence.
     * If previously assigned, just increases its count.
     *
     * @param ch    character read from file.
     * @return      true, if character successfully assigned/increased count.
     *              false, if assignment failed due to list being full.
     */
    public static boolean assignChar(int ch) {
        int charPosition = -1;

        //check if characterList is full
        if (isListFull(characterList)) return false;

        charPosition = getCharPosition(ch);

        if (charPosition == -1) {           //new character added
            characterList[pivot][0] = ch;
            characterList[pivot][1]++;      //increases count of character's occurrence
            pivot++;                        //moves pointer one spot to the next free position
        } else {                            //character already assigned
            characterList[charPosition][1]++;
        }
        return true;
    }

    /**
     * Gets position(index) of character in the characterList array.
     * @param ch    int, representing char
     * @return      index of character in characterList array, if already existing.
     *              -1, if not.
     */
    public static int getCharPosition(int ch) {
        for (int i = 0; i <= pivot - 1; i++) {
            if(characterList[i][0] == ch) return i;
        }
        return -1;
    }

    /**
     * Checks if the array containing the characters and their counts is full.
     *
     * @param charList     array of characters.
     * @return             true, if array is full.
     *                     false, if not.
     */
    public static boolean isListFull(int[][] charList) {
        return pivot == charList.length;
    }

    /*
     * Error logging.
     */
    public static void log(Exception e, String... message) {

        try (PrintStream ps = new PrintStream(new FileOutputStream(path.toFile(), true))) {
            ps.println(LocalDateTime.now() + "/n" + e);
            ps.printf("%s", message.length == 1 ? message[0] : "");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
