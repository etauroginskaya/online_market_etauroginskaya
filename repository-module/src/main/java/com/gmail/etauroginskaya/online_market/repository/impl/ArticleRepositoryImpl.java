package com.gmail.etauroginskaya.online_market.repository.impl;

import com.gmail.etauroginskaya.online_market.repository.ArticleRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ArticleRepositoryImpl extends GenericRepositoryImpl<Long, Article> implements ArticleRepository {

    private static final Logger logger = LoggerFactory.getLogger(ArticleRepositoryImpl.class);

    @Override
    public List<Article> getArticlesByCreatedDesc(int page, int maxResult) {
        String hql = "FROM Article AS A ORDER BY A.created DESC";
        Query query = entityManager.createQuery(hql)
                .setFirstResult(page)
                .setMaxResults(maxResult);
        return query.getResultList();
    }
}
