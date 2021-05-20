package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.UserDao;

import java.util.List;

/**
 * Interface that provides business logic for {@link User} entity.
 *
 * @author rarama
 */
public interface UserService {

  /**
   * Method which receives the {@link User} as DTO in order to be sent to the {@link UserDao} and
   * introduced to the database.
   *
   * @param user user data transfer object
   * @return boolean value - to show if the operation succeeded
   */
  boolean createUser(User user);

  /**
   * Method which returns the list of existing users in the database from the {@link UserDao}
   *
   * @return list of users - the existing users in the database
   */
  List<User> showAllUsers();
}
