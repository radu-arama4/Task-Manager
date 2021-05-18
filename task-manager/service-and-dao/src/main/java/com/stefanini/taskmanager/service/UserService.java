package com.stefanini.taskmanager.service;

import java.util.List;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.UserDao;

/**
 * 
 * Interface that provides business logic for {@link User} entity.
 * 
 * @author rarama
 *
 */

public interface UserService {

  /**
   * 
   * Method which extracts and encapsulates the {@link User} data from the command line arguments in
   * order to be sent to the {@link UserDao} and introduced to the database.
   * 
   * @param user - user data transfer object
   * @return boolean value - to show if the operation succeeded
   */
  boolean createUser(User user);

  /**
   * 
   * Method which receives the list of existing users in the database from the {@link UserDao}
   * 
   * @return list of users - the existing users in the database
   */
  List<User> showAllUsers();

}
