package com.stefanini.taskmanager.operations;

import com.stefanini.taskmanager.operations.group.AddTaskToGroupOperation;
import com.stefanini.taskmanager.operations.group.AddUserToGroupOperation;
import com.stefanini.taskmanager.operations.group.CreateGroupOperation;
import com.stefanini.taskmanager.operations.task.AddTaskOperation;
import com.stefanini.taskmanager.operations.user.CreateUserOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** Class for storing and executing all the given operations. */
public class OperationExecutor {
  private static final Logger logger = LogManager.getLogger(OperationExecutor.class);
  private final List<Operation> operations = new LinkedList<>();
  private static OperationExecutor instance = null;

  private OperationExecutor() {}

  public static OperationExecutor getInstance() {
    if (instance == null) {
      instance = new OperationExecutor();
    }
    return instance;
  }

  /**
   * Stores a given operation in the list.
   *
   * @param operation object of type Operation
   */
  public void addOperation(Operation operation) {
    operations.add(operation);
  }

  /** Executes all the existing operations in the list */
  public void executeOperations() {
    ThreadPoolExecutor threadPoolExecutor =
        new ThreadPoolExecutor(
            4, 4, 0, TimeUnit.SECONDS, new PriorityBlockingQueue<>(10, new OperationsComparator()));

    operations.forEach(
        operation -> {
          try {
            threadPoolExecutor.execute(operation);
          } catch (Exception e) {
            logger.error(e);
          }
        });
    operations.clear();
  }

  public void printOperations() {
    if (operations.size() == 0) {
      System.out.println("Empty!");
      return;
    }
    operations.forEach(
        operation -> {
          System.out.println(operation.getClass().getSimpleName());
        });
  }

  private static class OperationsComparator implements Comparator<Object> {
    @Override
    public int compare(Object o1, Object o2) {
      String firstName = o1.getClass().toString();
      String secondName = o2.getClass().toString();
      if (firstName.contains("Show") && secondName.contains("Show")) {
        return 0;
      }
      if (o1 instanceof AddTaskOperation && o2 instanceof CreateUserOperation) {
        AddTaskOperation operation1 = (AddTaskOperation) o1;
        CreateUserOperation operation2 = (CreateUserOperation) o2;
        if (operation1.getUser().getUserName().equals(operation2.getUser().getUserName())) {
          return -1;
        }
        return 0;
      }
      if (o1 instanceof AddTaskToGroupOperation && o2 instanceof CreateGroupOperation) {
        AddTaskToGroupOperation operation1 = (AddTaskToGroupOperation) o1;
        CreateGroupOperation operation2 = (CreateGroupOperation) o2;
        if (operation1.getGroup().getGroupName().equals(operation2.getGroup().getGroupName())) {
          return -1;
        }
        return 0;
      }
      if (o1 instanceof AddUserToGroupOperation && o2 instanceof CreateGroupOperation) {
        AddUserToGroupOperation operation1 = (AddUserToGroupOperation) o1;
        CreateGroupOperation operation2 = (CreateGroupOperation) o2;
        if (operation1.getGroup().getGroupName().equals(operation2.getGroup().getGroupName())) {
          return -1;
        }
        return 0;
      }
      return 0;
    }
  }
}
