package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.entity.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.Query;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class UserDaoHibernate implements UserDao {
  private final Session session;
  private static final Logger logger = LogManager.getLogger(UserDaoHibernate.class);

  private static final String GET_USERS = "from User";

  public UserDaoHibernate(Session session) {
    this.session = session;
  }

  @Override
  public UserTO createUser(UserTO newUser) {
    User user = new User();
    //Transaction transaction = session.beginTransaction();

    try {
      BeanUtils.copyProperties(user, newUser);
    } catch (IllegalAccessException | InvocationTargetException e) {
      logger.error(e);
    }

    try {
      session.save(user);
      session.flush();
      //transaction.commit();
    } catch (Exception e) {
      //transaction.rollback();
      logger.error(e);
      return null;
    }

    return toUserTO(user);
  }

  private UserTO toUserTO(User user) {
    UserTO returnedUser = new UserTO();
    try {
      BeanUtils.copyProperties(returnedUser, user);
    } catch (IllegalAccessException | InvocationTargetException e) {
      logger.error(e);
    }
    return returnedUser;
  }

  @Override
  public List<UserTO> getUsers() {
    Query query = session.createQuery(GET_USERS);
    List<User> users = (List<User>) query.getResultList();

    return users.stream().map(this::toUserTO).collect(Collectors.toList());
  }
}
