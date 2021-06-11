package com.stefanini.taskmanager.persistence.dao;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.persistence.entity.User;

import java.util.stream.Stream;

/**
 * Interface TaskDao provides methods for performing some CRUD operations on {@link TaskTO}
 * entities.
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
  Task addTask(Task task, User user);

  /**
   * Method used for adding multiple tasks to a single user.
   *
   * @param tasks - the tasks to be added
   * @param user - the user to whom the tasks will be added
   * @return List<Task> - the tasks that have been added
   */
  Stream<Task> addMultipleTasks(Stream<Task> tasks, User user);

  /**
   * Method used for extracting all existing Task entities from the database in form of list
   *
   * @param selectedUser - the user of whom the tasks will be shown
   * @return List<Task> - the tasks of the specified user
   */
  Stream<Task> getTasks(User selectedUser);
}
