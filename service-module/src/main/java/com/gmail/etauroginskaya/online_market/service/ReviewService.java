package com.gmail.etauroginskaya.online_market.service;

import com.gmail.etauroginskaya.online_market.service.model.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Page<ReviewDTO> getReviewsInPage(Pageable pageable);

    void updateStatusReview(Long id, Boolean newShowStatus);

    void deleteReview(Long id);
}
