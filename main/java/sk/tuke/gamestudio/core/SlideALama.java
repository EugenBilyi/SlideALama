package sk.tuke.gamestudio.core;

import sk.tuke.gamestudio.consoleUI.consoleUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Random;

import java.util.Scanner;

import sk.tuke.gamestudio.core.*;

@Component
public class SlideALama {
    private final Field board = new Field();

    private final Symbols symbols = new Symbols();
    private final consoleUI ui;

    @Autowired
    public SlideALama(consoleUI ui) {
        this.ui = ui;
    }

    public Field getBoard() {  // Добавленный метод геттер
        return board;
    }

    public int playGame() {
        ui.writeRules();
        int result;
        do {
            board.initializeBoard();
            result = board.CheckBoardInStart();
        } while (result == 1);

//        int chooseMode = ui.chooseMode();
        int chooseMode = 1;
        if (chooseMode == 1 || chooseMode == 2) {
            board.printBoard();
            while ((chooseMode == 1 && board.getScore() < 700) || (chooseMode == 2 && board.BoardIsClear() != 1)) {
                board.SymbolSubstitution();
                board.SameInRow();
                board.SameInColumn();
            }
            System.out.println("Congratulations! You won!! Your score: " + board.getScore());
        }

        String playerName;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        playerName = scanner.nextLine();

        int menuOption;
        do {
            System.out.println("Menu:");
            System.out.println("1. Rate the game");
            System.out.println("2. Leave a comment");
            System.out.println("3. Save your score");
            System.out.println("4. Display comments");
            System.out.println("5. Display scores");
            System.out.println("6. Display ratings");
            System.out.println("0. Exit");
            System.out.println("");

            System.out.print("Choose an option: ");
            menuOption = scanner.nextInt();
            System.out.println("");

            switch (menuOption) {
                case 1:
                    ui.askForRating(playerName);
                    break;
                case 2:
                    ui.askForComment(playerName);
                    break;
                case 3:
                    ui.askForScore(playerName, board.getScore());
                    break;
                case 4:
                    ui.writeComments();
                    break;
                case 5:
                    ui.writeScores();
                    break;
                case 6:
                    ui.writeRatings();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid number.");
                    break;
            }
            System.out.println("");
            System.out.println("---------------------------------------------------------------------");
        } while (menuOption != 0);

        return chooseMode;
    }
}