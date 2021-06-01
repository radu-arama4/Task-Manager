package com.stefanini.taskmanager.dto;

public class User {

  private Long id;
  private String firstName;
  private String lastName;
  private String userName;

  public User() {
  }

  public User(String userName) {
    this.userName = userName;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  @Override
  public boolean equals(Object obj) {
    User obj2 = (User) obj;
    return obj2.getFirstName().equals(this.firstName)
        && obj2.getLastName().equals(this.lastName)
        && obj2.getUserName().equals(this.userName);
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", userName='" + userName + '\'' +
            '}';
  }
}
