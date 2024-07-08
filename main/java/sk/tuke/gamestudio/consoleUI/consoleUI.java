package sk.tuke.gamestudio.consoleUI;

import org.springframework.stereotype.Component;
import sk.tuke.gamestudio.core.*;
import sk.tuke.gamestudio.service.*;
import sk.tuke.gamestudio.entity.*;

import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class consoleUI {
    private static Scanner scanner = new Scanner(System.in);

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CommentService commentService;


    public void printBoard(Field board) {
        final int boardSIZE = board.getBoardSIZE();

        for (int row = 0; row < boardSIZE; row++) {
            // Print top border of each cell in the row
            for (int col = 0; col < boardSIZE; col++) {
                if (row > 0 && col == 0)
                    System.out.print("  |---");
                else if (col > 0)
                    System.out.print("|---");
                else {
                    for (int number = 0; number < 5; number++) {
                        if (number == 0) {
                            System.out.print("    " + (number + 6) + " ");
                        } else
                            System.out.print("  " + (number + 6) + " ");
                    }
                    System.out.println();
                    System.out.print("  +---");
                }
            }
            if (row > 0)
                System.out.println("|");
            else
                System.out.println("+");

            // Print symbols with left and right borders
            for (int col = 0; col < boardSIZE; col++) {
                char symbol = board.getBoard()[row][col];
                if (col == 0)
                    System.out.print(boardSIZE - row + " ");
                switch (symbol) {
                    case 'A':
                        System.out.print("| \u001B[38;2;255;165;0m" + symbol + "\u001B[0m "); // Оранжевый цвет
                        break;
                    case 'B':
                        System.out.print("| \u001B[38;2;255;255;0m" + symbol + "\u001B[0m "); // Желтый цвет
                        break;
                    case 'C':
                        System.out.print("| \u001B[38;2;0;191;255m" + symbol + "\u001B[0m ");
                        break;
                    case 'D':
                        System.out.print("| \u001B[38;2;144;238;144m" + symbol + "\u001B[0m ");
                        break;
                    case 'E':
                        System.out.print("| \u001B[38;2;255;0;0m" + symbol + "\u001B[0m ");
                        break;
                    case 'F':
                        System.out.print("| \u001B[38;2;192;192;192m" + symbol + "\u001B[0m ");
                        break;
                    case 'G':
                        System.out.print("| \u001B[38;2;255;192;203m" + symbol + "\u001B[0m ");
                        break;
                    default:
                        System.out.print("| " + symbol + " ");
                        break;
                }
            }
            if (row == 0)
                System.out.println("| " + (row + 11) + "    Score = " + board.getScore());
            else
                System.out.println("| " + (row + 11));
        }

        // Print bottom border for the last row
        for (int col = 0; col < boardSIZE; col++) {
            if (col == 0)
                System.out.print("  +---");
            else
                System.out.print("+---");
        }
        System.out.println("+");
        board.SymbolsPrint();
    }

//    public int chooseMode() {
//        System.out.println();
//        System.out.println("You can choose the game mode you like!");
//        System.out.println("  1. The game will last up to 700 points");
//        System.out.println("  2. The game will last until the playing field is completely clear");
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Choose a mode! (Write 1 or 2): ");
//        int number = scanner.nextInt();
//        scanner.nextLine();
//        if (number != 1 && number != 2) {
//            while ((number != 1) && (number != 2)) {
//                System.out.println();
//                System.out.println("Bad input. You should write only 1 or 2. Try again");
//                System.out.print("Choose the game mode you like! (Write 1 or 2): ");
//                number = scanner.nextInt();
//            }
//        }
//
//        return number;
//    }

    public void writeRules() {
        System.out.println();
        System.out.println("                               Rules:");
        System.out.println();
        System.out.println("The game consists of a playing field, which is filled with various \"signs\"");
        System.out.println("The game has 2 modes that you will be able to choose:");
        System.out.println("  1. The game will last up to 700 points");
        System.out.println("  2. The game will last until the playing field is completely clear");
        System.out.println("Your task is to write the number of the column or row where you want to insert the current symbol.");
        System.out.println("If 3 or more of the same symbols match in a row or column,");
        System.out.println("  these symbols will disappear and the user will get points depending on the symbol.");
        System.out.println("A = 10");
        System.out.println("B = 20");
        System.out.println("C = 30");
        System.out.println("D = 40");
        System.out.println("E = 70");
        System.out.println("F = 100");
        System.out.println("G = 150");
        System.out.println("4 of a kind = x2");
        System.out.println("5 of a kind = x3");
    }

    public void writeComments() {
        try {
            List<Comment> comments = commentService.getComments("SlideALama");
            if (comments.isEmpty()) {
                System.out.println("No comments available.");
            } else {
                for (int i = 0; i < comments.size(); i++) {
                    System.out.println((i + 1) + ". " + comments.get(i).getPlayer() + ": " + comments.get(i).getComment());
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to retrieve comments: " + e.getMessage());
        }
    }

    public void writeScores() {
        try {
            List<Score> scores = scoreService.getAllScores("SlideALama");
            if (scores.isEmpty()) {
                System.out.println("No scores available.");
            } else {
                for (int i = 0; i < scores.size(); i++) {
                    System.out.println((i + 1) + ". " + scores.get(i).getPlayer() + ": " + scores.get(i).getPoints());
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to retrieve scores: " + e.getMessage());
        }
    }

    public void writeRatings() {
        try {
            List<Rating> ratings = ratingService.getRatings("SlideALama");
            if (ratings.isEmpty()) {
                System.out.println("No ratings available.");
            } else {
                for (int i = 0; i < ratings.size(); i++) {
                    System.out.println((i + 1) + ". " + ratings.get(i).getPlayer() + ": " + ratings.get(i).getRating());
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to retrieve ratings: " + e.getMessage());
        }
    }

    public void askForComment(String name) {
        while (true) {
            System.out.print("Would you like to leave a comment about the game? If you do, type yes: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if ("yes".equals(choice)) {
                System.out.print("Enter your comment: ");
                String commentText = scanner.nextLine().trim();

                // Ensure the comment text is not empty
                if (commentText.isEmpty()) {
                    System.out.println("Comment cannot be empty. Please enter a comment.");
                    continue;
                }

                // Используем внедренный сервис
                Comment comment = new Comment(name, "SlideALama", commentText, new java.util.Date());
                commentService.addComment(comment);
                System.out.println("Thank you for your comment!");
                break;
            } else if ("no".equals(choice)) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }


    public void askForRating(String name) {
        System.out.print("Would you like to rate the game? If you do, type yes: ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if ("yes".equals(choice)) {
            try {
                System.out.print("Enter your rating (1-5): ");
                int ratingValue = Integer.parseInt(scanner.nextLine().trim());

                if (ratingValue < 1 || ratingValue > 5) {
                    System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                    return;
                }

                // Используем внедренный сервис
                Rating rating = new Rating(name, "SlideALama", ratingValue, new java.util.Date());
                ratingService.setRating(rating);
                System.out.println("Thank you for rating the game!");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
            }
        }
    }


    public void askForScore(String name, int moveCount) {
        System.out.print("Would you like to save your score? If you do, type yes: ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if ("yes".equals(choice)) {
            // Используем внедренный сервис
            Score score = new Score("SlideALama", name, moveCount, new java.util.Date());
            scoreService.addScore(score);
            System.out.println("Your score has been saved. Congratulations!");
        }
    }
}