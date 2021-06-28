package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.persistence.dao.TaskDao;
import com.stefanini.taskmanager.service.proxy.email.EmailGenerator;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

/**
 * Interface that provides business logic for {@link TaskTO} entity.
 *
 * @author rarama
 */

@Transactional
public interface TaskService {

  /**
   * Method which receives the {@link TaskTO} data as DTO in order to be sent to the {@link TaskDao}
   * and introduced to the database.
   *
   * @param task task data transfer object
   * @param user user data transfer object
   * @return boolean value - to show if the operation succeeded
   */
  @EmailGenerator
  TaskTO addTask(TaskTO task, UserTO user);

  /**
   * Method which receives an {@link UserTO} DTO and sends it to the {@link TaskDao} in order to get
   * a list of {@link TaskTO} entities possessed by the {@link UserTO} with the received username.
   *
   * @param user user data transfer object
   * @return list of users - the existing users in the database
   */
  Stream<TaskTO> getTasksOfUser(UserTO user);
}
