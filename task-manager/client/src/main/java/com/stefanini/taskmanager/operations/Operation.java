package com.stefanini.taskmanager.operations;

/**
 * Interface that provides the execute method for different types of operations.
 *
 * @author rarama
 */
public interface Operation extends Runnable {
  /** Method user for starting executing a given operation. */
  void execute();

  @Override
  default void run() {
    this.execute();
  }
}
