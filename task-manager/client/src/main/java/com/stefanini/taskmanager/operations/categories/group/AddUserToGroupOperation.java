package com.stefanini.taskmanager.operations.categories.group;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProvider;

/**
 * Implements {@link Operation}. Encapsulates {@link GroupTO} and {@link UserTO} fields. The execution
 * consists of sending the encapsulated fields to the {@link GroupService#addUserToGroup(GroupTO,
 * UserTO)} method as parameters.
 */
public class AddUserToGroupOperation implements Operation {
  private final GroupTO group;
  private final UserTO user;
  private final ServiceFactory serviceFactory = ServiceFactoryProvider.createServiceFactory();
  private final GroupService groupService = serviceFactory.getGroupService();

  public AddUserToGroupOperation(GroupTO group, UserTO user) {
    this.group = group;
    this.user = user;
  }

  @Override
  public void execute() {
    groupService.addUserToGroup(group, user);
  }

  public GroupTO getGroup() {
    return group;
  }
}
