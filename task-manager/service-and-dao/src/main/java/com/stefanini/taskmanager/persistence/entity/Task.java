package com.stefanini.taskmanager.persistence.entity;

import javax.persistence.*;

@Entity
@Table(name = "task")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "task_id", unique = true, nullable = false)
  private Long taskId;

  @Column(name = "task_title", nullable = false)
  private String taskTitle;

  @Column(name = "task_description", nullable = false)
  private String taskDescription;

  public Task() {}

  public Task(String taskTitle, String description) {
    this.taskTitle = taskTitle;
    this.taskDescription = description;
  }

  public Task(Long taskId, String taskTitle, String description) {
    this.taskId = taskId;
    this.taskTitle = taskTitle;
    this.taskDescription = description;
  }

  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }

  public String getTaskTitle() {
    return taskTitle;
  }

  public String getDescription() {
    return taskDescription;
  }

  public String getTaskDescription() {
    return taskDescription;
  }

  public void setTaskTitle(String taskTitle) {
    this.taskTitle = taskTitle;
  }

  public void setTaskDescription(String taskDescription) {
    this.taskDescription = taskDescription;
  }
}
