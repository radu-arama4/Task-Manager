package com.stefanini.taskmanager.operations.user;

import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProvider;

import static com.stefanini.taskmanager.service.factory.ServiceType.STANDARD;

/**
 * Implements {@link Operation}. Encapsulates no fields. The execution consists of calling the {@link
 * UserService#showAllUsers()}
 */
public class ShowAllUsersOperation implements Operation {
  private final ServiceFactory serviceFactory =
      ServiceFactoryProvider.createServiceFactory(STANDARD);
  private final UserService userService;

  {
    assert serviceFactory != null;
    userService = serviceFactory.getUserService();
  }

  public ShowAllUsersOperation() {}

  @Override
  public void execute() {
    userService.showAllUsers();
  }
}
