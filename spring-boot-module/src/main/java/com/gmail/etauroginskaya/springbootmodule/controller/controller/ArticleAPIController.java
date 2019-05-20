package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.ArticleService;
import com.gmail.etauroginskaya.online_market.service.model.ArticleDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.API_ARTICLES_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.API_ARTICLE_URL;

@RestController
public class ArticleAPIController {

    private final ArticleService articleService;

    public ArticleAPIController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(API_ARTICLES_URL)
    public Page<ArticleDTO> getArticlesPage(@RequestParam(value = "page", defaultValue = "1") Integer currentPage,
                                            @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        return articleService.getArticlePage(pageSize, currentPage);
    }

    @GetMapping(API_ARTICLE_URL)
    public ArticleDTO getArticle(@PathVariable("id") Long id) {
        return articleService.getArticleById(id);
    }

    @PostMapping(API_ARTICLES_URL)
    public ArticleDTO saveArticle(@RequestBody ArticleDTO articleDTO) {
        return articleService.addArticle(articleDTO);
    }

    @DeleteMapping(API_ARTICLE_URL)
    public void deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteArticleById(id);
    }
}
