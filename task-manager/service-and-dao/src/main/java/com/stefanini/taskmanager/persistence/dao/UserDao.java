package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.User;

import java.util.List;

/**
 * Interface UserDao provides methods for performing some CRUD operations on {@link User} entities.
 *
 * @author rarama
 */
public interface UserDao {

  /**
   * Method used for adding a new User entity to the database
   *
   * @param newUser - the user that will be created
   * @return boolean value - to show if the operation succeeded
   */
  User createUser(User newUser);

  /**
   * Method used for extracting all existing User entities from the database in form of list
   *
   * @return List<User> - all the existing users from the database
   */
  List<User> getUsers();
}
