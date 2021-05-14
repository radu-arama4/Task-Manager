package com.stefanini.taskmanager.persistence.dao;

import java.util.List;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;


/**
 * 
 * Interface TaskDao provides methods for performing some CRUD operations on {@link Task} entities.
 * 
 * @author rarama
 *
 */
public interface TaskDao {

  /**
   * 
   * Method used for adding a new Task entity to the database.
   * 
   * @param task - the task to be added
   * @param user - the user to whom the task will be added
   * @return boolean value - to show if the operation succeeded
   */
  Task addTask(Task task, User user);

  /**
   * 
   * Method used for extracting all existing Task entities from the database in form of list
   * 
   * @param selectedUser - the user of whom the tasks will be shown
   * @return List<Task> - the tasks of the specified user
   */
  List<Task> showTasks(User selectedUser);

}
