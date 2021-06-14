package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.persistence.entity.hibernate.UserHibernate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.stream.Stream;

public class UserDaoHibernate implements UserDao {
  private final SessionFactory sessionFactory;
  private final CriteriaQuery<User> criteria;
  private final CriteriaBuilder builder;
  private final Root<UserHibernate> rootUser;
  private static final Logger logger = LogManager.getLogger(UserDaoHibernate.class);
  private static UserDao singleInstance;

  private UserDaoHibernate(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
    builder = sessionFactory.getCriteriaBuilder();
    criteria = builder.createQuery(User.class);
    rootUser = criteria.from(UserHibernate.class);
  }

  public static UserDao getInstance(SessionFactory sessionFactory) {
    if (singleInstance == null) {
      singleInstance = new UserDaoHibernate(sessionFactory);
    }
    return singleInstance;
  }

  @Override
  public User createUser(User newUser) {
    Session session = sessionFactory.getCurrentSession();
    try {
      session.save(newUser);
    } catch (Exception e) {
      logger.error(e);
      session.close();
      return null;
    }
    session.close();
    return newUser;
  }

  @Override
  public Stream<User> getUsers() {
    Session session = sessionFactory.getCurrentSession();

    criteria.select(rootUser);
    Query<User> query = session.createQuery(criteria);
    //session.close();
    return query.getResultList().stream();
  }
}
