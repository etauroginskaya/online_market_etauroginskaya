package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.ReviewRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Review;
<<<<<<< HEAD
import com.gmail.etauroginskaya.online_market.service.converter.ReviewConverter;
import com.gmail.etauroginskaya.online_market.service.exception.ServiceException;
import com.gmail.etauroginskaya.online_market.service.model.ReviewDTO;
import com.gmail.etauroginskaya.online_market.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
=======
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
>>>>>>> develop
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

<<<<<<< HEAD
=======
import static com.gmail.etauroginskaya.online_market.service.constants.FormatConstants.DATE_FORMATTER;
>>>>>>> develop
import static java.util.Arrays.asList;

@Service
public class ReviewServiceImpl implements ReviewService {

<<<<<<< HEAD
    private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
    private static final String CONNECTION_ERROR_MESSAGE = "Connection Failed! Check output console.";
    private static final String TRANSACTION_ERROR_MESSAGE = "Coming transaction Failed! Check output console.";
=======
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
>>>>>>> develop
    private final ReviewRepository reviewRepository;
    private final ReviewConverter reviewConverter;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewConverter reviewConverter) {
        this.reviewRepository = reviewRepository;
        this.reviewConverter = reviewConverter;
    }

    @Override
<<<<<<< HEAD
    public Page<ReviewDTO> getReviewsPage(Pageable pageable) {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int pageSize = pageable.getPageSize();
                int currentPage = pageable.getPageNumber();
                int startItem = currentPage * pageSize;
                int quantityReviews = reviewRepository.getQuantityReviews(connection);
                List<Review> reviews;
                List<ReviewDTO> dtos;
                if (quantityReviews < startItem) {
                    dtos = Collections.emptyList();
                } else {
                    reviews = reviewRepository.getReviewsInBatch(connection, currentPage, pageSize);
                    dtos = reviews.stream().map(reviewConverter::toDTO).collect(Collectors.toList());
                    connection.commit();
                }
                Page<ReviewDTO> reviewPage = new PageImpl<>(dtos, PageRequest.of(currentPage, pageSize), quantityReviews);
                connection.commit();
                return reviewPage;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(TRANSACTION_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public int updateShowReview(Long id, boolean newShowStatus) {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int result = reviewRepository.updateReviewStatusShow(connection, id, newShowStatus);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(TRANSACTION_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(CONNECTION_ERROR_MESSAGE, e);
        }
    }

    @Override
    public int deleteReview(Long id) {
        try (Connection connection = reviewRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Review review = new Review();
                review.setId(id);
                int result = reviewRepository.deleteListReviews(connection, asList(review));
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ServiceException(TRANSACTION_ERROR_MESSAGE, e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(CONNECTION_ERROR_MESSAGE, e);
        }
=======
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
>>>>>>> develop
    }
}
