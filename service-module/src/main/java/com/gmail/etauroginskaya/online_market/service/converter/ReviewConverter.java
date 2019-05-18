package com.gmail.etauroginskaya.online_market.service.converter;

import com.gmail.etauroginskaya.online_market.repository.model.Review;
import com.gmail.etauroginskaya.online_market.service.model.ReviewDTO;

public interface ReviewConverter {

    ReviewDTO toDTO(Review review);

    Review fromDTO(ReviewDTO reviewDTO);
}
