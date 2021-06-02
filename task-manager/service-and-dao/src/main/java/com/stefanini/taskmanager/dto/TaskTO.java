package com.stefanini.taskmanager.dto;

import com.stefanini.taskmanager.util.email.EmailField;
import com.stefanini.taskmanager.util.email.Email;

@Email(emailMessage = "Task {task title} {task description} has been assigned to {username}")
public class TaskTO {
  private Long id;
  @EmailField(fieldName = "taskTitle")
  private String taskTitle;
  @EmailField(fieldName = "taskDescription")
  private String taskDescription;

  public TaskTO() {
  }

  public TaskTO(String taskTitle, String description) {
    this.taskTitle = taskTitle;
    this.taskDescription = description;
  }

  public TaskTO(Long id, String taskTitle, String description) {
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

  public void setTaskTitle(String taskTitle) {
    this.taskTitle = taskTitle;
  }

  public String getTaskDescription() {
    return taskDescription;
  }

  public void setTaskDescription(String taskDescription) {
    this.taskDescription = taskDescription;
  }

  public String getTaskTitle() {
    return taskTitle;
  }

  public String getDescription() {
    return taskDescription;
  }

  @Override
  public boolean equals(Object obj) {
    TaskTO obj2 = (TaskTO) obj;
    return obj2.getDescription().equals(this.taskDescription)
        && obj2.getTaskTitle().equals(this.taskTitle);
  }

  @Override
  public String toString() {
    return "Task{" +
            "id=" + id +
            ", taskTitle='" + taskTitle + '\'' +
            ", taskDescription='" + taskDescription + '\'' +
            '}';
  }
}
