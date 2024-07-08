package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingServiceJDBC implements RatingService {
    public static final String URL = "jdbc:postgresql://localhost:5432/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "0668120458";
    public static final String SELECT_TOP = "SELECT player, game, rating, ratedOn FROM rating WHERE game = ? ORDER BY rating DESC LIMIT 10";
    public static final String DELETE_ALL = "DELETE FROM rating";
    public static final String INSERT_RATING = "INSERT INTO rating (player, game, rating, ratedOn) VALUES (?, ?, ?, ?)";
    public static final String SELECT_AVERAGE = "SELECT AVG(rating) FROM rating WHERE game = ?";
    public static final String SELECT_RATING = "SELECT rating FROM rating WHERE game = ? AND player = ?";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT_RATING)) {
            statement.setString(1, rating.getPlayer());
            statement.setString(2, rating.getGame());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RatingException("Problem inserting rating", e);
        }
    }

    @Override
    public List<Rating> getRatings(String game) throws RatingException {
        return getAllRatings(game);
    }

    @Override
    public List<Rating> getAllRatings(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT player, game, rating, ratedOn FROM rating WHERE game = ?")) {
            statement.setString(1, game);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Rating> ratings = new ArrayList<>();
                while (resultSet.next()) {
                    String player = resultSet.getString("player");
                    int rating = resultSet.getInt("rating");
                    Timestamp ratedOn = resultSet.getTimestamp("ratedOn");
                    ratings.add(new Rating(player, game, rating, new Date(ratedOn.getTime())));
                }
                return ratings;
            }
        } catch (SQLException e) {
            throw new RatingException("Problem retrieving all ratings", e);
        }
    }


    @Override
    public int getAverageRating(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_AVERAGE)) {
            statement.setString(1, game);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    throw new RatingException("No ratings found for the game: " + game);
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem getting average rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_RATING)) {
            statement.setString(1, game);
            statement.setString(2, player);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    throw new RatingException("No rating found for player " + player + " in the game " + game);
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem getting rating", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE_ALL);
        } catch (SQLException e) {
            throw new RatingException("Problem resetting ratings", e);
        }
    }

    public List<Rating> getTopRatings(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_TOP)) {
            statement.setString(1, game);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Rating> ratings = new ArrayList<>();
                while (resultSet.next()) {
                    String player = resultSet.getString("player");
                    int rating = resultSet.getInt("rating");
                    Date ratedOn = resultSet.getDate("ratedOn");
                    ratings.add(new Rating(player, game, rating, ratedOn));
                }
                return ratings;
            }
        } catch (SQLException e) {
            throw new RatingException("Problem getting top ratings", e);
        }
    }
}