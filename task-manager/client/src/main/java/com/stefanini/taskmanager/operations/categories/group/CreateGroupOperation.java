package com.stefanini.taskmanager.operations.categories.group;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.util.ApplicationContextProvider;

/**
 * Implements {@link Operation}. Encapsulates the {@link GroupTO} fields. The execution consists of
 * sending the encapsulated fields to the {@link GroupService#createGroup(GroupTO)} method as
 * parameters.
 */
public class CreateGroupOperation implements Operation {
  private final GroupTO group;
  private final GroupService groupService =
      ApplicationContextProvider.getApplicationContext().getBean(GroupService.class);

  public CreateGroupOperation(GroupTO group) {
    this.group = group;
  }

  @Override
  public void execute() {
    groupService.createGroup(group);
  }

  public GroupTO getGroup() {
    return group;
  }
}
