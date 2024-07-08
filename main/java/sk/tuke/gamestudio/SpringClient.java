package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.WebApplicationType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

import sk.tuke.gamestudio.service.*;
import sk.tuke.gamestudio.core.*;

@SpringBootApplication
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "sk.tuke.gamestudio.server.*"))
//@ComponentScan(basePackages = "sk.tuke.gamestudio")
public class SpringClient {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringClient.class)
                .web(WebApplicationType.NONE) // Указываем, что веб-сервер не нужен
                .run(args);
    }

    @Bean
    public CommandLineRunner runner(SlideALama slideALama) {
        return args -> slideALama.playGame();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Использование REST клиентов для всех сервисных компонентов
    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceRestClient();
    }

    @Bean
    public RatingService ratingService() {
        return new RatingServiceRestClient();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceRestClient();
    }
}