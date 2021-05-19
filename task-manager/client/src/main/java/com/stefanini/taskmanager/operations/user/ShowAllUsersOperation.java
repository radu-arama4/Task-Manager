package com.stefanini.taskmanager.operations.user;

import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryImpl;

public class ShowAllUsersOperation implements Operation {
  private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
  private final UserService userService = serviceFactory.getUserService();

  public ShowAllUsersOperation() {}

  @Override
  public void execute() {
    userService.showAllUsers();
  }
}
