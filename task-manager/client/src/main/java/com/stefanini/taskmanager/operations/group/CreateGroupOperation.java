package com.stefanini.taskmanager.operations.group;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryImpl;

public class CreateGroupOperation implements Operation {
  private final Group group;
  private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
  private final GroupService groupService = serviceFactory.getGroupService();

  public CreateGroupOperation(Group group) {
    this.group = group;
  }

  @Override
  public void execute() {
    groupService.createGroup(group);
  }
}
