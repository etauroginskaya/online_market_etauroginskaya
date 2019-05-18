package com.gmail.etauroginskaya.online_market.repository;

import com.gmail.etauroginskaya.online_market.repository.model.Review;

import java.sql.Connection;
import java.util.List;

public interface ReviewRepository extends ConnectionRepository {

    List<Review> getReviewsInBatch(Connection connection, int page, int quantityReviewsInBatch);

    int getQuantityReviews(Connection connection);

    int deleteListReviews(Connection connection, List<Review> reviews);

    int updateReviewStatusShow(Connection connection, Long id, boolean newShowStatus);
}
