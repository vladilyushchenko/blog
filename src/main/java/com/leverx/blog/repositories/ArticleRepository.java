package com.leverx.blog.repositories;

import com.leverx.blog.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
//    Optional<Article> findById(int id);
//
//    void update(Article article);
//
//    void deleteById(int id);
//
//    Optional<List<Article>> findByAuthorId(int id);
    List<Article> findArticlesByAuthorId(int authorId);

}
