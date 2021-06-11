package com.stefanini.taskmanager.service.exceptions;

/** Exception for notifying about unsupported service type. */
public class UnsupportedServiceTypeException extends RuntimeException {
  public UnsupportedServiceTypeException() {
    super("Undefined service type!");
  }
}
