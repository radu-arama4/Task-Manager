package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernate implements UserDao {
  private final Session session;
  private static final Logger logger = LogManager.getLogger(UserDaoHibernate.class);
  private static UserDao singleInstance;

  private static final String GET_USERS = "from User";

  private UserDaoHibernate(Session session) {
    this.session = session;
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
  public List<User> getUsers() {
    Query query = session.createQuery(GET_USERS);
    return (List<User>) query.getResultList();
  }
}
