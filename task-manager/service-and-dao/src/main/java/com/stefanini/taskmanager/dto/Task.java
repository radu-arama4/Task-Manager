package com.stefanini.taskmanager.dto;

public class Task {

  private Long id;
  private final String taskTitle;
  private final String taskDescription;

  public Task(String taskTitle, String description) {
    this.taskTitle = taskTitle;
    this.taskDescription = description;
  }

  public Task(Long id, String taskTitle, String description) {
    this.id = id;
    this.taskTitle = taskTitle;
    this.taskDescription = description;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTaskTitle() {
    return taskTitle;
  }

  public String getDescription() {
    return taskDescription;
  }

  @Override
  public boolean equals(Object obj) {
    Task obj2 = (Task) obj;

    return obj2.getDescription().equals(this.taskDescription)
        && obj2.getTaskTitle().equals(this.taskTitle);
  }
}
