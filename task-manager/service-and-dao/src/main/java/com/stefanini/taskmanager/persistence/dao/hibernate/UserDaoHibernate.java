package com.stefanini.taskmanager.persistence.dao.hibernate;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.entity.User;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class UserDaoHibernate implements UserDao {
  private final Session session;

  private static final String GET_USERS =
      "from User";

  public UserDaoHibernate(Session session) {
    this.session = session;
  }

  @Override
  public UserTO createUser(UserTO newUser) {
    User user = new User();
    Transaction transaction = session.beginTransaction();

    try {
      BeanUtils.copyProperties(user, newUser);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }

    session.save(user);
    transaction.commit();

    return null;
  }

  @Override
  public List<UserTO> getUsers() {
    List<UserTO> returnedUsers = new LinkedList<>();

    Query query = session.createQuery(GET_USERS);
    List<User> users = query.getResultList();

    for (User value : users) {
      System.out.println(value.getUserName());
      try {
        UserTO user = new UserTO();
        BeanUtils.copyProperties(user, value);
        returnedUsers.add(user);
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }

    return returnedUsers;
  }
}
