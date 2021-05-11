package com.stefanini.taskmanager.operations.group;

import com.stefanini.taskmanager.Operation;
import com.stefanini.taskmanager.receivers.GroupReceiver;

public class AddTaskToGroupOperation implements Operation {

  GroupReceiver group = null;

  public AddTaskToGroupOperation(GroupReceiver group) {
    this.group = group;
  }

  @Override
  public void execute() {
    group.addTaskToGroup();
  }

}
