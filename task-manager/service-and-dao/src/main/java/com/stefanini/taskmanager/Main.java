package com.stefanini.taskmanager;

import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.util.ApplicationManager;
import com.stefanini.taskmanager.util.context.ApplicationContextManager;

import java.util.UUID;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {
    ApplicationManager.initApp();
  }

  private static void newUser() {
    UserService userService =
        ApplicationContextManager.getApplicationContext().getBean(UserService.class);

    String firstName = generateRandomString();
    String lastName = generateRandomString();
    String username = generateRandomString();

    userService.createUser(new UserTO(firstName, lastName, username));
  }

  private static void showAllUsers() {
    UserService userService =
        ApplicationContextManager.getApplicationContext().getBean(UserService.class);

    Stream<UserTO> users = userService.getAllUsers();
    users.forEach(System.out::println);
  }

  private static String generateRandomString() {
    return UUID.randomUUID().toString().substring(0, 7);
  }
}
