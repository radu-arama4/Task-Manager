package com.stefanini.taskmanager.persistence.util.exceptions;

/** Exception for notifying about unsupported dao type. */
public class UnsupportedDaoTypeException extends RuntimeException {
  public UnsupportedDaoTypeException() {
    super("Undefined dao type!");
  }
}
