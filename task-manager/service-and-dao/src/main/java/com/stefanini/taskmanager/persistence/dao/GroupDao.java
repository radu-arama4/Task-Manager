package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;

/**
 * Interface TaskDao provides methods for performing some CRUD operations on {@link Group} entities.
 * 
 * @author rarama
 *
 */
public interface GroupDao {

  /**
   * Method for adding a new {@link Group} entity in the database
   * 
   * @param group - the group to be added
   * @return boolean value - to show if the operation succeeded
   */
  boolean createGroup(Group group);

  /**
   * Method for adding a specific user to an existing {@link Group}
   * 
   * @param user - the user to be added
   * @param group - the group in which the user will be added
   * @return boolean value - to show if the operation succeeded
   */
  boolean addUserToGroup(User user, Group group);

  /**
   * Method for adding a specific task to all the members of a {@link Group}
   * 
   * @param task - the task to be added
   * @param group - the group to which the task will be added
   * @return boolean value - to show if the operation succeeded
   */
  boolean addTaskToGroup(Task task, Group group);

}
