package com.gmail.etauroginskaya.online_market.service.converter.impl;

import com.gmail.etauroginskaya.online_market.repository.model.Article;
import com.gmail.etauroginskaya.online_market.repository.model.Comment;
import com.gmail.etauroginskaya.online_market.repository.model.User;
import com.gmail.etauroginskaya.online_market.service.converter.CommentConverter;
import com.gmail.etauroginskaya.online_market.service.converter.UserConverter;
import com.gmail.etauroginskaya.online_market.service.model.CommentDTO;
import org.springframework.stereotype.Component;

@Component
public class CommentConverterImpl implements CommentConverter {

    private final UserConverter userConverter;

    public CommentConverterImpl(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public CommentDTO toDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setCreated(comment.getCreated());
        commentDTO.setDescription(comment.getDescription());
        commentDTO.setUserDTO(userConverter.toDTO(comment.getUser()));
        return commentDTO;
    }

    @Override
    public Comment toEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setCreated(commentDTO.getCreated());
        comment.setDescription(commentDTO.getDescription());
        comment.setUser(userConverter.fromDTO(commentDTO.getUserDTO()));
        return comment;
    }

    @Override
    public Comment toEntityForSave(CommentDTO commentDTO) {
        Comment comment = new Comment();
        User user = new User();
        user.setId(commentDTO.getUserDTO().getId());
        comment.setUser(user);
        comment.setDescription(commentDTO.getDescription());
        Article article = new Article();
        article.setId(commentDTO.getArticleID());
        comment.setArticle(article);
        return comment;
    }
}
