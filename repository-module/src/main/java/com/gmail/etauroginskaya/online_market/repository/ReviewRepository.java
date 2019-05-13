package com.gmail.etauroginskaya.online_market.repository;

import com.gmail.etauroginskaya.online_market.repository.model.Review;

import java.sql.Connection;
import java.util.List;

public interface ReviewRepository extends ConnectionRepository {

    List<Review> getReviewsInBatch(Connection connection, int page, int quantityReviewsInBatch);

    Integer getQuantityReviews(Connection connection);

    void deleteListReviews(Connection connection, List<Review> reviews);

    void updateReviewStatusShow(Connection connection, Long id, Boolean newShowStatus);
}
