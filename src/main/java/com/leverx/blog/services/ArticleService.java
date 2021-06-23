package com.leverx.blog.services;

import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.dto.ArticlePaginationDto;
import com.leverx.blog.entities.Article;

import java.util.List;

public interface ArticleService {
    void save(ArticleDto articleDto);

    void updateById(ArticleDto articleDto, int id);

    List<Article> findArticlesByEmail(String email);

    void deleteById(int id, String userEmail);

    List<Article> findAllByPaginationDto(ArticlePaginationDto paginationDto);
}
