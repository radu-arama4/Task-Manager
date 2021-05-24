package com.stefanini.taskmanager.operations;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * Class for storing and executing all the given operations.
 *
 */
public class OperationExecutor {
  private final List<Operation> textFileOperations = new LinkedList<>();

  /**
   *
   * Stores a given operation in the list.
   *
   * @param operation object of type Operation
   */
  public void addOperation(Operation operation) {
    textFileOperations.add(operation);
  }

  /**
   *
   * Executes all the existing operations in the list
   *
   */
  public void executeOperations() {
    textFileOperations.forEach(Operation::execute);
    textFileOperations.clear();
  }
}
