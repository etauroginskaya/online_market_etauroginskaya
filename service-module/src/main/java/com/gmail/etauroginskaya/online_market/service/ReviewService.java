package com.gmail.etauroginskaya.online_market.service;

import com.gmail.etauroginskaya.online_market.service.model.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Page<ReviewDTO> getReviewsPage(Pageable pageable);

    int updateShowReview(Long id, boolean newShowStatus);

    int deleteReview(Long id);
}
