package com.stefanini.taskmanager.dto;

import java.util.ArrayList;
import java.util.List;

public class User {

  private String firstName;
  private String lastName;
  private String userName;

  private List<Task> tasks = null;

  public User(String firstName, String lastName, String userName) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
    tasks = new ArrayList<>();
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

}
