package com.stefanini.taskmanager.operations.group;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProvider;
import com.stefanini.taskmanager.service.factory.ServiceType;
import com.stefanini.taskmanager.util.ApplicationProperties;

/**
 * Implements {@link Operation}. Encapsulates the {@link Group} fields. The execution consists of
 * sending the encapsulated fields to the {@link GroupService#createGroup(Group)} method as parameters.
 */
public class CreateGroupOperation implements Operation {
  private final Group group;
  private ServiceFactory serviceFactory = null;
  private final ServiceType serviceType = ApplicationProperties.getInstance().getServiceType();

  {
    try {
      serviceFactory = ServiceFactoryProvider.createServiceFactory(serviceType);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private final GroupService groupService = serviceFactory.getGroupService();

  public CreateGroupOperation(Group group) {
    this.group = group;
  }

  @Override
  public void execute() {
    groupService.createGroup(group);
  }
}
