package com.stefanini.taskmanager.service;

/**
 * Enum for storing all the supported types of implementations for Service.
 */
public enum ServiceType {
    STANDARD,
    PROXY;
    public ServiceType value(String value) {
        return valueOf(value);
    }
}
