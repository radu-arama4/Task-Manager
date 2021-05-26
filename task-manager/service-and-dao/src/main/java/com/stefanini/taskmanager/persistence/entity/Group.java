package com.stefanini.taskmanager.persistence.entity;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity(name = "group_")
public class Group {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "group_id", unique = true, nullable = false)
  private Long groupId;

  @Column(name = "group_name", nullable = false)
  private String groupName;

  @ManyToMany
  @JoinTable(
          name = "user_to_group",
          joinColumns = { @JoinColumn(name = "group_id", referencedColumnName = "group_id") },
          inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "user_id") }
  )
  private List<User> users = new LinkedList<>();

  @ManyToMany
  @JoinTable(
          name = "task_to_group",
          joinColumns = { @JoinColumn(name = "group_id", referencedColumnName = "group_id") },
          inverseJoinColumns = { @JoinColumn(name = "task_id", referencedColumnName = "task_id", unique = true) }
  )
  private List<Task> tasks = new LinkedList<>();

  public Group() {}

  public Group(String groupName) {
    this.groupName = groupName;
  }

  public Group(Long groupId, String groupName) {
    this.groupId = groupId;
    this.groupName = groupName;
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

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
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
