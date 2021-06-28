package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

/** Interface that provides business logic for multiple entities. */
@Transactional
public interface ExtendedService { //todo rename
  /**
   * Method for creating a new {@link UserTO} with new {@link TaskTO}'s
   *
   * @param user - user data transfer object
   * @param tasks - task data transfer object
   */
  void createUserWithTasks(UserTO user, Stream<TaskTO> tasks);
}
