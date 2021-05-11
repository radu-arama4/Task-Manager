package com.stefanini.taskmanager.persistence.dao;

import java.sql.SQLException;
import java.util.List;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;


/**
 * 
 * Interface TaskDao provides methods for performing some CRUD operations on Task entities.
 * 
 * @author rarama
 *
 */
public interface TaskDao {

  /**
   * 
   * Method used for adding a new Task entity to the database.
   * 
   * @param task
   * @param user
   * @return boolvalue
   */
  boolean addTask(Task task, User user);

  /**
   * 
   * Method used for extracting all existing Task entities from the database in form of list
   * 
   * @param selectedUser
   * @return
   * @throws SQLException
   */
  List<Task> showTasks(User selectedUser) throws SQLException;

}
