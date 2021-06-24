package com.stefanini.taskmanager.operations.categories.multiple;

import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.ExtendedService;
import com.stefanini.taskmanager.service.proxy.transaction.TransactionProxy;
import com.stefanini.taskmanager.util.ApplicationContextProvider;

import java.util.List;

/**
 * Implements {@link Operation}. Encapsulates the {@link UserTO} and {@link List<TaskTO>} fields.
 * The execution consists of sending the encapsulated fields to the {@link
 * ExtendedService#createUserWithTasks(UserTO, java.util.stream.Stream)} method as parameters.
 */
public class CreateUserWithTasks implements Operation {
  private final UserTO user;
  private final List<TaskTO> tasks;
  private final ExtendedService extendedService = (ExtendedService)
          TransactionProxy.newInstance(
                  ApplicationContextProvider.getApplicationContext()
                          .getBean(ExtendedService.class));

  public CreateUserWithTasks(UserTO user, List<TaskTO> tasks) {
    this.user = user;
    this.tasks = tasks;
  }

  @Override
  public void execute() {
    extendedService.createUserWithTasks(user, tasks.stream());
  }

  public UserTO getUser() {
    return user;
  }
}
