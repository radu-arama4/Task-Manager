package com.stefanini.taskmanager.persistence.util.context;

public interface TransactionContext {
    void begin();
    void commit();
    void rollback();
}
