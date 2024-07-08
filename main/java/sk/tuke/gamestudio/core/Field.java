package sk.tuke.gamestudio.core;

import java.util.Random;
import java.util.Scanner;
import sk.tuke.gamestudio.consoleUI.*;

public class Field {
    private final Symbols symbols = new Symbols();
    private consoleUI ui = new consoleUI();
    private final int boardSIZE = 5;
    private final char[][] board = new char[boardSIZE][boardSIZE];
    private int score = 600;

    private final Checks checks = new Checks();

    public int getBoardSIZE() {
        return boardSIZE;
    }

    public char[][] getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public char getCurrentSymbol() {
        return symbols.getCurrentSymbol();
    }

    public char getNextSymbol() {
        return symbols.getNextSymbol();
    }

    public void initializeBoard() {
        Random random = new Random();

        for (int row = 0; row < boardSIZE; row++) {
            for (int col = 0; col < boardSIZE; col++) {
                char symbol = symbols.generateSymbol(random); // Change here
                board[row][col] = symbol;
            }
        }
    }

    public void SymbolsUpdate() {
        symbols.symbolsUpdate(new Random());
    }

    public void SymbolsPrint() {
        symbols.symbolsPrint();
    }
    public int CheckBoardInStart(){
        for(int row = 0; row < boardSIZE; row++){
            for(int col = 0; col < boardSIZE - 2; col++){
                if(board[row][col]==board[row][col+1] && board[row][col]==board[row][col+2])
                    return 1;
            }
        }
        for(int row = 0; row < boardSIZE - 2; row++){
            for(int col = 0; col < boardSIZE; col++){
                if(board[row][col]==board[row+1][col] && board[row][col]==board[row+2][col])
                    return 1;
            }
        }

        return 0;
    }

    public void printBoard() {
        ui.printBoard(this);
    }

    public void SymbolSubstitution(){
        System.out.println("Write the number where you want to insert the symbol");
        System.out.println("1-5 (left), 6-10 (down), 11-15 (right)");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Write a number from 1 to 15: ");
        int number = scanner.nextInt();
        scanner.nextLine();
        if(number < 1 || number > 15){
            while((number < 1) || (number > 15)) {
                System.out.println();
                System.out.println("Bad input. You should write number from 1 to 15. Try again");
                System.out.print("Write a number from 1 to 15: ");
                number = scanner.nextInt();
            }
        }

        switch(number){
            case 1: case 2: case 3: case 4: case 5:
                InsertCurrentSymbolLeft(number);
                break;
            case 6: case 7: case 8: case 9: case 10:
                InsertCurrentSymbolUp(number);
                break;
            case 11: case 12: case 13: case 14: case 15:
                InsertCurrentSymbolRight(number);
                break;
        }
        SymbolsUpdate();
        BoardMoveAll();
    }

    public void SymbolSubstitutionWeb(int number){
        switch(number){
            case 1: case 2: case 3: case 4: case 5:
                InsertCurrentSymbolLeft(number);
                break;
            case 6: case 7: case 8: case 9: case 10:
                InsertCurrentSymbolUp(number);
                break;
            case 11: case 12: case 13: case 14: case 15:
                InsertCurrentSymbolRight(number);
                break;
        }
        SymbolsUpdate();
        BoardMoveAll();
    }

    public void updateGameAfterMove() {
        SameInRow();
        SameInColumn();
        this.fallSymbols();      // Падение символов на освободившиеся места
    }


    private void fallSymbols() {
        // Логика "падения" символов в пустые места
        for (int col = 0; col < boardSIZE; col++) {
            for (int row = boardSIZE - 1; row >= 0; row--) {
                if (board[row][col] == ' ') { // Найден пробел
                    // Смещение всех символов вверх
                    for (int k = row; k > 0; k--) {
                        board[k][col] = board[k-1][col];
                    }
                    board[0][col] = ' '; // Новый символ или пробел на верху
                }
            }
        }
    }



    public void BoardMoveDown(int number) {
        if (CheckSpacesDown(number)) {
            for(int pocet = 0; pocet < 3; pocet++){
                for (int row = boardSIZE - 1; row > 0; row--) { // Начинаем с последней строки и двигаемся вверх
                    if (board[row][number - 6] == ' ') { // Если текущая ячейка пуста
                        // Найден пробел, нужно сдвинуть символы вниз
                        for (int tmpRow = row; tmpRow > 0; tmpRow--) {
                            board[tmpRow][number - 6] = board[tmpRow - 1][number - 6]; // Сдвигаем символ вниз
                        }
                        // Устанавливаем новый символ в верхней строке столбца
                        board[0][number - 6] = ' ';
                    }
                }
            }
        } else {
            // Если в столбце нет пробелов, заменяем нижний символ пробелом и сдвигаем все символы вниз
            for (int row = boardSIZE - 1; row > 0; row--) {
                board[row][number - 6] = board[row - 1][number - 6];
            }
            // Устанавливаем пробел в верхней ячейке столбца
            board[0][number - 6] = ' ';
        }
    }

    public void BoardMoveToRight(int number) {
        int rowIndex = switch (number) {
            case 15, 1 -> 4;
            case 14, 2 -> 3;
            case 13, 3 -> 2;
            case 12, 4 -> 1;
            case 11, 5 -> 0;
            default -> -1; // Это значение будет обозначать, что номер ряда некорректен
        };

        if (CheckSpacesSide(number)) {
            if (rowIndex != -1) { // Проверяем, что номер ряда корректный
                for (int col = 0; col < boardSIZE; col++) { // Начинаем с первого столбца и двигаемся вправо
                    if (board[rowIndex][col] == ' ') { // Проверяем, если в строке есть пробел
                        // Найден пробел, нужно сдвинуть символы вправо
                        for (int tmpCol = col; tmpCol > 0; tmpCol--) {
                            board[rowIndex][tmpCol] = board[rowIndex][tmpCol - 1]; // Сдвигаем символ вправо
                        }
                        // Устанавливаем пробел в первый столбец
                        board[rowIndex][0] = ' ';
                    }
                }
            }
        } else {
            for (int col = boardSIZE - 1; col > 0; col--) {
                board[rowIndex][col] = board[rowIndex][col - 1];
            }
            // Устанавливаем пробел в левой ячейке строки
            board[rowIndex][0] = ' ';
        }
    }

    public void BoardMoveToLeft(int number) {
        int rowIndex = switch (number) {
            case 15 -> 4;
            case 14 -> 3;
            case 13 -> 2;
            case 12 -> 1;
            case 11 -> 0;
            default -> -1; // Это значение будет обозначать, что номер ряда некорректен
        };
        if (CheckSpacesSide(number)) {
            if (rowIndex != -1) { // Проверяем, что номер ряда корректный
                for (int col = boardSIZE - 1; col >= 0; col--) { // Начинаем с последнего столбца и двигаемся влево
                    if (board[rowIndex][col] == ' ') { // Проверяем, если в строке есть пробел
                        // Найден пробел, нужно сдвинуть символы влево
                        for (int tmpCol = col; tmpCol < boardSIZE - 1; tmpCol++) {
                            board[rowIndex][tmpCol] = board[rowIndex][tmpCol + 1]; // Сдвигаем символ влево
                        }
                        // Устанавливаем пробел в последний столбец
                        board[rowIndex][boardSIZE - 1] = ' ';
                    }
                }
            }
        }
        else{
            for(int col = 0; col < boardSIZE - 1; col++){
                board[rowIndex][col] = board[rowIndex][col + 1];
            }
            // Устанавливаем пробел в правой ячейке строки
            board[rowIndex][boardSIZE - 1] = ' ';
        }
    }

    public void BoardMoveAll(){
        for(int count = 0; count < boardSIZE-1; count++){
            for(int number = 0; number < boardSIZE; number++){
                if(CheckSpacesDown(number+6)) BoardMoveDown(number+6);
            }
        }
    }

    public void InsertCurrentSymbolLeft(int number) {
        int rowIndex = switch (number) {
            case 1 -> 4;
            case 2 -> 3;
            case 3 -> 2;
            case 4 -> 1;
            case 5 -> 0;
            default -> 0;
        };

        if (CheckSpacesSide(number)) {
            for(int col = boardSIZE-1; col > 0; col--){  // Symbols move
                if(board[rowIndex][col] == ' '){
                    board[rowIndex][col] = board[rowIndex][col-1];
                    board[rowIndex][col-1] = ' ';
                }
            }
            board[rowIndex][0] = symbols.getCurrentSymbol();;
            BoardMoveAll();
        }
        else {
            BoardMoveToRight(number);
            board[rowIndex][0] = symbols.getCurrentSymbol();;
        }

    }

    public void InsertCurrentSymbolRight(int number) {
        int rowIndex = switch (number) {
            case 15, 1 -> 4;
            case 14, 2 -> 3;
            case 13, 3 -> 2;
            case 12, 4 -> 1;
            case 11, 5 -> 0;
            default -> -1;
        };

        if (CheckSpacesSide(number)) {
            for(int col = 0; col < boardSIZE-1; col++){
                if(board[rowIndex][col] == ' '){
                    board[rowIndex][col] = board[rowIndex][col+1];
                    board[rowIndex][col+1] = ' ';
                }
            }
            board[rowIndex][boardSIZE-1] = symbols.getCurrentSymbol();;
            BoardMoveAll();
        }
        else {
            BoardMoveToLeft(number);
            board[rowIndex][boardSIZE-1] = symbols.getCurrentSymbol();;
        }
    }

    public void InsertCurrentSymbolUp(int number) {
        int columnIndex = switch (number) {
            case 10 -> 4;
            case 9 -> 3;
            case 8 -> 2;
            case 7 -> 1;
            case 6 -> 0;
            default -> -1;
        };

        if (CheckSpacesDown(number)) {
            BoardMoveDown(number);
            board[0][columnIndex] = symbols.getCurrentSymbol();;
//            printBoard();
            if(CheckSpacesDown(number))
                BoardMoveDown(number);
        } else {
            BoardMoveDown(number);
            board[0][columnIndex] = symbols.getCurrentSymbol();;
        }
    }

    public static int chooseMode() {
        System.out.println();
        System.out.println("You can choose the game mode you like!");
        System.out.println("  1. The game will last up to 700 points");
        System.out.println("  2. The game will last until the playing field is completely clear");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose a mode! (Write 1 or 2): ");
        int number = scanner.nextInt();
        scanner.nextLine();
        if(number != 1 && number != 2){
            while((number != 1) && (number != 2)){
                System.out.println();
                System.out.println("Bad input. You should write only 1 or 2. Try again");
                System.out.print("Choose the game mode you like! (Write 1 or 2): ");
                number = scanner.nextInt();
            }
        }

        return number;
    }

    public void SameInRow() {
        Checks.SameInRow(this);
    }

    public void SameInColumn() {
        Checks.SameInColumn(this);
    }

    public boolean CheckSpacesDown(int number) {
        return Checks.CheckSpacesDown(this, number);
    }

    public boolean CheckSpacesSide(int number) {
        return Checks.CheckSpacesSide(this, number);
    }

    public int BoardIsClear() {
        return Checks.BoardIsClear(this);
    }
}