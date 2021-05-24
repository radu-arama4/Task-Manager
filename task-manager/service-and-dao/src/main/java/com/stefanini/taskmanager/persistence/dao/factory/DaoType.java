package com.stefanini.taskmanager.persistence.dao.factory;

/**
 * Enum for storing all the supported types of implementations for {@link DaoFactory}.
 */
public enum DaoType {
    JDBC,
    HIBERNATE;
    public DaoType value(String value) {
        return valueOf(value);
    }
}
