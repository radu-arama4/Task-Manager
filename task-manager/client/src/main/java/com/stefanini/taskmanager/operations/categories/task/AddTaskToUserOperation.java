package com.stefanini.taskmanager.operations.categories.task;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.util.context.ApplicationContextManager;

/**
 * Implements {@link Operation}. Encapsulates {@link UserTO} and {@link TaskTO} fields. The
 * execution consists of sending the encapsulated fields to {@link TaskService#addTask(TaskTO,
 * UserTO)} as parameters.
 */
public class AddTaskToUserOperation implements Operation {
  private final TaskTO task;
  private final UserTO user;
  private final TaskService taskService =
      ApplicationContextManager.getApplicationContext().getBean(TaskService.class);

  public AddTaskToUserOperation(TaskTO task, UserTO user) {
    this.task = task;
    this.user = user;
  }

  public TaskTO getTask() {
    return task;
  }

  public UserTO getUser() {
    return user;
  }

  @Override
  public void execute() {
    taskService.addTask(task, user);
  }
}
