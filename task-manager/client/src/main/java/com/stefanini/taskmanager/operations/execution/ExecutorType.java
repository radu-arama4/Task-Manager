package com.stefanini.taskmanager.operations.execution;

import com.stefanini.taskmanager.operations.execution.strategy.OperationExecutorStrategy;

/** Enum for storing all the supported types of implementations for {@link OperationExecutorStrategy}. */
public enum ExecutorType {
  SEQUENTIAL,
  PARALLEL;
  public ExecutorType value(String value) {
    return valueOf(value);
  }
}
