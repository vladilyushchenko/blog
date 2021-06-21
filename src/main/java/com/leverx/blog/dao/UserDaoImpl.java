package com.leverx.blog.dao;

import com.leverx.blog.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class UserDaoImpl implements UserDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<User> findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(User.class, id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.select(root).where(cb.equal(root.get("email"), email));
        Query<User> query = session.createQuery(cr);
        List<User> users = query.getResultList();
        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.get(0));
    }

    @Override
    public Optional<Integer> getIdByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable((Integer) session
                .createQuery("select user.id from User user where user.email = :email")
                .setParameter("email", email).uniqueResult());
    }

    @Override
    public void persist(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    @Override
    public int save(User user) {
        Session session = sessionFactory.getCurrentSession();
        return (int) session.save(user);
    }

    @Override
    public void updateActivatedById(int id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.set(root.get("activated"), Boolean.TRUE);
        criteria.where(builder.equal(root.get("id"), id));
        session.createQuery(criteria).executeUpdate();
    }

    @Override
    public void updatePasswordByEmail(String email, String password) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.set(root.get("password"), password);
        criteria.where(builder.equal(root.get("email"), email));
        session.createQuery(criteria).executeUpdate();
    }

}
