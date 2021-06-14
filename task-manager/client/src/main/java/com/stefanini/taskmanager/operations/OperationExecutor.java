package com.stefanini.taskmanager.operations;

import java.util.LinkedList;
import java.util.List;

/** Class for storing and executing all the given operations. */
public class OperationExecutor {
  private final List<Operation> textFileOperations = new LinkedList<>();
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
    textFileOperations.add(operation);
  }

  /** Executes all the existing operations in the list */
  public void executeOperations() {
    //    textFileOperations.forEach(
    //        operation -> {
    //          Thread executionThread = new Thread(operation);
    //          try {
    //            executionThread.start();
    //            executionThread.join();
    //          } catch (InterruptedException e) {
    //            e.printStackTrace();
    //          }
    //        });
    textFileOperations.forEach(Operation::execute);
    textFileOperations.clear();
  }

  public void printOperations() {
    if (textFileOperations.size() == 0) {
      System.out.println("Empty!");
      return;
    }
    textFileOperations.forEach(
        operation -> {
          System.out.println(operation.getClass().getSimpleName());
        });
  }
}
