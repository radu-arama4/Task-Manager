package com.stefanini.taskmanager.operations.categories.user;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.util.context.ApplicationContextManager;

import java.util.stream.Stream;

/**
 * Implements {@link Operation}. Encapsulates no fields. The execution consists of calling the
 * {@link UserService#getAllUsers()}
 */
public class ShowAllUsersOperation implements Operation {
  private final UserService userService =
      ApplicationContextManager.getApplicationContext().getBean(UserService.class);

  public ShowAllUsersOperation() {}

  @Override
  public void execute() {
    Stream<UserTO> users = userService.getAllUsers();
    users.forEach(System.out::println);
  }
}
