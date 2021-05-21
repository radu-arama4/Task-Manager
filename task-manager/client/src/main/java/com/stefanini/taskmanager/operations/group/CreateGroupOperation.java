package com.stefanini.taskmanager.operations.group;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProvider;

import static com.stefanini.taskmanager.service.factory.ServiceType.STANDARD;

/**
 * Implements {@link Operation}. Encapsulates the {@link Group} fields. The execution consists of
 * sending the encapsulated fields to the {@link GroupService#createGroup(Group)} method as parameters.
 */
public class CreateGroupOperation implements Operation {
  private final Group group;
  private final ServiceFactory serviceFactory =
      ServiceFactoryProvider.createServiceFactory(STANDARD);
  private final GroupService groupService;

  {
    assert serviceFactory != null;
    groupService = serviceFactory.getGroupService();
  }

  public CreateGroupOperation(Group group) {
    this.group = group;
  }

  @Override
  public void execute() {
    groupService.createGroup(group);
  }
}
