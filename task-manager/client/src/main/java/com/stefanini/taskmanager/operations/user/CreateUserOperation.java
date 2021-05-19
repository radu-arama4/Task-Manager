package com.stefanini.taskmanager.operations.user;

import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryImpl;

public class CreateUserOperation implements Operation {
  private final User user;
  private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
  private final UserService userService = serviceFactory.getUserService();

  public CreateUserOperation(User user) {
    this.user = user;
  }

  @Override
  public void execute() {
    userService.createUser(user);
  }
}
