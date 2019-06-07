package com.gmail.etauroginskaya.online_market.service.impl;

import com.gmail.etauroginskaya.online_market.repository.CommentRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Comment;
import com.gmail.etauroginskaya.online_market.service.CommentService;
import com.gmail.etauroginskaya.online_market.service.converter.CommentConverter;
import com.gmail.etauroginskaya.online_market.service.model.CommentDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.gmail.etauroginskaya.online_market.service.constants.FormatConstants.DATE_FORMATTER;

@Service
public class CommentServiceImpl implements CommentService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;

    public CommentServiceImpl(CommentRepository commentRepository, CommentConverter commentConverter) {
        this.commentRepository = commentRepository;
        this.commentConverter = commentConverter;
    }

    @Override
    @Transactional
    public long deleteCommentById(Long id) {
        Comment comment = commentRepository.getById(id);
        commentRepository.remove(comment);
        return comment.getArticle().getId();
    }

    @Override
    @Transactional
    public void addComment(CommentDTO commentDTO) {
        Comment comment = commentConverter.toEntityForSave(commentDTO);
        comment.setCreated(LocalDateTime.now().format(formatter));
        commentRepository.persist(comment);
    }
}