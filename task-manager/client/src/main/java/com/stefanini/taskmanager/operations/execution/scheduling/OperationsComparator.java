package com.stefanini.taskmanager.operations.execution.scheduling;

import com.stefanini.taskmanager.operations.categories.group.AddTaskToGroupOperation;
import com.stefanini.taskmanager.operations.categories.group.AddUserToGroupOperation;
import com.stefanini.taskmanager.operations.categories.group.CreateGroupOperation;
import com.stefanini.taskmanager.operations.categories.task.AddTaskToUserOperation;
import com.stefanini.taskmanager.operations.categories.user.CreateUserOperation;

import java.util.Comparator;

/** Comparator used for setting the priority in the operations priority queue. */
public class OperationsComparator implements Comparator<Object> {
  @Override
  public int compare(Object o1, Object o2) {
    String firstName = o1.getClass().toString();
    String secondName = o2.getClass().toString();
    if (firstName.contains("Show") && secondName.contains("Show")) {
      return 0;
    }
    if (o1 instanceof AddTaskToUserOperation && o2 instanceof CreateUserOperation) {
      return 1;
    }
    if (o1 instanceof CreateUserOperation && o2 instanceof AddTaskToUserOperation) {
      return -1;
    }
    if (o1 instanceof AddUserToGroupOperation && o2 instanceof CreateUserOperation) {
      return 1;
    }
    if (o1 instanceof CreateUserOperation && o2 instanceof AddUserToGroupOperation) {
      return -1;
    }
    if (o1 instanceof AddTaskToGroupOperation && o2 instanceof CreateGroupOperation) {
      return 1;
    }
    if (o1 instanceof CreateGroupOperation && o2 instanceof AddTaskToGroupOperation) {
      return -1;
    }
    if (o1 instanceof AddUserToGroupOperation && o2 instanceof CreateGroupOperation) {
      return 1;
    }
    if (o1 instanceof CreateGroupOperation && o2 instanceof AddUserToGroupOperation) {
      return -1;
    }
    return 0;
  }
}
