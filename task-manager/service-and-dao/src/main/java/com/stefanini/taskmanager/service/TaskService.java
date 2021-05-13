package com.stefanini.taskmanager.service;

import java.util.List;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.TaskDao;


/**
 * 
 * Interface that provides business logic for {@link Task} entity.
 * 
 * @author rarama
 *
 */
public interface TaskService {

  /**
   * 
   * Method which extracts and encapsulates the {@link Task} data from the command line arguments in
   * order to be sent to the {@link TaskDao} and introduced to the database.
   * 
   * @param arguments - command line arguments
   * @return boolean value - to show if the operation succeeded
   */
  boolean addTask(String[] arguments);

  /**
   * 
   * Method which extracts the username from the command line arguments and sends it to the
   * {@link TaskDao} in order to get a list of {@link Task} entities possessed by the {@link User}
   * with the received username.
   * 
   * @return list of users - the existing users in the database
   */
  List<Task> showTasks(String[] arguments);

}
