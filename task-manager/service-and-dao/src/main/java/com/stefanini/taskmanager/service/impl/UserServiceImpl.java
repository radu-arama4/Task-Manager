package com.stefanini.taskmanager.service.impl;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.dao.factory.DaoFactory;
import com.stefanini.taskmanager.persistence.entity.EntityFactory;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.util.OperationsUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Stream;

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

      OperationsUtil.copyObjectProperties(newUser, user);

      User createdUser = userDao.createUser(newUser);
      UserTO returnedUser = new UserTO();

      OperationsUtil.copyObjectProperties(returnedUser, createdUser);

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
    OperationsUtil.copyObjectProperties(returnedUser, user);
    return returnedUser;
  }

  @Override
  public Stream<UserTO> getAllUsers() {
    logger.info("getAllUsers method started");
    return userDao.getUsers().map(this::toUserTO);
  }
}
