package com.stefanini.taskmanager.operations.execution;

import com.stefanini.taskmanager.operations.execution.strategy.OperationExecutorStrategy;
import com.stefanini.taskmanager.operations.execution.strategy.ParallelExecutor;
import com.stefanini.taskmanager.operations.execution.strategy.SequentialExecutor;
import com.stefanini.taskmanager.util.ApplicationProperties;

/**
 * Factory for providing an {@link OperationExecutorStrategy} based on the type specified in the
 * configuration.
 */
public abstract class ExecutorFactory {
  private static final String executorType = ApplicationProperties.getInstance().getExecutorType();

  public static OperationExecutorStrategy createExecutor() {
    switch (ExecutorType.valueOf(executorType)) {
      case SEQUENTIAL:
        return new SequentialExecutor();
      case PARALLEL:
        return new ParallelExecutor();
      default:
        throw new RuntimeException("Not supported operation executor!");
    }
  }
}
