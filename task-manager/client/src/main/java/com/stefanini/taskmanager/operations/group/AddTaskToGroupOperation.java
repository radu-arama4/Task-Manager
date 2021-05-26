package com.stefanini.taskmanager.operations.group;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.factory.ServiceFactory;
import com.stefanini.taskmanager.service.factory.ServiceFactoryProvider;

/**
 * Implements {@link Operation}. Encapsulates {@link GroupTO} and {@link TaskTO} fields. The execution
 * consists of sending the encapsulated fields to the {@link GroupService#addTaskToGroup(GroupTO,
 * TaskTO)} method as parameters.
 */
public class AddTaskToGroupOperation implements Operation {
  private final GroupTO group;
  private final TaskTO task;
  private final ServiceFactory serviceFactory = ServiceFactoryProvider.createServiceFactory();
  private final GroupService groupService = serviceFactory.getGroupService();

  public AddTaskToGroupOperation(GroupTO group, TaskTO task) {
    this.group = group;
    this.task = task;
  }

  @Override
  public void execute() {
    groupService.addTaskToGroup(group, task);
  }
}
