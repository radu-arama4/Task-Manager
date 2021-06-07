package com.stefanini.taskmanager.persistence.entity.hibernate;

import com.stefanini.taskmanager.persistence.entity.Task;
import com.stefanini.taskmanager.service.proxy.email.Email;

import javax.persistence.*;

@Entity(name = "Task")
@Table(name = "task")
@Email
public class TaskHibernate implements Task {
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

  @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
  @JoinTable(
      name = "task_to_user",
      inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
      joinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "task_id")})
  private UserHibernate user;

  @ManyToOne(cascade = CascadeType.DETACH)
  @JoinTable(
      name = "task_to_group",
      inverseJoinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "group_id")},
      joinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "task_id")})
  private GroupHibernate group;

  public TaskHibernate() {}

  public TaskHibernate(String taskTitle, String description) {
    this.taskTitle = taskTitle;
    this.taskDescription = description;
  }

  public TaskHibernate(Long taskId, String taskTitle, String description) {
    this.taskId = taskId;
    this.taskTitle = taskTitle;
    this.taskDescription = description;
  }

  public GroupHibernate getGroup() {
    return group;
  }

  public void setGroup(GroupHibernate group) {
    this.group = group;
  }

  public UserHibernate getUser() {
    return user;
  }

  public void setUser(UserHibernate user) {
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
