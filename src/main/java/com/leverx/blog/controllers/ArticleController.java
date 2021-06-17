package com.leverx.blog.controllers;

import com.leverx.blog.dao.ArticleDao;
import com.leverx.blog.dao.UserDao;
import com.leverx.blog.entities.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {
    private final ArticleDao articleDao;

    @Autowired
    public ArticleController(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @GetMapping("/articles/find/{id}")
    public Article getArticle(@PathVariable("id") int id) {
        return articleDao.getById(id);
    }
}
