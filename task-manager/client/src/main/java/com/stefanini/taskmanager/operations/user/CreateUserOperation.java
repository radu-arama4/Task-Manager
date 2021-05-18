package com.stefanini.taskmanager.operations.user;

import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.UserServiceImpl;

public class CreateUserOperation implements Operation {

  private User user;
  private UserService userService = new UserServiceImpl();

  public CreateUserOperation(User user) {
    this.user = user;
  }

  @Override
  public void execute() {
    userService.createUser(user);
  }
}
