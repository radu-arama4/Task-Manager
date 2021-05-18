package com.stefanini.taskmanager;

import com.stefanini.taskmanager.dto.Group;
import com.stefanini.taskmanager.dto.Task;
import com.stefanini.taskmanager.dto.User;
import com.stefanini.taskmanager.parser.GroupParser;
import com.stefanini.taskmanager.parser.TaskParser;
import com.stefanini.taskmanager.parser.UserParser;
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
import com.stefanini.taskmanager.receivers.GroupReceiver;
import com.stefanini.taskmanager.receivers.TaskReceiver;
import com.stefanini.taskmanager.receivers.UserReceiver;

import java.lang.reflect.Array;
import java.util.Arrays;

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

        // TODO args[0] variable, args[1]-infinit flags and parse
        String command = args[0];
        String[] flags = Arrays.copyOfRange(args, 1, args.length);

        User user = null;
        Task task = null;
        Group group = null;

        switch (command) {
            // TODO call here 2 parsing methods
            case "-createUser":
                logger.info("New operation: create user");
                user = UserParser.parseUser(flags);
                operationExecutor.executeOperation(new CreateUserOperation(new UserReceiver(args)));
                break;
            case "-showAllUsers":
                logger.info("New operation: show all users");

                operationExecutor.executeOperation(new ShowAllUsersOperation(new UserReceiver(args)));
                break;
            case "-addTask":
                logger.info("New operation: add task to user");

                user = UserParser.parseUser(flags);
                task = TaskParser.parseTask(flags);

                operationExecutor.executeOperation(new AddTaskOperation(new TaskReceiver(args)));
                break;
            case "-showTasks":
                logger.info("New operation: show tasks of given user");
                // TODO Operation receives DTO, not Receiver
                user = UserParser.parseUser(flags);
                operationExecutor.executeOperation(new ShowTasksOperation(new TaskReceiver(args)));
                break;
            case "-createGroup":
                logger.info("New operation: create group");
                group = GroupParser.parseGroup(args);
                operationExecutor.executeOperation(new CreateGroupOperation(new GroupReceiver(args)));
                break;
            case "-addUserToGroup":
                logger.info("New operation: add user to group");
                user = UserParser.parseUser(args);
                group = GroupParser.parseGroup(args);
                operationExecutor.executeOperation(new AddUserToGroupOperation(new GroupReceiver(args)));
                break;
            case "-addTaskToGroup":
                logger.info("New operation: add task to group");
                task = TaskParser.parseTask(flags);
                group = GroupParser.parseGroup(args);
                operationExecutor.executeOperation(new AddTaskToGroupOperation(new GroupReceiver(args)));
                break;
            default:
                logger.warn("Command not recognized: " + args[0]);
                break;
        }

        logger.info("Program finished");

    }

}
