package com.stefanini.taskmanager.service.impl;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.entity.User;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.util.BeansUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Scope("singleton")
public class UserServiceImpl implements UserService {
  @Autowired private UserDao userDao;
  private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

  public UserServiceImpl() {}

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
      User newUser = new User();

      BeansUtil.copyObjectProperties(newUser, user);
      User createdUser = userDao.save(newUser);
      UserTO returnedUser = new UserTO();

      BeansUtil.copyObjectProperties(returnedUser, createdUser);

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
    BeansUtil.copyObjectProperties(returnedUser, user);
    return returnedUser;
  }

  @Override
  public Stream<UserTO> getAllUsers() {
    logger.info("getAllUsers method started");

    return StreamSupport.stream(userDao.findAll().spliterator(), false).map(this::toUserTO);
  }
}
