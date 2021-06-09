package com.stefanini.taskmanager.operations.multiple;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.ExtendedService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProvider;

import java.util.List;

/**
 * Implements {@link Operation}. Encapsulates the {@link UserTO} and {@link List<TaskTO>} fields.
 * The execution consists of sending the encapsulated fields to the {@link
 * ExtendedService#createUserWithTasks(UserTO, List)} method as parameters.
 */
public class CreateUserWithTasks implements Operation {
  private final UserTO user;
  private final List<TaskTO> tasks;
  private final ServiceFactory serviceFactory = ServiceFactoryProvider.createServiceFactory();
  private final ExtendedService extendedService = serviceFactory.getExtendedService();

  public CreateUserWithTasks(UserTO user, List<TaskTO> tasks) {
    this.user = user;
    this.tasks = tasks;
  }

  @Override
  public void execute() {
    extendedService.createUserWithTasks(user, tasks);
  }
}
