package com.leverx.blog.dto.mapping;

import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.entities.Article;
import org.springframework.stereotype.Service;

@Service
public class ArticleMapping {
    public static Article mapToEntity(ArticleDto articleDto) {
        Article article = new Article();
        article.setText(articleDto.getText());
        article.setTitle(articleDto.getTitle());
        return article;
    }
}
