package com.stefanini.taskmanager.persistence.entity.hibernate;

import com.stefanini.taskmanager.persistence.entity.Group;
import com.stefanini.taskmanager.service.proxy.email.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "group_")
@Table(name = "group_")
@Email
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class GroupHibernate implements Group {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "group_id", unique = true, nullable = false)
  private Long groupId;

  @Column(name = "group_name", nullable = false)
  private String groupName;

  @ManyToMany
  @JoinTable(
      name = "user_to_group",
      joinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "group_id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
      uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "user_id"})
  )
  @ToString.Exclude private Set<UserHibernate> users = new HashSet<>();

  @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
  @ToString.Exclude private Set<TaskHibernate> tasks = new HashSet<>();

  public GroupHibernate() {}

  public GroupHibernate(String groupName) {
    this.groupName = groupName;
  }

  public GroupHibernate(Long groupId, String groupName) {
    this.groupId = groupId;
    this.groupName = groupName;
  }

  public void addTask(TaskHibernate task) {
    this.tasks.add(task);
  }

  public void addUser(UserHibernate user) {
    this.users.add(user);
  }


}
