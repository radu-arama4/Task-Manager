package com.stefanini.taskmanager.operations.execution.scheduling;

import java.util.Objects;

/**
 * Used for mapping between the name of the entities and the queue of operations related to the
 * specified entity.
 */
public class OperationKey {
  private final String name;
  private final String type;

  public OperationKey(String name, String type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof OperationKey)) {
      return false;
    }
    OperationKey operationKey1 = this;
    OperationKey operationKey2 = (OperationKey) obj;
    return operationKey1.getName().equals(operationKey2.getName())
        && operationKey1.getType().equals(operationKey2.getType());
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type);
  }
}
