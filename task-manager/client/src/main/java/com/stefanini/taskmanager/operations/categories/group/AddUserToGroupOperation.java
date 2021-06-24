package com.stefanini.taskmanager.operations.categories.group;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.GroupService;
import com.stefanini.taskmanager.service.proxy.transaction.TransactionProxy;
import com.stefanini.taskmanager.util.ApplicationContextProvider;

/**
 * Implements {@link Operation}. Encapsulates {@link GroupTO} and {@link UserTO} fields. The execution
 * consists of sending the encapsulated fields to the {@link GroupService#addUserToGroup(GroupTO,
 * UserTO)} method as parameters.
 */
public class AddUserToGroupOperation implements Operation {
  private final GroupTO group;
  private final UserTO user;
  private final GroupService groupService = (GroupService)
          TransactionProxy.newInstance(
                  ApplicationContextProvider.getApplicationContext()
                          .getBean(GroupService.class));

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
