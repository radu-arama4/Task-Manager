package com.stefanini.taskmanager.dto;

public class User {

  private String firstName;
  private String lastName;
  private String userName;

  public User(String firstName, String lastName, String userName) {
    this.firstName = firstName;
    this.lastName = lastName;
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
