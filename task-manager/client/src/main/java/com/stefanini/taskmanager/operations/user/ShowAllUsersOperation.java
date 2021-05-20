package com.stefanini.taskmanager.operations.user;

import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.factory.ServiceType;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProduction;

public class ShowAllUsersOperation implements Operation {
  private final ServiceFactory serviceFactory = ServiceFactoryProduction.createServiceFactory(ServiceType.SERVICE_TYPE.value);
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
