package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.ReviewRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Review;
import com.gmail.etauroginskaya.online_market.service.ReviewService;
import com.gmail.etauroginskaya.online_market.service.converter.ReviewConverter;
import com.gmail.etauroginskaya.online_market.service.model.ReviewDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewConverter reviewConverter) {
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
    }

    @Override
    @Transactional
    public Page<ReviewDTO> getReviewsPage(int pageSize, int currentPage) {
        int startItem = currentPage * pageSize;
        int quantityReviews = reviewRepository.getCountOfEntities();
        List<ReviewDTO> dtos;
        if (quantityReviews < startItem) {
            dtos = Collections.emptyList();
        } else {
            List<Review> reviews = reviewRepository.getLimitEntityWithOffset(currentPage, pageSize);
            dtos = reviews.stream()
                    .map(reviewConverter::toDTO)
                    .collect(Collectors.toList());
        }
        Page<ReviewDTO> reviewPage = new PageImpl<>(dtos, PageRequest.of(currentPage, pageSize), quantityReviews);
        return reviewPage;
    }

    @Override
    @Transactional
    public int updateShowReview(Long id, boolean newShowStatus) {
        return reviewRepository.updateReviewStatusShow(id, newShowStatus);
    }

    @Override
    @Transactional
    public int deleteReviewById(Long id) {
        return reviewRepository.deleteReviewsById(asList(id));
    }
}
