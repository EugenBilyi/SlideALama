package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        var existingRating = entityManager.createQuery("select r from Rating r where r.game = :game and r.player = :player and r.ratedOn = :ratedOn", Rating.class)
                .setParameter("game", rating.getGame())
                .setParameter("player", rating.getPlayer())
                .setParameter("ratedOn", rating.getRatedOn())
                .getResultList();
        if (existingRating.isEmpty()) {
            entityManager.persist(rating);
        } else {
            Rating updateRating = existingRating.get(0);
            updateRating.setRating(rating.getRating());
            updateRating.setRatedOn(rating.getRatedOn());
            entityManager.merge(updateRating);
        }
    }

    @Override
    public List<Rating> getRatings(String game) {
        return getAllRatings(game);
    }

    @Override
    public List<Rating> getAllRatings(String game) {
        var query = entityManager.createQuery("select r from Rating r where r.game = :game", Rating.class);
        query.setParameter("game", game);
        return query.getResultList();
    }

    @Override
    public int getAverageRating(String game) {
        var query = entityManager.createQuery("select avg(r.rating) from Rating r where r.game = :game", Double.class);
        query.setParameter("game", game);
        Double result = query.getSingleResult();
        return result == null ? 0 : result.intValue();
    }

    @Override
    public int getRating(String game, String player) {
        var query = entityManager.createQuery("select r.rating from Rating r where r.game = :game and r.player = :player", Integer.class);
        query.setParameter("game", game);
        query.setParameter("player", player);
        Integer result = query.getSingleResult();
        return result == null ? 0 : result;
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("delete from rating").executeUpdate();
    }
}
