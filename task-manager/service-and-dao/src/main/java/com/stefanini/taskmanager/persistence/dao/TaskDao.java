package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;

import java.util.List;

/**
 * Interface TaskDao provides methods for performing some CRUD operations on {@link TaskTO} entities.
 *
 * @author rarama
 */
public interface TaskDao {

  /**
   * Method used for adding a new Task entity to the database.
   *
   * @param task - the task to be added
   * @param user - the user to whom the task will be added
   * @return boolean value - to show if the operation succeeded
   */
  TaskTO addTask(TaskTO task, UserTO user);

  /**
   * Method used for extracting all existing Task entities from the database in form of list
   *
   * @param selectedUser - the user of whom the tasks will be shown
   * @return List<Task> - the tasks of the specified user
   */
  List<TaskTO> getTasks(UserTO selectedUser);
}
