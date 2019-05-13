package com.gmail.etauroginskaya.online_market.repository.impl;

import com.gmail.etauroginskaya.online_market.repository.ReviewRepository;
import com.gmail.etauroginskaya.online_market.repository.exception.DatabaseQueryException;
import com.gmail.etauroginskaya.online_market.repository.model.Review;
import com.gmail.etauroginskaya.online_market.repository.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReviewRepositoryImpl extends ConnectionRepositoryImpl implements ReviewRepository {

    private static final Logger logger = LoggerFactory.getLogger(ReviewRepositoryImpl.class);
    private static final String QUERY_ERROR_MESSAGE = "SQL query Failed! Check output console.";

    @Override
    public List<Review> getReviewsInBatch(Connection connection, int page, int quantityReviewsInBatch) {
        String sql = "SELECT r.*, u.surname, u.name, u.patronymic FROM review r JOIN user u ON r.user_id = u.id " +
                "WHERE r.deleted = false LIMIT ?, ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, page * quantityReviewsInBatch);
            statement.setInt(2, quantityReviewsInBatch);
            try (ResultSet rs = statement.executeQuery()) {
                List<Review> reviews = new ArrayList<>();
                while (rs.next()) {
                    reviews.add(getReview(rs));
                }
                return reviews;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }

    private Review getReview(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setId(rs.getLong("id"));
        review.setDescription(rs.getString("description"));
        review.setCreated(rs.getString("created"));
        review.setShow(rs.getBoolean("show"));
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setSurname(rs.getString("surname"));
        user.setName(rs.getString("name"));
        user.setPatronymic(rs.getString("patronymic"));
        review.setUser(user);
        return review;
    }

    @Override
    public Integer getQuantityReviews(Connection connection) {
        String sql = "SELECT COUNT(*) AS total FROM review WHERE deleted = 'false'";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("total");
            }
            return count;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteListReviews(Connection connection, List<Review> reviews) {
        List<Long> list = reviews.stream().map(Review::getId).collect(Collectors.toList());
        String sql = "UPDATE review SET deleted = true WHERE id IN (?)";
        sql = anyParams(sql, list.size());
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int i = 1;
            for (Long num : list) {
                statement.setLong(i++, num);
            }
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Deleting reviews failed, no row affected.");
                throw new DatabaseQueryException("Deleting reviews failed, no row affected.");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }

    private String anyParams(String sql, final int params) {
        final StringBuilder sb = new StringBuilder(new String(new char[params]).replace("\0", "?,"));
        sb.setLength(Math.max(sb.length() - 1, 0));
        if (sb.length() > 1) {
            sql = sql.replace("(?)", "(" + sb + ")");
        }
        return sql;
    }

    @Override
    public void updateReviewStatusShow(Connection connection, Long id, Boolean newShowStatus) {
        String sql = "UPDATE review SET `show` = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, newShowStatus);
            statement.setLong(2, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                logger.error(String.format("Updating status show review with id %s failed, no row affected.", id));
                throw new DatabaseQueryException
                        (String.format("Updating status show review with id %s failed, no row affected.", id));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseQueryException(QUERY_ERROR_MESSAGE);
        }
    }
}
