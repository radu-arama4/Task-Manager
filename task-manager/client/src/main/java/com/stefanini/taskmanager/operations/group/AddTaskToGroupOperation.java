package com.stefanini.taskmanager.operations.group;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryImpl;

public class AddTaskToGroupOperation implements Operation {
  private final Group group;
  private final Task task;
  private final ServiceFactory serviceFactory = new ServiceFactoryImpl();
  private final GroupService groupService = serviceFactory.getGroupService();

  public AddTaskToGroupOperation(Group group, Task task) {
    this.group = group;
    this.task = task;
  }

  @Override
  public void execute() {
    groupService.addTaskToGroup(group, task);
  }
}
