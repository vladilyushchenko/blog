package com.leverx.blog.dao;

import com.leverx.blog.entities.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleDao {
    Optional<Article> findById(int id);

    void save(Article article);

    void update(Article article);

    void deleteById(int id);

    Optional<List<Article>> findAll();

    Optional<List<Article>> findByAuthorId(int id);


}
