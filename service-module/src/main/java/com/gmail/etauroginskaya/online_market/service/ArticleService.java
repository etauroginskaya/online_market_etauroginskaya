package com.gmail.etauroginskaya.online_market.service;

import com.gmail.etauroginskaya.online_market.service.model.ArticleDTO;
import org.springframework.data.domain.Page;

public interface ArticleService {

    Page<ArticleDTO> getArticlePage(int pageSize, int currentPage);

    ArticleDTO addArticle(ArticleDTO articleDTO);

    ArticleDTO getArticleById(Long id);

    void deleteArticleById(Long id);
}
