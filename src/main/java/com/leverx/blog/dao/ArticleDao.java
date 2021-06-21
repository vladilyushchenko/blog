package com.leverx.blog.dao;

import com.leverx.blog.entities.Article;

import java.util.Optional;

public interface ArticleDao {
    Optional<Article> getById(int id);

    void save(Article article);
}
