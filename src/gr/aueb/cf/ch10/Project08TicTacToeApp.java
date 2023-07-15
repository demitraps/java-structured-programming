package gr.aueb.cf.ch10;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *  {@link Project08TicTacToeApp} implements
 *  a Tic Tac Toe game application.
 *  It reads the position selection from two players (X and o)
 *  and updates the board game adding the players symbol.
 *  The game continues until either a player has made a winning combination
 *  or the number of rounds has been completed, where the game is a draw.
 *
 * @author demitra
 */
public class Project08TicTacToeApp {
    public static String[][] board = {
            {"not used", "0"},
            {"available", "1\uFE0F\u20E3"}, {"available", "2\uFE0F\u20E3"}, {"available", "3\uFE0F\u20E3"},
            {"available", "4\uFE0F\u20E3"}, {"available", "5\uFE0F\u20E3"}, {"available", "6\uFE0F\u20E3"},
            {"available", "7\uFE0F\u20E3"}, {"available", "8\uFE0F\u20E3"}, {"available", "9\uFE0F\u20E3"}
    };
    public static final String PLAYER_X = "\u274E";
    public static final String PLAYER_O = "\uD83C\uDD7E\uFE0F";
    public static String[] players = {PLAYER_X, PLAYER_O}; //players[0] = X, players[1] = 0
    public static int turn = 2; //even round for players[0], odd round for players[1]
    public static boolean gameOver = false;
    public static int round = 1;

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            int choice;

            System.out.printf("Welcome to Tic-Tac-Toe!%s%s\n", players[0],players[1]);
            printBoard();

            do {
                System.out.printf("Player %s: It is your turn!\n", players[turn%2]);
                System.out.print("Select a position from 1 to 9 to play: ");

                try {
                    choice = in.nextInt();
                    placeSelection(players[turn%2], choice);
                } catch (InputMismatchException e) {
                    System.out.println("\u26A0\uFE0F Invalid choice. Please try again.");
                    in.nextLine();
                }
            } while(!gameOver && round < 10);

            if (round > 9) {
                System.out.println("It's a draw! \uD83E\uDD1D");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An unexpected error occurred.");
        }
    }

    /**
     * Prints the current state of the Tic Tac Toe board.
     */
    public static void printBoard() {
        System.out.println("\u25AB\uFE0F \u3030\uFE0F \u25AB\uFE0F \u3030\uFE0F \u25AB\uFE0F \u3030\uFE0F \u25AB\uFE0F");
        for (int i = 1; i <= 9; i +=3) {
            System.out.printf("\u25AB\uFE0F %s \u25AB\uFE0F %s \u25AB\uFE0F %s \u25AB\uFE0F\n", board[i][1], board[i+1][1], board[i+2][1]);
            System.out.println("\u25AB\uFE0F \u3030\uFE0F \u25AB\uFE0F \u3030\uFE0F \u25AB\uFE0F \u3030\uFE0F \u25AB\uFE0F");
        }
    }

    /**
     * Places the player's symbol on the selected position of the Tic Tac Toe board.
     *
     * @param player        the player's symbol (X or O emoji).
     * @param choice        the selected position (1 to 9).
     */
    public static void placeSelection(String player, int choice) {
        if (isValidSelection(choice)) {
            if (isPositionAvailable(choice)) {
                board[choice][1] = player;
                board[choice][0] = "taken";
                printBoard();
                turn++;
                round++;
                if (round >= 5) checkIfWon(player);
            } else {
                System.out.println("\u26A0\uFE0F Position taken. Try again.");
            }
        } else {
            System.out.println("\u26A0\uFE0F Invalid selection. Try again selecting a position from 1 to 9.");
        }
    }

    /**
     * Checks if the selected choice is a valid position on the Tic Tac Toe board.
     * @param choice        the selected position.
     * @return              true, if the selection is valid (1 - 9)
     *                      false, otherwise.
     */
    public static boolean isValidSelection(int choice) {
        return choice > 0 && choice < 10;
    }

    /**
     * Checks if the selected position on the Tic Tac Toe board is available,
     * meaning it has not been selected previously by other players.
     *
     * @param choice        the selected position ( 1 - 9)
     * @return              true if the position is available,
     *                      false, otherwise.
     */
    public static boolean isPositionAvailable(int choice) {
        return board[choice][0].equals("available");
    }

    /**
     *  Checks if player has won the game.
     *
     * @param player       the player's symbol (X or O emoji).
     */
    public static void checkIfWon(String player) {
        // 8 possible winning combinations: 3 horizontal, 3 vertical, 2 diagonal
        String[] winningCombos = {board[1][1] + board[2][1] + board[3][1],
                                  board[4][1] + board[5][1] + board[6][1],
                                  board[7][1] + board[8][1] + board[9][1],
                                  board[1][1] + board[4][1] + board[7][1],
                                  board[2][1] + board[5][1] + board[8][1],
                                  board[3][1] + board[6][1] + board[9][1],
                                  board[1][1] + board[5][1] + board[9][1],
                                  board[3][1] + board[5][1] + board[7][1]};
        String winningString = player + player + player;

        for (String winningCombo : winningCombos) {
            if (winningCombo.equals(winningString)) {
                System.out.printf("Player %s has won!\uD83C\uDF86\n", player);
                gameOver = true;
            }
        }
    }
}
