package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Arrays;
import java.util.List;

@Service
public class RatingServiceRestClient implements RatingService {
    private final String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) {
        return restTemplate.getForObject(url + game + "/average", Integer.class);
    }

    @Override
    public List<Rating> getAllRatings(String game) {
        return Arrays.asList(restTemplate.getForObject(url + "/" + game + "/all", Rating[].class));
    }

    @Override
    public List<Rating> getRatings(String game) {
        return Arrays.asList(restTemplate.getForObject(url + "/" + game + "/all", Rating[].class));
    }


    @Override
    public int getRating(String game, String player) {
        return restTemplate.getForObject(url + "/" + game + "/" + player, Integer.class);
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
