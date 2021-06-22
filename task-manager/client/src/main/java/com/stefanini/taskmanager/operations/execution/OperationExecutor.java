package com.stefanini.taskmanager.operations.execution;

import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.operations.execution.strategy.OperationExecutorStrategy;

import java.util.LinkedList;
import java.util.List;

/** Class for storing and executing all the given operations. */
public class OperationExecutor {
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
    OperationExecutorStrategy strategy = ExecutorFactory.createExecutor();
    strategy.executeOperations(operations);
    operations.clear();
  }

  /** Print all the currently stored commands. */
  public void printOperations() {
    if (operations.size() == 0) {
      System.out.println("Empty!");
      return;
    }
    operations.forEach(operation -> System.out.println(operation.getClass().getSimpleName()));
  }
}
