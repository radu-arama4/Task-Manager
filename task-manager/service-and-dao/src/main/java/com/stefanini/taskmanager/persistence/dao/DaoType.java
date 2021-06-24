package com.stefanini.taskmanager.persistence.dao;

/**
 * Enum for storing all the supported types of implementations for Dao.
 */
public enum DaoType {
    JDBC,
    HIBERNATE;
    public DaoType value(String value) {
        return valueOf(value);
    }
}
