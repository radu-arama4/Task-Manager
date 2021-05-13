package com.stefanini.taskmanager.operations.group;

import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.receivers.GroupReceiver;

public class AddUserToGroupOperation implements Operation {

  GroupReceiver group = null;

  public AddUserToGroupOperation(GroupReceiver group) {
    this.group = group;
  }

  @Override
  public void execute() {
    group.addUserToGroup();
  }

}
