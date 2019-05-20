package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.ArticleService;
import com.gmail.etauroginskaya.online_market.service.model.ArticleDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.ARTICLE_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ARTICLES_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ARTICLE_URL;
import static java.util.Arrays.asList;

@Controller
@RequestMapping()
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(ARTICLES_URL)
    public String getViewArticles(Model model,
                                  @RequestParam(value = "page", defaultValue = "1") Integer currentPage,
                                  @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<ArticleDTO> articlePage = articleService.getArticlePage(pageSize, currentPage - 1);
        model.addAttribute("articlePage", articlePage);
        if (articlePage.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, articlePage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("countsItem", asList(10, 25, 50));
        return ARTICLES_URL;
    }

    @GetMapping(ARTICLE_URL)
    public String getArticle(Model model,
                             @PathVariable("id") Long id) {
        ArticleDTO articleDTO = articleService.getArticleById(id);
        model.addAttribute("article", articleDTO);
        return ARTICLE_PAGE;
    }
}
