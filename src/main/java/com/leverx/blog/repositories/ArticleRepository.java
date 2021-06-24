package com.leverx.blog.repositories;

import com.leverx.blog.entities.Article;
import com.leverx.blog.entities.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findArticlesByAuthorId(int authorId);

    List<Article> findAllByAuthorId(int authorId, Pageable pageable);

    @Query(value = "select ")
    List<Article> findByTags_TagName(List<Tag> tags);
}
