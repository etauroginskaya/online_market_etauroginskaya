package com.gmail.etauroginskaya.online_market.service;

import com.gmail.etauroginskaya.online_market.service.model.ArticleDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArticleService {

    Page<ArticleDTO> getArticlePage(int pageSize, int currentPage);

    List<ArticleDTO> getAllArticles();

    ArticleDTO addArticle(ArticleDTO articleDTO);

    ArticleDTO getArticleById(Long id);

    ArticleDTO deleteArticleById(Long id);

    void updateArticleTitleAndDescription(ArticleDTO articleDTO);
}
