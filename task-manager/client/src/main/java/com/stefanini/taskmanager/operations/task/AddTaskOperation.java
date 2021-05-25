package com.stefanini.taskmanager.operations.task;

import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProvider;

/**
 * Implements {@link Operation}. Encapsulates {@link User} and {@link Task} fields. The execution
 * consists of sending the encapsulated fields to {@link TaskService#addTask(Task, User)} as
 * parameters.
 */
public class AddTaskOperation implements Operation {
  private final Task task;
  private final User user;
  private final ServiceFactory serviceFactory = ServiceFactoryProvider.createServiceFactory();
  private final TaskService taskService = serviceFactory.getTaskService();

  public AddTaskOperation(Task task, User user) {
    this.task = task;
    this.user = user;
  }

  @Override
  public void execute() {
    taskService.addTask(task, user);
  }
}
