package com.stefanini.taskmanager.operations.execution.strategy;

import com.stefanini.taskmanager.operations.Operation;

import java.util.List;

/** Interface for the different types of operation executors. */
public interface OperationExecutorStrategy {
  /**
   * Method which triggers the process of executing the operations.
   *
   * @param operations a given list of {@link Operation}s
   */
  void executeOperations(List<Operation> operations);
}
