package com.leverx.blog.services;

import com.leverx.blog.entities.Article;

import java.util.Optional;

public interface ArticleService {
    Optional<Article> findById(int id);
}
