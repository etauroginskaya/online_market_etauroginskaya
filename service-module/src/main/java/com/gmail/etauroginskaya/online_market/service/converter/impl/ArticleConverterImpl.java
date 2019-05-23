package com.gmail.etauroginskaya.online_market.service.converter.impl;

import com.gmail.etauroginskaya.online_market.repository.model.Article;
import com.gmail.etauroginskaya.online_market.repository.model.Comment;
import com.gmail.etauroginskaya.online_market.service.converter.ArticleConverter;
import com.gmail.etauroginskaya.online_market.service.converter.CommentConverter;
import com.gmail.etauroginskaya.online_market.service.converter.UserConverter;
import com.gmail.etauroginskaya.online_market.service.model.ArticleDTO;
import com.gmail.etauroginskaya.online_market.service.model.CommentDTO;
import com.gmail.etauroginskaya.online_market.service.model.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ArticleConverterImpl implements ArticleConverter {

    private final CommentConverter commentConverter;
    private final UserConverter userConverter;

    public ArticleConverterImpl(CommentConverter commentConverter, UserConverter userConverter) {
        this.commentConverter = commentConverter;
        this.userConverter = userConverter;
    }

    @Override
    public ArticleDTO toDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setCreated(article.getCreated());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setDescription(article.getDescription());
        UserDTO userDTO = new UserDTO();
        userDTO.setSurname(article.getUser().getSurname());
        userDTO.setName(article.getUser().getName());
        articleDTO.setUser(userDTO);
        List<CommentDTO> dtos = article.getComments().stream()
                .map(commentConverter::toDTO)
                .collect(Collectors.toList());
        articleDTO.setComments(dtos);
        return articleDTO;
    }

    @Override
    public Article toEntity(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setCreated(articleDTO.getCreated());
        article.setDescription(articleDTO.getDescription());
        article.setUser(userConverter.fromDTO(articleDTO.getUser()));
        Set<Comment> comments = articleDTO.getComments().stream()
                .map(commentConverter::toEntity)
                .collect(Collectors.toSet());
        article.setComments(comments);
        return article;
    }
}
