package com.stefanini.taskmanager.operations.execution.scheduling;

import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.operations.categories.group.AddTaskToGroupOperation;
import com.stefanini.taskmanager.operations.categories.group.AddUserToGroupOperation;
import com.stefanini.taskmanager.operations.categories.group.CreateGroupOperation;
import com.stefanini.taskmanager.operations.categories.multiple.CreateUserWithTasks;
import com.stefanini.taskmanager.operations.categories.task.AddTaskToUserOperation;
import com.stefanini.taskmanager.operations.categories.task.ShowTasksOfUserOperation;
import com.stefanini.taskmanager.operations.categories.user.CreateUserOperation;
import com.stefanini.taskmanager.operations.categories.user.ShowAllUsersOperation;

import java.util.*;

/** Contains methods for dividing the operations in order to be able to execute concurrently. */
public class OperationScheduleUtil {
  /**
   * Divides the operations based on the username or group name. Add ..
   *
   * @param operations list of operations
   * @return map with divided operations
   */
  //todo complete javadoc
  public static Map<OperationKey, PriorityQueue<Operation>> divideOperations(
      List<Operation> operations) {
    Map<OperationKey, PriorityQueue<Operation>> dividedOperations = new HashMap<>();
    PriorityQueue<Operation> printOperations = new PriorityQueue<>(new OperationsComparator());
    dividedOperations.put(new OperationKey("", "printAllUsersOperation"), printOperations); //

    operations.forEach(
        operation -> {
          if (operation instanceof ShowAllUsersOperation) {
            printOperations.add(operation);
            return;
          }

          if (operation.getClass().toString().contains("User")) {
            scheduleUserOperations(dividedOperations, operation);
          } else if (operation.getClass().toString().contains("Group")) {
            scheduleGroupOperations(dividedOperations, operation);
          }
        });

    return dividedOperations;
  }

  private static void scheduleUserOperations(
      Map<OperationKey, PriorityQueue<Operation>> dividedOperations, Operation operation) {
    String userName = null;

    if (operation instanceof CreateUserOperation) {
      CreateUserOperation createUserOperation = (CreateUserOperation) operation;
      userName = createUserOperation.getUser().getUserName();
    } else if (operation instanceof CreateUserWithTasks) {
      CreateUserWithTasks createUserWithTasks = (CreateUserWithTasks) operation;
      userName = createUserWithTasks.getUser().getUserName();
    } else if (operation instanceof AddTaskToUserOperation) {
      AddTaskToUserOperation addTaskToUserOperation = (AddTaskToUserOperation) operation;
      userName = addTaskToUserOperation.getUser().getUserName();
    } else if (operation instanceof ShowTasksOfUserOperation) {
      ShowTasksOfUserOperation showTasksOfUserOperation = (ShowTasksOfUserOperation) operation;
      userName = showTasksOfUserOperation.getUser().getUserName();
    }

    OperationKey operationKey = new OperationKey(userName, "User");

    findOrCreateKeyToAddOperation(dividedOperations, operation, operationKey);
  }

  private static void scheduleGroupOperations(
      Map<OperationKey, PriorityQueue<Operation>> dividedOperations, Operation operation) {
    String groupName = null;
    if (operation instanceof CreateGroupOperation) {
      CreateGroupOperation createGroupOperation = (CreateGroupOperation) operation;
      groupName = createGroupOperation.getGroup().getGroupName();
    } else if (operation instanceof AddTaskToGroupOperation) {
      AddTaskToGroupOperation addTaskToGroupOperation = (AddTaskToGroupOperation) operation;
      groupName = addTaskToGroupOperation.getGroup().getGroupName();
    } else if (operation instanceof AddUserToGroupOperation) {
      AddUserToGroupOperation addUserToGroupOperation = (AddUserToGroupOperation) operation;
      groupName = addUserToGroupOperation.getGroup().getGroupName();
    }

    OperationKey operationKey = new OperationKey(groupName, "Group");

    findOrCreateKeyToAddOperation(dividedOperations, operation, operationKey);
  }

  private static void findOrCreateKeyToAddOperation(
      Map<OperationKey, PriorityQueue<Operation>> dividedOperations,
      Operation operation,
      OperationKey operationKey) {
    if (dividedOperations.containsKey(operationKey)) {
      PriorityQueue<Operation> foundQueue = dividedOperations.get(operationKey);
      foundQueue.add(operation);
    } else {
      PriorityQueue<Operation> newQueue = new PriorityQueue<>(12, new OperationsComparator());
      newQueue.add(operation);
      dividedOperations.put(operationKey, newQueue);
    }
  }
}
