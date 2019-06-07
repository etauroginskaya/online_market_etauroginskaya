package com.gmail.etauroginskaya.online_market.service;

import com.gmail.etauroginskaya.online_market.service.model.CommentDTO;

public interface CommentService {

    long deleteCommentById(Long id);

    void addComment(CommentDTO commentDTO);
}
