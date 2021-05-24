package com.stefanini.taskmanager.operations.user;

import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProvider;
import com.stefanini.taskmanager.service.factory.ServiceType;
import com.stefanini.taskmanager.util.ApplicationProperties;

import java.util.List;

/**
 * Implements {@link Operation}. Encapsulates no fields. The execution consists of calling the
 * {@link UserService#getAllUsers()}
 */
public class ShowAllUsersOperation implements Operation {
  private ServiceFactory serviceFactory = null;
  private final ServiceType serviceType = ApplicationProperties.getInstance().getServiceType();

  {
    try {
      serviceFactory = ServiceFactoryProvider.createServiceFactory(serviceType);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private final UserService userService = serviceFactory.getUserService();

  public ShowAllUsersOperation() {}

  @Override
  public void execute() {
    List<User> users = userService.getAllUsers();
    users.forEach(System.out::println);
  }
}
