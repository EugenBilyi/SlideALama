package sk.tuke.gamestudio.service;

import org.springframework.stereotype.Service;
import sk.tuke.gamestudio.core.SlideALama;

import java.util.Random;

@Service
public class GameService {
    private final SlideALama slideALama;

    public GameService(SlideALama slideALama) {
        this.slideALama = slideALama;
    }

    public char[][] getBoard() {
        return slideALama.getBoard().getBoard();
    }

    public char getCurrentSymbol() {
        return slideALama.getBoard().getCurrentSymbol();
    }

    public char getNextSymbol() {
        return slideALama.getBoard().getNextSymbol();
    }

    public int getScore() {
        return slideALama.getBoard().getScore();
    }

    public void makeMove(int position) {
        slideALama.getBoard().updateGameAfterMove();
        slideALama.getBoard().SymbolSubstitutionWeb(position);
        slideALama.getBoard().updateGameAfterMove();
    }

    public boolean removeRandomSymbol() {
        try {
            char[][] board = slideALama.getBoard().getBoard();
            Random random = new Random();
            int number = random.nextInt(7);
            char symbol = ' ';

            switch(number){
                case 0:
                    symbol = 'A';
                    break;
                case 1:
                    symbol = 'B';
                    break;
                case 2:
                    symbol = 'C';
                    break;
                case 3:
                    symbol = 'D';
                    break;
                case 4:
                    symbol = 'E';
                    break;
                case 5:
                    symbol = 'F';
                    break;
                case 6:
                    symbol = 'G';
                    break;
                default:
                    break;
            }
            for(int row = 0; row < 5; row++){
                for(int col = 0; col < 5; col++) {
                    if (board[row][col] == symbol && board[row][col] != ' ') {
                        board[row][col] = ' ';
                    }
                }
            }

            slideALama.getBoard().updateGameAfterMove();
            slideALama.getBoard().updateGameAfterMove();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
