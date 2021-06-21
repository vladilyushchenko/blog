package com.leverx.blog.services;

import com.leverx.blog.dao.ArticleDao;
import com.leverx.blog.dao.UserDao;
import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.entities.Article;
import com.leverx.blog.entities.enums.ArticleStatus;
import com.leverx.blog.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleDao articleDao;
    private final UserDao userDao;

    public ArticleServiceImpl(ArticleDao articleDao, UserDao userDao) {
        this.articleDao = articleDao;
        this.userDao = userDao;
    }

    @Override
    public Article findById(int id) {
        Optional<Article> article = articleDao.getById(id);
        throwIfNotFound(article, String.format("There is no article with id %d", id));
        return article.get();
    }

    @Override
    public void save(ArticleDto articleDto) {
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setText(articleDto.getText());
        article.setAuthorId(getAuthorIdByEmail(articleDto.getAuthorEmail()));
        article.setCreatedAt(new Date());
        article.setStatus(ArticleStatus.PUBLIC);
        articleDao.save(article);
    }

    private int getAuthorIdByEmail(String email) {
        Optional<Integer> id = userDao.getIdByEmail(email);
        if (id.isEmpty()) {
            throw new NotFoundException(String.format("It's no user with email %s", email));
        }
        return id.get();
    }

    private void throwIfNotFound(Optional<Article> article, String text) throws NotFoundException {
        if (article.isEmpty()) {
            throw new NotFoundException(text);
        }
    }
}
