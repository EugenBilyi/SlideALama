package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sk.tuke.gamestudio.core.SlideALama;
import sk.tuke.gamestudio.service.GameService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GameController {

    @Autowired
    private SlideALama slideALama;

    @Autowired
    private GameService gameService;


    @GetMapping("/api/gameboard")
    public ResponseEntity<Map<String, Object>> getGameBoard() {
        Map<String, Object> response = new HashMap<>();
        response.put("board", slideALama.getBoard().getBoard());
        response.put("score", slideALama.getBoard().getScore());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/symbols")
    public ResponseEntity<Map<String, Object>> getSymbols() {
        Map<String, Object> response = new HashMap<>();
        response.put("currentSymbol", gameService.getCurrentSymbol());
        response.put("nextSymbol", gameService.getNextSymbol());
        response.put("score", gameService.getScore());  // Добавление счёта
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/makeMove")
    public ResponseEntity<Map<String, Object>> makeMove(@RequestParam int position) {
        Map<String, Object> response = new HashMap<>();
        try {
            gameService.makeMove(position);
            int currentScore = gameService.getScore();
            response.put("score", currentScore);
            response.put("gameOver", currentScore >= 700); // Индикатор завершения игры, если счет достиг 700 или больше
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error processing move: " + e.getMessage());
            return ResponseEntity.badRequest().body(response); // Теперь возвращаем Map с описанием ошибки
        }
    }


    @GetMapping("/api/finalGameData")
    public ResponseEntity<Map<String, Object>> getFinalGameData() {
        Map<String, Object> response = new HashMap<>();
        response.put("htmlBoard", getHtmlBoard()); // Предполагается, что этот метод возвращает HTML игрового поля
        response.put("score", gameService.getScore());
        return ResponseEntity.ok(response);
    }

    public String getHtmlBoard() {
        char[][] board = slideALama.getBoard().getBoard();
        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        for (int row = 0; row < board.length; row++) {
            sb.append("<tr>");
            for (int col = 0; col < board[row].length; col++) {
                sb.append("<td class='everyCell'><img src='").append(getImagePath(board[row][col])).append("' class='game-cell'></td>");
            }
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

    private String getImagePath(char symbol) {
        switch (symbol) {
            case 'A': return "/images/bell(A).png";
            case 'B': return "/images/banana(B).png";
            case 'C': return "/images/plum(C).png";
            case 'D': return "/images/pear(D).png";
            case 'E': return "/images/cherry(E).png";
            case 'F': return "/images/watermelon(F).png";
            case 'G': return "/images/7(G).png";
            default:  return "/images/default.png";
        }
    }

    @PostMapping("/api/removeRandomSymbol")
    public ResponseEntity<Map<String, Object>> removeRandomSymbol() {
        Map<String, Object> response = new HashMap<>();
        try {
            gameService.removeRandomSymbol();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error removing random symbol: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
