package com.stefanini.taskmanager.operations.execution.strategy;

import com.stefanini.taskmanager.operations.Operation;

import java.util.List;

/** Used for executing the operations sequentially. */
public class SequentialExecutor implements OperationExecutorStrategy {
  @Override
  public void executeOperations(List<Operation> operations) {
    operations.forEach(Operation::execute);
  }
}
