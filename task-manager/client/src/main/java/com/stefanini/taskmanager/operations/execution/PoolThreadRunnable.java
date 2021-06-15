package com.stefanini.taskmanager.operations.execution;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class PoolThreadRunnable implements Runnable {
  private static final Logger logger = LogManager.getLogger(PoolThreadRunnable.class);

  private Thread thread = null;
  private final BlockingQueue taskQueue;
  private boolean isStopped = false;

  public PoolThreadRunnable(BlockingQueue queue) {
    taskQueue = queue;
  }

  public void run() {
    this.thread = Thread.currentThread();
    while (!isStopped()) {
      try {
        Runnable runnable = (Runnable) taskQueue.take();
        runnable.run();
      } catch (Exception e) {
        logger.debug(e);
      }
    }
  }

  public synchronized void doStop() {
    isStopped = true;
    this.thread.interrupt();
  }

  public synchronized boolean isStopped() {
    return isStopped;
  }
}
