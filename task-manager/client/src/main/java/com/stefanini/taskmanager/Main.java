package com.stefanini.taskmanager;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
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

import java.util.Arrays;
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

    String command = args[0];
    String[] flags = Arrays.copyOfRange(args, 1, args.length);

    User user;
    Task task;
    Group group;

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
                    user = new User(firstName, lastName, username);
                    System.out.println("wai");
                    operationExecutor.addOperation(new CreateUserOperation(user));
                    logger.info("New operation: create user"); //TODO print
                    break;
                  }
                case 2:
                  {
                    operationExecutor.addOperation(new ShowAllUsersOperation());
                    logger.info("New operation: show all users");
                    break;
                  }
                case 3:
                  {
                    System.out.println(ADD_TASK.message);
                    String username = scanner.nextLine();
                    String taskTitle = scanner.nextLine();
                    String taskDescription = scanner.nextLine();
                    scanner.nextLine();
                    task = new Task(taskTitle, taskDescription);
                    user = new User(username);
                    operationExecutor.addOperation(new AddTaskOperation(task, user));
                    logger.info("New operation: add task to user");
                    break;
                  }
                case 4:
                  {
                    System.out.println(SHOW_TASKS_OF_USER.message);
                    String userName = scanner.nextLine();
                    scanner.nextLine();
                    user = new User(userName);
                    operationExecutor.addOperation(new ShowTasksOperation(user));
                    logger.info("New operation: show tasks of given user");
                    break;
                  }
                case 5:
                  {
                    System.out.println(CREATE_GROUP.message);
                    String groupName = scanner.nextLine();
                    scanner.nextLine();
                    group = new Group(groupName);
                    operationExecutor.addOperation(new CreateGroupOperation(group));
                    logger.info("New operation: create group");
                    break;
                  }
                case 6:
                  {
                    System.out.println(ADD_USER_TO_GROUP.message);
                    String userName = scanner.nextLine();
                    String groupName = scanner.nextLine();
                    scanner.nextLine();
                    user = new User(userName);
                    group = new Group(groupName);
                    operationExecutor.addOperation(new AddUserToGroupOperation(group, user));
                    logger.info("New operation: add user to group");
                    break;
                  }
                case 7:
                  {
                    System.out.println(ADD_TASK_TO_GROUP.message);
                    String taskTitle = scanner.nextLine();
                    String taskDescription = scanner.nextLine();
                    String groupName = scanner.nextLine();
                    scanner.nextLine();
                    task = new Task(taskTitle, taskDescription);
                    group = new Group(groupName);
                    operationExecutor.addOperation(new AddTaskToGroupOperation(group, task));
                    logger.info("New operation: add task to group");
                    break;
                  }
                case 8:
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

    //    switch (command) {
    //      case "-createUser":
    //        logger.info("New operation: create user");
    //        user = UserParser.parseUser(flags);
    //        operationExecutor.addOperation(new CreateUserOperation(user));
    //        break;
    //      case "-showAllUsers":
    //        logger.info("New operation: show all users");
    //        operationExecutor.addOperation(new ShowAllUsersOperation());
    //        break;
    //      case "-addTask":
    //        logger.info("New operation: add task to user");
    //        user = UserParser.parseUser(flags);
    //        task = TaskParser.parseTask(flags);
    //        operationExecutor.addOperation(new AddTaskOperation(task, user));
    //        break;
    //      case "-showTasks":
    //        logger.info("New operation: show tasks of given user");
    //        user = UserParser.parseUser(flags);
    //        operationExecutor.addOperation(new ShowTasksOperation(user));
    //        break;
    //      case "-createGroup":
    //        logger.info("New operation: create group");
    //        group = GroupParser.parseGroup(flags);
    //        operationExecutor.addOperation(new CreateGroupOperation(group));
    //        break;
    //      case "-addUserToGroup":
    //        logger.info("New operation: add user to group");
    //        user = UserParser.parseUser(flags);
    //        group = GroupParser.parseGroup(flags);
    //        operationExecutor.addOperation(new AddUserToGroupOperation(group, user));
    //        break;
    //      case "-addTaskToGroup":
    //        logger.info("New operation: add task to group");
    //        task = TaskParser.parseTask(flags);
    //        group = GroupParser.parseGroup(flags);
    //        operationExecutor.addOperation(new AddTaskToGroupOperation(group, task));
    //        break;
    //      default:
    //        logger.warn("Command not recognized: " + command);
    //        break;
    //    }

    // operationExecutor.executeOperations();

    // TODO move it somewhere else
    // DataBaseUtil.disconnectFromDb();

    logger.info("Program finished");
  }
}
