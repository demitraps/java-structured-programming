package gr.aueb.cf.ch10;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Phonebook app.
 * The {@link Project02PhonebookApp} class stores up to 500 contacts in an array.
 * Contact information include: surname, name, phone number.
 * Prints menu of options.
 * Actions that can be performed in phonebook:
 * 1. search contact by phone number
 * 2. insert contact
 * 3. update contact
 * 4. delete contact
 * 5. see all contacts
 * 6. exit phonebook.
 *
 * @author demitra
 */
public class Project02PhonebookApp {
    final static Scanner in = new Scanner(System.in);
    final static Path path = Paths.get("Users/demitra/Documents/tmp/logPhonebook.txt");
    final static String[][] contacts = new String[500][3];
    static int pivot = 0; // pointer of index of the first free position in array contacts

    public static void main(String[] args) {
        boolean quit = false;
        String choiceString;

        System.out.println("Welcome to your phonebook! \uD83D\uDCD2");

        do {
            printPhoneBookMenu();
            choiceString = getMenuChoice();
            if (choiceString.matches("[qQ]")) quit = true;
            else {
                try {
                    handleChoice(choiceString);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        } while (!quit);

        System.out.println("Exiting...");
        System.out.println("Goodbye! \uD83D\uDC4B");
    }

    /**
     * Handles choices 1 to 5.
     *
     * @param input input choice by user.
     */
    public static void handleChoice(String input) {
        int choice;
        String phoneNumber;

        try {
            choice = Integer.parseInt(input);

            if(!isValidChoice(choice)) throw new IllegalArgumentException("Error: number outside 1-5 range.");

            switch (choice) {
                case 1:
                    try {
                        phoneNumber = getPhoneNumber();
                        String[] searchedContact = searchContactByPhoneNumberService(phoneNumber);
                        printOneContact(searchedContact);
                        System.out.println();
                    } catch (IllegalArgumentException e) {
                        log(e, "Error in search");
                    }
                    break;
                case 2:
                    try {
                        printContactInputPrompt();
                        insertContactService(getLastName(), getFirstName(), getPhoneNumber());
                        System.out.println();
                    } catch (IllegalArgumentException e) {
                        log(e, "Error in insertion");
                        throw e;
                    }
                    break;
                case 3:
                    try {
                        printUpdatedContactInputPrompt();
                        updateContactService(getLastName(), getFirstName(), getPhoneNumber(), getPhoneNumber());
                        System.out.println();
                    } catch (IllegalArgumentException e) {
                        log(e, "Error in update");
                        throw e;
                    }
                    break;
                case 4:
                    try {
                        phoneNumber = getPhoneNumber();
                        deleteContactService(phoneNumber);
                        System.out.println();
                    } catch (IllegalArgumentException e) {
                        log(e, "Error in deletion");
                        throw e;
                    }
                    break;
                case 5:
                    try {
                        String[][] existingContacts = getAllContactsService();
                        printAllContacts(existingContacts);
                        System.out.println();
                    } catch (IllegalArgumentException e) {
                        log(e, "Error in printing all contacts");
                        throw e;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Unknown choice");
            }

        } catch (IllegalArgumentException e) {
            log(e);
            throw e;
        }
    }

    /**
     * Checks if menu choice, typed in by user, is valid.
     * @param choice    input by user.
     * @return          true, if within 1 - 5 range (inclusive).
     *                  false, if not.
     */
    public static boolean isValidChoice(int choice) {
        return ((choice >= 1 && (choice <= 5)));
    }

    /**
     * Prints details of single contact.
     * @param contact   one contact (lastname, firstname and phonenumber) from contacts list.
     */
    public static void printOneContact(String[] contact) {
        for(String details: contact) {
            System.out.print(details + " ");
        }
        System.out.println();
    }

    /**
     * Prints all existing contacts.
     * @param contacts array of contacts.
     */
    public static void printAllContacts(String[][] contacts) {
        for (String[] contact : contacts) {
            System.out.printf("%s %s: %s\n", contact[0], contact[1], contact[2]);
        }
    }

    /**
     * Prints list of actions.
     */
    public static void printPhoneBookMenu() {
        System.out.println("Select one of the actions below:");
        System.out.println("1. \uD83D\uDD0E Search for contact.");
        System.out.println("2. \uD83D\uDCF2 Insert contact.");
        System.out.println("3. \uD83D\uDCDD Update contact.");
        System.out.println("4. \uD83D\uDCF5 Delete contact.");
        System.out.println("5. \uD83D\uDCD4 See all contacts.");
        System.out.println("Q. \u21AA\uFE0F Exit phonebook.");
    }

    /**
     * Prints prompt to input menu selection.
     *
     * @return  selection input by user.
     */
    public static String getMenuChoice() {
        System.out.print("Insert selection(1, 2, 3, 4, 5, or Q): ");
         return in.nextLine().trim();
    }

    /**
     * Prints prompt to add contact information.
     */
    public static void printContactInputPrompt() {
        System.out.println("Type in contact's lastname, firstname and phone number:");
    }

    /**
     * Prints prompt to add updated contact information.
     */
    public static void printUpdatedContactInputPrompt() {
        System.out.println("Type in contact's lastname, firstname, old phone number and new phone number:");
    }

    /**
     * Prints prompt to type in lastname.
     * Returns input.
     *
     * @return  lastname string.
     */
    public static String getLastName() {
        System.out.print("Input lastname: ");
        return in.nextLine().trim();
    }

    /**
     * Prints prompt to type in firstname.
     * Returns input.
     *
     * @return  firstname string.
     */
    public static String getFirstName() {
        System.out.print("Input firstname: ");
        return in.nextLine().trim();
    }

    /**
     * Prints prompt to type in phonenumber.
     * Returns input.
     *
     * @return  phonenumber string.
     */
    public static String getPhoneNumber() {
        System.out.print("Input phone number: ");
        return in.nextLine().trim();
    }


    //  --- Services layer. ---
    //  Provided to client (e.g. main)

    /**
     * Checks if contact exists.
     * Returns contact if it exists
     * else throws error.
     *
     * @param phoneNumber   phone number of contact.
     * @return              contact.
     */
    public static String[] searchContactByPhoneNumberService(String phoneNumber) {
        try {
            if (searchContactIndexByPhoneNumber(phoneNumber) == -1) {
                throw new IllegalArgumentException("Contact not found.");
            } else {
                return contacts[searchContactIndexByPhoneNumber(phoneNumber)];
            }
        } catch (IllegalArgumentException e) {
            log(e);
            throw e;
        }
    }

    /**
     * Checks if contact is inserted.
     * Prints message if successful,
     * else throws error.
     *
     * @param lastName      surname of contact.
     * @param firstName     name of contact.
     * @param phoneNumber   phone number of contact.
     */
    public static void insertContactService(String lastName, String firstName, String phoneNumber) {
        try {
            if((insertContact(lastName, firstName, phoneNumber))) {
                System.out.println("Contact successfully inserted.");
            } else {
                throw new IllegalArgumentException("Error in insert.");
            }
        } catch (IllegalArgumentException e) {
            log(e);
            throw e;
        }
    }

    /**
     * Checks if contact is updated.
     * Prints message if successful,
     * else throws error.
     *
     * @param lastName      surname of contact.
     * @param firstName     name of contact.
     * @param oldPhoneNumber    original phone number.
     * @param newPhoneNumber    new phone number to update.
     */
    public static void updateContactService(String lastName, String firstName, String oldPhoneNumber, String newPhoneNumber) {
        try {
            if((updateContact(lastName, firstName, oldPhoneNumber, newPhoneNumber))) {
                System.out.println("Contact successfully updated.");
            } else {
                throw new IllegalArgumentException("Error in update.");
            }
        } catch (IllegalArgumentException e) {
            log(e);
            throw e;
        }
    }

    /**
     * Checks if contact is deleted.
     * Prints message if successful,
     * else throws error.
     *
     * @param phoneNumber  phone number of contact.
     */
    public static void deleteContactService(String phoneNumber) {
        try {
            if((deleteContact(phoneNumber))) {
                System.out.println("Contact successfully deleted.");
            } else {
                throw new IllegalArgumentException("Error in deletion.");
            }
        } catch (IllegalArgumentException e) {
            log(e);
            throw e;
        }
    }

    /**
     * Checks if contact list is empty.
     * Returns array of contacts if not empty,
     * else throws error.
     *
     * @return  array of contacts.
     */
    public static String[][] getAllContactsService() {
        String[][] currentContacts = getAllContacts();
        try {
            if(currentContacts.length == 0) throw new IllegalArgumentException("Contact list is empty.");
            return currentContacts;
        } catch (IllegalArgumentException e) {
            log(e);
            throw e;
        }
    }


    // --- CRUD Services layer. ---
    // Provided to Services layer.

    /**
     * Searches all contacts until it finds a phone number match.
     *
     * @param phoneNumber  searched phone number.
     * @return              position of contact (index) if found.
     *                      -1 if not found.
     */
    public static int searchContactIndexByPhoneNumber(String phoneNumber) {
        for (int i = 0; i <= pivot - 1; i++) {
            if (contacts[i][2].equals(phoneNumber)) return i;
        }
        return -1; // if phone number not found
    }

    /**
     * Inserts new contact if
     * a. not already existing
     * b. phonebook is not full.
     *
     * @param lastName      surname of contact.
     * @param firstName     name of contact.
     * @param phoneNumber   phone number of contact
     * @return
     */
    public static boolean insertContact(String lastName, String firstName, String phoneNumber) {
        //check if phonebook is full
        if (isFull(contacts)) return false;

        //check if contact exists
        if (searchContactIndexByPhoneNumber(phoneNumber) != -1) return false;

        contacts[pivot][0] = lastName;
        contacts[pivot][1] = firstName;
        contacts[pivot][2] = phoneNumber;
        pivot++; //move pointer to next free position

        return true;
    }

    /**
     * Updates contact details, if contact exists.
     *
     * @param lastName          surname of contact.
     * @param firstName         name of contact.
     * @param oldPhoneNumber    original phone number.
     * @param newPhoneNumber    new phone number to update.
     * @return
     */
    public static boolean updateContact(String lastName, String firstName, String oldPhoneNumber, String newPhoneNumber) {
        int positionToUpdate = searchContactIndexByPhoneNumber(oldPhoneNumber);

        //check if contact doesn't exist
        if (positionToUpdate == -1) return false;

        contacts[positionToUpdate][0] = lastName;
        contacts[positionToUpdate][1] = firstName;
        contacts[positionToUpdate][2] = newPhoneNumber;

        return true;
    }

    /**
     * Deletes a contact, if contact exists.
     * Moves all contacts after the deleted contact's position 1 position prior.
     *
     * @param phoneNumber  phone number of contact.
     * @return              true, if successfully deleted.
     *                      false, deletion fails.
     */
    public static boolean deleteContact(String phoneNumber) {
        int positionToDelete = searchContactIndexByPhoneNumber(phoneNumber);
        boolean confirmDeleted = false;

        if (searchContactIndexByPhoneNumber(phoneNumber) != -1) {
            System.arraycopy(contacts, positionToDelete + 1, contacts, positionToDelete, pivot - (positionToDelete + 1));
            pivot--;
            confirmDeleted = true;
        }

        return confirmDeleted;
    }

    /**
     * Returns a new array with all available contacts.
     *
     * @return  array of contacts.
     */
    public static String[][] getAllContacts() {
        return Arrays.copyOf(contacts, pivot);
    }

    /**
     * Checks if contacts array is full.
     *
     * @param contacts  contacts array.
     * @return          true if full (500 contacts).
     *                  false if not full.
     */
    public static boolean isFull(String[][] contacts) {
        return pivot == contacts.length;

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


