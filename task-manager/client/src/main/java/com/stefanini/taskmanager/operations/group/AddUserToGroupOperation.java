package com.stefanini.taskmanager.operations.group;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProvider;
import com.stefanini.taskmanager.service.factory.ServiceType;
import com.stefanini.taskmanager.util.ApplicationProperties;

/**
 * Implements {@link Operation}. Encapsulates {@link Group} and {@link User} fields. The execution
 * consists of sending the encapsulated fields to the {@link GroupService#addUserToGroup(Group,
 * User)} method as parameters.
 */
public class AddUserToGroupOperation implements Operation {
  private final Group group;
  private final User user;
  private ServiceFactory serviceFactory = null;
  private final ServiceType serviceType = ApplicationProperties.getInstance().getServiceType();

  {
    try {
      serviceFactory =
          ServiceFactoryProvider.createServiceFactory(serviceType);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private final GroupService groupService = serviceFactory.getGroupService();

  public AddUserToGroupOperation(Group group, User user) {
    this.group = group;
    this.user = user;
  }

  @Override
  public void execute() {
    groupService.addUserToGroup(group, user);
  }
}
