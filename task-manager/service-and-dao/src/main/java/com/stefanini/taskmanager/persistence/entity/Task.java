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

  /*
    Currently there can be the same task id in both intermediate tables and this should be solved.
    TODO solve this
   */

  @ManyToOne(cascade = CascadeType.DETACH)
  @JoinTable(
      name = "task_to_user",
      inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
      joinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "task_id")})
  User user;

  @ManyToOne(cascade = CascadeType.DETACH)
  @JoinTable(
      name = "task_to_group",
      inverseJoinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "group_id")},
      joinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "task_id")})
  Group group;

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

  public Group getGroup() {
    return group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
