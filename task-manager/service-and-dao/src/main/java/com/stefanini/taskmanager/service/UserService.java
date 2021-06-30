package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.UserDao;
import com.stefanini.taskmanager.service.proxy.email.EmailGenerator;

import javax.transaction.Transactional;
import java.util.stream.Stream;

/**
 * Interface that provides business logic for {@link UserTO} entity.
 *
 * @author rarama
 */
@Transactional
public interface UserService{

  /**
   * Method which receives the {@link UserTO} as DTO in order to be sent to the {@link UserDao} and
   * introduced to the database.
   *
   * @param user user data transfer object
   * @return boolean value - to show if the operation succeeded
   */
  @EmailGenerator
  UserTO createUser(UserTO user);

  /**
   * Method which returns the list of existing users in the database from the {@link UserDao}
   *
   * @return list of users - the existing users in the database
   */
  Stream<UserTO> getAllUsers();
}
