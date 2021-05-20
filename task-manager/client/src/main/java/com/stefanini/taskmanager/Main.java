package com.stefanini.taskmanager;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.parser.GroupParser;
import com.stefanini.taskmanager.parser.TaskParser;
import com.stefanini.taskmanager.parser.UserParser;
import com.stefanini.taskmanager.persistence.util.DButil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.stefanini.taskmanager.operations.OperationExecutor;
import com.stefanini.taskmanager.operations.group.AddTaskToGroupOperation;
import com.stefanini.taskmanager.operations.group.AddUserToGroupOperation;
import com.stefanini.taskmanager.operations.group.CreateGroupOperation;
import com.stefanini.taskmanager.operations.task.AddTaskOperation;
import com.stefanini.taskmanager.operations.task.ShowTasksOperation;
import com.stefanini.taskmanager.operations.user.CreateUserOperation;
import com.stefanini.taskmanager.operations.user.ShowAllUsersOperation;
import java.util.Arrays;

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

    switch (command) {
      case "-createUser":
        logger.info("New operation: create user");
        user = UserParser.parseUser(flags);
        operationExecutor.addOperation(new CreateUserOperation(user));
        break;
      case "-showAllUsers":
        logger.info("New operation: show all users");
        operationExecutor.addOperation(new ShowAllUsersOperation());
        break;
      case "-addTask":
        logger.info("New operation: add task to user");
        user = UserParser.parseUser(flags);
        task = TaskParser.parseTask(flags);
        operationExecutor.addOperation(new AddTaskOperation(task, user));
        break;
      case "-showTasks":
        logger.info("New operation: show tasks of given user");
        user = UserParser.parseUser(flags);
        operationExecutor.addOperation(new ShowTasksOperation(user));
        break;
      case "-createGroup":
        logger.info("New operation: create group");
        group = GroupParser.parseGroup(flags);
        operationExecutor.addOperation(new CreateGroupOperation(group));
        break;
      case "-addUserToGroup":
        logger.info("New operation: add user to group");
        user = UserParser.parseUser(flags);
        group = GroupParser.parseGroup(flags);
        operationExecutor.addOperation(new AddUserToGroupOperation(group, user));
        break;
      case "-addTaskToGroup":
        logger.info("New operation: add task to group");
        task = TaskParser.parseTask(flags);
        group = GroupParser.parseGroup(flags);
        operationExecutor.addOperation(new AddTaskToGroupOperation(group, task));
        break;
      default:
        logger.warn("Command not recognized: " + command);
        break;
    }

    operationExecutor.executeOperations();

    //TODO move it somewhere else
    DButil.disconnectFromDb();

    logger.info("Program finished");
  }
}
