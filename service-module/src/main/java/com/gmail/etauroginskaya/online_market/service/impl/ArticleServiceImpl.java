package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.ArticleRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Article;
import com.gmail.etauroginskaya.online_market.repository.model.Comment;
import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.service.ArticleService;
import com.gmail.etauroginskaya.online_market.service.converter.ArticleConverter;
import com.gmail.etauroginskaya.online_market.service.model.ArticleDTO;
import com.gmail.etauroginskaya.online_market.service.model.CommentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    private static int LIMIT_SHORT_DESCRIPTION = 200;

    private final ArticleRepository articleRepository;
    private final ArticleConverter articleConverter;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleConverter articleConverter) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
    }

    @Override
    @Transactional
    public Page<ArticleDTO> getArticlePage(int pageSize, int currentPage) {
        int startItem = currentPage * pageSize;
        int quantityEntity = articleRepository.getCountOfEntities();
        List<ArticleDTO> dtos;
        if (quantityEntity < startItem) {
            dtos = Collections.emptyList();
        } else {
            List<Article> articles = articleRepository.getLimitEntityWithOffset(startItem, pageSize);
            dtos = articles.stream()
                    .map(articleConverter::toDTO)
                    .collect(Collectors.toList());
            for (ArticleDTO articleDTO : dtos) {
                if (articleDTO.getDescription().length() > LIMIT_SHORT_DESCRIPTION) {
                    String shortDescription = articleDTO.getDescription().substring(0, LIMIT_SHORT_DESCRIPTION)
                            .concat("...");
                    articleDTO.setDescription(shortDescription);
                }
            }
        }
        Page<ArticleDTO> articlePage = new PageImpl<>(dtos, PageRequest.of(currentPage, pageSize), quantityEntity);
        return articlePage;
    }

    @Override
    @Transactional
    public ArticleDTO addArticle(ArticleDTO articleDTO) {
        Article article = articleConverter.toEntity(articleDTO);
        Comment comment = new Comment();
        for (CommentDTO commentDTO : articleDTO.getComments()) {
            comment.setCreated(commentDTO.getCreated());
            comment.setDescription(commentDTO.getDescription());
            User user = new User();
            user.setId(commentDTO.getUserDTO().getId());
            comment.setUser(user);
        }
        article.setComments(Collections.singleton(comment));
        articleRepository.persist(article);
        return articleConverter.toDTO(article);
    }

    @Override
    @Transactional
    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.getById(id);
        ArticleDTO articleDTO = articleConverter.toDTO(article);
        return articleDTO;
    }

    @Override
    @Transactional
    public void deleteArticleById(Long id) {
        Article article = articleRepository.getById(id);
        articleRepository.remove(article);
    }
}