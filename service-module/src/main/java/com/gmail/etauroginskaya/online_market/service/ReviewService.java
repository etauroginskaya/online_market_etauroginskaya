package com.gmail.etauroginskaya.online_market.service;

import com.gmail.etauroginskaya.online_market.service.model.ReviewDTO;
import org.springframework.data.domain.Page;
<<<<<<< HEAD
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Page<ReviewDTO> getReviewsPage(Pageable pageable);

    int updateShowReview(Long id, boolean newShowStatus);

    int deleteReview(Long id);
=======

public interface ReviewService {

    Page<ReviewDTO> getReviewsPage(int pageSize, int currentPage);

    int updateShowReview(Long id, boolean newShowStatus);

    int deleteReviewById(Long id);

    void addReview(ReviewDTO reviewDTO);
>>>>>>> develop
}
