package com.stefanini.taskmanager.operations.categories.user;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.util.context.ApplicationContextManager;

/**
 * Implements {@link Operation}. Encapsulates {@link UserTO} field. The execution consists of
 * sending the encapsulated fields to {@link UserService#createUser(UserTO)} implementation as
 * parameters.
 */
public class CreateUserOperation implements Operation {
  private final UserTO user;
  private final UserService userService =
      ApplicationContextManager.getApplicationContext().getBean("standard", UserService.class);

  public CreateUserOperation(UserTO user) {
    this.user = user;
  }

  @Override
  public void execute() {
    userService.createUser(user);
  }

  public UserTO getUser() {
    return user;
  }
}
