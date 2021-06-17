package com.stefanini.taskmanager.operations.task;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProvider;

import java.util.stream.Stream;

/**
 * Implements {@link Operation}. Encapsulates {@link UserTO} field. The execution consists of
 * sending the encapsulated fields to {@link TaskService#getTasksOfUser(UserTO)} as parameters.
 */
public class ShowTasksOfUserOperation implements Operation {
  private final UserTO user;
  private final ServiceFactory serviceFactory = ServiceFactoryProvider.createServiceFactory();
  private final TaskService taskService = serviceFactory.getTaskService();

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
