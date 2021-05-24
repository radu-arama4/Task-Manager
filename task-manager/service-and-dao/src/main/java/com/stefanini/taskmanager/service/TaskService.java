package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.persistence.dao.TaskDao;

import java.util.List;

/**
 * Interface that provides business logic for {@link Task} entity.
 *
 * @author rarama
 */
public interface TaskService {

  /**
   * Method which receives the {@link Task} data as DTO in order to be sent to the {@link TaskDao}
   * and introduced to the database.
   *
   * @param task task data transfer object
   * @param user user data transfer object
   * @return boolean value - to show if the operation succeeded
   */
  boolean addTask(Task task, User user);

  /**
   * Method which receives an {@link User} DTO and sends it to the {@link TaskDao} in order to get a
   * list of {@link Task} entities possessed by the {@link User} with the received username.
   *
   * @param user user data transfer object
   * @return list of users - the existing users in the database
   */
  List<Task> getTasksOfUser(User user);
}
