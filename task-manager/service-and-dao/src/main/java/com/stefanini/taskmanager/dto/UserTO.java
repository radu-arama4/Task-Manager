package com.stefanini.taskmanager.dto;

import com.stefanini.taskmanager.util.email.EmailField;
import com.stefanini.taskmanager.util.email.Email;

@Email(emailMessage = "User {firstName} / {lastName} identified by {userName} has been created")
public class UserTO {
  private Long userId;
  @EmailField(fieldName = "firstName")
  private String firstName;
  @EmailField(fieldName = "lastName")
  private String lastName;
  @EmailField(fieldName = "userName")
  private String userName;

  public UserTO() {
  }

  public UserTO(String userName) {
    this.userName = userName;
  }

  public UserTO(String firstName, String lastName, String userName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
  }

  public UserTO(Long id, String firstName, String lastName, String userName) {
    this.userId = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userName = userName;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public boolean equals(Object obj) {
    UserTO obj2 = (UserTO) obj;
    return obj2.getFirstName().equals(this.firstName)
        && obj2.getLastName().equals(this.lastName)
        && obj2.getUserName().equals(this.userName);
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + userId +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", userName='" + userName + '\'' +
            '}';
  }
}
