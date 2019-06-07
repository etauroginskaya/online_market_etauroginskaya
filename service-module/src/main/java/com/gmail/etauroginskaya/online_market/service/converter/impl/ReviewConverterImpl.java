package com.gmail.etauroginskaya.online_market.service.converter.impl;

import com.gmail.etauroginskaya.online_market.repository.model.Review;
import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.service.converter.ReviewConverter;
<<<<<<< HEAD
import com.gmail.etauroginskaya.online_market.service.converter.UserConverter;
=======
>>>>>>> develop
import com.gmail.etauroginskaya.online_market.service.model.ReviewDTO;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewConverterImpl implements ReviewConverter {

<<<<<<< HEAD
    private final UserConverter userConverter;

    public ReviewConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

=======
>>>>>>> develop
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
<<<<<<< HEAD
        userDTO.setPatronymic(review.getUser().getPatronymic());
=======
>>>>>>> develop
        reviewDTO.setUser(userDTO);
        reviewDTO.setShow(review.isShow());
        return reviewDTO;
    }

    @Override
    public Review fromDTO(ReviewDTO reviewDTO) {
        Review review = new Review();
<<<<<<< HEAD
        review.setId(reviewDTO.getId());
        review.setDescription(reviewDTO.getDescription());
        review.setCreated(reviewDTO.getCreated());
        User user = userConverter.fromDTO(reviewDTO.getUser());
        review.setUser(user);
        review.setShow(review.isShow());
=======
        review.setDescription(reviewDTO.getDescription());
        User user = new User();
        user.setId(reviewDTO.getUser().getId());
        review.setUser(user);
>>>>>>> develop
        return review;
    }
}
