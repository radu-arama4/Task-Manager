package com.stefanini.taskmanager.operations.categories.group;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.util.context.ApplicationContextManager;

/**
 * Implements {@link Operation}. Encapsulates {@link GroupTO} and {@link TaskTO} fields. The
 * execution consists of sending the encapsulated fields to the {@link
 * GroupService#addTaskToGroup(GroupTO, TaskTO)} method as parameters.
 */
public class AddTaskToGroupOperation implements Operation {
  private final GroupTO group;
  private final TaskTO task;
  private final GroupService groupService =
      ApplicationContextManager.getApplicationContext().getBean(GroupService.class);

  public AddTaskToGroupOperation(GroupTO group, TaskTO task) {
    this.group = group;
    this.task = task;
  }

  @Override
  public void execute() {
    groupService.addTaskToGroup(group, task);
  }

  public GroupTO getGroup() {
    return group;
  }
}
