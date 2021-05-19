package com.stefanini.taskmanager.operations.group;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryImpl;

public class AddUserToGroupOperation implements Operation {
  private final Group group;
  private final User user;
  private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
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
