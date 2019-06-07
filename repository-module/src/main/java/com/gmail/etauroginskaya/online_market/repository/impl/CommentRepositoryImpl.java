package com.gmail.etauroginskaya.online_market.repository.impl;

import com.gmail.etauroginskaya.online_market.repository.CommentRepository;
import com.gmail.etauroginskaya.online_market.repository.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositoryImpl extends GenericRepositoryImpl<Long, Comment> implements CommentRepository {
}
