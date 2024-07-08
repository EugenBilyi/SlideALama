package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import sk.tuke.gamestudio.service.*;
import sk.tuke.gamestudio.entity.*;

import java.util.Map;

@Controller
@RequestMapping("/slidealama")
@SessionScope
public class SlideALamaController {
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;

    @GetMapping
    public String slideALama(Model model) {
        model.addAttribute("scores", scoreService.getTopScores("SlideALama"));
        model.addAttribute("comments", commentService.getComments("SlideALama"));
        model.addAttribute("averageRating", ratingService.getAverageRating("SlideALama"));
        return "slidealama";
    }

    @GetMapping("/game")
    public String game() {
        return "redirect:/game.html";  // Перенаправляет пользователя непосредственно на game.html
    }

    @PostMapping("/saveScore")
    public String saveScore(@RequestParam String player, @RequestParam int score) {
        scoreService.addScore(new Score("SlideALama", player, score, new java.util.Date()));
        return "redirect:/slidealama"; // Перенаправление обратно на основную страницу после сохранения счета
    }



    @GetMapping("/guest")
    public String slideALamaGuest(Model model) {
        model.addAttribute("scores", scoreService.getTopScores("SlideALama"));
        model.addAttribute("comments", commentService.getComments("SlideALama"));
        return "slidealamaUnAuthorising"; // Имя файла slidealamaUnAuthorising.html без расширения, если используете Thymeleaf
    }

    @PostMapping("/comment")
    public String addComment(@RequestParam String player, @RequestParam String comment) {
        if (player == null || player.trim().isEmpty()) {
            return "redirect:/slidealama"; // Возвращаем пользователя обратно на форму с сообщением об ошибке
        }
        else if (player != null && (comment == null || comment.trim().isEmpty())) {
            return "redirect:/slidealama"; // Возвращаем пользователя обратно на форму с сообщением об ошибке

        }
        commentService.addComment(new Comment(player, "SlideALama", comment, new java.util.Date()));
        return "redirect:/slidealama";
    }

    @PostMapping("/rate")
    public String rateGame(@RequestParam String player, @RequestParam int rating) {
        if (player == null || player.trim().isEmpty()) {
            return "redirect:/slidealama"; // Возвращаем пользователя обратно на форму с сообщением об ошибке
        }
        ratingService.setRating(new Rating(player, "SlideALama", rating, new java.util.Date()));
        return "redirect:/slidealama";
    }

    @Autowired
    private GameService gameService;

    @GetMapping("/field")
    @ResponseBody
    public String getHtmlField() {
        char[][] board = gameService.getBoard();
        StringBuilder sb = new StringBuilder();

        // Добавление верхних кнопок с уникальными классами
        sb.append("<div class='top-buttons'>");
        for (int col = 0; col < board[0].length; col++) {
            sb.append("<button class='subButtonTop subButtonTop" + (col + 1) + "'><img src='/images/arrowDown.png'></button>");
        }
        sb.append("</div>");

        // Генерация строки таблицы с кнопками слева и справа
        sb.append("<table>\n");
        for (int row = 0; row < board.length; row++) {
            sb.append("<tr>");
            sb.append("<td><button class='subButtonLeft subButtonLeft" + (row + 1) + "'><img src='/images/arrowRight.png'></button></td>"); // Кнопка слева с уникальным классом
            for (int col = 0; col < board[row].length; col++) {
                sb.append("<td class='everyCell'><img src='").append(getImagePath(board[row][col])).append("' class='game-cell'></td>\n");
            }
            sb.append("<td><button class='subButtonRight subButtonRight" + (row + 1) + "'><img src='/images/arrowLeft.png'></button></td>"); // Кнопка справа с уникальным классом
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");

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
            default:  return "/images/default.png"; // Добавьте изображение по умолчанию, если символ не распознан
        }
    }

}
