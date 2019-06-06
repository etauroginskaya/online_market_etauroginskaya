package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.ReviewRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Review;
import com.gmail.etauroginskaya.online_market.service.ReviewService;
import com.gmail.etauroginskaya.online_market.service.converter.ReviewConverter;
import com.gmail.etauroginskaya.online_market.service.model.ReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.etauroginskaya.online_market.service.constants.FormatConstants.DATE_FORMATTER;
import static java.util.Arrays.asList;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewConverter reviewConverter) {
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
    }

    @Override
    @Transactional
    public Page<ReviewDTO> getReviewsPage(int pageSize, int currentPage) {
        currentPage--;
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
        return new PageImpl<>(dtos, PageRequest.of(currentPage, pageSize), quantityReviews);
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

    @Override
    @Transactional
    public void addReview(ReviewDTO reviewDTO) {
        Review review = reviewConverter.fromDTO(reviewDTO);
        review.setCreated(LocalDateTime.now().format(formatter));
        review.setShow(true);
        reviewRepository.persist(review);
    }
}
