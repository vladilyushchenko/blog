package com.leverx.blog.mapper;

import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.entity.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {
    public Article mapToEntity(ArticleDto articleDto) {
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setText(articleDto.getText());
        article.setTitle(articleDto.getTitle());
        article.setStatus(articleDto.getStatus());
        article.setUpdatedAt(articleDto.getUpdatedAt());
        article.setCreatedAt(articleDto.getCreatedAt());
        article.setAuthorId(articleDto.getAuthorId());
        article.setTags(articleDto.getTags());
        return article;
    }

    public ArticleDto mapToDto(Article article) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(article.getId());
        articleDto.setStatus(article.getStatus());
        articleDto.setAuthorId(article.getAuthorId());
        articleDto.setText(article.getText());
        articleDto.setTitle(article.getTitle());
        articleDto.setCreatedAt(article.getCreatedAt());
        articleDto.setUpdatedAt(article.getUpdatedAt());
        articleDto.setTags(article.getTags());
        return articleDto;
    }
}
