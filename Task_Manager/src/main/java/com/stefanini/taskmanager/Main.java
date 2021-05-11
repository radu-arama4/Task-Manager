package com.stefanini.taskmanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.stefanini.taskmanager.operations.group.AddTaskToGroupOperation;
import com.stefanini.taskmanager.operations.group.AddUserToGroupOperation;
import com.stefanini.taskmanager.operations.group.CreateGroupOperation;
import com.stefanini.taskmanager.operations.task.AddTaskOperation;
import com.stefanini.taskmanager.operations.task.ShowTasksOperation;
import com.stefanini.taskmanager.operations.user.CreateUserOperation;
import com.stefanini.taskmanager.operations.user.ShowAllUsersOperation;
import com.stefanini.taskmanager.receivers.GroupReceiver;
import com.stefanini.taskmanager.receivers.TaskReceiver;
import com.stefanini.taskmanager.receivers.UserReceiver;

public class Main {

  private static Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {

    logger.info("Program started");

    if (args.length == 0) {
      logger.warn("No given arguments. Finish.");
      return;
    }

    OperationExecutor operationExecutor = new OperationExecutor();
    logger.info("Operation executor created");

    switch (args[0]) {
      case "-createUser":
        logger.info("New operation: create user");
        operationExecutor.executeOperation(new CreateUserOperation(new UserReceiver(args)));
        // userService.createUser(args);
        break;
      case "-showAllUsers":
        logger.info("New operation: show all users");
        operationExecutor.executeOperation(new ShowAllUsersOperation(new UserReceiver(args)));
        // userService.showAllUsers();
        break;
      case "-addTask":
        logger.info("New operation: add task to user");
        operationExecutor.executeOperation(new AddTaskOperation(new TaskReceiver(args)));
        // taskService.addTask(args);
        break;
      case "-showTasks":
        logger.info("New operation: show tasks of given user");
        operationExecutor.executeOperation(new ShowTasksOperation(new TaskReceiver(args)));
        // taskService.showTasks(args);
        break;
      case "-createGroup":
        logger.info("New operation: create group");
        operationExecutor.executeOperation(new CreateGroupOperation(new GroupReceiver(args)));
        // groupService.createGroup(args);
        break;
      case "-addUserToGroup":
        logger.info("New operation: add user to group");
        operationExecutor.executeOperation(new AddUserToGroupOperation(new GroupReceiver(args)));
        // groupService.addUserToGroup(args);
        break;
      case "-addTaskToGroup":
        logger.info("New operation: add task to group");
        operationExecutor.executeOperation(new AddTaskToGroupOperation(new GroupReceiver(args)));
        // groupService.addTaskToGroup(args);
        break;
      default:
        logger.warn("Command not recognized: " + args[0]);
        System.out.println("Incorrect command! Try again!");
        break;
    }

    logger.info("Program finished");

  }

}
