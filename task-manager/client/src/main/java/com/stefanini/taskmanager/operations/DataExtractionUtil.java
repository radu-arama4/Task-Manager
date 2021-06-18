package com.stefanini.taskmanager.operations;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.operations.categories.group.AddTaskToGroupOperation;
import com.stefanini.taskmanager.operations.categories.group.AddUserToGroupOperation;
import com.stefanini.taskmanager.operations.categories.group.CreateGroupOperation;
import com.stefanini.taskmanager.operations.categories.multiple.CreateUserWithTasks;
import com.stefanini.taskmanager.operations.categories.task.AddTaskToUserOperation;
import com.stefanini.taskmanager.operations.categories.task.ShowTasksOfUserOperation;
import com.stefanini.taskmanager.operations.categories.user.CreateUserOperation;
import com.stefanini.taskmanager.operations.categories.user.ShowAllUsersOperation;
import com.stefanini.taskmanager.operations.execution.OperationExecutor;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static com.stefanini.taskmanager.Messages.*;

public class DataExtractionUtil {
  private static final OperationExecutor operationExecutor = OperationExecutor.getInstance();
  private static final Scanner scanner = new Scanner(System.in);

  private static UserTO user;
  private static TaskTO task;
  private static GroupTO group;

  public static void initNewUser() {
    System.out.println(NEW_USER.getMessage());
    String firstName = scanner.next();
    String lastName = scanner.next();
    String username = scanner.next();
    scanner.nextLine();
    user = new UserTO(firstName, lastName, username);
    operationExecutor.addOperation(new CreateUserOperation(user));
  }

  public static void initShowUsers() {
    operationExecutor.addOperation(new ShowAllUsersOperation());
  }

  public static void initNewTask() {
    System.out.println(ADD_TASK.getMessage());
    String username = scanner.next();
    String taskTitle = scanner.next();
    String taskDescription = scanner.next();
    scanner.nextLine();
    task = new TaskTO(taskTitle, taskDescription);
    user = new UserTO(username);
    operationExecutor.addOperation(new AddTaskToUserOperation(task, user));
  }

  public static void initShowTasks() {
    System.out.println(SHOW_TASKS_OF_USER.getMessage());
    String userName = scanner.next();
    user = new UserTO(userName);
    operationExecutor.addOperation(new ShowTasksOfUserOperation(user));
  }

  public static void initCreateGroup() {
    System.out.println(CREATE_GROUP.getMessage());
    String groupName = scanner.next();
    group = new GroupTO(groupName);
    operationExecutor.addOperation(new CreateGroupOperation(group));
  }

  public static void initAddUserToGroup() {
    System.out.println(ADD_USER_TO_GROUP.getMessage());
    String groupName = scanner.next();
    String userName = scanner.next();
    user = new UserTO(userName);
    group = new GroupTO(groupName);
    operationExecutor.addOperation(new AddUserToGroupOperation(group, user));
  }

  public static void initAddTaskToGroup() {
    System.out.println(ADD_TASK_TO_GROUP.getMessage());
    String groupName = scanner.next();
    String taskTitle = scanner.next();
    String taskDescription = scanner.next();
    task = new TaskTO(taskTitle, taskDescription);
    group = new GroupTO(groupName);
    operationExecutor.addOperation(new AddTaskToGroupOperation(group, task));
  }

  public static void initNewUserWithTasks() {
    System.out.println(NEW_USER.getMessage());
    String firstName = scanner.next();
    String lastName = scanner.next();
    String username = scanner.next();
    user = new UserTO(firstName, lastName, username);
    List<TaskTO> tasks = new LinkedList<>();
    String taskTitle = ".";
    String taskDescription = ".";
    boolean shouldCollectTasks = true;
    while (shouldCollectTasks) {
      System.out.println(ADD_TASK_WITHOUT_USER.getMessage());
      taskTitle = scanner.next();
      taskDescription = scanner.next();
      if (taskTitle.equals("-") || taskDescription.equals("-")) {
        shouldCollectTasks = false;
      }
      tasks.add(new TaskTO(taskTitle, taskDescription));
    }
    operationExecutor.addOperation(new CreateUserWithTasks(user, tasks));
  }

  public static void printOperations() {
    operationExecutor.printOperations();
  }

  public static void executeOperations() {
    operationExecutor.executeOperations();
  }
}
