package com.stefanini.taskmanager.operations.user;

import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.UserServiceImpl;

public class ShowAllUsersOperation implements Operation {

  private UserService userService = new UserServiceImpl();

  public ShowAllUsersOperation() {
  }

  @Override
  public void execute() {
    userService.showAllUsers();
  }
}
