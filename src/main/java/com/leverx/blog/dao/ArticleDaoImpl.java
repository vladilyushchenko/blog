package com.leverx.blog.dao;

import com.leverx.blog.entities.Article;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class ArticleDaoImpl implements ArticleDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public ArticleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Article getById(int id) {
        return sessionFactory.getCurrentSession().get(Article.class, id);
    }
}
