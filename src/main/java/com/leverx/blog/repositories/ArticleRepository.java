package com.leverx.blog.repositories;

import com.leverx.blog.entities.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findArticlesByAuthorId(int authorId);

    List<Article> findAllByAuthorId(int authorId, Pageable pageable);

    List<Article> findDistinctByTags_NameIn(Set<String> names);
}
