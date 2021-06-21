package com.leverx.blog.services;

import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.entities.Article;

public interface ArticleService {
    Article findById(int id);

    void save(ArticleDto articleDto);
}
