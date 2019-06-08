package com.gmail.etauroginskaya.online_market.service.converter.impl;

import com.gmail.etauroginskaya.online_market.repository.model.Review;
import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.service.converter.ReviewConverter;
import com.gmail.etauroginskaya.online_market.service.model.ReviewDTO;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverterImpl implements ReviewConverter {

    @Override
    public ReviewDTO toDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setDescription(review.getDescription());
        reviewDTO.setCreated(review.getCreated());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(review.getUser().getId());
        userDTO.setSurname(review.getUser().getSurname());
        userDTO.setName(review.getUser().getName());
        reviewDTO.setUser(userDTO);
        reviewDTO.setShow(review.isShow());
        return reviewDTO;
    }

    @Override
    public Review fromDTO(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setDescription(reviewDTO.getDescription());
        User user = new User();
        user.setId(reviewDTO.getUser().getId());
        review.setUser(user);
        return review;
    }
}