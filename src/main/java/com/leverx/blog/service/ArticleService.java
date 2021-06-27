package com.leverx.blog.service;

import com.leverx.blog.dto.ArticleDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {
    ArticleDto save(ArticleDto articleDto, String authorEmail);

    void updateById(ArticleDto articleDto, int id, String editorEmail);

    List<ArticleDto> findArticlesByEmail(String email);

    void deleteById(int id, String userEmail);

    List<ArticleDto> findAllByAuthorAndPageable(Integer authorId, Pageable pageable);

    List<ArticleDto> findAllByTagNames(String[] tagNames);
}
