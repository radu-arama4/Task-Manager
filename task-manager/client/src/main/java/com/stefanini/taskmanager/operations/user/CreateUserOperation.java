package com.stefanini.taskmanager.operations.user;

import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProvider;

import static com.stefanini.taskmanager.service.factory.ServiceType.STANDARD;

/**
 * Implements {@link Operation}. Encapsulates {@link User} field. The execution consists of sending
 * the encapsulated fields to {@link UserService#createUser(User)} implementation as parameters.
 */
public class CreateUserOperation implements Operation {
  private final User user;
  private final ServiceFactory serviceFactory =
      ServiceFactoryProvider.createServiceFactory(STANDARD);
  private final UserService userService;

  {
    assert serviceFactory != null;
    userService = serviceFactory.getUserService();
  }

  public CreateUserOperation(User user) {
    this.user = user;
  }

  @Override
  public void execute() {
    userService.createUser(user);
  }
}
