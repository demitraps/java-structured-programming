package gr.aueb.cf.ch10;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The {@link Project10TheaterSeatsApp} class
 * represents a theater seat booking system.
 * It allows users to view the seat availability,
 * book seats, and cancel bookings.
 *
 * @author demitra
 */
public class Project10TheaterSeatsApp {
    public static  String[] columns = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
    public static String[][] seats = new String[360][2];
    public static Scanner in = new Scanner(System.in);
    public static int choice = 0;
    public static String seatSelected;

    public static void main(String[] args) {
        createSeats();
        System.out.println("Welcome to the theater seat booking system. \uD83C\uDFAD");

        do {
            printMenu();
            try {
                choice = in.nextInt();
                in.nextLine(); //to consume newline

                if (choice < 1 || choice > 4) {
                    System.out.println("Invalid input. Please select a number from 1 to 4.");
                }

                switch (choice) {
                    case 1:
                        printSeating();
                        break;
                    case 2:
                        seatSelected = getSeatSelection();
                        book(seatSelected);
                        break;
                    case 3:
                        seatSelected = getSeatSelection();
                        cancel(seatSelected);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        break;
                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please select a number from 1 to 4.");
                in.nextLine(); //consume the invalid input
            }
        } while (choice != 4);
        System.out.println("Goodbye!");
    }

    /**
     * Creates the theater seats and initializes their availability status.
     */
    public static void createSeats() {
        int index = 0;
        for (int i = 1; i <= 30; i++) {
            for (int col = 0; col < columns.length; col++) {
                String seat = columns[col] + i;
                seats[index][0] = seat;
                seats[index][1] = "available";
                index++;
            }
        }
    }

    /**
     * Prints the main menu options for the user.
     */
    public static void printMenu() {
        System.out.println("Select one of the actions below: ");
        System.out.println("1. View seat chart availability.");
        System.out.println("2. Book a seat.");
        System.out.println("3. Cancel a booking.");
        System.out.println("4. Exit.");
        System.out.print("Type in your selection ( 1 - 4): ");

    }

    /**
     * Prints the seating chart with seat availability.
     */
    public static void printSeating() {
        int index = 0;
        System.out.println("  A B  C D E F G H I J K L");
        for (int row = 1; row <= 30; row++) {
            System.out.printf("%02d",row);
            for (int col = 0; col < columns.length; col++) {
                if (seats[index][1].equals("available")) {
                    System.out.print("\u2705");
                } else {
                    System.out.print("\u2611\uFE0F");
                }
                index++;
            }
            System.out.println();
        }
    }

    /**
     * Prompts the user to enter a seat selection and returns it as a String.
     *
     * @return      the seat selection entered by the user.
     */
    public static String getSeatSelection() {
        String selection;
        System.out.print("Type in your selection, eg. C2: ");
        selection = in.nextLine();
        return selection;
    }

    /**
     * Books a seat based on the user's seat selection.
     *
     * @param seat      the seat to be booked.
     */
    public static void book(String seat) {
        boolean seatValid = false;
        for (int i = 0; i < 360; i++) {
            if (seats[i][0].equals(seat)) {
                if (seats[i][1].equals("available")){
                    seats[i][1] = "taken";
                    System.out.printf("Seat %s has been booked.\n", seat);
                    seatValid = true;
                    break;
                } else {
                    System.out.println("Seat already taken. Try again");
                    seatValid = true;
                    break;
                }
            }
        }
        if (!seatValid) {
            System.out.println("The seat combination is invalid. Try again.");
        }
    }

    /**
     * Cancels a booking for the specified seat.
     *
     * @param seat      the seat to be cancelled
     */
    public static void cancel(String seat) {
        boolean seatValid = false;
        for (int i = 0; i < 360; i++) {
            if (seats[i][0].equals(seat)) {
                if (seats[i][1].equals("taken")){
                    seats[i][1] = "available";
                    System.out.printf("Reservation for seat %s has been cancelled.\n", seat);
                    seatValid = true;
                    break;
                } else {
                    System.out.println("Seat has not been booked yet. Try again");
                    getSeatSelection();
                    seatValid = true;
                    break;
                }
            }
        }
        if (!seatValid) {
            System.out.println("The seat combination is invalid. Try again.");
        }
    }
}
