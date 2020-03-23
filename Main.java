package tictactoe;

import java.util.Scanner;

public class Main {
    private static String board;
    private static boolean gameLasts = false;
    private static boolean xTurn = true;
    private static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        gameLoop();
    }

    private static void gameLoop() {
        // Flag to check if game lasts currently so if the loop should be continued
        gameLasts = true;
        // Our board is string so we make it 9 spaces
        resetBoard();
        // Initial pringing of our board
        printBoard();
        while (gameLasts){
            // Player input position
            input();
            printBoard();
            // Check for win, draw, impossible situations
            analyseBoard();
            // In each loop flip the flag that controls which players turn it is
            xTurn ^= true;
        }
    }

    private static void printBoard() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.format("| %c %c %c |\n", board.charAt(3 * i), board.charAt(3 * i + 1), board.charAt(3 * i + 2));
        }
        System.out.println("---------");
    }

    private static void resetBoard() {
        board = "         ";
    }

    private static void analyseBoard() {
        // check win by row / collumn
        int streakCount = 0;
        char winner = 'a';
        for (int i = 0; i < 3; i++) {
            if ( (board.charAt(3*i+i) == 'X' || board.charAt(3*i+i) == 'O') && ( (board.charAt(3 * i) == board.charAt(3 * i + 1) && board.charAt(3 * i + 1) == board.charAt(3 * i + 2)) ||
                    ((board.charAt(i) == board.charAt(i + 3) && board.charAt(i) == board.charAt(i + 6))) ) ) {
                streakCount++;
                winner = board.charAt(3 * i + i);
            }
        }

        //check win by diagonal
        if ( (board.charAt(4) == 'X' || board.charAt(4) == 'O') && ( (board.charAt(0) == board.charAt(4) && board.charAt(0) == board.charAt(8)) ||
                (board.charAt(2)) == board.charAt(4) && board.charAt(4) == board.charAt(6)) ) {
            winner = board.charAt(4);
            streakCount++;
        }

        if (streakCount == 1) {
            System.out.format("%c wins\n", winner);
            gameLasts = false;
            return;
        } else if (streakCount > 1 || isNotBalanced()) {
            System.out.println("Impossible");
            gameLasts = false;
            return;
        }

        // check draw / unfinished
        for (int i = 0; i < 9; i++) {
            if ((board.charAt(i) != 'X') && (board.charAt(i) != 'O')) {
                return;
            }
        }
        System.out.println("Draw");
        gameLasts = false;
    }

    private static boolean isNotBalanced() {
        int xCount = 0, oCount = 0;
        for (int i = 0; i < 9; i++) {
            if (board.charAt(i) == 'X') {
                xCount++;
            } else if (board.charAt(i) == 'O') {
                oCount++;
            }
        }
        int diff = xCount - oCount;
        return Math.abs(diff) > 1;
    }

    private static void input() {
        String s1, s2;
        int coord1, coord2, position;
        while (true) {
            System.out.print("Enter the coordinates: ");
            s1 = sc.next();
            s2 = sc.next();
            try {
                coord1 = Integer.parseInt(s1);
                coord2 = Integer.parseInt(s2);
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                continue;
            }

            if ((coord1 > 0 && coord1 < 4) && (coord2 > 0 && coord2 < 4)) {
                position = (coord1 - 1) + 3 * (3 - coord2);
                if (board.charAt(position) != 'X' && board.charAt(position) != 'O') {
                    break;
                }
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            System.out.println("Coordinates should be from 1 to 3!");
        }
        putIntoPosition(position);
    }

    private static void putIntoPosition(int position) {
        char character;
        if( xTurn ) character = 'X';
        else character = 'O';

        char[] tmpChar = board.toCharArray();
        tmpChar[position] = character;
        board = String.valueOf(tmpChar);
    }
}