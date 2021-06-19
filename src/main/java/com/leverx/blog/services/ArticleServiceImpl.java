package com.leverx.blog.services;

import com.leverx.blog.dao.ArticleDao;
import com.leverx.blog.entities.Article;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleDao articleDao;

    public ArticleServiceImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    public Optional<Article> findById(int id) {
        return articleDao.getById(id);
    }
}
