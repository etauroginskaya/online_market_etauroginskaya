package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.ArticleService;
import com.gmail.etauroginskaya.online_market.service.model.ArticleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.API_ARTICLES_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.API_ARTICLE_URL;

@RestController
public class ArticleAPIController {

    private final ArticleService articleService;

    public ArticleAPIController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(API_ARTICLES_URL)
    public ResponseEntity getArticles() {
        List<ArticleDTO> dtos = articleService.getAllArticles();
        return new ResponseEntity(dtos, HttpStatus.OK);
    }

    @GetMapping(API_ARTICLE_URL)
    public ResponseEntity getArticle(@PathVariable("id") Long id) {
        ArticleDTO articleDTO = articleService.getArticleById(id);
        if (articleDTO == null) {
            return new ResponseEntity("Not found article or article is deleted", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity(articleDTO, HttpStatus.OK);
    }

    @PostMapping(API_ARTICLES_URL)
    public ArticleDTO saveArticle(@RequestBody ArticleDTO articleDTO) {
        return articleService.addArticle(articleDTO);
    }

    @DeleteMapping(API_ARTICLE_URL)
    public ResponseEntity deleteArticle(@PathVariable("id") Long id) {
        ArticleDTO articleDTO = articleService.deleteArticleById(id);
        if (articleDTO == null) {
            return new ResponseEntity("Not found article or article is deleted earlier", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity(articleDTO, HttpStatus.OK);
    }
}
