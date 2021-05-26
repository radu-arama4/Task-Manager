package com.stefanini.taskmanager.persistence.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", unique = true, nullable = false)
  private Long userId;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "username", unique = true, nullable = false)
  private String userName;

  @ManyToMany
  @JoinTable(
          name = "task_to_user",
          joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "user_id") },
          inverseJoinColumns = { @JoinColumn(name = "task_id", referencedColumnName = "task_id", unique = true) }
  )
  private List<Task> tasks = new LinkedList<>();

  public User() {}

  public User(String firstName, String lastName, String userName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
  }

  public User(Long userId, String firstName, String lastName, String userName) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
  }

  public void addTask(Task task) {
    this.tasks.add(task);
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getUserName() {
    return userName;
  }

}
