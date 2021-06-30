package com.stefanini.taskmanager.operations.categories.task;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.util.context.ApplicationContextManager;

import java.util.stream.Stream;

/**
 * Implements {@link Operation}. Encapsulates {@link UserTO} field. The execution consists of
 * sending the encapsulated fields to {@link TaskService#getTasksOfUser(UserTO)} as parameters.
 */
public class ShowTasksOfUserOperation implements Operation {
  private final UserTO user;
  private final TaskService taskService =
      ApplicationContextManager.getApplicationContext().getBean(TaskService.class);

  public ShowTasksOfUserOperation(UserTO user) {
    this.user = user;
  }

  @Override
  public void execute() {
    Stream<TaskTO> tasks = taskService.getTasksOfUser(user);
    tasks.forEach(System.out::println);
  }

  public UserTO getUser() {
    return user;
  }
}
