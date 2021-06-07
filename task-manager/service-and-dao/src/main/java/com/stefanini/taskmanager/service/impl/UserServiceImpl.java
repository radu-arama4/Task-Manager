package com.stefanini.taskmanager.service.impl;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.persistence.entity.EntityFactory;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
  private final UserDao userDao;
  private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

  public UserServiceImpl(DaoFactory daoFactory) {
    this.userDao = daoFactory.createUserDao();
  }

  @Override
  public UserTO createUser(UserTO user) {
    logger.info("createUser method started");

    String firstName = user.getFirstName();
    String lastName = user.getLastName();
    String userName = user.getUserName();

    if (firstName == null || lastName == null || userName == null) {
      logger.warn("Missing information!");
      return null;
    } else {
      User newUser = EntityFactory.createUser();

      //isolate mapping

      try {
        BeanUtils.copyProperties(newUser, user);
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }

      User createdUser = userDao.createUser(newUser);
      UserTO returnedUser = new UserTO();

      try {
        BeanUtils.copyProperties(returnedUser, createdUser);
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }

      if (createdUser != null) {
        logger.info(
            "New user with [first name: "
                + firstName
                + "], [last name: "
                + lastName
                + "], [username: "
                + userName
                + "] added.");
        return returnedUser;
      }
    }
    return null;
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
  public List<UserTO> getAllUsers() {
    logger.info("getAllUsers method started");

    List<User> users = userDao.getUsers();

    return users.stream().map(this::toUserTO).collect(Collectors.toList());
  }
}
