package com.stefanini.taskmanager.persistence.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "task")
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "task_id", unique = true, nullable = false)
  private Long taskId;

  @Column(name = "task_title")
  private String taskTitle;

  @Column(name = "task_description")
  private String taskDescription;

  @ManyToMany
  private List<User> user = new LinkedList<>();

  @ManyToMany
  private List<Group> group = new LinkedList<>();

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

  public List<User> getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user.add(user);
  }

  public List<Group> getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group.add(group);
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
