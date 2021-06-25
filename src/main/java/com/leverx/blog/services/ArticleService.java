package com.leverx.blog.services;

import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.dto.ArticlePaginationDto;
import com.leverx.blog.entities.Article;
import org.springframework.data.util.Pair;

import java.util.List;

public interface ArticleService {
    ArticleDto save(ArticleDto articleDto, String authorEmail);

    void updateById(ArticleDto articleDto, int id, String editorEmail);

    List<ArticleDto> findArticlesByEmail(String email);

    void deleteById(int id, String userEmail);

    List<ArticleDto> findAllByPaginationDto(ArticlePaginationDto paginationDto);

    List<ArticleDto> findAllByTagNames(String[] tagNames);
}
