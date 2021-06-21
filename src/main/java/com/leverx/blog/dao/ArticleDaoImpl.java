package com.leverx.blog.dao;

import com.leverx.blog.entities.Article;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Component
public class ArticleDaoImpl implements ArticleDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public ArticleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Article> getById(int id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Article.class, id));
    }

    @Override
    public void save(Article article) {
        Session session = sessionFactory.getCurrentSession();
        session.save(article);
    }
}
