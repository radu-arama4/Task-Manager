package com.stefanini.taskmanager;

import com.stefanini.taskmanager.dto.GroupTO;
import com.stefanini.taskmanager.dto.TaskTO;
import com.stefanini.taskmanager.dto.UserTO;
import com.stefanini.taskmanager.operations.OperationExecutor;
import com.stefanini.taskmanager.operations.group.AddTaskToGroupOperation;
import com.stefanini.taskmanager.operations.group.AddUserToGroupOperation;
import com.stefanini.taskmanager.operations.group.CreateGroupOperation;
import com.stefanini.taskmanager.operations.task.AddTaskOperation;
import com.stefanini.taskmanager.operations.task.ShowTasksOperation;
import com.stefanini.taskmanager.operations.user.CreateUserOperation;
import com.stefanini.taskmanager.operations.user.ShowAllUsersOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static com.stefanini.taskmanager.Messages.*;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    logger.info("Program started");

    if (args.length == 0) {
      logger.warn("No given arguments. Finish.");
      return;
    }

    OperationExecutor operationExecutor = new OperationExecutor();
    logger.info("Operation executor created");

    UserTO user;
    TaskTO task;
    GroupTO group;

    Scanner scanner = new Scanner(System.in);

    boolean mainShouldContinue = true;

    // TODO organize
    while (mainShouldContinue) {
      System.out.println(MAIN.message);
      int choice1 = scanner.nextInt();
      switch (choice1) {
        case 1:
          {
            boolean operationsShouldContinue = true;
            while (operationsShouldContinue) {
              System.out.println(OPERATIONS.message);
              int choice2 = scanner.nextInt();
              switch (choice2) {
                case 1:
                  {
                    System.out.println(NEW_USER.message);
                    String firstName = scanner.nextLine();
                    String lastName = scanner.nextLine();
                    String username = scanner.nextLine();
                    scanner.nextLine();
                    user = new UserTO(firstName, lastName, username);
                    System.out.println("wai");
                    operationExecutor.addOperation(new CreateUserOperation(user));
                    System.out.println("New operation: create user");
                    break;
                  }
                case 2:
                  {
                    operationExecutor.addOperation(new ShowAllUsersOperation());
                    System.out.println("New operation: show all users");
                    break;
                  }
                case 3:
                  {
                    System.out.println(ADD_TASK.message);
                    String username = scanner.nextLine();
                    String taskTitle = scanner.nextLine();
                    String taskDescription = scanner.nextLine();
                    scanner.nextLine();
                    task = new TaskTO(taskTitle, taskDescription);
                    user = new UserTO(username);
                    operationExecutor.addOperation(new AddTaskOperation(task, user));
                    System.out.println("New operation: add task to user");
                    break;
                  }
                case 4:
                  {
                    System.out.println(SHOW_TASKS_OF_USER.message);
                    String userName = scanner.nextLine();
                    scanner.nextLine();
                    user = new UserTO(userName);
                    operationExecutor.addOperation(new ShowTasksOperation(user));
                    System.out.println("New operation: show tasks of given user");
                    break;
                  }
                case 5:
                  {
                    System.out.println(CREATE_GROUP.message);
                    String groupName = scanner.nextLine();
                    scanner.nextLine();
                    group = new GroupTO(groupName);
                    operationExecutor.addOperation(new CreateGroupOperation(group));
                    System.out.println("New operation: create group");
                    break;
                  }
                case 6:
                  {
                    System.out.println(ADD_USER_TO_GROUP.message);
                    String userName = scanner.nextLine();
                    String groupName = scanner.nextLine();
                    scanner.nextLine();
                    user = new UserTO(userName);
                    group = new GroupTO(groupName);
                    operationExecutor.addOperation(new AddUserToGroupOperation(group, user));
                    System.out.println("New operation: add user to group");
                    break;
                  }
                case 7:
                  {
                    System.out.println(ADD_TASK_TO_GROUP.message);
                    String taskTitle = scanner.nextLine();
                    String taskDescription = scanner.nextLine();
                    String groupName = scanner.nextLine();
                    scanner.nextLine();
                    task = new TaskTO(taskTitle, taskDescription);
                    group = new GroupTO(groupName);
                    operationExecutor.addOperation(new AddTaskToGroupOperation(group, task));
                    System.out.println("New operation: add task to group");
                    break;
                  }
                case 8:
                  {
                    System.out.println(NEW_USER.message);
                    String firstName = scanner.nextLine();
                    String lastName = scanner.nextLine();
                    String username = scanner.nextLine();
                    user = new UserTO(firstName, lastName, username);
                    List<TaskTO> tasks = new LinkedList<>();
                    while (scanner.hasNext()) {
                      System.out.println(ADD_TASK.message);
                      String taskTitle = scanner.nextLine();
                      String taskDescription = scanner.nextLine();
                      tasks.add(new TaskTO(taskTitle, taskDescription));
                    }
                  }
                case 9:
                  {
                    operationsShouldContinue = false;
                    break;
                  }
                default:
                  {
                    break;
                  }
              }
            }
            break;
          }
        case 2:
          {
            operationExecutor.printOperations();
            break;
          }
        case 3:
          {
            operationExecutor.executeOperations();
            break;
          }
        case 4:
          {
            mainShouldContinue = false;
            break;
          }
      }
      System.out.println("-------------------------------");
    }

    // TODO move it somewhere else
    // DataBaseUtil.disconnectFromDb();

    logger.info("Program finished");
  }
}
