package sk.tuke.gamestudio.core;

import sk.tuke.gamestudio.consoleUI.*;
public class Checks {

    public static void SameInRow(Field board) {
        int boardSIZE = board.getBoardSIZE();
        char[][] boardArray = board.getBoard();

        for (int row = 0; row < boardSIZE; row++) {
            for (int col = 0; col < boardSIZE - 2; col++) {
                int scoreMultiplier = 1;
                if (col < 3) {
                    if ((boardArray[row][col] == boardArray[row][col + 1]) &&
                            (boardArray[row][col] == boardArray[row][col + 2]) &&
                            (boardArray[row][col] != ' ')) {
                        if (col < 2) {
                            if (boardArray[row][col] == boardArray[row][col + 3] &&
                                    boardArray[row][col] != ' ') {
                                scoreMultiplier = 2;
                                if (col < 1) {
                                    if (boardArray[row][col] == boardArray[row][col + 4] &&
                                            boardArray[row][col] != ' ') scoreMultiplier = 3;
                                }
                            }
                        }
                        switch (boardArray[row][col]) {
                            case 'A':
                                board.setScore(board.getScore() + 10 * scoreMultiplier);
                                break;
                            case 'B':
                                board.setScore(board.getScore() + 20 * scoreMultiplier);
                                break;
                            case 'C':
                                board.setScore(board.getScore() + 30 * scoreMultiplier);
                                break;
                            case 'D':
                                board.setScore(board.getScore() + 40 * scoreMultiplier);
                                break;
                            case 'E':
                                board.setScore(board.getScore() + 70 * scoreMultiplier);
                                break;
                            case 'F':
                                board.setScore(board.getScore() + 100 * scoreMultiplier);
                                break;
                            case 'G':
                                board.setScore(board.getScore() + 150 * scoreMultiplier);
                                break;
                            default:
                                break;
                        }
                        if (scoreMultiplier == 1) {
                            boardArray[row][col] = ' ';
                            boardArray[row][col + 1] = ' ';
                            boardArray[row][col + 2] = ' ';
                        }
                        if (scoreMultiplier == 2) {
                            boardArray[row][col] = ' ';
                            boardArray[row][col + 1] = ' ';
                            boardArray[row][col + 2] = ' ';
                            boardArray[row][col + 3] = ' ';
                        }
                        if (scoreMultiplier == 3) {
                            boardArray[row][col] = ' ';
                            boardArray[row][col + 1] = ' ';
                            boardArray[row][col + 2] = ' ';
                            boardArray[row][col + 3] = ' ';
                            boardArray[row][col + 4] = ' ';
                        }
                        board.BoardMoveAll();
                    }
                }
            }
        }
    }

    public static void SameInColumn(Field board) {
        int boardSIZE = board.getBoardSIZE();
        char[][] boardArray = board.getBoard();

        for (int row = 0; row < boardSIZE - 2; row++) {
            for (int col = 0; col < boardSIZE; col++) {
                int scoreMultiplier = 1;
                if (row < 3) {
                    if ((boardArray[row][col] == boardArray[row + 1][col]) &&
                            (boardArray[row][col] == boardArray[row + 2][col]) &&
                            (boardArray[row][col] != ' ')) {
                        if (row < 2) {
                            if (boardArray[row][col] == boardArray[row + 3][col] &&
                                    boardArray[row][col] != ' ') {
                                scoreMultiplier = 2;
                                if (row < 1) {
                                    if (boardArray[row][col] == boardArray[row + 4][col] &&
                                            boardArray[row][col] != ' ') scoreMultiplier = 3;
                                }
                            }
                        }
                        switch (boardArray[row][col]) {
                            case 'A':
                                board.setScore(board.getScore() + 10 * scoreMultiplier);
                                break;
                            case 'B':
                                board.setScore(board.getScore() + 20 * scoreMultiplier);
                                break;
                            case 'C':
                                board.setScore(board.getScore() + 30 * scoreMultiplier);
                                break;
                            case 'D':
                                board.setScore(board.getScore() + 40 * scoreMultiplier);
                                break;
                            case 'E':
                                board.setScore(board.getScore() + 70 * scoreMultiplier);
                                break;
                            case 'F':
                                board.setScore(board.getScore() + 100 * scoreMultiplier);
                                break;
                            case 'G':
                                board.setScore(board.getScore() + 150 * scoreMultiplier);
                                break;
                            default:
                                break;
                        }
                        if (scoreMultiplier == 1) {
                            boardArray[row][col] = ' ';
                            boardArray[row + 1][col] = ' ';
                            boardArray[row + 2][col] = ' ';
                        }
                        if (scoreMultiplier == 2) {
                            boardArray[row][col] = ' ';
                            boardArray[row + 1][col] = ' ';
                            boardArray[row + 2][col] = ' ';
                            boardArray[row + 3][col] = ' ';
                        }
                        if (scoreMultiplier == 3) {
                            boardArray[row][col] = ' ';
                            boardArray[row + 1][col] = ' ';
                            boardArray[row + 2][col] = ' ';
                            boardArray[row + 3][col] = ' ';
                            boardArray[row + 4][col] = ' ';
                        }
                        board.BoardMoveAll();
                    }
                }
            }
        }
        SameInRow(board);
        board.printBoard();
    }

    public static boolean CheckSpacesDown(Field board, int number) {
        int boardSIZE = board.getBoardSIZE();
        char[][] boardArray = board.getBoard();
        int columnIndex = switch (number) {
            case 6 -> 0;
            case 7 -> 1;
            case 8 -> 2;
            case 9 -> 3;
            case 10 -> 4;
            default -> 0;
        };
        for (int row = 0; row < boardSIZE; row++) {
            if (boardArray[row][columnIndex] == ' ')
                return true;
        }
        return false;
    }

    public static boolean CheckSpacesSide(Field board, int number) {
        int boardSIZE = board.getBoardSIZE();
        char[][] boardArray = board.getBoard();
        int rowIndex = switch (number) {
            case 1, 15 -> 4;
            case 2, 14 -> 3;
            case 3, 13 -> 2;
            case 4, 12 -> 1;
            case 5, 11 -> 0;
            default -> -1;
        };
        for (int col = 0; col < boardSIZE; col++) {
            if (boardArray[rowIndex][col] == ' ')
                return true;
        }
        return false;
    }

    public static int BoardIsClear(Field board) {
        int boardSIZE = board.getBoardSIZE();
        char[][] boardArray = board.getBoard();
        for (int row = 0; row < boardSIZE; row++) {
            for (int col = 0; col < boardSIZE; col++) {
                if (boardArray[row][col] != ' ') return 0;
            }
        }
        return 1;
    }
}