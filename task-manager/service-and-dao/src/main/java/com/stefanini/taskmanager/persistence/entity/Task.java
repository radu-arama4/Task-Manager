package com.stefanini.taskmanager.persistence.entity;

import com.stefanini.taskmanager.service.proxy.email.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity(name = "Task")
@Table(name = "task")
@Email
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Task{
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
  @ToString.Exclude private User user;

  @ManyToOne(cascade = CascadeType.DETACH)
  @JoinTable(
      name = "task_to_group",
      inverseJoinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "group_id")},
      joinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "task_id")})
  @ToString.Exclude private Group group;

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
}
