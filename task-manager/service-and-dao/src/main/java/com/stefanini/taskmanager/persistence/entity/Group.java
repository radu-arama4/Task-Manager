package com.stefanini.taskmanager.persistence.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "group_")
public class Group {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "group_id", unique = true, nullable = false)
  private Long groupId;

  @Column(name = "group_name", nullable = false)
  private String groupName;

  @ManyToMany(cascade = CascadeType.DETACH)
  @JoinTable(
          name = "user_to_group",
          joinColumns = { @JoinColumn(name = "group_id", referencedColumnName = "group_id") },
          inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "user_id") }
  )
  private Set<User> users = new HashSet<>();

  @ManyToMany(cascade = CascadeType.REMOVE)
  @JoinTable(
          name = "task_to_group",
          joinColumns = { @JoinColumn(name = "group_id", referencedColumnName = "group_id") },
          inverseJoinColumns = { @JoinColumn(name = "task_id", referencedColumnName = "task_id", unique = true) }
  )
  private Set<Task> tasks = new HashSet<>();

  public Group() {}

  public Group(String groupName) {
    this.groupName = groupName;
  }

  public Group(Long groupId, String groupName) {
    this.groupId = groupId;
    this.groupName = groupName;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public Set<Task> getTasks() {
    return tasks;
  }

  public void setTasks(Set<Task> tasks) {
    this.tasks = tasks;
  }

  public void addTask(Task task) {
    this.tasks.add(task);
  }

  public void addUser(User user) {
    this.users.add(user);
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public Long getGroupId() {
    return groupId;
  }

  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  public String getGroupName() {
    return groupName;
  }
}
