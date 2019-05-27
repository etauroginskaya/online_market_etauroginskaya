package com.gmail.etauroginskaya.online_market.repository.impl;

import com.gmail.etauroginskaya.online_market.repository.ReviewRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ReviewRepositoryImpl extends GenericRepositoryImpl<Long, Review> implements ReviewRepository {

    private static final Logger logger = LoggerFactory.getLogger(ReviewRepositoryImpl.class);

    @Override
    public int deleteReviewsById(List<Long> reviewsId) {
        String hql = "UPDATE Review R SET R.isDeleted=true WHERE R.id IN :listID";
        Query query = entityManager.createQuery(hql)
                .setParameter("listID", reviewsId);
        return query.executeUpdate();
    }

    @Override
    public int updateReviewStatusShow(Long id, boolean newShowStatus) {
        String hql = "UPDATE Review R SET R.isShow=:show WHERE U.id=:id";
        Query query = entityManager.createQuery(hql)
                .setParameter("show", newShowStatus);
        return query.executeUpdate();
    }
}
