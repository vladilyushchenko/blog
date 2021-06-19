package com.leverx.blog.services;

import com.leverx.blog.dao.ArticleDao;
import com.leverx.blog.entities.Article;
import com.leverx.blog.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleDao articleDao;

    public ArticleServiceImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    public Article findById(int id) {
        Optional<Article> article = articleDao.getById(id);
        throwIfNotFound(article, String.format("There is no article with id %d", id));
        return article.get();
    }

    private void throwIfNotFound(Optional<Article> article, String text) throws NotFoundException {
        if (article.isEmpty()) {
            throw new NotFoundException(text);
        }
    }
}
