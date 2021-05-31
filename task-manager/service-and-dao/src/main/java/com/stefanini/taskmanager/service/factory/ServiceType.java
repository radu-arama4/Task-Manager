package com.stefanini.taskmanager.service.factory;

/**
 * Enum for storing all the supported types of implementations for {@link ServiceFactory}.
 */
public enum ServiceType {
    STANDARD,
    PROXY;
    public ServiceType value(String value) {
        return valueOf(value);
    }
}
