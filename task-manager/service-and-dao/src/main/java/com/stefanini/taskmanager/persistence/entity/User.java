package com.stefanini.taskmanager.persistence.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column
  private String firstName;
  @Column
  private String lastName;
  @Column
  private String userName;
  @ManyToMany(mappedBy = "user")
  private List<Task> tasks = new ArrayList<>();
  @ManyToMany(mappedBy = "users")
  private List<Group> groups = new ArrayList<>();

  public User() {

  }

  public User(String firstName, String lastName, String userName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
  }

  public User(Long id, String firstName, String lastName, String userName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  public List<Group> getGroups() {
    return groups;
  }

  public void setGroups(List<Group> groups) {
    this.groups = groups;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
