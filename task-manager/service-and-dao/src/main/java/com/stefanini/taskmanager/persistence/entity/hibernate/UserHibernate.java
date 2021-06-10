package com.stefanini.taskmanager.persistence.entity.hibernate;

import com.stefanini.taskmanager.persistence.entity.User;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "User")
@Table(name = "user")
public class UserHibernate implements User {
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

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<TaskHibernate> tasks = new HashSet<>();

  @ManyToMany(mappedBy = "users", cascade = CascadeType.DETACH)
  private Set<GroupHibernate> groups = new HashSet<>();

  public UserHibernate() {}

  public UserHibernate(String firstName, String lastName, String userName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
  }

  public UserHibernate(Long userId, String firstName, String lastName, String userName) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
  }
}
