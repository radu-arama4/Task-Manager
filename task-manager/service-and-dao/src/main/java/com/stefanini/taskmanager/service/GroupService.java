package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.GroupDao;

/**
 * 
 * Interface that provides business logic for {@link Group} entity.
 * 
 * @author rarama
 *
 */
public interface GroupService {

  /**
   * 
   * Method which extracts and encapsulates the {@link Group} data from the command line arguments
   * in order to be sent to the {@link GroupDao} and introduced to the database as a new entity.
   * 
   * @param arguments - command line arguments
   * @return boolean value - to show if the operation succeeded
   */
  boolean createGroup(String[] arguments);

  /**
   * 
   * Method which extracts and encapsulates the {@link Group} data and {@link User} data from the
   * command line arguments in order to be sent to the {@link GroupDao} and used to update the
   * database by adding the specified user to given group.
   * 
   * @param arguments - command line arguments
   * @return boolean value - to show if the operation succeeded
   */
  boolean addUserToGroup(String[] arguments);

  /**
   * 
   * Method which extracts and encapsulates the {@link Group} data and {@link Task} data from the
   * command line arguments in order to be sent to the {@link GroupDao} and used to update the
   * database by adding the specified task to the members of the given group.
   * 
   * @param arguments - command line arguments
   * @return boolean value - to show if the operation succeeded
   */
  boolean addTaskToGroup(String[] arguments);

}
