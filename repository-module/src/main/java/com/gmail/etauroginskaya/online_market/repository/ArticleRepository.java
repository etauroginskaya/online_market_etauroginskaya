package com.gmail.etauroginskaya.online_market.repository;

import com.gmail.etauroginskaya.online_market.repository.model.Article;

import java.util.List;

public interface ArticleRepository extends GenericRepository<Long, Article> {

    List<Article> getArticlesByCreatedDesc(int page, int maxResult);
}
