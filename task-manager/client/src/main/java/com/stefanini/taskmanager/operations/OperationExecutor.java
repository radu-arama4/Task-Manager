package com.stefanini.taskmanager.operations;

import com.stefanini.taskmanager.operations.group.AddTaskToGroupOperation;
import com.stefanini.taskmanager.operations.group.AddUserToGroupOperation;
import com.stefanini.taskmanager.operations.group.CreateGroupOperation;
import com.stefanini.taskmanager.operations.multiple.CreateUserWithTasks;
import com.stefanini.taskmanager.operations.task.AddTaskToUserOperation;
import com.stefanini.taskmanager.operations.task.ShowTasksOfUserOperation;
import com.stefanini.taskmanager.operations.user.CreateUserOperation;
import com.stefanini.taskmanager.operations.user.ShowAllUsersOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    OperationExecutorStrategy strategy = new Parallel();
    strategy.executeOperations(operations);
    operations.clear();
  }

  interface OperationExecutorStrategy {
    void executeOperations(List<Operation> operations);
  }

  static class Sequential implements OperationExecutorStrategy {
    @Override
    public void executeOperations(List<Operation> operations) {
      operations.forEach(Operation::execute);
    }
  }

  static class Parallel implements OperationExecutorStrategy {
    @Override
    public void executeOperations(List<Operation> operations) {
      ExecutorService service = Executors.newFixedThreadPool(4);
      Map<OperationKey, PriorityQueue<Operation>> dividedOperations = divideOperations(operations);

      dividedOperations.forEach(
          (operationKey, operations1) -> {
            try {
              service.execute(
                  () -> {
                    List<Operation> listOfOperations = new LinkedList<>(operations1);
                    Sequential sequential = new Sequential();
                    sequential.executeOperations(listOfOperations);
                  });
            } catch (Exception e) {
              logger.error(e);
            }
          });
    }
  }

  public void printOperations() {
    if (operations.size() == 0) {
      System.out.println("Empty!");
      return;
    }
    operations.forEach(operation -> System.out.println(operation.getClass().getSimpleName()));
  }

  static class OperationKey {
    private final String name;
    private final String type;

    public OperationKey(String name, String type) {
      this.name = name;
      this.type = type;
    }

    public String getName() {
      return name;
    }

    public String getType() {
      return type;
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof OperationKey)) {
        return false;
      }
      OperationKey operationKey1 = this;
      OperationKey operationKey2 = (OperationKey) obj;
      return operationKey1.getName().equals(operationKey2.getName())
          && operationKey1.getType().equals(operationKey2.getType());
    }

    @Override
    public int hashCode() {
      return Objects.hash(name, type);
    }
  }

  public static Map<OperationKey, PriorityQueue<Operation>> divideOperations(
      List<Operation> operations) {
    Map<OperationKey, PriorityQueue<Operation>> dividedOperations = new HashMap<>();
    PriorityQueue<Operation> printOperations = new PriorityQueue<>(new OperationsComparator());
    dividedOperations.put(new OperationKey("", "printAllOperations"), printOperations);

    List<String> userNames = new LinkedList<>();
    List<String> groupNames = new LinkedList<>();

    operations.forEach(
        operation -> {
          if (operation.getClass().toString().contains("User")) {
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
              ShowTasksOfUserOperation showTasksOfUserOperation =
                  (ShowTasksOfUserOperation) operation;
              userName = showTasksOfUserOperation.getUser().getUserName();
            } else if (operation instanceof ShowAllUsersOperation) {
              printOperations.add(operation);
              return;
            }

            if (userNames.contains(userName)) {
              PriorityQueue<Operation> foundQueue =
                  dividedOperations.get(new OperationKey(userName, "User"));
              foundQueue.add(operation);
            } else {
              userNames.add(userName);
              PriorityQueue<Operation> newQueue =
                  new PriorityQueue<>(12, new OperationsComparator());
              newQueue.add(operation);
              dividedOperations.put(new OperationKey(userName, "User"), newQueue);
            }
          } else if (operation.getClass().toString().contains("Group")) {
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

            if (groupNames.contains(groupName)) {
              PriorityQueue<Operation> foundQueue =
                  dividedOperations.get(new OperationKey(groupName, "Group"));
              foundQueue.add(operation);
            } else {
              groupNames.add(groupName);
              PriorityQueue<Operation> newQueue =
                  new PriorityQueue<>(12, new OperationsComparator());
              newQueue.add(operation);
              dividedOperations.put(new OperationKey(groupName, "Group"), newQueue);
            }
          }
        });

    return dividedOperations;
  }

  private static class OperationsComparator implements Comparator<Object> {
    @Override
    public int compare(Object o1, Object o2) {
      String firstName = o1.getClass().toString();
      String secondName = o2.getClass().toString();
      if (firstName.contains("Show") && secondName.contains("Show")) {
        return 0;
      }
      if (o1 instanceof AddTaskToUserOperation && o2 instanceof CreateUserOperation) {
        return 1;
      }
      if (o1 instanceof CreateUserOperation && o2 instanceof AddTaskToUserOperation) {
        return -1;
      }
      if (o1 instanceof AddUserToGroupOperation && o2 instanceof CreateUserOperation) {
        return 1;
      }
      if (o1 instanceof CreateUserOperation && o2 instanceof AddUserToGroupOperation) {
        return -1;
      }
      if (o1 instanceof AddTaskToGroupOperation && o2 instanceof CreateGroupOperation) {
        return 1;
      }
      if (o1 instanceof CreateGroupOperation && o2 instanceof AddTaskToGroupOperation) {
        return -1;
      }
      if (o1 instanceof AddUserToGroupOperation && o2 instanceof CreateGroupOperation) {
        return 1;
      }
      if (o1 instanceof CreateGroupOperation && o2 instanceof AddUserToGroupOperation) {
        return -1;
      }
      return 0;
    }
  }
}
