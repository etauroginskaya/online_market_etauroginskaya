package com.gmail.etauroginskaya.online_market.service.converter;

import com.gmail.etauroginskaya.online_market.repository.model.Comment;
import com.gmail.etauroginskaya.online_market.service.model.CommentDTO;

public interface CommentConverter {

    CommentDTO toDTO(Comment comment);

    Comment toEntity(CommentDTO commentDTO);
}
