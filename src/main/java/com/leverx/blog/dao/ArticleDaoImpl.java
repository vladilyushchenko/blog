package com.leverx.blog.dao;

import com.leverx.blog.entities.Article;
import com.leverx.blog.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
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
    public Optional<Article> findById(int id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Article.class, id));
    }

    @Override
    public void save(Article article) {
        Session session = sessionFactory.getCurrentSession();
        session.save(article);
    }

    @Override
    public void update(Article article) {
        Session session = sessionFactory.getCurrentSession();
        session.update(article);
    }

    @Override
    public void deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from Article where id = :id").setParameter("id", id).executeUpdate();
    }

    @Override
    public Optional<List<Article>> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session
                .createQuery("select a from Article a", Article.class).getResultList());
    }

    @Override
    public Optional<List<Article>> findByAuthorId(int id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Article> cr = cb.createQuery(Article.class);
        Root<Article> root = cr.from(Article.class);
        cr.select(root).where(cb.equal(root.get("authorId"), id));
        Query<Article> query = session.createQuery(cr);
        return Optional.ofNullable(query.getResultList());
    }
}
