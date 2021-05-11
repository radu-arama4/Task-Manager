package com.stefanini.taskmanager.operations.group;

import com.stefanini.taskmanager.Operation;
import com.stefanini.taskmanager.receivers.GroupReceiver;

public class CreateGroupOperation implements Operation {

  GroupReceiver group = null;

  public CreateGroupOperation(GroupReceiver group) {
    this.group = group;
  }

  @Override
  public void execute() {
    group.addGroup();
  }
}
