package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.ArticleRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Article;
import com.gmail.etauroginskaya.online_market.repository.model.Comment;
import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.service.ArticleService;
import com.gmail.etauroginskaya.online_market.service.converter.ArticleConverter;
import com.gmail.etauroginskaya.online_market.service.model.ArticleDTO;
import com.gmail.etauroginskaya.online_market.service.model.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.etauroginskaya.online_market.service.constants.FormatConstants.DATE_FORMATTER;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static int LIMIT_SHORT_DESCRIPTION = 200;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);

    private final ArticleRepository articleRepository;
    private final ArticleConverter articleConverter;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleConverter articleConverter) {
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
    }

    @Override
    @Transactional
    public Page<ArticleDTO> getArticlePage(int pageSize, int currentPage) {
        currentPage--;
        int startItem = currentPage * pageSize;
        int quantityEntity = articleRepository.getCountOfEntities();
        List<ArticleDTO> dtos;
        if (quantityEntity < startItem) {
            dtos = Collections.emptyList();
        } else {
            List<Article> articles = articleRepository.getArticlesByCreatedDesc(startItem, pageSize);
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
        return new PageImpl<>(dtos, PageRequest.of(currentPage, pageSize), quantityEntity);
    }

    @Override
    @Transactional
    public List<ArticleDTO> getAllArticles() {
        return articleRepository.getAll().stream()
                .map(articleConverter::toDTOForAPI)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ArticleDTO addArticle(ArticleDTO articleDTO) {
        articleDTO.setCreated(LocalDateTime.now().format(formatter));
        Article article = articleConverter.toEntity(articleDTO);
        if (!articleDTO.getComments().isEmpty()) {
            Comment comment = new Comment();
            for (CommentDTO commentDTO : articleDTO.getComments()) {
                comment.setCreated(commentDTO.getCreated());
                comment.setDescription(commentDTO.getDescription());
                User user = new User();
                user.setId(commentDTO.getUserDTO().getId());
                comment.setUser(user);
            }
            article.setComments(Collections.singleton(comment));
        }
        articleRepository.persist(article);
        return articleConverter.toDTO(article);
    }

    @Override
    @Transactional
    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.getById(id);
        if (article == null) {
            return null;
        }
        return articleConverter.toDTO(article);
    }

    @Override
    @Transactional
    public ArticleDTO deleteArticleById(Long id) {
        Article article = articleRepository.getById(id);
        if (article != null) {
            articleRepository.remove(article);
            return articleConverter.toDTOForAPI(article);
        }
        return null;
    }

    @Override
    @Transactional
    public void updateArticleTitleAndDescription(ArticleDTO articleDTO) {
        Article article = articleRepository.getById(articleDTO.getId());
        article.setTitle(articleDTO.getTitle());
        article.setDescription(articleDTO.getDescription());
        article.setCreated(LocalDateTime.now().format(formatter));
        articleRepository.merge(article);
    }
}