package com.stefanini.taskmanager.persistence.util.context;

/** Interface which provides methods for performing transaction operations. */
public interface TransactionContext {
  void begin();

  void commit();

  void rollback();
}
