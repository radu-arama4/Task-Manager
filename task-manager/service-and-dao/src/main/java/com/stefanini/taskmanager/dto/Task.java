package com.stefanini.taskmanager.dto;

public class Task {

  private String taskTitle;
  private String description;

  public Task(String taskTitle, String description) {
    this.taskTitle = taskTitle;
    this.description = description;
  }

  public String getTaskTitle() {
    return taskTitle;
  }

  public String getDescription() {
    return description;
  }
}