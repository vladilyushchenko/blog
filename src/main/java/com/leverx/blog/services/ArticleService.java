package com.leverx.blog.services;

import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.dto.ArticlePaginationDto;
import com.leverx.blog.entities.Article;

import java.util.List;

public interface ArticleService {
    ArticleDto save(ArticleDto articleDto, String authorEmail);

    void updateById(ArticleDto articleDto, int id, String editorEmail);

    List<ArticleDto> findArticlesByEmail(String email);

    void deleteById(int id, String userEmail);

    List<ArticleDto> findAllByPaginationDto(ArticlePaginationDto paginationDto);
}
