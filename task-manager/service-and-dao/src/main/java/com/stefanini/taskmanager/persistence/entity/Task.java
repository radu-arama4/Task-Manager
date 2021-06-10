package com.stefanini.taskmanager.persistence.entity;

/**
 * Interface that represents the Task entity. Contains the getters and setters of the contained
 * fields.
 */
public interface Task {
  Long getTaskId();

  String getTaskTitle();

  String getTaskDescription();

  void setTaskId(Long taskId);

  void setTaskTitle(String taskTitle);

  void setTaskDescription(String taskDescription);
}
