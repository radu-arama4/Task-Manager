package com.stefanini.taskmanager.operations.execution.strategy;

import com.stefanini.taskmanager.operations.Operation;
import com.stefanini.taskmanager.operations.execution.scheduling.OperationKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.stefanini.taskmanager.operations.execution.scheduling.OperationScheduleUtil.divideOperations;

/** Used for executing the operations in parallel by using a thread pool. */
public class ParallelExecutor implements OperationExecutorStrategy {
  private static final Logger logger = LogManager.getLogger(ParallelExecutor.class);

  @Override
  public void executeOperations(List<Operation> operations) {
    ExecutorService service = Executors.newFixedThreadPool(4);
    Map<OperationKey, PriorityQueue<Operation>> dividedOperations = divideOperations(operations);

    dividedOperations.forEach(
        (operationKey, queueOfOperations) -> {
          try {
            service.execute(
                () -> {
                  SequentialExecutor sequential = new SequentialExecutor();
                  sequential.executeOperations(new LinkedList<>(queueOfOperations));
                });
          } catch (Exception e) {
            logger.error(e);
          }
        });
  }
}

