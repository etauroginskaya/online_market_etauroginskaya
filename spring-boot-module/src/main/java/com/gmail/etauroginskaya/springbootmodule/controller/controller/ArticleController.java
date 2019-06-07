package com.gmail.etauroginskaya.springbootmodule.controller.controller;

import com.gmail.etauroginskaya.online_market.service.ArticleService;
import com.gmail.etauroginskaya.online_market.service.model.AppUserPrincipal;
import com.gmail.etauroginskaya.online_market.service.model.ArticleDTO;
import com.gmail.etauroginskaya.online_market.service.model.CommentDTO;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.gmail.etauroginskaya.online_market.service.constants.FormatConstants.DATE_FORMATTER;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.ARTICLES_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.ARTICLE_NEW_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.PageConstants.ARTICLE_PAGE;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.ADD_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.ParameterConstants.DELETE_SUCCESSFULLY;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ARTICLES_ADD_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ARTICLES_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ARTICLE_DELETE_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ARTICLE_UPDATE_URL;
import static com.gmail.etauroginskaya.springbootmodule.controller.constant.UrlConstants.ARTICLE_URL;
import static java.util.Arrays.asList;

@Controller
@RequestMapping()
public class ArticleController {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(ARTICLES_URL)
    public String getViewArticles(Model model,
                                  @RequestParam(value = "page", defaultValue = "1") Integer currentPage,
                                  @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<ArticleDTO> articlePage = articleService.getArticlePage(pageSize, currentPage);
        model.addAttribute("articlePage", articlePage);
        if (articlePage.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, articlePage.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("countsItem", asList(10, 25, 50));
        return ARTICLES_PAGE;
    }

    @GetMapping(ARTICLE_URL)
    public String getArticle(Model model,
                             @PathVariable("id") Long id) {
        ArticleDTO articleDTO = articleService.getArticleById(id);
        model.addAttribute("article", articleDTO);
        model.addAttribute("comment", new CommentDTO());
        return ARTICLE_PAGE;
    }

    @PostMapping(ARTICLE_DELETE_URL)
    public String deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteArticleById(id);
        return "redirect:".concat(ARTICLES_URL).concat(DELETE_SUCCESSFULLY);
    }

    @PostMapping(ARTICLE_UPDATE_URL)
    public String updateArticle(@PathVariable("id") Long id,
                                @Valid @ModelAttribute("article") ArticleDTO articleDTO,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            ArticleDTO article = articleService.getArticleById(id);
            articleDTO.setUser(article.getUser());
            articleDTO.setComments(article.getComments());
            articleDTO.setCreated(article.getCreated());
            model.addAttribute("article", articleDTO);
            return ARTICLE_PAGE;
        }
        articleService.updateArticleTitleAndDescription(articleDTO);
        return "redirect:".concat(ARTICLE_URL);
    }

    @GetMapping(value = {ARTICLES_ADD_URL})
    public String getNewArticleForm(Model model,
                                    @ModelAttribute(name = "article") ArticleDTO articleDTO) {
        model.addAttribute("currentData", LocalDateTime.now().format(formatter));
        return ARTICLE_NEW_PAGE;
    }

    @PostMapping(ARTICLES_ADD_URL)
    public String addArticle(@AuthenticationPrincipal AppUserPrincipal userPrincipal,
                             @Valid @ModelAttribute("article") ArticleDTO articleDTO,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("currentData", LocalDateTime.now().format(formatter));
            return ARTICLE_NEW_PAGE;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userPrincipal.getId());
        articleDTO.setUser(userDTO);
        articleService.addArticle(articleDTO);
        return "redirect:".concat(ARTICLES_URL).concat(ADD_SUCCESSFULLY);
    }
}