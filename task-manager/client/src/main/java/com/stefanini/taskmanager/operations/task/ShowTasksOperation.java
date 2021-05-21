package com.stefanini.taskmanager.operations.task;

import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProvider;

import static com.stefanini.taskmanager.service.factory.ServiceType.STANDARD;

/**
 * Implements {@link Operation}. Encapsulates {@link User} field. The execution consists of sending
 * the encapsulated fields to {@link TaskService#showTasks(User)} as parameters.
 */
public class ShowTasksOperation implements Operation {
  private final User user;
  private final ServiceFactory serviceFactory =
      ServiceFactoryProvider.createServiceFactory(STANDARD);
  private final TaskService taskService;

  {
    assert serviceFactory != null;
    taskService = serviceFactory.getTaskService();
  }

  public ShowTasksOperation(User user) {
    this.user = user;
  }

  @Override
  public void execute() {
    taskService.showTasks(user);
  }
}
