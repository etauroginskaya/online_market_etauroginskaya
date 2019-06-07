package com.gmail.etauroginskaya.online_market.service.converter;

import com.gmail.etauroginskaya.online_market.repository.model.Article;
import com.gmail.etauroginskaya.online_market.service.model.ArticleDTO;

public interface ArticleConverter {

    ArticleDTO toDTO(Article article);

    Article toEntity(ArticleDTO articleDTO);

    ArticleDTO toDTOForAPI(Article article);
}
