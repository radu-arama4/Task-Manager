package com.stefanini.taskmanager.operations.user;

import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.receivers.UserReceiver;

public class CreateUserOperation implements Operation {

  private UserReceiver user;

  public CreateUserOperation(UserReceiver user) {
    this.user = user;
  }

  @Override
  public void execute() {
    user.addUser();
  }
}
