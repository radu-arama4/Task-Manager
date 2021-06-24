package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * Interface UserDao provides methods for performing some CRUD operations on {@link UserTO} entities.
 *
 * @author rarama
 */
@Component
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
  Stream<User> getUsers();
}
