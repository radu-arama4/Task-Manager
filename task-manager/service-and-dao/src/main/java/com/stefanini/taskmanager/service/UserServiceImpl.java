package com.stefanini.taskmanager.service;

import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.persistence.dao.UserDaoImpl;

public class UserServiceImpl implements UserService {

  private UserDao userDao = UserDaoImpl.getInstance();
  private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

  @Override
  public boolean createUser(String[] arguments) {

    logger.info("createUser method started");

    if (arguments.length > 4) {
      logger.warn("Too many arguments!" + Arrays.toString(arguments));
      return false;
    }

    String firstName = null;
    String lastName = null;
    String userName = null;

    for (String arg : arguments) {
      if (arg.startsWith("-fn='") && arg.endsWith("'")) {
        firstName = arg.substring(5, arg.length() - 1);
      } else if (arg.startsWith("-ln='") && arg.endsWith("'")) {
        lastName = arg.substring(5, arg.length() - 1);
      } else if (arg.startsWith("-un='") && arg.endsWith("'")) {
        userName = arg.substring(5, arg.length() - 1);
      }
    }

    if (firstName == null || lastName == null || userName == null) {
      logger.warn("Missing information!");
      return false;
    } else if (!checkDuplicatedUserNames(userName)) {
      if (userDao.createUser(new User(firstName, lastName, userName))) {
        logger.info("New user with [first name: " + firstName + "], [last name: " + lastName
            + "], [username: " + userName + "] added.");
        return true;
      }
    } else {
      logger.warn("Duplicated username!");
    }
    return false;
  }

  @Override
  public List<User> showAllUsers() {

    List<User> users = null;

    users = userDao.getUsers();

    for (User user : users) {
      logger.info(user.getFirstName() + " " + user.getLastName() + " " + user.getUserName());
    }

    return users;
  }

  public boolean checkDuplicatedUserNames(String userName) {

    List<User> users = null;
    users = userDao.getUsers();

    for (User user : users) {
      if (user.getUserName().equals(userName)) {
        return true;
      }
    }

    return false;
  }

}
