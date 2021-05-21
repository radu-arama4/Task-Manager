package com.stefanini.taskmanager.persistence.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long user_id;
  @Column
  private String firstName;
  @Column
  private String lastName;
  @Column
  private String userName;
  @ManyToMany(mappedBy = "user")
  private List<Task> tasks = new LinkedList<>();
  @ManyToMany
  private List<Group> groups = new LinkedList<>();

  public User() {

  }

  public User(String firstName, String lastName, String userName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
  }

  public User(Long user_id, String firstName, String lastName, String userName) {
    this.user_id = user_id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
  }

  public void addTask(Task task){
    this.tasks.add(task);
    task.setUser(this);
  }

  public void addGroup(Group group){
    this.groups.add(group);
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
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

  public List<Group> getGroups() {
    return groups;
  }

  public void setGroups(List<Group> groups) {
    this.groups = groups;
  }
}
