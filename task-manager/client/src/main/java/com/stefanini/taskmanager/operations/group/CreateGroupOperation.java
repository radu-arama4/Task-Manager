package com.stefanini.taskmanager.operations.group;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.GroupServiceImpl;

public class CreateGroupOperation implements Operation {

  private Group group = null;
  private GroupService groupService = new GroupServiceImpl();

  public CreateGroupOperation(Group group) {
    this.group = group;
  }

  @Override
  public void execute() {
    groupService.createGroup(group);
  }
}
