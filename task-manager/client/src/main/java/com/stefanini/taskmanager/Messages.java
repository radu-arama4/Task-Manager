package com.stefanini.taskmanager;

/**
 * Enum containing all the possible messages that the user can get when inserting data for the new
 * created operations.
 */
public enum Messages {
  MAIN(
      "Press:\n"
          + "1 - New command\n"
          + "2 - View commands\n"
          + "3 - Execute commands\n"
          + "4 - Exit."),
  OPERATIONS(
      "Press:\n"
          + "1 - New user\n"
          + "2 - Show all users\n"
          + "3 - Add task\n"
          + "4 - Show tasks of user\n"
          + "5 - Create group\n"
          + "6 - Add user to group\n"
          + "7 - Add task to group\n"
          + "8 - Add user with tasks \n"
          + "9 - Go back"),
  NEW_USER("Introduce the first name, the last name and the username:"),
  ADD_TASK("Introduce the username, the task title and the task description:"),
  ADD_TASK_WITHOUT_USER("Introduce the task title and the task description:"),
  SHOW_TASKS_OF_USER("Introduce the username:"),
  CREATE_GROUP("Introduce the group name:"),
  ADD_USER_TO_GROUP("Introduce the group name and the username:"),
  ADD_TASK_TO_GROUP("Introduce the group name, the task title, and the task description:");

  String message;

  Messages(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
