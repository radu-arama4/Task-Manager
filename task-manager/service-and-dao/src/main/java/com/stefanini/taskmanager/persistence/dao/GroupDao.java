package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;

/**
 * Interface TaskDao provides methods for performing some CRUD operations on {@link GroupTO} entities.
 *
 * @author rarama
 */
public interface GroupDao {

  /**
   * Method for adding a new {@link GroupTO} entity in the database
   *
   * @param group - the group to be added
   * @return boolean value - to show if the operation succeeded
   */
  GroupTO createGroup(GroupTO group);

  /**
   * Method for adding a specific user to an existing {@link GroupTO}
   *
   * @param user - the user to be added
   * @param group - the group in which the user will be added
   * @return boolean value - to show if the operation succeeded
   */
  boolean addUserToGroup(UserTO user, GroupTO group);

  /**
   * Method for adding a specific task to all the members of a {@link GroupTO}
   *
   * @param task - the task to be added
   * @param group - the group to which the task will be added
   * @return boolean value - to show if the operation succeeded
   */
  boolean addTaskToGroup(TaskTO task, GroupTO group);
}
