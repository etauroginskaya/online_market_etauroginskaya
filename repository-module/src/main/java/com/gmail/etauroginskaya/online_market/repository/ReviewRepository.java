package com.gmail.etauroginskaya.online_market.repository;

import com.gmail.etauroginskaya.online_market.repository.model.Review;

import java.util.List;

public interface ReviewRepository extends GenericRepository<Long, Review> {

    int deleteReviewsById(List<Long> reviewsId);

    int updateReviewStatusShow(Long id, boolean newShowStatus);
}
