package com.stefanini.taskmanager.operations.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadPool {
  private final BlockingQueue taskQueue;
  private final List<PoolThreadRunnable> runnables = new ArrayList<>();
  private boolean isStopped = false;

  public ThreadPool(int noOfThreads, int maxNoOfTasks) {
    taskQueue = new ArrayBlockingQueue(maxNoOfTasks);

    for (int i = 0; i < noOfThreads; i++) {
      PoolThreadRunnable poolThreadRunnable = new PoolThreadRunnable(taskQueue);
      runnables.add(poolThreadRunnable);
    }
    for (PoolThreadRunnable runnable : runnables) {
      new Thread(runnable).start();
    }
  }

  public synchronized void execute(Runnable task) throws Exception {
    if (this.isStopped) throw new IllegalStateException("ThreadPool is stopped");
    this.taskQueue.offer(task);
  }

  public synchronized void stop() {
    this.isStopped = true;
    for (PoolThreadRunnable runnable : runnables) {
      runnable.doStop();
    }
  }
}