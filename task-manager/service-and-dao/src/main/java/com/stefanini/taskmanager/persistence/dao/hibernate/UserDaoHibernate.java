package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.persistence.entity.hibernate.UserHibernate;
import com.stefanini.taskmanager.persistence.util.DataBaseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.stream.Stream;

@Component
@Qualifier("hibernate")
@Scope("singleton")
public class UserDaoHibernate implements UserDao {
  private final SessionFactory sessionFactory = DataBaseUtil.connectWithHibernate();
  private final CriteriaQuery<User> criteria;
  private final CriteriaBuilder builder;
  private final Root<UserHibernate> rootUser;
  private static final Logger logger = LogManager.getLogger(UserDaoHibernate.class);

  public UserDaoHibernate() {
    builder = sessionFactory.getCriteriaBuilder();
    criteria = builder.createQuery(User.class);
    rootUser = criteria.from(UserHibernate.class);
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
    return newUser;
  }

  @Override
  public Stream<User> getUsers() {
    Session session = sessionFactory.getCurrentSession();
    criteria.select(rootUser);
    Query<User> query = session.createQuery(criteria);
    return query.getResultList().stream();
  }
}
