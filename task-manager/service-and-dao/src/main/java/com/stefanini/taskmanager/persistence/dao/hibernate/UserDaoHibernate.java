package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.persistence.entity.hibernate.UserHibernate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.stream.Stream;

public class UserDaoHibernate implements UserDao {
  private final Session session;
  private final CriteriaQuery<User> criteria;
  private final CriteriaBuilder builder;
  private final Root<UserHibernate> rootUser;
  private static final Logger logger = LogManager.getLogger(UserDaoHibernate.class);
  private static UserDao singleInstance;

  private UserDaoHibernate(Session session) {
    this.session = session;
    builder = session.getCriteriaBuilder();
    criteria = builder.createQuery(User.class);
    rootUser = criteria.from(UserHibernate.class);
  }

  public static UserDao getInstance(Session session) {
    if (singleInstance == null) {
      singleInstance = new UserDaoHibernate(session);
    }
    return singleInstance;
  }

  @Override
  public User createUser(User newUser) {
    try {
      session.save(newUser);
    } catch (Exception e) {
      logger.error(e);
      return null;
    }
    return newUser;
  }

  @Override
  public Stream<User> getUsers() {
    criteria.select(rootUser);
    Query<User> query = session.createQuery(criteria);
    return query.getResultList().stream();
  }
}
